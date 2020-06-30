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
 * $Id: FolderDocRenameForm.java,v 1.2 2005/10/03 06:55:29 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.filesystem;

/**
 *	Purpose: To store the values of the html controls of
 *  FolderDocRenameForm in folder_doc_rename.jsp
 * 
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   04-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
 
 //Struts API
import org.apache.struts.validator.ValidatorForm;
import org.apache.log4j.*;

public class FolderDocRenameForm extends ValidatorForm  {
    private Long[] txtId;                 /* list of folder-ids for rename*/
    private String[] txtNewName;          /* new name(s) for the folder(s) */
    private String[] txtNewDesc;          /* new desc(s) for the folder(s) */

  /**
   * Purpose   :  Returns  txtId.
   * @return   :  Long[] 
   */
    public Long[] getTxtId() {
        return txtId;
    }

  /**
   * Purpose   : Sets the value of txtId.
   * @param newTxtId Value of txtId from the form
   */
    public void setTxtId(Long[] newTxtId) {
        txtId = newTxtId;
    }

  /**
   * Purpose   :  Returns  txtNewName.
   * @return   :  String[] 
   */
    public String[] getTxtNewName() {
        return txtNewName;
    }

  /**
   * Purpose   : Sets the value of txtNewName.
   * @param newTxtNewName Value of txtNewName from the form
   */
    public void setTxtNewName(String[] newTxtNewName) {
        txtNewName = newTxtNewName;
    }

    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            String strArrayValues = "";
            
            if(txtId != null){
                strArrayValues = "{";
                for(int index = 0; index < txtId.length; index++){
                    strArrayValues += " " + txtId[index];
                }
                strArrayValues += "}";
                strTemp += "\n\ttxtId : " + strArrayValues;
            }else{
                strTemp += "\n\ttxtId : " + txtId;
            }

            if(txtNewName != null){
                strArrayValues = "{";
                for(int index = 0; index < txtNewName.length; index++){
                    strArrayValues += " " + txtNewName[index];
                }
                strArrayValues += "}";
                strTemp += "\n\ttxtNewName : " + strArrayValues;
            }else{
                strTemp += "\n\ttxtNewName : " + txtNewName;
            }
        }
        return strTemp;
    }

  /**
   * Purpose   :  Returns  txtNewDesc.
   * @return   :  String[] 
   */
    public String[] getTxtNewDesc() {
      return txtNewDesc;
    }

  /**
   * Purpose   : Sets the value of txtNewDesc.
   * @param newTxtNewDesc Value of txtNewDesc from the form
   */
    public void setTxtNewDesc(String[] newTxtNewDesc) {
      txtNewDesc = newTxtNewDesc;
    }

}
