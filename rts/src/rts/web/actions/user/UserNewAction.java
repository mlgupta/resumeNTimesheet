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
 * $Id: UserNewAction.java,v 1.4 2005/11/25 14:08:22 suved Exp $
 *****************************************************************************
 */

package rts.web.actions.user;

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

public class UserNewAction extends Action  {
    public static final String PROP_BUNDLE_ACL_NAME="Public";  

    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String PERSONAL_FOLDER_NAME="personal";
        String PERMISSION_BUNDLE_SUFFIX="_permission_bundle";
        HttpSession httpSession = null;
        DbsTransaction newTransaction=null;
        DbsDirectoryUser userNew =null;
        DbsLibrarySession dbsLibrarySession = null;
        UserPreferences userPreferences = null;
        UserInfo userInfo = null;        
        Logger logger = Logger.getLogger("DbsLogger");
        logger.info("Entering UserNewAction");
        String forward = new String("success");
        ActionErrors errors = new ActionErrors();  
        try {
          httpSession = request.getSession(false);
          userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
      
          userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");        
      
          dbsLibrarySession = userInfo.getDbsLibrarySession();
          newTransaction=dbsLibrarySession.beginTransaction();
          String userName= request.getParameter("txtUserName");
          String password= request.getParameter("txtPassword");
          String emailAddress= request.getParameter("txtEmailAddress");
          String description= request.getParameter("txaDescription");   
          dbsLibrarySession.setAdministrationMode(true);
          DbsCollection userCollection=dbsLibrarySession.getDirectoryUserCollection();
          DbsDirectoryUser userExists=null;
          try{
              userExists=(DbsDirectoryUser)userCollection.getItems(userName);
          }catch(DbsException dbEx){
              if(dbEx.containsErrorCode(12214)){
                  //do nothing when the user is not existing
              }else{
                  throw dbEx;
              }
          }
			
          if(!(userExists instanceof DbsDirectoryUser)){
           DbsFileSystem fileSystem = new DbsFileSystem(dbsLibrarySession);
           DbsDirectoryUser owner=(DbsDirectoryUser)dbsLibrarySession.getUser();
           DbsDirectoryGroup ownerGroup[]=owner.getAllAncestors();
           DbsDirectoryGroup companyGroup=ownerGroup[0];
          
            
           DbsAttributeValue folderAttr=companyGroup.getAttribute("COMPANY_DIR");
           String compFolder=(String)folderAttr.getString(dbsLibrarySession); 
           String absoluteCompPath=compFolder ;
           System.out.println(absoluteCompPath);
           DbsFolderPathResolver fpr=new DbsFolderPathResolver(dbsLibrarySession);
           DbsPublicObject dbsPub=(fileSystem.findPublicObjectByPath(compFolder).getResolvedPublicObject());
           DbsFolder compDir=(DbsFolder)dbsPub;
           
           
           
  //         DbsFolder compDir=(DbsFolder)fpr.findPublicObjectByPath("/"+compFolder+"/");
           //DbsFolder compDir=(DbsFolder)SearchUtil.findObject(dbsLibrarySession,DbsFolder.CLASS_NAME,compFolder);
           fpr.setRootfolder();            
           DbsFolder rootFolder=(DbsFolder)fpr.findPublicObjectByPath("/");
           DbsFolder homeDir=null;
           String homeFolder=compDir.getAnyFolderPath()+"/"+PERSONAL_FOLDER_NAME+"/"+userName;
           System.out.println("HomeFolder"+homeFolder);
           try{
               homeDir=(DbsFolder)fpr.findPublicObjectByPath(homeFolder);
           }catch(DbsException folderFindException){
                          
             if(folderFindException.containsErrorCode(30619)){
                homeDir=fileSystem.createFolder(homeFolder,rootFolder,true,null);
             }
           }
        
           Hashtable options = new Hashtable() ;
           options.put(DbsUserManager.ADMIN_ENABLED, DbsAttributeValue.newAttributeValue(false));
           options.put(DbsUserManager.HAS_HOME_FOLDER,DbsAttributeValue.newAttributeValue(false));
           options.put(DbsUserManager.HAS_EMAIL,DbsAttributeValue.newAttributeValue(true));
           options.put(DbsUserManager.EMAIL_ADDRESS,DbsAttributeValue.newAttributeValue(emailAddress));
           options.put(DbsUserManager.CAN_CHANGE_PASSWORD,DbsAttributeValue.newAttributeValue(true));
           DbsUserManager userManager=new DbsUserManager(dbsLibrarySession);
           userNew = userManager.createUser(userName,password,options);
           userNew.setAttribute(DbsDirectoryUser.DESCRIPTION_ATTRIBUTE,DbsAttributeValue.newAttributeValue(description));
           homeDir.setOwnerByName(userNew.getName());           
           DbsPrimaryUserProfile pup = userNew.getPrimaryUserProfile();
           pup.setHomeFolder(homeDir);
           DbsCollection systemAclColl= dbsLibrarySession.getSystemAccessControlListCollection();
           DbsAccessControlList privateAcl=null;
           for(int index =0;index< systemAclColl.getItemCount() ; index++){
              if(systemAclColl.getItems(index).toString().equalsIgnoreCase("Private")){            
                  privateAcl= systemAclColl.getSystemAccessControlList(systemAclColl.getItems(index));
                  break;
              }
           }
           if(privateAcl!=null){
              homeDir.setAcl(privateAcl);
           }else{
             logger.info("Private Acl Not Found ... Exiting");
             throw new Exception("Private Acl Not Found");
           }

           companyGroup.addMember(userNew);
           userNew.putProperty("RTSUSER",DbsAttributeValue.newAttributeValue("RTS user Property Bundle set,else this user cannot set it's user preferences"));
           
          if(userNew.getPropertyBundle()!=null){
              DbsPropertyBundle defaultBundle=userNew.getPropertyBundle();
               DbsAccessControlList propBundleAcl=(DbsAccessControlList)SearchUtil.findObject(dbsLibrarySession,DbsAccessControlList.CLASS_NAME,PROP_BUNDLE_ACL_NAME);
               //defaultBundle.setAcl(propBundleAcl);
               //System.out.println(propBundleAcl.getAcl().getName());
              userNew.getPrimaryUserProfile().setDefaultAcls(defaultBundle);
          }
           
          /* DbsPermissionBundle dbsPermBundle=(DbsPermissionBundle)SearchUtil.findObject(dbsLibrarySession,DbsPermissionBundle.CLASS_NAME,"All");
           System.out.println("dbsPermBundle is null:  "+(( dbsPermBundle == null )?"true":"false"));
           if( dbsPermBundle != null ){
             DbsAccessControlEntryDefinition aceDef=new DbsAccessControlEntryDefinition(dbsLibrarySession);
             aceDef.addPermissionBundle(dbsPermBundle);
             aceDef.setGrantee(userNew);
             DbsAccessControlEntry compAce=dbsLibrarySession.createSystemObject(aceDef);
              
             DbsAccessControlList accessList = companyGroup.getAcl();
             //accessList.updateAccessControlEntry(compAce,aceDef);
             accessList.grantAccess(aceDef);
           }else{
                           
           }*/
          }
          dbsLibrarySession.completeTransaction(newTransaction);           
            logger.info("Data saved for user : " + userName);
            logger.info("Transaction completed");
          newTransaction=null;
  
        }catch(DbsException dbsException){
//            logger.info(dbsException.getErrorMessage());
            ActionError editError=new ActionError("errors.catchall",dbsException.getErrorMessage());
            errors.add(ActionErrors.GLOBAL_ERROR,editError);
        }catch(Exception exception){
//            logger.info(exception);
            ActionError editError=new ActionError("errors.catchall",exception);
            errors.add(ActionErrors.GLOBAL_ERROR,editError);
        }
        finally{
            try{
                if(newTransaction!=null){
                    if(userNew!=null){
                            userNew.free();
                    }
                    dbsLibrarySession.abortTransaction(newTransaction);                 
                    logger.info("Transaction Aborted");                    
                }       
            }catch(DbsException nestedException){
              logger.info(nestedException.getErrorMessage());
            }  
        }//end finally
        
        
        return mapping.findForward(forward);
    }

}
