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
 * $Id: FolderDocCopyMoveForm.java,v 1.2 2005/10/03 06:21:50 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.filesystem;

/**
 *	Purpose: To store the values of the html controls of
 *  FolderDocCopyForm in folder_doc_copy.jsp
 * 
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   04-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */ 
/* rts package references */ 
import rts.web.beans.filesystem.*;
 //Struts API
import org.apache.struts.validator.ValidatorForm;
import org.apache.log4j.*;

public class FolderDocCopyMoveForm extends ValidatorForm  {
    private Boolean hdnOverWrite;                /* Boolean overwrite existing content */ 
    private String hdnTargetFolderPath;          /* control to be set for selecting folders */ 
    private Long[] chkFolderDocIds;              /* array of doc ids */
    private byte hdnActionType;                  /* actionType, copy/move */ 

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

            strTemp += "\n\thdnTargetFolderPath : " + hdnTargetFolderPath;
            strTemp += "\n\thdnOverWrite : " + hdnOverWrite;
        }
        return strTemp;
    }

    /**
     * Purpose   :  Returns  hdnTargetFolderPath.
     * @return   :  String 
     */
    public String getHdnTargetFolderPath() {
      return hdnTargetFolderPath;
    }
  
    /**
     * Purpose   : Sets the value of hdnTargetFolderPath.
     * @param hdnTargetFolderPath Value of hdnTargetFolderPath from the form
     */
    public void setHdnTargetFolderPath(String hdnTargetFolderPath) {
      this.hdnTargetFolderPath = hdnTargetFolderPath;
    }

    /**
     * Purpose   :  Returns  hdnOverWrite.
     * @return   :  Boolean 
     */
    public Boolean isHdnOverWrite() {
        return hdnOverWrite;
    }

    /**
     * Purpose   : Sets the value of hdnOverWrite.
     * @param newHdnOverWrite Value of hdnOverWrite from the form
     */
    public void setHdnOverWrite(Boolean newHdnOverWrite) {
        hdnOverWrite = newHdnOverWrite;
    }

    /**
     * Purpose   :  Returns  hdnActionType.
     * @return   :  byte 
     */
    public byte getHdnActionType() {
        return hdnActionType;
    }

    /**
     * Purpose   : Sets the value of hdnActionType.
     * @param newHdnActionType Value of hdnActionType from the form
     */
    public void setHdnActionType(byte newHdnActionType) {
        hdnActionType = newHdnActionType;
    }
    
}
