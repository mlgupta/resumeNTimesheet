package rts.web.actions.preferences;
/*rts package references */
import rts.beans.*;
import rts.web.actionforms.preferences.SetEncryptionPasswordForm;
import rts.web.beans.exception.ExceptionBean;
import rts.web.beans.user.UserInfo;

/*java API */ 
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
/*Struts API */ 
import org.apache.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;

public class SetURLEncryptPasswordB4Action extends Action 
{

  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    String target=new String("success");
    Locale locale=getLocale(request);
    HttpSession httpSession=null;
    Logger logger=Logger.getLogger("DbsLogger");
    DbsLibrarySession dbsLibrarySession;   
    ExceptionBean exceptionBean;
    

    ActionErrors errors= new ActionErrors();

    try{
      logger.debug("Entering SetURLEncryptPasswordB4Action...");
      httpSession=request.getSession(false);
      UserInfo userInfo=(UserInfo)httpSession.getAttribute("UserInfo"); 
      dbsLibrarySession=userInfo.getDbsLibrarySession();

      if( userInfo.isAdmin() || userInfo.isSystemAdmin()){ 
        SetEncryptionPasswordForm setEncryptionPasswordForm=new SetEncryptionPasswordForm();
        setEncryptionPasswordForm.setTxtPassword("");
        setEncryptionPasswordForm.setTxtConfirmPassword("");
        request.setAttribute("setEncryptionPasswordForm",setEncryptionPasswordForm);
        logger.debug("setEncryptionPasswordForm: "+request.getAttribute("setEncryptionPasswordForm"));
      }else{
        ActionError actionError=new ActionError("set.url.password.SysAdmin.only");
        errors.add(ActionErrors.GLOBAL_ERROR,actionError);  
        logger.debug("ActionError set for SysAdmin access..");
        
      }
    }catch(Exception ex){
      logger.debug(ex.getMessage());
      ActionError actionError=new ActionError("errors.catchall",ex);
      errors.add(ActionErrors.GLOBAL_ERROR,actionError);
      ex.printStackTrace();
    }
    logger.debug("Exiting SetURLEncryptPasswordB4Action...");
    if(!errors.isEmpty()){
      target=new String("failure");
      saveErrors(request,errors);
    }
    
    return mapping.findForward(target);
  }
}
