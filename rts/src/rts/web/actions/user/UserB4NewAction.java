package rts.web.actions.user;
/**
 *	Purpose: To populate user_new.jsp with the default data.
 *  @author              Mishra Maneesh 
 *  @version             1.0
 * 	Date of creation:    
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
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

public class UserB4NewAction extends Action {
    DbsLibrarySession dbsLibrarySession = null;
    //Initialize logger
    Logger logger = Logger.getLogger("DbsLogger");

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
        String[] credentialManager=null; 
        ArrayList memberList=new ArrayList();
        UserNewEditForm userNewEditForm=new UserNewEditForm();
        
        // Validate the request parameters specified by the user
        ActionErrors errors = new ActionErrors();
        try{
            logger.info("Entering UserB4NewAction");
            logger.debug("Initializing Variable ...");        
            httpSession = request.getSession(false);      
            userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
            /*dbsLibrarySession = userInfo.getDbsLibrarySession();
            credentialManager=new String[1];
            credentialManager[0]=dbsLibrarySession.getUser().getCredentialManager();
            userNewEditForm.setRadStatus("3");
            userNewEditForm.setTxtAccessControlList(dbsLibrarySession.getUser().getAcl().getName());
            userNewEditForm.setRadQuota("1");            
            userNewEditForm.setTxtQuota("30"); 
            userNewEditForm.setCboCredentialManager(credentialManager);
            userNewEditForm.setCboLanguage(new String[]{""});
            userNewEditForm.setCboCharacterSet(new String[]{""});
            userNewEditForm.setCboLocale(new String[]{""});
            userNewEditForm.setCboTimeZone(new String[]{""});      */
//            System.out.println(userNewEditForm.getCboCredentialManager()[0]);
            request.setAttribute("userNewEditForm",userNewEditForm);
            logger.debug("userNewEditForm : " + userNewEditForm); 
            logger.info("Exiting UserB4NewAction");
        /*}catch(DbsException dbsException){
            logger.info("Aborting UserB4NewAction");  
            ActionError editError=new ActionError("errors.catchall",dbsException.getErrorMessage());
            errors.add(ActionErrors.GLOBAL_ERROR,editError);*/
        }catch(Exception exception){
            logger.info("Aborting UserB4NewAction");  
            logger.info(exception);
            ActionError editError=new ActionError("errors.catchall",exception);
            errors.add(ActionErrors.GLOBAL_ERROR,editError);
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return (mapping.getInputForward());
        }
        return mapping.findForward("success");
    }
}
