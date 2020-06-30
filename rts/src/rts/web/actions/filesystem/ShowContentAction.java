/*
 *****************************************************************************
 *                       Confidentiality Information                         *
 *                                                                           *
 * This module is the confidential and proprietary information of            *
 * DBSentry Corp.; it is not to be copied, reproduced, or transmitted in any *
 * form, by any means, in whole or in part, nor is it to be used for any     *
 * purpose other than that for which it is expressly provided without the    *
 * written permission of DBSentry Corp.                                      *
 *                                                                           *
 * Copyright (c) 2004-2005 DBSentry Corp.  All Rights Reserved.              *
 *                                                                           *
 *****************************************************************************
 * $Id: ShowContentAction.java,v 1.4 2005/07/15 10:55:29 rajan Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/*rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.crypto.CryptographicUtil;
import rts.web.beans.exception.ExceptionBean;
import rts.web.beans.user.UserInfo;
import rts.web.beans.utility.ParseXMLTagUtil;
/*java API */
import java.io.IOException;
import java.io.*;
import java.io.ByteArrayInputStream;
import java.net.*;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;


/**
 * Purpose: Action called to show doc(s) content from link(s) generated in doc_generate_url.jsp
 * @author            : Suved Mishra
 * @version           : 1.0
 * Date of Creation   : 13-01-05
 * Last Modified Date : 10-05-05
 * Last Modified By   : Suved Mishra
 */
/** The link(s) generated would have userId,userpassword & docId in encrypted form */



public class ShowContentAction extends Action 
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

    String target= new String("success");
    boolean validSession=false;
    Logger logger= Logger.getLogger("DbsLogger");
    UserInfo userInfo=null;
    ServletContext context;
    String contextPath=new String();
    DbsLibraryService dbsLibraryService=null;
    DbsLibrarySession dbsLibrarySession= null;
    DbsCleartextCredential dbsCleartextCredential;
    DbsPublicObject dbsPublicObject=null;
    String ifsSchemaPassword=new String();
    HttpSession httpSession=null;    
    ExceptionBean exceptionBean;
    logger.debug("Entering ShowContentAction now...");
    CryptographicUtil cryptoUtil=new CryptographicUtil(); 
    String encodedString=new String();
    String decryptedString=new String();
    String userId=null;
    String userPassword=null;
    Long documentId=null;
    ActionErrors errors=new ActionErrors();

      try{
      
          ShowContentForm showContentForm=(ShowContentForm)form;
          logger.debug("ShowContentForm: "+showContentForm);
          context=getServlet().getServletContext();
          contextPath=(String)context.getAttribute("contextPath");
          logger.debug("contextPath: "+contextPath);

          /*before decrypting userpassword check for the existence of .password & .keyStore*/

          /*if they exist, go ahead with decryption */
          if(cryptoUtil.getSystemKeyStoreFile(contextPath)){

            String relativePath= contextPath+"WEB-INF"+File.separator+"params_xmls"+File.separator+"GeneralActionParam.xml";

            ParseXMLTagUtil parseUtil= new ParseXMLTagUtil(relativePath);            
          
          
            /*Long documentId= new Long(request.getParameter("documentId"));
            logger.debug("documentId: "+documentId+"documentId.getClass(): "+documentId.getClass());*/          
          

            /* obtain necessary parameters*/        
            String ifsService= parseUtil.getValue("IfsService","Configuration");
            logger.debug("ifsService: "+ifsService);
            
            String serviceConfiguration= parseUtil.getValue("ServiceConfiguration","Configuration");
            logger.debug("serviceConfiguration: "+serviceConfiguration);
           

            /*obtain userId from URL */
            /*String userId=request.getParameter("userId");
            logger.debug("userId: "+userId);*/

            /*if .password exists obtain cryptoPassword for decryption */
            if(cryptoUtil.getSystemKeyStoreFile(contextPath)){
            String pwdFilePath=cryptoUtil.getPwdFilePath(contextPath);
            //logger.debug("pwdFilePath: "+pwdFilePath);

            String cryptoPassword=cryptoUtil.getCryptoPassword(pwdFilePath);
            //logger.debug("cryptoPassword: "+cryptoPassword);

            //logger.debug("username: "+(String)request.getParameter("userId"));

            /*while obtaining encodedString, URLDecoder will automatically perform it's function
              and the encodedString is now Base64Decoded to obtain a byte[]buf */
            encodedString=request.getParameter("auth");

            byte[] buf = new sun.misc.BASE64Decoder().decodeBuffer(encodedString);
            String decodedStr=URLDecoder.decode(encodedString,"UTF-8");
            logger.debug("URLDECODE: "+decodedStr);
          
            logger.debug("byteArray2 (buf)in showContentAction: "+new String(buf));
            logger.debug("byteArray2(buf).length: "+buf.length);

            /* buf is assigned to byte[]ToDecrypt*/
            byte[] byteArrayToDecrypt=buf;
            logger.debug("byteArrayToDecrypt.length: "+byteArrayToDecrypt.length);

            /*obatin byte[]Input Stream from byte[]ToDecrypt and byte[]Decrypted using 
              crypto.decryptURL()*/
            ByteArrayInputStream isStream= new ByteArrayInputStream(byteArrayToDecrypt);
            logger.debug("isStream.class: "+isStream.getClass());
            ByteArrayInputStream deciStream=(ByteArrayInputStream)cryptoUtil.decryptURL(isStream,cryptoPassword,contextPath);

            int destavailable= deciStream.available();
            logger.debug("destavailable: "+destavailable);
            byte[] byteArrayDecrypted=new byte[destavailable];
            int decbytecount= deciStream.read(byteArrayDecrypted,0,destavailable);
            logger.debug("decbytecount: "+decbytecount);

            /*if streamreading has been successful, obtain decryptedString */
            
            if(decbytecount == destavailable){
              decryptedString=new String(byteArrayDecrypted);
              //logger.debug("decryptedString: "+decryptedString);
              
              /* split decryptedString to obtain userId,userPassword,documentId  */
              
              /* obtain documentId  */
              String[] params=decryptedString.split("=");
              /*for(int i=0;i<params.length;i++)
                logger.debug("params["+i+"]: "+params[i]);*/
              
              documentId = new Long(params[params.length-1]);  
              
              /* obtain userId  */
              String[] param4userid = params[1].split("&");
              /*for(int i=0;i<param4userid.length;i++)
                logger.debug("param4userid["+i+"]: "+param4userid[i]);*/
              userId = param4userid[0];           
              
              /* obtain userPassword  */  
              String[] param4pwd = params[2].split("&");
              /*for(int i=0;i<param4pwd.length;i++)
                logger.debug("param4pwd["+i+"]: "+param4pwd[i]);*/
              userPassword = param4pwd[0];          
            
            }else{
              logger.error("error in reading sream...");
            }
             
            /*start libraryService,librarySession */
            dbsCleartextCredential= new DbsCleartextCredential(userId,userPassword);

            if(DbsLibraryService.isServiceStarted(ifsService)){
              logger.info(ifsService + " is running");
              dbsLibraryService = DbsLibraryService.findService("IfsDefaultService");
            }/*else{          
              dbsLibraryService=DbsLibraryService.startService(ifsService,ifsSchemaPassword,serviceConfiguration,domain);          
              logger.debug("dbsLibraryService started: "+DbsLibraryService.isServiceStarted(ifsService));
            }*/
          
            try{
              dbsLibrarySession=dbsLibraryService.connect(dbsCleartextCredential,null);
            }catch(DbsException dex){
              logger.error(dex.getErrorMessage());
              logger.debug("errortrace: "+dex.getStackTrace());
              if(dex.containsErrorCode(10170)){
                throw dex;
              }
              else if(dex.containsErrorCode(21008)){
                logger.info("This exception is thrown when library service started successfully");
                logger.info("and then database went down");
                logger.info("In this case Library Service need to be restarted");

                ifsSchemaPassword = parseUtil.getValue("IfsSchemaPassword","Configuration");
                serviceConfiguration = parseUtil.getValue("ServiceConfiguration","Configuration");
                String domain = parseUtil.getValue("Domain","Configuration");
                
                /*ifsSchemaPassword = context.getInitParameter("IfsSchemaPassword");
                serviceConfiguration = context.getInitParameter("ServiceConfiguration");
                domain = context.getInitParameter("Domain"); */

                logger.info("Disposing Library Service...");
                dbsLibraryService.dispose(ifsSchemaPassword);
                logger.info("Disposing Library Service Complete");

                logger.info("ReStarting Library Service...");            
                dbsLibraryService = DbsLibraryService.startService(ifsService,ifsSchemaPassword,serviceConfiguration,domain);
                logger.info("ReStarting Library Service Complete");
                dbsLibrarySession = dbsLibraryService.connect(dbsCleartextCredential,null);              
              }
            }

            /*obtain doc as dbsPublicObject using docId from URL */
            dbsPublicObject=dbsLibrarySession.getPublicObject(documentId).getResolvedPublicObject();
          } 
        
        /*if dbsPublicObject is an instance of DbsDocument read it's content */
        if(dbsPublicObject instanceof DbsDocument){
          DbsDocument dbsDocument=(DbsDocument)dbsPublicObject;
        
          logger.debug("Doc name: "+dbsDocument.getName());
          InputStream inputStream= dbsDocument.getContentStream();
          DbsFormat dbsFormat= dbsDocument.getFormat();
          String mimeType=dbsFormat.getMimeType();
          logger.debug("Doc mimeType: "+mimeType);

          response.setContentType(mimeType);
          response.setHeader("Content-Disposition","filename=\""+ dbsDocument.getName() + "\"");
          response.setContentLength((int)dbsDocument.getContentSize());
          logger.debug("Doc ContentSize: "+dbsDocument.getContentSize());

          byte[] content= new byte[(int)dbsDocument.getContentSize()];
          inputStream.read(content,0,(int)dbsDocument.getContentSize());
          inputStream.close();

          OutputStream outputStream= response.getOutputStream();
          outputStream.write(content);
          outputStream.close();
          response.flushBuffer();

          /*if(!validSession){
            logger.debug("Disposing libraryService now...");
            dbsLibraryService.dispose(ifsSchemaPassword);
          }*/
          dbsLibrarySession.disconnect();
        }

      }else{/*if .password & .keyStore donot exist setup an ActionError */
        ActionError actionError=new ActionError("errors.in.displaying.content");
        errors.add(ActionErrors.GLOBAL_ERROR,actionError);
        saveErrors(request,errors);
       try{
          if(userInfo==null)
            target=new String("failureuserInfoNull");
          
          if(userInfo!=null)
            target=new String("failureInSession");
         
          }catch(Exception e){
            target=new String("failureuserInfoNull");
          }

        }

    }catch(DbsException dex){
      exceptionBean = new ExceptionBean(dex);
      if(dex.containsErrorCode(10201)){
        exceptionBean.setMessageKey("errors.unable.to.access.file");
      }
      logger.error(exceptionBean.getMessage());
      saveErrors(request,exceptionBean.getActionErrors());
       try{
          if(userInfo==null)
            target=new String("failureuserInfoNull");
          
          if(userInfo!=null)
            target=new String("failureInSession");
         
          }catch(Exception e){
            target=new String("failureuserInfoNull");
          }
      
    }catch(Exception ex){
      ActionError actionError = new ActionError("errors.unable.to.display.file");
      errors.add(ActionErrors.GLOBAL_ERROR,actionError);            
      saveErrors(request,errors);
       try{
          if(userInfo==null)
            target=new String("failureuserInfoNull");
          
          if(userInfo!=null)
            target=new String("failureInSession");
         
          }catch(Exception e){
            target=new String("failureuserInfoNull");
          }
      
    }

    if(target.equals("success"))
      return null;

    else
      return mapping.findForward(target);
  }   
}
