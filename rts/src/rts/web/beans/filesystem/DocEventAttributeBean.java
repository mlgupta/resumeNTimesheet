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
 * $Id: DocEventAttributeBean.java,v 1.2 2005/07/15 10:42:57 rajan Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;
/*rts package references */
import rts.beans.*;
import rts.web.beans.user.UserInfo;
import rts.web.beans.filesystem.DocLogBean;
import rts.web.beans.filesystem.DocLogConstants;
import rts.web.beans.scheduler.DateHelper;
import rts.web.beans.user.UserPreferences;
/* Java API */
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/* Logger API */
import org.apache.log4j.*;


/** Purpose               : Bean used to display document attribute "AUDIT_LOG"
 * @author                : Suved Mishra
 * @version               : 1.0
 * Date Of Creation       : 19-02-2005
 * Last Modified by       :
 * Last Modification Date :
 */


public class DocEventAttributeBean  {

  Logger logger = Logger.getLogger("DbsLogger");
  private String docName = new String();
  private String docPath = new String();
  private static String DATE_FORMAT="MM/dd/yyyy HH:mm:ss";
  
  public ArrayList getEventLog(UserInfo userInfo,Long docId,String searchUser,String searchLogType,String fromDate,String toDate){
  
    DbsAttributeValue dbsAttributeValue = null;
    String[] eventArray = null;
    ArrayList eventArrayList = new ArrayList();
    DocLogBean docLogBean = null;
    DateHelper dateUtil = new DateHelper();
    logger.debug("Entering getEventArray() now...");
    logger.debug("SearchLogType: "+searchLogType);
    try{
    
    DbsLibrarySession dbsLibrarySession =userInfo.getDbsLibrarySession();
      
    DbsDocument dbsDocument = (DbsDocument)dbsLibrarySession.getPublicObject(docId);
    docName = dbsDocument.getName();
    docPath = dbsDocument.getAnyFolderPath();
    dbsAttributeValue = dbsDocument.getAttribute(DbsDocument.AUDIT_INFO);
    eventArray = dbsAttributeValue.getStringArray(dbsLibrarySession);
    
    /* if attribute is null initially, the document has not been uploaded through Samhita*/
      
    if(eventArray == null){        
      eventArray = new String[1];
      String lastModifyDate=new String();
      String userName = new String();
      String action = new String();
      eventArray[0]=userName+"\t"+lastModifyDate+"\t"+action;
      docLogBean = new DocLogBean();
      docLogBean.setEvent(eventArray[0]);
      docLogBean.setActionPerformed(action);
      docLogBean.setTimeStamp(lastModifyDate);
      docLogBean.setUserId(userName);
      eventArrayList.add(docLogBean);
      logger.error("Attribute null initially: "+eventArray[0]);
      //dbsDocument.setAttribute(DbsDocument.AUDIT_INFO,dbsAttributeValue.newAttributeValue(eventArray));
    }else{
      /* search log by searchLogType (by default: Samhita)*/
      //if(searchLogType.equalsIgnoreCase(DocLogConstants.Log_From_Samhita))  {
      
      for(int i=0;i<eventArray.length;i++){
        logger.debug("event["+i+"]: "+eventArray[i]);
          
    //    if(!eventArray[i].startsWith("Agent")){
          String [] lineItems=eventArray[i].split("\t");
          if((searchLogType.equalsIgnoreCase(DocLogConstants.Log_From_Both)) || (searchLogType.equalsIgnoreCase(lineItems[2])) ){
            /* check individual log entries for search criteria specified */
            if(checkIfValidEntry(lineItems,searchUser,fromDate,toDate)){
              docLogBean = new DocLogBean();
              docLogBean.setEvent(eventArray[i]);
              logger.debug("docLogBean.getEvent(): "+docLogBean.getEvent());
              docLogBean.setUserId(lineItems[0]);
              logger.debug("docLogBean.getUserId(): "+docLogBean.getUserId());
              docLogBean.setTimeStamp(lineItems[1]);
              logger.debug("docLogBean.getTimeStamp(): "+docLogBean.getTimeStamp());
              docLogBean.setActionPerformed(lineItems[3]);
              logger.debug("docLogBean.getActionPerformed(): "+docLogBean.getActionPerformed());
              logger.debug("LineItems Length="+lineItems.length);
              eventArrayList.add(docLogBean);
            }
          }
      //  }
      }
      
     //}
    
    }

  }catch(DbsException dex){
    logger.error("DbsException: "+dex.getMessage());
  }
   
   return eventArrayList;
        
  }

 /**Purpose: obtain doc name
   * @return String 
   */
  public String getDocName(){
    
    return docName;
  }


 /**Purpose: obtain doc path
   * @return String 
   */

  public String getDocPath(){
  
    return docPath;
  
  }
  /**Purpose: checking for valid log entry depending upon search criteria specified
   * @param lineitems
   * @param searchUser
   * @param fromDate
   * @param toDate
   * @return boolean
   */
  private boolean checkIfValidEntry(String[] lineitems,String searchUser,String fromDate,String toDate){
    
    boolean isValid = true;
    boolean boolUserSearch = true; /* boolean for usersearch if username specified */
    boolean boolFromDate = true;  /* boolean for fromDatesearch if fromDate specified */
    boolean boolToDate = true;  /* boolean for toDatesearch if toDate specified */
    //boolean boolSearchType = true;  
    String userName = new String();

    if(searchUser.equals("") || (searchUser.trim().length() == 0))
      boolUserSearch = false;
      
    if(fromDate.equals(""))
      boolFromDate = false;
      
    if(toDate.equals(""))
      boolToDate = false;
      
     /*if(searchType==null || searchType.equals(""))
      boolSearchType = false;*/
    /* if no criteria specified, the entry is valid */
    if(!boolUserSearch && !boolFromDate && !boolToDate){
      logger.debug("isValid: "+isValid);
      return isValid;
    }
    
    /* search based upon searchUser (level:-1)*/
    if(boolUserSearch){
      userName = lineitems[0];
      
      if(!checkUserValid(userName,searchUser))
        isValid = false;
      
      logger.debug("isValid: "+isValid);
      
      if(!isValid)
        return isValid;
    }
    
     /* search based upon searchType (level:-2)*/
/*    if(boolSearchType){
      String searchTypeInLog = lineitems[2];
      
      if(!checkUserValid(searchTypeInLog,searchType))
        isValid = false;
      
      logger.debug("isValid: "+isValid);
      
      if(!isValid)
        return isValid;
    }*/
    
    /* search if searchUser specified is valid and formDate is specified (level:-2)*/
    if(isValid && boolFromDate){
      DateHelper dateHelper = new DateHelper();
      Date dSearchFromDate = dateHelper.parse(fromDate,DATE_FORMAT);
      
      Date dFromDate = dateHelper.parse(lineitems[1],DATE_FORMAT);
      int i;
      if((i=dFromDate.compareTo(dSearchFromDate)) >=0){
        isValid = true;
        logger.debug("i: "+i);
      }else{
        logger.debug("i: "+i);
        isValid = false;
        logger.debug("isValid: "+isValid);
        return isValid;
      }    
      logger.debug("isValid: "+isValid);
    }
    
    
    /* search if searchUser, formDate are valid and toDate is specified (level:-3)*/
    if(isValid && boolToDate){
    
      DateHelper dateHelperNew = new DateHelper();
      Date dSearchToDate = dateHelperNew.parse(toDate,DATE_FORMAT);
      
      Date dToDate = dateHelperNew.parse(lineitems[1],DATE_FORMAT);
      int j;
      if((j=dToDate.compareTo(dSearchToDate)) <=0){
        isValid = true;
        logger.debug("j: "+j);
      }else{
        logger.debug("j: "+j);
        isValid = false;
        logger.debug("isValid: "+isValid);
        return isValid;
      }    
      logger.debug("isValid: "+isValid);
    }
    
    /* return isValid depending upon criteria satisfaction at various levels */
    logger.debug("isValid: "+isValid);
    return isValid;
  }

 
    
    /**Purpose: perform user search  
   * @param userName (userId in log)
   * @param searchUser (userName specified by user)
   * @return boolean indicating whether search criteria matches userName in log
   */
  private boolean checkUserValid(String userName,String searchUser){
    
    boolean userValid = false;
    
    /* if searchUser is of the type:- " *xyz* "*/
    if(searchUser.startsWith("*") && searchUser.endsWith("*")){
    
    String subString = searchUser.substring(searchUser.indexOf("*")+1,searchUser.lastIndexOf("*"));
    logger.debug("subString: "+subString);
    boolean found = false;
    int limit = (userName.length() - subString.length() + 1);

    if (limit > 0){
      int i=0;
      while ((i < limit)&&(!found)){
        if (userName.regionMatches(true, i, subString , 0, subString.length()))
          found = true;
        else
          i++;
      }
    }
    userValid = found;
    logger.debug("userValid: "+userValid);
    return userValid; 
      
    }
    
       
    /* if searchUser is of the type:- " *xyz "*/
    if(searchUser.startsWith("*") && !searchUser.endsWith("*")){
      
      String subString = searchUser.substring(1);
      logger.debug("subString: "+subString);
      
      if(userName.endsWith(subString))
        userValid = true;
      
      logger.debug("userValid: "+userValid);
      return userValid;  
    
    }
    
    /* if searchUser is of the type:- " xyz* "*/
    if(!searchUser.startsWith("*") && searchUser.endsWith("*")){
      
      String subString = searchUser.substring(0,searchUser.length()-1);
      logger.debug("subString: "+subString);
      
      if(userName.startsWith(subString))
        userValid = true;
      
      logger.debug("userValid: "+userValid);
      return userValid;  
      
    }
    
    /* if exact username is specified */
    if(!searchUser.startsWith("*") && !searchUser.endsWith("*")){
      
      logger.debug("searchUser: "+searchUser);
      if(userName.equalsIgnoreCase(searchUser))
        userValid = true;
      
      logger.debug("userValid: "+userValid);  
      return userValid;  
      
    }
    
    logger.debug("userValid: "+userValid);
    return userValid;
    
  }

}
