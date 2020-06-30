/*
 *****************************************************************************
 *                       Confidentiality Information                         *
 *                                                                           *
 * This module is the confidential and proprietary information of            *
 * DBSentry Corp.; it is not to be copied, reproduced, or transmitted in any *
 * form, by any means, in whole or in part, nor is it to be used for any     *
 * purpose other than that for which it is expressly provided without the    *
 * written permission of DBSentry Corp.                                      *
 *                                                                           *
 * Copyright (c) 2004-2005 DBSentry Corp.  All Rights Reserved.              *
 *                                                                           *
 *****************************************************************************
 * $Id: UserEditAction.java,v 1.2 2005/11/25 14:08:22 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.user; 

/**
 *	Purpose: To save user_edit.jsp with the specified user data.
 *  @author              Mishra Maneesh 
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
import rts.beans.*;
import rts.web.actionforms.user.*;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;

//Java API
import java.io.*;
import java.util.*;

//Servlet API
import javax.servlet.*;
import javax.servlet.http.*;

//Struts API
import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.apache.struts.validator.*;

public class UserEditAction extends Action {
    public static final int SYSTEM_ADMIN=1;
    public static final int ADMIN=2;    
    public static final int NON_ADMIN_CAN_CHANGE_PASSWORD=3;    
//    public static final int NON_ADMIN_CANT_CHANGE_PASSWORD=4;    
    public static final String QUOTA_LIMITED="1";  
    DbsLibrarySession dbsLibrarySession = null;
    public static final String PROP_BUNDLE_ACL_NAME="Public";
    //Initialize logger
    Logger logger = Logger.getLogger("DbsLogger");
    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Locale locale = getLocale(request);
        UserInfo userInfo = null;
        HttpSession httpSession = null;

        DbsDirectoryUser userToEdit=null;
        DbsPrimaryUserProfile userProf=null;
        DbsTransaction editTransaction=null;
        DbsExtendedUserProfile emailProfile=null;
        String userName=null;
        DbsValueDefault userValueDefault=null;
        DbsAccessControlList homeFolderAcl=null;
        DbsAccessControlList mailFolderAcl=null;
        DbsAccessControlList inboxAcl=null;
        String inboxName=null;
        DbsFileSystem fileSystem=null;
        UserNewEditForm userNewEditForm=null;
        String isQuotaLimited="";
        String workFlowAclName=null;        
        String workFlowAclDefault="WORKFLOW_NOT_ENABLED";
        // Validate the request parameters specified by the user
        ActionErrors errors = new ActionErrors();  
    
        try{
            httpSession = request.getSession(false);      
            userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
            UserNewEditForm userNewEditFormOld=(UserNewEditForm)httpSession.getAttribute("userNewEditFormOld");
            userNewEditForm= (UserNewEditForm)form;
            dbsLibrarySession = userInfo.getDbsLibrarySession(); 
            userName=((String)PropertyUtils.getSimpleProperty(form, "txtUserName")).trim();      
            logger.info("Saving data for edit user : " + userName);            
            logger.debug("Initializing Variable ...");        
            String password=((String)PropertyUtils.getSimpleProperty(form, "txtPassword")).trim();
           
            String description=(String)PropertyUtils.getSimpleProperty(form,"txaDescription"); 
            String emailAddress=((String)PropertyUtils.getSimpleProperty(form,"txtEmailAddress")).trim(); 
            userToEdit=(DbsDirectoryUser)SearchUtil.findObject(dbsLibrarySession,DbsDirectoryUser.CLASS_NAME,userName);
            userProf=userToEdit.getPrimaryUserProfile();
            editTransaction=dbsLibrarySession.beginTransaction();
            logger.info("Transaction started");
            if(!password.equals("PasswordNotChanged")){
                Vector passwordParams = new Vector();
                passwordParams.addElement(userToEdit.getCredentialManager());
                passwordParams.addElement(userToEdit.getDistinguishedName());
                passwordParams.addElement(password);
                passwordParams.addElement(null);		
                dbsLibrarySession.invokeServerMethod("DYNCredentialManagerSetPassword", passwordParams);
            }
            emailProfile=SearchUtil.getEmailUserProfile(dbsLibrarySession,userToEdit) ;   
            
                userToEdit.setAttribute(DbsDirectoryUser.DESCRIPTION_ATTRIBUTE,DbsAttributeValue.newAttributeValue(description));            
             
            if((!emailAddress.equals(userNewEditFormOld.getTxtEmailAddress())) && emailAddress!=null && (!emailAddress.equals(""))) {
                if(emailProfile !=null){  
                    try{
                        emailProfile.setAttribute(DbsUserManager.EMAIL_ADDRESS,DbsAttributeValue.newAttributeValue(emailAddress));
                    }catch(DbsException dbsExcep){
                        if(dbsExcep.containsErrorCode(30010)){                          
                            ActionError editError=new ActionError("errors.email.unique.30010",emailAddress);
                            errors.add(ActionErrors.GLOBAL_ERROR,editError);
                        }else
                        {
                            throw dbsExcep;
                        }
                    }
                }
            }
           userToEdit.putProperty("RTSUSER",DbsAttributeValue.newAttributeValue("RTS user Property Bundle set,else this user cannot set it's user preferences"));

            if(userToEdit.getPropertyBundle()!=null){
                DbsPropertyBundle defaultBundle=userToEdit.getPropertyBundle();
                DbsAccessControlList propBundleAcl=(DbsAccessControlList)SearchUtil.findObject(dbsLibrarySession,DbsAccessControlList.CLASS_NAME,PROP_BUNDLE_ACL_NAME);
                 //defaultBundle.setAcl(propBundleAcl);
                 //System.out.println(propBundleAcl.getAcl().getName());
                userProf.setDefaultAcls(defaultBundle);
                logger.debug("default Acl set to :"+userProf.getDefaultAcls().getDbsPropertyBundle().getName());
            }else{
              logger.debug("default Acl not set");
            }
           
            dbsLibrarySession.completeTransaction(editTransaction);
            logger.debug("userNewEditForm : " + userNewEditForm);
            logger.info("Data saved for user : " + userName);
            logger.info("Transaction completed");
            editTransaction=null;
        }catch(DbsException dbsException){
//            logger.info(dbsException.getErrorMessage());
            ActionError editError=new ActionError("errors.catchall",dbsException.getErrorMessage());
            errors.add(ActionErrors.GLOBAL_ERROR,editError);
        }catch(Exception exception){
//            logger.info(exception);
            ActionError editError=new ActionError("errors.catchall",exception);
            errors.add(ActionErrors.GLOBAL_ERROR,editError);
        }finally{
            try{
                if(editTransaction!=null){
                    dbsLibrarySession.abortTransaction(editTransaction);                 
                    logger.info("Transaction Aborted");                    
                }       
            }catch(DbsException nestedException){
              logger.info(nestedException.getErrorMessage());
            }  
        }//end finally
        if (!errors.isEmpty()) {
          logger.info("User not saved");
          saveErrors(request, errors);
          return (mapping.getInputForward());
        }
        ActionMessages messages = new ActionMessages();
        ActionMessage msg = new ActionMessage("user.edit.ok",userName);
        messages.add("message1", msg);
        httpSession.setAttribute("messages",messages);
        return mapping.findForward("success");
    }
}
