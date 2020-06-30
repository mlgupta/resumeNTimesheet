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
 * $Id: UserB4EditAction.java,v 1.1 2005/08/23 10:54:07 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.user; 

/**
 *	Purpose: To populate user_edit.jsp with the specified user data.
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

public class UserB4EditAction extends Action {
    DbsLibrarySession dbsLibrarySession = null;
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
        String[] credentialManager=null; 
        DbsDirectoryUser userToEdit=null;       
        // Validate the request parameters specified by the user
        ActionErrors errors = new ActionErrors();
        ArrayList memberList=new ArrayList();
        UserNewEditForm userNewEditForm=new UserNewEditForm();
        try{
            logger.debug("Initializing Variable ...");        
            httpSession = request.getSession(false);      
            userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
            dbsLibrarySession = userInfo.getDbsLibrarySession();    
            String userName= (String)httpSession.getAttribute("radSelect");
            logger.info("Fetching data for user : " + userName);
            userToEdit=(DbsDirectoryUser)SearchUtil.findObject(dbsLibrarySession,DbsDirectoryUser.CLASS_NAME,userName);
            logger.debug(userToEdit);
            if(userToEdit!=null){
                DbsPrimaryUserProfile userToEditProf=userToEdit.getPrimaryUserProfile();
                credentialManager=new String[1];
                credentialManager[0]=userToEdit.getCredentialManager();
                userNewEditForm.setTxtUserName(userName);                 
                userNewEditForm.setTxaDescription(userToEdit.getAttribute(DbsDirectoryUser.DESCRIPTION_ATTRIBUTE).toString());            
                
               
               
             
               
                
//                System.out.println(defaultAcls);
   
                DbsExtendedUserProfile emailProfile=SearchUtil.getEmailUserProfile(dbsLibrarySession,userToEdit);
                if(emailProfile!=null){
                    DbsAttributeValue mailAttr=emailProfile.getAttribute("EMAILADDRESS");
                    if(mailAttr!=null){  
                        userNewEditForm.setTxtEmailAddress(mailAttr.getString(dbsLibrarySession));
                    }                   
                    
                }                
                
                logger.debug("userNewEditForm : " + userNewEditForm);
                logger.info("Fetched data for user : " + userName);
                request.setAttribute("userNewEditForm",userNewEditForm);
                httpSession.setAttribute("userNewEditFormOld",userNewEditForm);                
            }else{
                ActionError editError=new ActionError("errors.user.notfound",userName);
                errors.add(ActionErrors.GLOBAL_ERROR,editError);            
                httpSession.setAttribute("errors",errors);
                return mapping.findForward("failure");
            }
        }catch(DbsException dbsException){
            ActionError editError=new ActionError("errors.catchall",dbsException.getErrorMessage());
            errors.add(ActionErrors.GLOBAL_ERROR,editError);
        }catch(Exception exception){
            ActionError editError=new ActionError("errors.catchall",exception);
            errors.add(ActionErrors.GLOBAL_ERROR,editError);
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            //return (mapping.getInputForward());
        }
        logger.debug("Exiting userB4EditAction now...");
        return mapping.findForward("success");
    }
}