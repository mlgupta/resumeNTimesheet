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
 * $Id: FolderDocListForm.java,v 1.2 2005/10/03 06:36:00 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.filesystem;

/**
 *	Purpose: To store the values of the html controls of
 *  FolderDocListForm in drawers_resumes_list.jsp
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

public class FolderDocListForm extends ActionForm  {
    private Long[] chkFolderDocIds;         /* array of doc ids */
    private String txtAddress;              /* folder path */
    private Long currentFolderId;           /* current folder id */
    private Long parentFolderId;            /* parent folder id */
    private Long documentId;                /* document id */
    private int txtPageNo;                  /* current page of list display */
    private Integer overWrite;              /* Integer overWrite existing content */
    boolean blnDownload;                    /* boolean download binary obj */
    //Just dummy of no use
  //  private byte hdnActionType;

    /**
     * Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    /**
     * Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return super.validate(mapping, request);
    }

  /**
   * Purpose   :  Returns  txtAddress.
   * @return   :  String 
   */
    public String getTxtAddress() {
        return txtAddress;
    }

  /**
   * Purpose   : Sets the value of txtAddress.
   * @param newTxtAddress Value of txtAddress from the form
   */
    public void setTxtAddress(String newTxtAddress) {
        txtAddress = newTxtAddress;
    }

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
     * Purpose   :  Returns  documentId.
     * @return   :  Long 
     */
    public Long getDocumentId() {
        return documentId;
    }

  /**
   * Purpose   : Sets the value of documentId.
   * @param newDocumentId Value of documentId from the form
   */
    public void setDocumentId(Long newDocumentId) {
        documentId = newDocumentId;
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
   * @param newTxtPageNo Value of txtPageNo from the form
   */
    public void setTxtPageNo(int newTxtPageNo) {
        txtPageNo = newTxtPageNo;
    }
    
    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            String strArrayValues = "";

            if(chkFolderDocIds != null){
                strArrayValues = "{";
                for(int index = 0; index < chkFolderDocIds.length; index++){
                    strArrayValues += " " + chkFolderDocIds[index];
                }
                strArrayValues += "}";
                strTemp += "\n\tchkFolderDocIds : " + strArrayValues;
            }else{
                strTemp += "\n\tchkFolderDocIds : " + chkFolderDocIds;
            }
        
            strTemp += "\n\ttxtAddress : " + txtAddress;
            strTemp += "\n\tcurrentFolderId : " + currentFolderId;
            strTemp += "\n\tparentFolderId : " + parentFolderId;
            strTemp += "\n\tdocumentId : " + documentId;
            strTemp += "\n\ttxtPageNo : " + txtPageNo;
    //        strTemp += "\n\thdnActionType : " + hdnActionType;
        }
        return strTemp;
    }

    /**
     * Purpose   :  Returns  overWrite.
     * @return   :  Integer 
     */
    public Integer getOverWrite() {
        return overWrite;
    }

  /**
   * Purpose   : Sets the value of overWrite.
   * @param newOverWrite Value of overWrite from the form
   */
    public void setOverWrite(Integer newOverWrite) {
        overWrite = newOverWrite;
    }

    /**
     * Purpose   :  Returns  blnDownload.
     * @return   :  boolean 
     */
    public boolean isBlnDownload() {
        return blnDownload;
    }

  /**
   * Purpose   : Sets the value of blnDownload.
   * @param newBlnDownload Value of blnDownload from the form
   */
    public void setBlnDownload(boolean newBlnDownload) {
        blnDownload = newBlnDownload;
    }

/*    public byte getHdnActionType() {
        return hdnActionType;
    }

    public void setHdnActionType(byte newHdnActionType) {
        hdnActionType = newHdnActionType;
    }
*/
    
}
