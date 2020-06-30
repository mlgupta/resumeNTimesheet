package rts.web.actions.loginout;
/* java API */
import java.io.File;
import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/* Struts API */
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
/* rts package references */
import rts.beans.DbsCleartextCredential;
import rts.beans.DbsDirectoryGroup;
import rts.beans.DbsDirectoryUser;
import rts.beans.DbsException;
import rts.beans.DbsLibraryService;
import rts.beans.DbsLibrarySession;

import rts.web.actionforms.loginout.LoginForm;
import rts.web.beans.exception.ExceptionBean;
import rts.web.beans.filesystem.DateHelperForFileSystem;
import rts.web.beans.loginout.LoginBean;
import rts.web.beans.utility.ParseXMLTagUtil;

public class DirectoryHierarchyAction extends Action  {

private static String DATE_FORMAT="MM/dd/yyyy HH:mm:ss";
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
  logger.info("Logging User ...");
  DateHelperForFileSystem dateHelper = new DateHelperForFileSystem();

  //Variable declaration
  ExceptionBean exceptionBean;        
  String forward = "success";
  DbsLibraryService dbsLibraryService;
  DbsCleartextCredential dbsCleartextCredential;
  DbsLibrarySession dbsLibrarySession = null;
  ServletContext context;
  HttpSession httpSession;
  LoginForm loginForm;
  String ifsService;
  String ifsSchemaPassword;
  String serviceConfiguration;
  String domain;
  ActionErrors actionErrors = new ActionErrors();
  ActionError actionError;
  LoginBean loginBean = null;
  String xmlString = null;
  try{

    loginForm = (LoginForm)form;
    context = getServlet().getServletContext();
    //logger.debug("contextPath: "+(String)context.getAttribute("contextPath"));
    String relativePath= (String)context.getAttribute("contextPath")+
                         "WEB-INF"+File.separator+"params_xmls"+
                         File.separator+"GeneralActionParam.xml";
                         
    ParseXMLTagUtil parseUtil= new ParseXMLTagUtil(relativePath);            

    ifsService = parseUtil.getValue("IfsService","Configuration");
    ifsSchemaPassword = 
                parseUtil.getValue("IfsSchemaPassword","Configuration");
    serviceConfiguration =  
                parseUtil.getValue("ServiceConfiguration","Configuration");
    domain = parseUtil.getValue("Domain","Configuration");

    loginBean = new LoginBean((request.getParameter("userID")).toUpperCase(),
                              (request.getParameter("userPassword")));

    // set service specific parameters
    loginBean.setServiceParams(ifsService,ifsSchemaPassword,serviceConfiguration,
                               domain);
    // start library service 
    loginBean.startDbsService();
    // set user cleartext credentials
    loginBean.setdbsCtc();
    // set user library session
    loginBean.setUserSession();
    
    if( loginBean.dbsSession.isConnected() && loginBean.getDbsUser() !=null ){
      // obtain user's current group
      DbsDirectoryGroup[] userGroups = loginBean.getDbsUser().getAllAncestors();
      if( userGroups != null ){
        // set user's company name
        loginBean.setUserCompanyName(userGroups[0].getName());
        logger.debug("Company Name: "+loginBean.getUserCompanyName());
        // set company's resume folder
        loginBean.setResumeFolder();
        // create XML file for further reference
        xmlString = loginBean.getXMLAsString();
        logger.debug("Directory structure obtained successfully");
      }
    }else{
      logger.debug("Session unavailable or DirectoryUser not found");
    }
    request.setAttribute("xmlString",xmlString);
    // terminate user session
    loginBean.terminateService();
    
  }catch( DbsException dbsEx ){
    dbsEx.printStackTrace();
    forward = "failure";
  }catch(Exception ex){
    exceptionBean = new ExceptionBean(ex);
    logger.error(exceptionBean.getMessage());
    logger.debug(exceptionBean.getErrorTrace());
    saveErrors(request,exceptionBean.getActionErrors());
    forward = "failure";
    ex.printStackTrace();
  }
  return mapping.findForward(forward);

}
}