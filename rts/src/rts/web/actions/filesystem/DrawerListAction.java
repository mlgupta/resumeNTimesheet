package rts.web.actions.filesystem;
/* rts package references */
  import rts.beans.*;
  import rts.web.actionforms.filesystem.*;
  import rts.web.beans.filesystem.FolderDoc;
  import rts.web.beans.filesystem.FolderDocInfo;
  import rts.web.beans.filesystem.FolderDocList;
  import rts.web.beans.user.UserPreferences;
  import rts.web.beans.user.UserInfo;
  /* java API */
  import java.io.IOException;
  import java.util.ArrayList;
  import javax.servlet.http.*;
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
 * Purpose: To obtain the drawer list for the iframe in resume_drawer_list.jsp
 */

public class DrawerListAction extends Action  {

    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */

  public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    
    String target = new String("success");
    HttpSession httpSession = null; 
    Logger logger = Logger.getLogger("DbsLogger");
    UserPreferences userPreferences = null;
    UserInfo userInfo = null;
    FolderDoc folderDoc = null;
    DbsLibrarySession dbsLibrarySession = null;
    FolderDocInfo folderDocInfo = null;
    ArrayList drawerLists = new ArrayList();
    int setNo = 0;
    ArrayList publicObjectFoundLists = null;
    
    try{
      
      logger.debug("Entering drawerListAction... ");
      
      httpSession = (HttpSession)request.getSession(false);
      
      userPreferences = (UserPreferences)
                        httpSession.getAttribute("UserPreferences");
                        
      //folderDoc = (FolderDoc)httpSession.getAttribute("folderDoc");
      
      userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
      
      folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
      
      dbsLibrarySession = userInfo.getDbsLibrarySession(); 
 
      folderDoc = new FolderDoc(dbsLibrarySession);
 
      logger.debug("folderDocInfo : " + folderDocInfo);
      logger.debug("userPreferences : " + userPreferences);
      
      publicObjectFoundLists = ( ArrayList )
                              httpSession.getAttribute("pOLists");
           
      //boolean isFolderDocNull = (folderDoc == null )?true:false;
      boolean isFolderDocNull = (publicObjectFoundLists == null )?true:false;
      logger.debug("isFolderDocNull: "+isFolderDocNull);
      
      if( !isFolderDocNull ){

      /* set the drawer setNo according to operation "oper" to be performed */
        setNo = folderDocInfo.getSetNo();
        
        if(request.getParameter("oper").equalsIgnoreCase("prev")){
          
          setNo -= 1;         /* oper = "prev" , decrement setNo by 1 */
          
        }else if( request.getParameter("oper").equalsIgnoreCase("list") ){
        
          if( folderDocInfo.getListingType() == FolderDocInfo.SEARCH_LISTING){
            setNo = 1;        /* oper = "list" and search type, set                                
                               * setNo = 1. */
          }else{          
            setNo = 1;        /* oper = "list" and folder listing, set
                               *  setNo = 1. */
          }
          
        }else{
          
          setNo +=1;          /* oper = "next" , increment setNo by 1 */
          
        }
        logger.debug("oper: "+request.getParameter("oper"));
        logger.debug("setNo: "+setNo);
        
        /* set folderDocInfo.setSetNo() to reflect the current setNo */
        folderDocInfo.setSetNo(setNo);  
        
  
        /* obtain the corresponding drawerList */
        
        drawerLists =folderDoc.getNextDrawerList(publicObjectFoundLists, 
                    folderDocInfo.getSetNo(),userPreferences.getRecordsPerPage(),
                    (byte)folderDocInfo.getListingType());
  
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
      
    }catch( DbsException dbsEx){
    
      dbsEx.printStackTrace();
      
    }catch( Exception ex ){
    
      ex.printStackTrace();
      
    }
    
    //httpSession.removeAttribute("folderDoc");
    logger.debug("Exiting drawerListAction... ");    
    
    return mapping.findForward(target);
  }
}