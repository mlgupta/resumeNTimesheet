package rts.web.actions.preferences;
/*rts package references */
import rts.beans.*;
import rts.web.actionforms.preferences.SetEncryptionPasswordForm;
import rts.web.beans.crypto.CryptographicUtil;
import rts.web.beans.user.UserInfo;
/*java API */
import java.io.IOException;
import java.io.*;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
/*Struts API */
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;

/**
 *	Purpose: To set URLEncryption Password for encrypting/decrypting links.
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    26-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class SetURLEncryptPasswordAction extends Action 
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
    HttpSession httpSession=null;
    UserInfo userInfo=null;
    Logger logger=Logger.getLogger("DbsLogger");
    Locale locale=getLocale(request);
    DbsLibrarySession dbsLibrarySession=null;
    DbsTransaction editTransaction=null;
    String editUserName=new String();
    ActionErrors errors=new ActionErrors();
    
    
    try{
      logger.debug("Entering SetURLEncryptPasswordAction...");
      httpSession=request.getSession(false);
      userInfo=(UserInfo)httpSession.getAttribute("UserInfo");
      dbsLibrarySession=userInfo.getDbsLibrarySession();
      
      String password= request.getParameter("txtPassword");
      //logger.debug("txtPassword: "+password);
      CryptographicUtil cryptoUtil= new CryptographicUtil();
      /* obtain servlet context path */
      String contextPath=(String)httpSession.getServletContext().getAttribute("contextPath");
      /* obtain the password file path */
      String pwdFilePath=cryptoUtil.getPwdFilePath(contextPath);
      /* create password file */
      File passwordFile= new File(pwdFilePath);
      FileWriter fr= new FileWriter(passwordFile);
      fr.write(password);
      fr.close();
      logger.debug("filesize upon writing: "+passwordFile.length());
      
      
      DbsDirectoryUser editUser= dbsLibrarySession.getUser();
      editUserName= editUser.getName();
      editTransaction=dbsLibrarySession.beginTransaction();
      logger.debug("Transaction commences now...");
      /* create keyStore file also */
      String result=cryptoUtil.setSystemKeystorePass(password,contextPath);
      logger.debug("result String : "+result);
      
      if(result.equals("SUCCESS")){          
          ActionMessages messages = new ActionMessages();
          ActionMessage msg = new ActionMessage("set.url.password.success");
          messages.add("message1",msg);
          httpSession.setAttribute("messages",messages);    
      }else if(result.equals("FAILURE")){
          dbsLibrarySession.abortTransaction(editTransaction);                 
          logger.debug("Transaction Aborted");
          ActionError editError=new ActionError("set.url.password.failure");
          errors.add(ActionErrors.GLOBAL_ERROR,editError);
      }else if(result.equals("KEY_GENERATION_ERROR")){
          dbsLibrarySession.abortTransaction(editTransaction);                 
          logger.debug("Transaction Aborted");
          ActionError editError=new ActionError("keygeneration.error",editUserName);
          errors.add(ActionErrors.GLOBAL_ERROR,editError);                
      }

      dbsLibrarySession.completeTransaction(editTransaction);
      editTransaction=null;
    }catch(DbsException dbsException){
        logger.error(dbsException.getErrorMessage());
        ActionError editError=new ActionError("errors.catchall",dbsException.getErrorMessage());
        errors.add(ActionErrors.GLOBAL_ERROR,editError);
    }catch(Exception exception){
        logger.error(exception);
        ActionError editError=new ActionError("errors.catchall",exception);
        errors.add(ActionErrors.GLOBAL_ERROR,editError);
    }finally{
        try{
          if(editTransaction!=null){
            dbsLibrarySession.abortTransaction(editTransaction);                 
            logger.debug("Transaction Aborted");                    
          }       
        }catch(DbsException nestedException){
            logger.info(nestedException.getErrorMessage());
        }  
    }//end finally
    if (!errors.isEmpty()) {      
      httpSession.setAttribute("errors",errors);
    } 
    logger.debug("Exiting SetURLEncryptPasswordAction...");
    
    return mapping.findForward(target);
  }
}
