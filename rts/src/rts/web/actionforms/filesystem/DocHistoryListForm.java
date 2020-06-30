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
 * $Id: DocHistoryListForm.java,v 1.2 2005/10/03 05:50:21 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.filesystem;
/** 
 *	Purpose: To store the values of the html controls of
 *  DocHistoryListForm in doc_history_list.jsp
 * 
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   19-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* Java API */
import javax.servlet.http.*;
/* Struts API */
import org.apache.struts.action.*;
import org.apache.log4j.*;


public class DocHistoryListForm extends ActionForm {
    private Long radDocId;                  /* document id selected from doc_history_list.jsp */
    private Long[] chkFolderDocIds;         /* array of doc ids*/
    private String documentName;            /* document name */
    private int txtHistoryPageNo;           /* history list page no */    
    private int txtHistoryPageCount;        /* total nos of history pages */
    
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
   * Purpose   :  Returns  radDocId.
   * @return   :  Long 
   */
    public Long getRadDocId() {
        return radDocId;
    }

  /**
   * Purpose   : Sets the value of radDocId.
   * @param newRadDocId Value of radDocId from the form
   */
    public void setRadDocId(Long newRadDocId) {
        radDocId = newRadDocId;
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
   * Purpose   :  Returns  documentName.
   * @return   :  String 
   */
    public String getDocumentName() {
        return documentName;
    }

  /**
   * Purpose   : Sets the value of documentName.
   * @param newDocumentName Value of documentName from the form
   */
    public void setDocumentName(String newDocumentName) {
        documentName = newDocumentName;
    }

  /**
   * Purpose   :  Returns  txtHistoryPageCount.
   * @return   :  String 
   */
    public int getTxtHistoryPageCount() {
        return txtHistoryPageCount;
    }

  /**
   * Purpose   : Sets the value of txtHistoryPageCount.
   * @param newTxtHistoryPageCount Value of txtHistoryPageCount from the form
   */
    public void setTxtHistoryPageCount(int newTxtHistoryPageCount) {
        txtHistoryPageCount = newTxtHistoryPageCount;
    }

  /**
   * Purpose   :  Returns  txtHistoryPageNo.
   * @return   :  String 
   */
    public int getTxtHistoryPageNo() {
        return txtHistoryPageNo;
    }

  /**
   * Purpose   : Sets the value of txtHistoryPageNo.
   * @param newTxtHistoryPageNo Value of txtHistoryPageNo from the form
   */
    public void setTxtHistoryPageNo(int newTxtHistoryPageNo) {
        txtHistoryPageNo = newTxtHistoryPageNo;
    }

    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            String strArrayValues = "";
            strTemp += "\n\tradDocId : " + radDocId;

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
            strTemp += "\n\tchkFolderDocIds : " + strArrayValues;
        
            strTemp += "\n\tdocumentName : " + documentName;
            strTemp += "\n\ttxtHistoryPageNo : " + txtHistoryPageNo;
            strTemp += "\n\ttxtHistoryPageCount : " + txtHistoryPageCount;
        }
        return strTemp;
    }
}
