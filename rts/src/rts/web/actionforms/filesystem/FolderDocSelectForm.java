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
 * $Id: FolderDocSelectForm.java,v 1.4 2005/10/03 06:57:46 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.filesystem;
/* Java API */
import javax.servlet.http.HttpServletRequest;
/* Struts API */ 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

/**
 *	Purpose: To Store FolderDocSelect control values
 *  @author             Sudheer Pujar
 *  @version            1.0
 * 	Date of creation:   04-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class FolderDocSelectForm extends ActionForm  {
  private String folderDocument;            
  private String hdnOpenerControl;
  private boolean hdnFoldersOnly=true;
  private String hdnFolderName;
  private String hdnFolderDocument;
  private String hdnFolderDesc;
  //private boolean boolChkDocOverWrite;

  public String getFolderDocument() {
    return folderDocument;
  }

  public void setFolderDocument(String newFolderDocument) {
    folderDocument = newFolderDocument;
  }

  public String getHdnOpenerControl() {
    return hdnOpenerControl;
  }

  public void setHdnOpenerControl(String newHdnOpenerControl) {
    hdnOpenerControl = newHdnOpenerControl;
  }

  public boolean isHdnFoldersOnly() {
    return hdnFoldersOnly;
  }

  public void setHdnFoldersOnly(boolean newHdnFoldersOnly) {
    hdnFoldersOnly = newHdnFoldersOnly;
  }

  public String getHdnFolderName() {
    return hdnFolderName;
  }

  public void setHdnFolderName(String newHdnFolderName) {
    hdnFolderName = newHdnFolderName;
  }

  public String getHdnFolderDocument() {
    return hdnFolderDocument;
  }

  public void setHdnFolderDocument(String newHdnFolderDocument) {
    hdnFolderDocument = newHdnFolderDocument;
  }

  public String getHdnFolderDesc() {
    return hdnFolderDesc;
  }

  public void setHdnFolderDesc(String hdnFolderDesc) {
    this.hdnFolderDesc = hdnFolderDesc;
  }

  public String toString(){
    String strTemp = "";
    strTemp += "\n\tfolderDocument : " + folderDocument;
    strTemp += "\n\thdnOpenerControl : " + hdnOpenerControl;
    strTemp += "\n\thdnFoldersOnly : " + hdnFoldersOnly;
    strTemp += "\n\thdnFolderName : " + hdnFolderName;
    strTemp += "\n\thdnFolderDocument : " + hdnFolderDocument;
    strTemp += "\n\thdnFolderDesc : " + hdnFolderDesc;
    return strTemp;    
  }
 /*public boolean getBoolChkDocOverWrite() {
    return boolChkDocOverWrite;
 }
 
 public void setBoolChkDocOverWrite(boolean newBoolChkDocOverWrite) {
    boolChkDocOverWrite = newBoolChkDocOverWrite;
 }*/
 
}
