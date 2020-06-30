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
 * $Id: FolderDocApplyAclForm.java,v 1.1.1.1 2005/05/26 12:32:04 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.filesystem;

/**
 *	Purpose: To store the values of the html controls of
 *  FolderDocListForm in folder_doc_list.jsp
 * 
 * @author              Rajan Kamal Gupta
 * @version             1.0
 * 	Date of creation:   09-02-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */ 
/* Java API */
import java.util.*;
import javax.servlet.http.*;
 //Struts API
import org.apache.struts.action.*;
import org.apache.log4j.*;

public class FolderDocApplyAclForm extends ActionForm  {
    private boolean chkApplyRecursively;
    private Long[] hdnFolderDocIds;
    private Long[] chkFolderDocIds;
    private Long radAclId;
    private String txtSearchByAclName;
    private int txtApplyAclPageNo;
    private int txtApplyAclPageCount;
    
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

    public boolean isChkApplyRecursively() {
        return chkApplyRecursively;
    }

    public void setChkApplyRecursively(boolean newChkApplyRecursively) {
        chkApplyRecursively = newChkApplyRecursively;
    }

    public Long[] getHdnFolderDocIds() {
        return hdnFolderDocIds;
    }

    public void setHdnFolderDocIds(Long[] newHdnFolderDocIds) {
        hdnFolderDocIds = newHdnFolderDocIds;
    }

    public Long getRadAclId() {
        return radAclId;
    }

    public void setRadAclId(Long newRadAclId) {
        radAclId = newRadAclId;
    }

    public Long[] getChkFolderDocIds() {
        return chkFolderDocIds;
    }

    public void setChkFolderDocIds(Long[] newChkFolderDocIds) {
        //this statement is significant this will transfer data from one form to another
        hdnFolderDocIds = newChkFolderDocIds;      
        chkFolderDocIds = newChkFolderDocIds;
    }

    public String getTxtSearchByAclName() {
    return txtSearchByAclName;
    }

    public void setTxtSearchByAclName(String newTxtSearchByAclName) {
    txtSearchByAclName = newTxtSearchByAclName;
    }

    public int getTxtApplyAclPageNo() {
        return txtApplyAclPageNo;
    }

    public void setTxtApplyAclPageNo(int newTxtApplyAclPageNo) {
        txtApplyAclPageNo = newTxtApplyAclPageNo;
    }

    public int getTxtApplyAclPageCount() {
        return txtApplyAclPageCount;
    }

    public void setTxtApplyAclPageCount(int newTxtApplyAclPageCount) {
        txtApplyAclPageCount = newTxtApplyAclPageCount;
    }

    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            String strArrayValues = "";
            strTemp += "\n\tchkApplyRecursively : " + chkApplyRecursively;
        
            if(hdnFolderDocIds != null){
                strArrayValues = "{";
                for(int index = 0; index < hdnFolderDocIds.length; index++){
                    strArrayValues += " " + hdnFolderDocIds[index];
                }
                strArrayValues += "}";
                strTemp += "\n\thdnFolderDocIds : " + strArrayValues;
            }else{
                strTemp += "\n\thdnFolderDocIds : " + hdnFolderDocIds;
            }
        

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
        
            strTemp += "\n\tradAclId : " + radAclId;
            strTemp += "\n\ttxtSearchByAclName : " + txtSearchByAclName;
            strTemp += "\n\ttxtApplyAclPageNo : " + txtApplyAclPageNo;
            strTemp += "\n\ttxtApplyAclPageCount : " + txtApplyAclPageCount;
        }
        return strTemp;
    }

}
