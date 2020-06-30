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
 * $Id: AdvanceSearchForm.java,v 1.5 2005/10/03 05:07:41 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.filesystem; 

/**
 *	Purpose: To store the values of the html controls of
 *  AdvanceSearchForm in advance_search.jsp
 * 
 * @author              Rajan Kamal Gupta
 * @version             1.0
 * 	Date of creation:   05-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
 */

//Struts API
import org.apache.struts.action.ActionForm;
import org.apache.log4j.*;

public class AdvanceSearchForm extends ActionForm {
    private String txtContainingText;       /* for content-based search */
    private String txtLookinFolderPath;     /* for target folder path */
    private String txtDocDescription;       /* for description-based search */
    private String txtFolderOrDocName;      /* folder/doc name to be searched */
    private Long currentFolderId;           /* current folderid during search */
  /*private boolean chkDateSelected;
    private boolean chkDocTypeSelected;
    private boolean chkSizeSelected;
    private boolean chkAdvanceFeatureSelected;
    private boolean chkSubFoldersSearch=true;
    private boolean chkCaseSensitiveSearch;
    private byte cboDateOption;
    private byte cboSizeOption;
    private long txtDocSize;
    private String txtFromDate;
    private String txtToDate;
    private Long cboDocType=null;
    private boolean advancedOptionEnabled;*/

    /**
     * Purpose   :  Returns  txtFolderOrDocName.
     * @return   :  String 
     */
    public String getTxtFolderOrDocName() {
        return txtFolderOrDocName;
    }

    /**
     * Purpose   : Sets the value of txtFolderOrDocName.
     * @param newTxtFolderOrDocName Value of txtFolderOrDocName from the form
     */
    public void setTxtFolderOrDocName(String newTxtFolderOrDocName) {
        txtFolderOrDocName = newTxtFolderOrDocName;
    }

    /**
     * Purpose   :  Returns  txtContainingText.
     * @return   :  String 
     */
    public String getTxtContainingText() {
        return txtContainingText;
    }

    /**
     * Purpose   : Sets the value of txtContainingText.
     * @param newTxtContainingText Value of txtContainingText from the form
     */
    public void setTxtContainingText(String newTxtContainingText) {
        txtContainingText = newTxtContainingText;
    }

    /**
     * Purpose   :  Returns  txtLookinFolderPath.
     * @return   :  String 
     */

    public String getTxtLookinFolderPath() {
        return txtLookinFolderPath;
    }

    /**
     * Purpose   : Sets the value of txtLookinFolderPath.
     * @param newTxtLookinFolderPath Value of txtLookinFolderPath from the form
     */

    public void setTxtLookinFolderPath(String newTxtLookinFolderPath) {
        txtLookinFolderPath = newTxtLookinFolderPath;
    }

    /**
     * Purpose   :  Returns  currentFolderId.
     * @return   :  String 
     */

    public Long getCurrentFolderId() {
        return currentFolderId;
    }

    /**
     * Purpose   : Sets the value of currentFolderId.
     * @param newCurrentFolderId Value of currentFolderId from the form
     */

    public void setCurrentFolderId(Long newCurrentFolderId) {
        currentFolderId = newCurrentFolderId;
    }

    /**
     * Purpose   :  Returns  txtDocDescription.
     * @return   :  String 
     */

    public String getTxtDocDescription()
    {
      return txtDocDescription;
    }

    /**
     * Purpose   : Sets the value of txtDocDescription.
     * @param newTxtDocDescription Value of txtDocDescription from the form
     */
  
    public void setTxtDocDescription(String newTxtDocDescription)
    {
      txtDocDescription = newTxtDocDescription;
    }

    /**
     * Purpose  : override default toString() behaviour,for custom repesentation 
     */
     
    public String toString(){

        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            strTemp += "\n\ttxtFolderOrDocName : " + txtFolderOrDocName;
            strTemp += "\n\ttxtContainingText : " + txtContainingText;
            strTemp += "\n\ttxtLookinFolderPath : " + txtLookinFolderPath;
            strTemp += "\n\ttxtDocDescription   : " + txtDocDescription;
            strTemp += "\n\tcurrentFolderId     : " + currentFolderId;
            //strTemp += "\n\tchkAdvanceFeatureSelected : " + chkAdvanceFeatureSelected;

            //strTemp += "\n\tchkDateSelected : " + chkDateSelected;
            //strTemp += "\n\tcboDateOption : " + cboDateOption;
            //strTemp += "\n\ttxtFromDate : " + txtFromDate;
            //strTemp += "\n\ttxtToDate : " + txtToDate;

            //strTemp += "\n\tchkDocTypeSelected : " + chkDocTypeSelected;
            //strTemp += "\n\tcboDocType : " + cboDocType;
            
            //strTemp += "\n\tchkSizeSelected : " + chkSizeSelected;
            //strTemp += "\n\tcboSizeOption : " + cboSizeOption;
            //strTemp += "\n\ttxtDocSize : " + txtDocSize;

            //strTemp += "\n\tchkSubFoldersSearch : " + chkSubFoldersSearch;
            //strTemp += "\n\tchkCaseSensitiveSearch : " + chkCaseSensitiveSearch; 
            
        }
        return strTemp;
    }

    /**
     * Purpose   :  Returns  txtFromDate.
     * @return   :  String 
     */
    /*public String getTxtFromDate() {
        return txtFromDate;
    }*/

    /**
     * Purpose   : Sets the value of txtFromDate.
     * @param newTxtFromDate Value of txtFromDate from the form
     */
    /*public void setTxtFromDate(String newTxtFromDate) {
        txtFromDate = newTxtFromDate;
    }*/

    /**
     * Purpose   :  Returns  txtToDate.
     * @return   :  String 
     */
    /*public String getTxtToDate() {
        return txtToDate;
    }*/

    /**
     * Purpose   : Sets the value of txtToDate.
     * @param newTxtToDate Value of txtToDate from the form
     */
    /*public void setTxtToDate(String newTxtToDate) {
        txtToDate = newTxtToDate;
    }

    public long getTxtDocSize() {
        return txtDocSize;
    }

    public void setTxtDocSize(long newTxtDocSize) {
        txtDocSize = newTxtDocSize;
    }


    public Long getCboDocType() {
        return cboDocType;
    }

    public void setCboDocType(Long newCboDocType) {
        cboDocType = newCboDocType;
    }

    public byte getCboSizeOption() {
        return cboSizeOption;
    }

    public void setCboSizeOption(byte newCboSizeOption) {
        cboSizeOption = newCboSizeOption;
    }*/


    /*public boolean isChkAdvanceFeatureSelected() {
        return chkAdvanceFeatureSelected;
    }

    public void setChkAdvanceFeatureSelected(boolean newChkAdvanceFeatureSelected) {
        chkAdvanceFeatureSelected = newChkAdvanceFeatureSelected;
    }

    public boolean isChkSubFoldersSearch() {
        return chkSubFoldersSearch;
    }

    public void setChkSubFoldersSearch(boolean newChkSubFoldersSearch) {
        chkSubFoldersSearch = newChkSubFoldersSearch;
    }

    public boolean isChkCaseSensitiveSearch() {
        return chkCaseSensitiveSearch;
    }

    public void setChkCaseSensitiveSearch(boolean newChkCaseSensitiveSearch) {
        chkCaseSensitiveSearch = newChkCaseSensitiveSearch;
    }

    public byte getCboDateOption() {
        return cboDateOption;
    }

    public void setCboDateOption(byte newCboDateOption) {
        cboDateOption = newCboDateOption;
    }

    public boolean isChkSizeSelected() {
        return chkSizeSelected;
    }

    public void setChkSizeSelected(boolean newChkSizeSelected) {
        chkSizeSelected = newChkSizeSelected;
    }

    public void setChkDateSelected(boolean newChkDateSelected) {
        chkDateSelected = newChkDateSelected;
    }

    public void setChkDocTypeSelected(boolean newChkDocTypeSelected) {
        chkDocTypeSelected = newChkDocTypeSelected;
    }*/

    /*public boolean isAdvancedOptionEnabled() {
        return advancedOptionEnabled;
    }

    public void setAdvancedOptionEnabled(boolean newAdvancedOptionEnabled) {
        advancedOptionEnabled = newAdvancedOptionEnabled;
    }

    public boolean isChkDateSelected() {
        return chkDateSelected;
    }

    public boolean isChkDocTypeSelected() {
        return chkDocTypeSelected;
    }*/


}
