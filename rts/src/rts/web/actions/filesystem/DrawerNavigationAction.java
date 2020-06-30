package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.ShowContentForm;
import rts.web.beans.filesystem.FolderDoc;
import rts.web.beans.filesystem.FolderDocInfo;
import rts.web.beans.user.UserPreferences;
import rts.web.beans.user.UserInfo;
/* java API */
import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
/* Struts API */
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.*;

/**
 * Purpose : To facilitate drawer navigation in drawer module    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   06-08-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class DrawerNavigationAction extends Action  {

    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */


  public ActionForward execute ( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response )throws IOException,ServletException{
    
    String target = new String("success");
    HttpSession httpSession = null;
    DbsLibrarySession dbsLibrarySession = null;
    UserPreferences userPreferences = null;
    UserInfo userInfo = null;
    FolderDoc folderDoc= null;
    FolderDocInfo folderDocInfo = null;
    Logger logger = Logger.getLogger("DbsLogger");
    ArrayList drawerLists = null;
    int setNo = 0;
    
    try{
      
      logger.debug("Entering drawerNavigationAction...");
      
      httpSession = ( HttpSession )request.getSession(false);
      
      userPreferences = ( UserPreferences )
                        httpSession.getAttribute("UserPreferences");
                        
      folderDocInfo = ( FolderDocInfo )httpSession.getAttribute("FolderDocInfo");
      
      folderDoc = (FolderDoc)httpSession.getAttribute("folderDoc");
      
      
      boolean isFolderDocNull = ( folderDoc == null ) ? true:false;
      
      logger.debug("isFolderDocNull: "+isFolderDocNull);
      
      if( !isFolderDocNull ){

        /* set the drawer setNo according to operation "oper" to be performed */
        setNo = folderDocInfo.getSetNo();
        
        if(request.getParameter("oper").equalsIgnoreCase("prev")){
          
          setNo -= 1;         /* oper = "prev" , decrement setNo by 1 */
          
        }else{
          
          setNo +=1;          /* oper = "next" , increment setNo by 1 */
          
        }
        logger.debug("oper: "+request.getParameter("oper"));
        logger.debug("setNo: "+setNo);
        
        /* set folderDocInfo.setSetNo() to reflect the current setNo */
        folderDocInfo.setSetNo(setNo);  
        
        logger.debug("folderDocLists.size(): "
                      +folderDoc.getPublicObjectFoundListsSize());
        
        /* obtain the corresponding drawerList */
        /*drawerLists = folderDoc.getNextDrawerList(setNo,userPreferences.getRecordsPerPage(),
                      (byte)folderDocInfo.getListingType());*/

        /* isFirstSet will take care of previous drawers navigation */
        boolean isFirstSet = (folderDocInfo.getSetNo() == 1)?true:false;
        
        /* isLastSet will take care of next drawers navigation */
        boolean isLastSet = ( folderDoc.getDrawerCount() <= 
              (userPreferences.getRecordsPerPage()*(folderDocInfo.getSetNo())) ) ?
              true:false;
        
        logger.debug("isFirstSet: "+isFirstSet+" isLastSet: "+isLastSet);
        request.setAttribute("drawerLists",drawerLists);
        
        
        request.setAttribute("isFirstSet",new Boolean(isFirstSet));
        
        request.setAttribute("isLastSet",new Boolean(isLastSet));
        
      }
      
    /*}catch( DbsException dbsEx){
    
      dbsEx.printStackTrace();
      
    */}catch( Exception ex ){
      
      ex.printStackTrace();
      
    }
    
    logger.debug("Exiting drawerNavigationAction...");
    
    return mapping.findForward(target);  
  }

}