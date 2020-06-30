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
 * $Id: UserDeleteAction.java,v 1.1 2005/08/23 10:54:07 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.user; 

/**
 *	Purpose: To delete the user selected from user_list.jsp
 *  @author              Mishra Maneesh 
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.user.*;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;
import rts.web.beans.crypto.CryptographicUtil;
//Java API
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
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

public class UserDeleteAction extends Action {

    DbsLibrarySession dbsLibrarySession = null;
    DbsTransaction deleteTransaction=null;
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
        DbsDirectoryUser userToDelete=null;  
        String userName=null;
        boolean cmsdkUserDeleted = false;    /* boolean for deleting cmsdk user */
        boolean dbUserDeleted = false;       /* boolean for deleting db user */
        CryptographicUtil cryptUtil=new CryptographicUtil();
        
        // Validate the request parameters specified by the user
        ActionErrors errors = new ActionErrors();  
    
        try{
            httpSession = request.getSession(false);
            userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
            UserNewEditForm userNewEditFormOld=(UserNewEditForm)httpSession.getAttribute("userNewEditFormOld");
            dbsLibrarySession = userInfo.getDbsLibrarySession(); 
            userName= (String)httpSession.getAttribute("radSelect");
            logger.info("Deleting user : " + userName);
            logger.debug("Initializing Variable ...");        
            httpSession.removeAttribute("radSelect");

            
            DbsUserManager dbsManager = new DbsUserManager(dbsLibrarySession);      
            Hashtable deleteOptions = new Hashtable();
            // Override the default to free the user's home folder and contents
            deleteOptions.put(DbsUserManager.FREE_HOME_FOLDER,DbsAttributeValue.newAttributeValue(true));
            //System.out.println(DbsAttributeValue.newAttributeValue(true));         
            // Override the default to free the credential manager user
            deleteOptions.put(DbsUserManager.FREE_CREDENTIAL_MANAGER_USER,DbsAttributeValue.newAttributeValue(true));
                
            // Override the default to change the owner to system for 
            // PublicObjects owned by this user
            deleteOptions.put(DbsUserManager.CHANGE_OWNER, DbsAttributeValue.newAttributeValue(true));
            deleteOptions.put(DbsUserManager.NEW_OWNER_USER_NAME,DbsAttributeValue.newAttributeValue(dbsLibrarySession.getUser().getName()));
            // Delete a user
            logger.info("Tansaction started");
            deleteTransaction=dbsLibrarySession.beginTransaction();
            dbsManager.deleteUser(userName, deleteOptions);
            cryptUtil.deleteKeystore(userName,(String)httpSession.getServletContext().getAttribute("contextPath"));
            dbsLibrarySession.completeTransaction(deleteTransaction);
            cmsdkUserDeleted = true;
            logger.info("Deleted user :"+userName);
            logger.info("Transaction completed");
            deleteTransaction=null; 
            
        
           
        
        }catch(DbsException dbsException) {
        
          logger.error(dbsException.getErrorMessage());
      
          if(dbsException.containsErrorCode(30659)){
             ActionError editError=new ActionError("errors.user.delete.30659",userName);       
             errors.add(ActionErrors.GLOBAL_ERROR,editError);  
             logger.info(dbsException.getErrorMessage());
          }else{
             ActionError editError=new ActionError("errors.catchall",dbsException.getErrorMessage());
             errors.add(ActionErrors.GLOBAL_ERROR,editError);
          }
        }catch(Exception exception){
//            logger.info(exception);
            ActionError editError=new ActionError("errors.catchall",exception);
            errors.add(ActionErrors.GLOBAL_ERROR,editError);
        }finally{
            try{
                if(deleteTransaction!=null){
                    dbsLibrarySession.abortTransaction(deleteTransaction);
                    logger.info("Transaction aborted from user");
                }
                /* both users must be deleted */
               
            }catch(DbsException nestedException){
                logger.info(nestedException.getErrorMessage());
            }  
        }//end finally
        if (!errors.isEmpty()) {
            httpSession.setAttribute("errors",errors);
            return mapping.findForward("success");
        }
        ActionMessages messages = new ActionMessages();
        ActionMessage msg = new ActionMessage("user.delete.ok",userName);
        messages.add("message1", msg);
        httpSession.setAttribute("messages",messages);
        return mapping.findForward("success");  
    }
}
