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
 * $Id: DocGenerateLinkAction.java,v 1.7 2005/08/30 11:46:14 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/*rts package references*/
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.crypto.CryptographicUtil;
import rts.web.beans.exception.ExceptionBean;
import rts.web.beans.filesystem.FolderDocList;
import rts.web.beans.user.UserInfo;
import rts.web.beans.utility.ParseXMLTagUtil;
/*java API */
import java.io.IOException;
import java.io.*;
import java.io.ByteArrayInputStream;
import java.net.*;
import java.util.*;
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
 * Purpose: Action called to generate link(s) for documents(s)(only)
 * @author            : Suved Mishra
 * @version           : 1.0
 * Date of Creation   : 13-01-05
 * Last Modified Date : 10-05-05
 * Last Modified By   : Suved Mishra
 */
/** The link(s) generated would have userId,userpassword & docId in encrypted form */


public class DocGenerateLinkAction extends Action{ 

/**
 * This is the main action called from the Struts framework.
 * @param mapping The ActionMapping used to select this instance.
 * @param form The optional ActionForm bean for this request.
 * @param request The HTTP Request we are processing.
 * @param response The HTTP Response we are processing.
 */
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

  String target= new String("success");
  String UserId=new String();
  DbsLibrarySession dbsLibrarySession= null;
  HttpSession httpSession= null;    
  ExceptionBean exceptionBean;
  ActionErrors errors=new ActionErrors();
  Locale locale = getLocale(request);
  CryptographicUtil crypto=new CryptographicUtil();
  String cryptoPassword=new String();
  String encryptedString=new String();
  ArrayList DocLinkLists= new ArrayList();

  Logger logger= Logger.getLogger("DbsLogger");
  logger.debug("Debugging DocGenerateLinkAction: ");

  try{
    httpSession= request.getSession(false);
    FolderDocListForm folderDocListForm=(FolderDocListForm)form;
    logger.debug("folderDocListForm: "+folderDocListForm);

      /*obtain selected doc-ids */
    Long []selectedDocIds= folderDocListForm.getChkFolderDocIds();
    logger.debug("selectedDocIds length: "+selectedDocIds.length);

    /*obtain userId,userPassword */
    UserInfo userInfo= (UserInfo)httpSession.getAttribute("UserInfo");
    logger.debug("UserInfo: "+userInfo);
    dbsLibrarySession= userInfo.getDbsLibrarySession();
    String userID= dbsLibrarySession.getUser().getName();
    String userPassword=(String)httpSession.getAttribute("password");
    
    /*obtain contextPath,SystemKeyStorePath,passwordFilePath */
    String contextPath=(String)
                    httpSession.getServletContext().getAttribute("contextPath");
    String keyPath=crypto.getSystemKeyStorePath(contextPath);
    //logger.debug("keyPath: "+keyPath);
    String pwdFilePath=crypto.getPwdFilePath(contextPath);
    //logger.debug("pwdFilePath: "+pwdFilePath);
    
    logger.debug("keyPath and password file paths obtained.");
    
    String relativePath= contextPath+"WEB-INF"+File.separator+"params_xmls"+
                         File.separator+"GeneralActionParam.xml";

    ParseXMLTagUtil parseUtil= new ParseXMLTagUtil(relativePath);            
    

    /*before link generation, check for the existence of .password & .keyStore */
    /*failure, setup an ActionError */
    if(!crypto.getSystemKeyStoreFile(contextPath)){
      target=new String("failure");
      ActionError actionError=new ActionError("errors.problem.in.linkgeneration");
      errors.add(ActionErrors.GLOBAL_ERROR,actionError);
      saveErrors(request,errors);
    }else{
    /*else go ahead to encrypt the userpassword */
    /*obtain cryptoPassword from .password, a byte[]Stream from userpassword, encrypt using 
      crypto.encryptURL()*/

    /*obtain an arraylist of doc(s) selected in the form: name,id,txtLinkGenerated */
      for(int counter=0;counter<selectedDocIds.length;counter++){
        //logger.debug(pwdFilePath);          
        cryptoPassword=crypto.getCryptoPassword(pwdFilePath);
        
        String toBeEncrypted="userId="+userID+"&password="+userPassword+
                             "&documentId="+dbsLibrarySession.getPublicObject(
                             selectedDocIds[counter]).getId();
        
        //logger.debug("cryptoPassword in DocGenerateLinkAction : "+cryptoPassword);
        byte[] byteArrayToEncrypt= toBeEncrypted.getBytes();
        //logger.debug("byteArrayToEncrypt: "+new String(byteArrayToEncrypt));
        ByteArrayInputStream inStream= new ByteArrayInputStream(byteArrayToEncrypt);
        //logger.debug("ByteArray To Encrypt "+new String(byteArrayToEncrypt));
        logger.debug("inStream: "+inStream.getClass());
        ByteArrayInputStream enciStream=(ByteArrayInputStream)crypto.encryptURL(
                                    inStream,userID,cryptoPassword,contextPath);
        int available=enciStream.available();
        logger.debug("available: "+available);
        byte[] byteArrayEncrypted=new byte[available];
        int byteCount=enciStream.read(byteArrayEncrypted,0,available);
        //logger.debug("byteArray1 "+new String(byteArrayEncrypted));
  
        logger.debug("byteCount: "+byteCount);
        enciStream.close();
  
        /*if streamreading has been successful, encode the byte[]Encrypted to encryptedString 
          using Base64 Encoder and then URLEncode the latter */
        if(byteCount == available){
          encryptedString = new sun.misc.BASE64Encoder().encode(byteArrayEncrypted);
          logger.debug("Base64Enc "+ encryptedString);
          String encodedStr=URLEncoder.encode(encryptedString,"UTF-8");
          //request.setAttribute("EncodedString",encodedStr);
          
          //logger.debug("request.getRequestURI(): "+request.getRequestURI());
          
          /* construct linkString using request.getRequestURL().toString() */
          //String linkString= request.getRequestURL().toString().replaceAll("docGenerateLinkAction.do","showContentAction.do");
  
          /*obtain IP:Port reference from GeneralActionParams.xml */              
          String linkString= parseUtil.getValue("redirector","Configuration")+
                             request.getRequestURI().replaceAll(
                             "docGenerateLinkAction.do","showContentAction.do");
                             
          logger.debug("linkString: "+linkString);
    
          FolderDocList folderDocList;
          
          DbsPublicObject dbsPublicObject;
          
          folderDocList= new FolderDocList();          
          dbsPublicObject= dbsLibrarySession.getPublicObject(selectedDocIds[counter]);
            
          //logger.debug("dbsPublicObject.getId: "+dbsPublicObject.getId());
          folderDocList.setId(dbsPublicObject.getId());
  
          //logger.debug("dbsPublicObject.getName: "+dbsPublicObject.getName());
          folderDocList.setName(dbsPublicObject.getName());
  
          String linkGenerated =linkString+"?auth="+encodedStr;
          logger.debug("linkGenerated: "+linkGenerated);
          
          folderDocList.setTxtLinkGenerated(linkGenerated);
          DocLinkLists.add(folderDocList);
        
          logger.debug("DocLinkLists.size: "+DocLinkLists.size());
          request.setAttribute("DocLinkLists",DocLinkLists);
  
          }else{
            /*else set up an ActionError */
            target=new String("failure");
            ActionError actionError=new ActionError("errors.problem.in.streamread");
            errors.add(ActionErrors.GLOBAL_ERROR,actionError);
            saveErrors(request,errors);
          }
        
       }
    
    }
      
  }catch(DbsException dex){
      exceptionBean= new ExceptionBean(dex);
      logger.error(exceptionBean.getMessage());
      logger.debug(exceptionBean.getErrorTrace());
      saveErrors(request,exceptionBean.getActionErrors());
  }catch(Exception ex){
      exceptionBean= new ExceptionBean(ex);
      logger.error(exceptionBean.getMessage());
      logger.debug(exceptionBean.getErrorTrace());
      saveErrors(request,exceptionBean.getActionErrors());
  }
  logger.debug("Exiting DocGenerateLinkAction...");
  return mapping.findForward(target);
}
}
