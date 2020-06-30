package rts.web.actions.preferences;

import rts.beans.*;
import rts.web.actionforms.preferences.UserPreferencesProfileForm;
import rts.web.beans.user.UserInfo;
import rts.web.beans.user.UserPreferences;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.util.*;
import org.apache.struts.validator.*;

/**
 *	Purpose: To save user_preference_profile.jsp with the specified data for the logged in User.
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    25-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class UserPreferenceProfileAction extends Action  {

  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */

  public ActionForward execute ( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws IOException,ServletException{
    
    String target = new String("success");
    HttpSession httpSession = null;
    DbsLibrarySession dbsLibrarySession = null;
    DbsDirectoryUser editUser= null;
    UserPreferences userPreferences = null;
    UserInfo userInfo = null;
    UserPreferencesProfileForm userPreferencesProfileForm = null;
    Logger logger = null;
    int treeAccesslevel=0;
    int itemsToBeDisplayedPerPage=0;
    int navigationType=1;
    int chkOpenDocInNewWin=2; /*variable for doc open option */
    
    try{
      logger = Logger.getLogger("DbsLogger");
      httpSession = request.getSession(false);
      userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
      
      userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");        
      
      dbsLibrarySession = userInfo.getDbsLibrarySession();
      editUser= dbsLibrarySession.getUser();
      logger.info("Saving User Preference Profile for : " + editUser);
      logger.debug("Initializing Variable ...");            
      userPreferencesProfileForm=(UserPreferencesProfileForm)form;
      
      treeAccesslevel= Integer.parseInt(
                               PropertyUtils.getSimpleProperty(
                               form, "txtPermittedTreeAccessLevel").toString());
                               
      itemsToBeDisplayedPerPage=Integer.parseInt(
                                PropertyUtils.getSimpleProperty(
                                form, "txtItemsToBeDisplayedPerPage").toString());
      
      //navigationType=userPreferences.FLAT_NAVIGATION;
      if (Integer.parseInt(PropertyUtils.getSimpleProperty(form, "cboNavigationType").toString())==UserPreferences.FLAT_NAVIGATION) {
        navigationType=userPreferences.FLAT_NAVIGATION;
      }else if ( Integer.parseInt(PropertyUtils.getSimpleProperty(form, "cboNavigationType").toString())==UserPreferences.TREE_NAVIGATION ) {
        navigationType=userPreferences.TREE_NAVIGATION;            
      }
      
      /* code added on account of doc open option */ 
                    
      chkOpenDocInNewWin=UserPreferences.IN_NEW_WINDOW;
      
      logger.debug("chkOpenDocInNewWin in UserPreferenceProfileAction: "+
                                                            chkOpenDocInNewWin);            
  
      /* code added for custom group labels */
      if( userInfo.isSystemAdmin() || userInfo.isAdmin() ){
        
        logger.debug("System Admin mode is already enabled");
        DbsDirectoryGroup[] listOfGroups = editUser.getAllAncestors();
        if( listOfGroups != null ){
          listOfGroups[0].setAttribute("CUSTOM1_LBL",
                          DbsAttributeValue.newAttributeValue(
                          (!userPreferencesProfileForm.getCustom1_lbl().equalsIgnoreCase("Custom1_Label"))?
                          userPreferencesProfileForm.getCustom1_lbl():"Custom1_Label"));
                                      
          listOfGroups[0].setAttribute("CUSTOM2_LBL",
                          DbsAttributeValue.newAttributeValue(
                          (!userPreferencesProfileForm.getCustom2_lbl().equalsIgnoreCase("Custom2_Label"))?
                          userPreferencesProfileForm.getCustom2_lbl():"Custom2_Label"));
        
          listOfGroups[0].setAttribute("CUSTOM3_LBL",
                          DbsAttributeValue.newAttributeValue(
                          (!userPreferencesProfileForm.getCustom3_lbl().equalsIgnoreCase("Custom3_Label"))?
                          userPreferencesProfileForm.getCustom3_lbl():"Custom3_Label"));
          
          listOfGroups[0].setAttribute("CUSTOM4_LBL",
                          DbsAttributeValue.newAttributeValue(
                          (!userPreferencesProfileForm.getCustom4_lbl().equalsIgnoreCase("Custom4_Label"))?
                          userPreferencesProfileForm.getCustom4_lbl():"Custom4_Label"));
        
        }
        
      }else{
        logger.debug("System Admin / Admin mode is not enabled");
      }
      
      /* set user-specific property bundle */
      if(editUser.getPropertyBundle()!=null){
        /* Permitted Tree Access Level : level upto which folder data be 
         * brought at one time */
        editUser.getPropertyBundle().putPropertyValue("PermittedTreeAccessLevel",
                          DbsAttributeValue.newAttributeValue(treeAccesslevel));
        /* Items Per Page : illustrates user's choice of number of records  
         * to be displayed per page */                  
        editUser.getPropertyBundle().putPropertyValue("ItemsToBeDisplayedPerPage",
                  DbsAttributeValue.newAttributeValue(itemsToBeDisplayedPerPage));
        /* Navigation Type : illustrates user's choice of Navigation
         * viz: Tree navigation or Flat (with bread crumbs) navigation */          
        editUser.getPropertyBundle().putPropertyValue("NavigationType",
                            DbsAttributeValue.newAttributeValue(navigationType));
                            
        /*added code */ 
        editUser.getPropertyBundle().putPropertyValue("OpenDocOption",
                        DbsAttributeValue.newAttributeValue(chkOpenDocInNewWin));
                        
        logger.debug("editUser.getPropertyBundle().putPropertyValue: "+
                    editUser.getPropertyBundle().getPropertyValue("OpenDocOption").getInteger(dbsLibrarySession));
                    
      }else{  // create user specific property bundle for the same properties
        /* obtain propertyBundleDefinition */
        DbsPropertyBundleDefinition dbsPropertyBundleDef = 
                              new DbsPropertyBundleDefinition(dbsLibrarySession);
        /* set it's Class Name*/                      
        dbsPropertyBundleDef.setAttribute(DbsPropertyBundle.CLASS_NAME,
                                          DbsAttributeValue.newAttributeValue(
                                          "User Profile Preference"));
        /* set Permitted Tree Access Level */                                  
        dbsPropertyBundleDef.addPropertyValue("PermittedTreeAccessLevel",
                                          DbsAttributeValue.newAttributeValue(
                                          treeAccesslevel));
        /* set Items Per Page */                                  
        dbsPropertyBundleDef.addPropertyValue("ItemsToBeDisplayedPerPage",
                                          DbsAttributeValue.newAttributeValue(
                                          itemsToBeDisplayedPerPage));
        /* set Navigation Type */                                          
        dbsPropertyBundleDef.addPropertyValue("NavigationType",
                                          DbsAttributeValue.newAttributeValue(
                                          navigationType));
                                          
        /*added code */ 
        dbsPropertyBundleDef.addPropertyValue("OpenDocOption",
                                          DbsAttributeValue.newAttributeValue(
                                          chkOpenDocInNewWin));
        /* create Property Bundle */                                  
        DbsPropertyBundle dbsPropertyBundle=dbsLibrarySession.createPublicObject(
                                                          dbsPropertyBundleDef);
        /* set it for the current user */                        
        editUser.setPropertyBundle(dbsPropertyBundle);
        
      }
  
      userPreferences.setRecordsPerPage(itemsToBeDisplayedPerPage);
      userPreferences.setNavigationType(navigationType);
      /* added code */
      userPreferences.setChkOpenDocInNewWin(chkOpenDocInNewWin);
      /* set custom labels */
      userPreferences.setCustom1_lbl(userPreferencesProfileForm.getCustom1_lbl());
      userPreferences.setCustom2_lbl(userPreferencesProfileForm.getCustom2_lbl());
      userPreferences.setCustom3_lbl(userPreferencesProfileForm.getCustom3_lbl());
      userPreferences.setCustom4_lbl(userPreferencesProfileForm.getCustom4_lbl());
      
      logger.debug("userPreferenceProfileForm : " + userPreferencesProfileForm);
      logger.info("User Preference Profile saved for : " + editUser);            
      
      
    }catch( DbsException dbsEx ){
      
      logger.error("dbsError : "+dbsEx.getErrorMessage());
      dbsEx.printStackTrace();
      
    }catch( Exception ex ){
      
      logger.error("Error : "+ex.getMessage());
      ex.printStackTrace();
    }
    if( !target.equals("failure") ){
      target = ( userPreferences.getNavigationType() == 
                UserPreferences.FLAT_NAVIGATION )?
                "forwardToFlatNav":"forwardToTreeNav";
    }       

    return mapping.findForward(target);
  }


}