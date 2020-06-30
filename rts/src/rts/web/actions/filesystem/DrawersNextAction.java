package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.filesystem.*;
/* java API */
import java.io.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;

public class DrawersNextAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      //Initialize logger 
      Logger logger = Logger.getLogger("DbsLogger");
      String target = null;
      FolderDocInfo folderDocInfo = null;
      HttpSession httpSession = null; 
      int setNo =0;
      
      try{
        
        logger.debug("Entering drawersNextAction... ");
        
        httpSession = (HttpSession)request.getSession(false);
        
        folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");

        /* set the hierarchy setNo according to operation "oper" to be performed */
        setNo = folderDocInfo.getHierarchySetNo();
        logger.debug("setNo: "+setNo);
        
        if(request.getParameter("oper").equalsIgnoreCase("prev")){
          
          setNo -= 1;         /* oper = "prev" , decrement setNo by 1 */
          
        }else{
          
          setNo +=1;          /* oper = "next" , increment setNo by 1 */
          
        }
        logger.debug("setNo after manipulation: "+setNo);
        folderDocInfo.setHierarchySetNo(setNo);
        logger.debug("folderDocInfo: "+folderDocInfo);
      
      }catch(Exception dbsEx){
        dbsEx.printStackTrace();
      }
      
    logger.debug("Exiting drawersNextAction... ");    
    target = ( folderDocInfo.getRtsType() == 1 )?"resume":"timesheet";
    return mapping.findForward(target);
      
  }
}