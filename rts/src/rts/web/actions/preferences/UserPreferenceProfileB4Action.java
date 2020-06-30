package rts.web.actions.preferences;

import rts.beans.*;
import rts.web.actionforms.preferences.UserPreferencesProfileForm;
import rts.web.beans.crypto.CryptographicUtil;
import rts.web.beans.user.UserInfo;
import rts.web.beans.user.UserPreferences;
import rts.web.beans.utility.SearchUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import org.apache.commons.beanutils.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.*;
import org.apache.log4j.*;

/**
 *	Purpose: To populate UserPreferenceProfileBean and UserPreferenceProfileForm.
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    25-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class UserPreferenceProfileB4Action extends Action  {

    HttpSession httpSession = null;
    DbsLibrarySession dbsLibrarySession = null;
    Logger logger = null;
    
  /* member function to map int value to boolean & return it.*/
  private boolean int2bool(int intVal){
    boolean retval=false;
    return(retval = (intVal == 1)?false:true);
  }

  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */

  public ActionForward execute ( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws IOException,ServletException {
    
    String target = new String("success");
    UserInfo userInfo = null;
    Locale locale = getLocale(request);
    ArrayList memberList=new ArrayList();
    
    UserPreferencesProfileForm userPreferencesProfileForm=
                                              new UserPreferencesProfileForm();        
    
    UserPreferences userPreferences=new UserPreferences();
    String[] credentialManager=null; 
    DbsDirectoryUser userToEdit=null;
    DbsAttributeValue dbsAttrVal = null;
    ActionErrors errors = new ActionErrors();
    int val=0;
    Boolean hideURLOption=new Boolean(false);
    Boolean URLOptionVisibility=new Boolean(true);

    try{
      
      logger = Logger.getLogger("DbsLogger"); 
  
      logger.info("Entering UserPreferenceB4ProfileAction");
      
      httpSession = request.getSession(false);      
      userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
      dbsLibrarySession = userInfo.getDbsLibrarySession();    
      /* This is to deal with Set URLEncrypt/Decrypt Password button visibilty */
      if(userInfo.isSystemAdmin()){
        hideURLOption=new Boolean(false);
      }else{
        hideURLOption=new Boolean(true);
      }
      /* set user status */
      userPreferencesProfileForm.setIsSystemAdmin(userInfo.isSystemAdmin() || 
                                                  userInfo.isAdmin());
        
      String userName= dbsLibrarySession.getUser().getName();
  
      if(httpSession.getAttribute("messages")!=null){
          logger.debug("Saving action message in request stream");
          saveMessages(request,(ActionMessages)httpSession.getAttribute("messages"));
          httpSession.removeAttribute("messages");
      } 
      if(httpSession.getAttribute("errors")!=null){
          logger.debug("Saving action errors in request stream");
          saveErrors(request,(ActionErrors)httpSession.getAttribute("errors"));
          httpSession.removeAttribute("errors");
      }
      /* obtain current user */
      userToEdit=(DbsDirectoryUser)SearchUtil.findObject(
                                  dbsLibrarySession,DbsDirectoryUser.CLASS_NAME,
                                  dbsLibrarySession.getUser().getName());
                                          
      if(userToEdit!=null){
      
        DbsPrimaryUserProfile userToEditProf=userToEdit.getPrimaryUserProfile();
        credentialManager=new String[1];
        credentialManager[0]=userToEdit.getCredentialManager();
        userPreferencesProfileForm.setUserName(userName);
        /* obtain corr. home folder */
        if(userToEditProf.getHomeFolder()!=null){
            userPreferencesProfileForm.setHomeFolder(
                          userToEditProf.getHomeFolder().getAnyFolderPath());
                          
        }else{
            userPreferencesProfileForm.setHomeFolder("**Not Set**");
        }
          
        /* obtain corr. email profile */
        DbsExtendedUserProfile emailProfile=SearchUtil.getEmailUserProfile(
                                                dbsLibrarySession,userToEdit);                                               
        if(emailProfile!=null){
          DbsAttributeValue mailAttr=emailProfile.getAttribute("EMAILADDRESS");
          
          if(mailAttr!=null){  
            userPreferencesProfileForm.setEmailId(
                                      mailAttr.getString(dbsLibrarySession));
          }else{
            userPreferencesProfileForm.setEmailId("**E-Mail Address not set**");
          }
          
        }
        
        /* obtain list of groups associated with the user */
        DbsDirectoryGroup[] listOfGroups = 
                                    dbsLibrarySession.getUser().getAllAncestors();
        if( listOfGroups !=null ){
          String[] memberGroups = new String[listOfGroups.length] ;
          for( int index =0; index<listOfGroups.length; index++ ){
            memberGroups[index] = (listOfGroups[index].getName());
          }
          
          userPreferencesProfileForm.setCboGroup(memberGroups);
          /* Just in case the group doesnot have these attributes */
          try{
          
            dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM1_LBL");
            logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
            
            userPreferencesProfileForm.setCustom1_lbl(
                                (dbsAttrVal != null && 
                                dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                                dbsAttrVal.getString(dbsLibrarySession):"Custom1_Label");
            
    
            dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM2_LBL");
            logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
            
            userPreferencesProfileForm.setCustom2_lbl(
                                (dbsAttrVal != null && 
                                dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                                dbsAttrVal.getString(dbsLibrarySession):"Custom2_Label");
                                
            dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM3_LBL");
            logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
            
            userPreferencesProfileForm.setCustom3_lbl(
                                (dbsAttrVal != null && 
                                dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                                dbsAttrVal.getString(dbsLibrarySession):"Custom3_Label");
                                
    
            dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM4_LBL");
            logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
            
            userPreferencesProfileForm.setCustom4_lbl(
                                (dbsAttrVal != null && 
                                dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                                dbsAttrVal.getString(dbsLibrarySession):"Custom4_Label");
          
          }catch( DbsException dbsEx ){
            logger.error("Error : "+dbsEx.getErrorMessage());
            userPreferencesProfileForm.setCustom1_lbl("Custom1_Label");
            userPreferencesProfileForm.setCustom2_lbl("Custom2_Label");
            userPreferencesProfileForm.setCustom3_lbl("Custom3_Label");
            userPreferencesProfileForm.setCustom4_lbl("Custom4_Label");
          }
        
        }else{  
          userPreferencesProfileForm.setCboGroup(new String[] {"WORLD"});
          userPreferencesProfileForm.setCustom1_lbl("Custom1_Label");
          userPreferencesProfileForm.setCustom2_lbl("Custom2_Label");
          userPreferencesProfileForm.setCustom3_lbl("Custom3_Label");
          userPreferencesProfileForm.setCustom4_lbl("Custom4_Label");
        }
        
      }
      /* obtain user-specific property bundle */  
      if((dbsLibrarySession.getUser().getPropertyBundle())!=null){
          
        DbsPropertyBundle propertyBundleToEdit= 
                              dbsLibrarySession.getUser().getPropertyBundle();
        
        if(propertyBundleToEdit!=null){
            /* Permitted Tree Access Level : level upto which folder data be 
             * brought at one time */
            DbsProperty property= propertyBundleToEdit.getProperty(
                                                    "PermittedTreeAccessLevel");
            if (property!=null){
              DbsAttributeValue attrValue=property.getValue();
              userPreferencesProfileForm.setTxtPermittedTreeAccessLevel(
                                    attrValue.getInteger(dbsLibrarySession));                 
            }else{
              userPreferencesProfileForm.setTxtPermittedTreeAccessLevel(
                                              userPreferences.getTreeLevel());
            }
            /* Items Per Page : illustrates user's choice of number of records  
             * to be displayed per page */                  
            property=propertyBundleToEdit.getProperty("ItemsToBeDisplayedPerPage");
            if (property!=null) {
              DbsAttributeValue attrValue=property.getValue();
              userPreferencesProfileForm.setTxtItemsToBeDisplayedPerPage(
                                    attrValue.getInteger(dbsLibrarySession));
            }else{
              userPreferencesProfileForm.setTxtItemsToBeDisplayedPerPage(
                                        userPreferences.getRecordsPerPage());
            }
            /* Navigation Type : illustrates user's choice of Navigation
             * viz: Tree navigation or Flat (with bread crumbs) navigation */          
            property=propertyBundleToEdit.getProperty("NavigationType");
            if (property!=null) {
                DbsAttributeValue attrValue=property.getValue();
                userPreferencesProfileForm.setCboNavigationType(
                                    attrValue.getInteger(dbsLibrarySession)); 
            }else{
                userPreferencesProfileForm.setCboNavigationType(
                                        userPreferences.getNavigationType());
            }
            
            /*userPreferencesProfileForm.setCboNavigationType(
                                          userPreferences.getNavigationType());*/
            
            /* code added on account of open doc option */
            property=propertyBundleToEdit.getProperty("OpenDocOption");                    
            if(property!=null){
              DbsAttributeValue attrValue=property.getValue();
              val=attrValue.getInteger(dbsLibrarySession);
              //userPreferencesProfileForm.setBoolChkOpenDocInNewWin(int2bool(val));
              logger.debug("attrValue.getInteger(dbsLibrarySession): "
                                  +attrValue.getInteger(dbsLibrarySession));
              //logger.debug("userPreferencesProfileForm.getBoolChkOpenDocInNewWin(): "
                    //+userPreferencesProfileForm.getBoolChkOpenDocInNewWin());
            }else{
              val=userPreferences.getChkOpenDocInNewWin();
              //userPreferencesProfileForm.setBoolChkOpenDocInNewWin(int2bool(val));
              logger.debug("chkOpenDocInNewWin set by userPreferences: "
                                  +userPreferences.getChkOpenDocInNewWin());
              //logger.debug("userPreferencesProfileForm.getBoolChkOpenDocInNewWin(): "
                    //+userPreferencesProfileForm.getBoolChkOpenDocInNewWin());
            }
            
        }
      
      }else{  // populate userPreferencesProfileForm from userPreferences
          
        userPreferencesProfileForm.setTxtPermittedTreeAccessLevel(
                                              userPreferences.getTreeLevel());
                                              
        userPreferencesProfileForm.setTxtItemsToBeDisplayedPerPage(
                                          userPreferences.getRecordsPerPage());
                                          
        userPreferencesProfileForm.setCboNavigationType(
                                          userPreferences.getNavigationType());
                                          
        val=userPreferences.getChkOpenDocInNewWin();
        /*added code */ 
        //userPreferencesProfileForm.setBoolChkOpenDocInNewWin(int2bool(val));
        
        //logger.debug("Setting BoolChkOpenDocInNewWin for the first time as : "
                      //+userPreferencesProfileForm.getBoolChkOpenDocInNewWin() );
        //request.setAttribute(DbsDirectoryUser.ENCRYPTION_ENABLED,new Boolean(false));
      }
      /*added code */
      String contextPath=(String)
                httpSession.getServletContext().getAttribute("contextPath");
      /* if URLEncrypt/Decrypt Password is set already, disable the button */          
      CryptographicUtil cryptoUtil= new CryptographicUtil();
      if(cryptoUtil.getSystemKeyStoreFile(contextPath)){
        URLOptionVisibility=new Boolean(false);
      }
  
      request.setAttribute("URLOptionVisibility",URLOptionVisibility);
      //userPreferencesProfileForm.setHideURLOption(URLOptionVisibility.booleanValue());
      
      logger.debug("URLOptionVisibility: "
        +((Boolean)request.getAttribute("URLOptionVisibility")).booleanValue());
      
  //            if(userToEdit.isSystemAdminEnabled() || userToEdit.isAdminEnabled() || userToEdit.getName().equals(userToEdit.getOwner().getName())) {
  //                request.setAttribute("isChangePassEnabled","true");
  //            }
      request.setAttribute("userPreferencesProfileForm",userPreferencesProfileForm);
      //request.setAttribute("themeList",themeList); 
      request.setAttribute("hideURLOption",hideURLOption);
      logger.debug("hideURLOption: "+((Boolean)request.getAttribute("hideURLOption")).booleanValue());
      logger.debug("userPreferencesProfileForm" + userPreferencesProfileForm);
      logger.info("Exiting userPreferenceProfileB4Action");
    
  }catch( DbsException dbsEx ){
      dbsEx.printStackTrace();
  }catch( Exception ex ){
      ex.printStackTrace();
  }
    
  return mapping.findForward(target);
 }
  
}