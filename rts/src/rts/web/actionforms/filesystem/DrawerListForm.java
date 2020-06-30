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
 * $Id: DrawerListForm.java,v 1.4 2005/10/20 09:53:41 suved Exp $
 *****************************************************************************
 */
 
package rts.web.actionforms.filesystem;

/**
 *	Purpose: To store the values of the html controls of
 *  DrawerListForm in drawer_list.jsp
 * 
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   04-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/* Java API */
import java.util.*;
import javax.servlet.http.*; 
 //Struts API
import org.apache.struts.action.*;
import org.apache.log4j.*;

public class DrawerListForm extends ActionForm  {
    private Long[] chkFolderDocIds;       /* array of doc ids */
    private Long currentFolderId;         /* current folder id */
    private Long parentFolderId;          /* parent folder id */
    private int txtPageNo;                /* text page no */

/**
 * Purpose   :  Returns  chkFolderDocIds.
 * @return   :  Long[] 
 */
  public Long[] getChkFolderDocIds() {
      return chkFolderDocIds;
  }

/**
 * Purpose   : Sets the value of chkFolderDocIds.
 * @param newChkFolderDocIds Value of chkFolderDocIds from the form
 */
  public void setChkFolderDocIds(Long[] newChkFolderDocIds) {
      chkFolderDocIds = newChkFolderDocIds;
  }

/**
 * Purpose   :  Returns  chkFolderDocIds.
 * @return   :  Long[] 
 */
  public Long getCurrentFolderId() {
    return currentFolderId;
  }

/**
 * Purpose   : Sets the value of currentFolderId.
 * @param currentFolderId Value of currentFolderId from the form
 */
  public void setCurrentFolderId(Long currentFolderId) {
    this.currentFolderId = currentFolderId;
  }

/**
 * Purpose   :  Returns  parentFolderId.
 * @return   :  Long 
 */
  public Long getParentFolderId() {
    return parentFolderId;
  }

/**
 * Purpose   : Sets the value of parentFolderId.
 * @param parentFolderId Value of parentFolderId from the form
 */
  public void setParentFolderId(Long parentFolderId) {
    this.parentFolderId = parentFolderId;
  }

/**
 * Purpose   :  Returns  txtPageNo.
 * @return   :  int 
 */
  public int getTxtPageNo() {
    return txtPageNo;
  }

/**
 * Purpose   : Sets the value of txtPageNo.
 * @param txtPageNo Value of txtPageNo from the form
 */
  public void setTxtPageNo(int txtPageNo) {
    this.txtPageNo = txtPageNo;
  }

  public String toString(){
    String strTemp = "";
    Logger logger = Logger.getLogger("DbsLogger");
    if(logger.getLevel() == Level.DEBUG){
      strTemp += "\n\tcurrentFolderId : " + currentFolderId;
      strTemp += "\n\tparentFolderId : " +parentFolderId;
      strTemp += "\n\ttxtPageNo : " +txtPageNo;
      if( chkFolderDocIds== null || chkFolderDocIds.length == 0 ){
        strTemp += "\n\tchkFolderDocIds : "+chkFolderDocIds;  
      }else{
        for( int index = 0; index < chkFolderDocIds.length; index++ ){
          strTemp += "\n\tchkFolderDocIds["+index+"] : "+chkFolderDocIds[index];
        }
      }
      
    }
      return strTemp;
    }

}