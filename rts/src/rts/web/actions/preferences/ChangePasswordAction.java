package rts.web.actions.preferences;

import rts.beans.*;
import rts.web.actionforms.preferences.ChangePasswordForm;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;

/**
 *	Purpose: To change the password for the logged in user.
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    26-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class ChangePasswordAction extends Action {

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
//        MessageResources messages = getResources(request);
        UserInfo userInfo = null;
        HttpSession httpSession = null;
        Logger logger = null;
        logger = Logger.getLogger("DbsLogger");
        ChangePasswordForm changePasswordForm=new ChangePasswordForm();
        DbsDirectoryUser userToEdit=null;
        String[] credentialManager=null;
        String userName=null;
        String password=null;
        String confirmPassword=null;
        
        // Validate the request parameters specified by the user
        ActionErrors errors = new ActionErrors();
        try {
        logger.info("Entering ChangePasswordAction");
        logger.debug("Initializing Variable ...");
        httpSession = request.getSession(false);
        userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
        dbsLibrarySession = userInfo.getDbsLibrarySession();
        userName=dbsLibrarySession.getUser().getName();
        logger.info("Saving password for user : " + userName);
        /* obtain user,password,confirmPassword */
        userToEdit=(DbsDirectoryUser)SearchUtil.findObject(dbsLibrarySession,DbsDirectoryUser.CLASS_NAME,userName);
        password=((String)PropertyUtils.getSimpleProperty(form, "txtPassword")).trim();
        confirmPassword=((String)PropertyUtils.getSimpleProperty(form, "txtConfirmPassword")).trim();
                
        String distinguishedName = userToEdit.getDistinguishedName();
        String credentialManagerName = userToEdit.getCredentialManager();
        /* change password begins ... */
        Vector passwordParams = new Vector();
        passwordParams.addElement(credentialManagerName);
        passwordParams.addElement(distinguishedName);
        passwordParams.addElement(password);
        passwordParams.addElement(null);
        dbsLibrarySession.invokeServerMethod("DYNCredentialManagerSetPassword", passwordParams);
        logger.info("Password Changed for user : " + userName);
        logger.info("Exiting ChangePasswordAction");
        /* change password ends ... */
        ActionMessages messages = new ActionMessages();
        ActionMessage msg = new ActionMessage("change.userPassword.success",userName);
        messages.add("message1", msg);
        httpSession.setAttribute("messages",messages);        
        }catch(DbsException dbsException){
            logger.info(dbsException.getErrorMessage());
        }catch(Exception exception) {
            logger.info(exception);
        }
        return mapping.findForward("success");
    }
}
