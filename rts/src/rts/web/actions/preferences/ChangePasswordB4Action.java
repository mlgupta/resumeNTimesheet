package rts.web.actions.preferences;

import rts.beans.*;
import rts.web.beans.user.UserInfo;
import rts.web.actionforms.preferences.ChangePasswordForm;

import java.util.Locale;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;
import org.apache.log4j.*;
import org.apache.struts.validator.*;


public class ChangePasswordB4Action extends Action{

    DbsLibraryService dbsLibraryService = null;
    DbsCleartextCredential dbsCleartextCredential = null;
    DbsLibrarySession dbsLibrarySession = null;
    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Locale locale = getLocale(request);
        MessageResources messages = getResources(request);
        UserInfo userInfo = null;
        HttpSession httpSession = null;
        Logger logger = null;
        logger = Logger.getLogger("DbsLogger");
        ChangePasswordForm changePasswordForm=new ChangePasswordForm();
        String userName= null;
        String password=null;
        String confirmPassword=null;
        // Validate the request parameters specified by the user
        ActionErrors errors = new ActionErrors();
        try {
            logger.info("Entering ChangePasswordB4Action");
            httpSession = request.getSession(false);      
            userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
            dbsLibrarySession = userInfo.getDbsLibrarySession();
            userName=  dbsLibrarySession.getUser().getName();
            logger.debug("UserName" + " " + userName);
            changePasswordForm.setTxtUserName(userName);
//            changePasswordForm.setTxtPassword(password);
//            changePasswordForm.setTxtConfirmPassword(confirmPassword);
            request.setAttribute("changePasswordForm",changePasswordForm);
        }catch(DbsException dbsException){
          ActionError editError=new ActionError("errors.catchall",dbsException.getErrorMessage());
          errors.add(ActionErrors.GLOBAL_ERROR,editError);
        }
        if (!errors.isEmpty()) {
      
        saveErrors(request, errors);
        return (mapping.getInputForward());
    }
          return mapping.findForward("success");
    }
}
