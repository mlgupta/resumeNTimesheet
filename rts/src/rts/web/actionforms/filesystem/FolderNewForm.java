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
 * $Id: FolderNewForm.java,v 1.2 2005/10/03 07:01:25 suved Exp $
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
import org.apache.log4j.*;

public class FolderNewForm extends ActionForm  {
 

    private String hdnFolderName;             /* folder name */
    private String hdnFolderDesc;             /* folder description */

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
   * Purpose   :  Returns  hdnFolderName.
   * @return   :  String
   */
    public String getHdnFolderName() {
        return hdnFolderName;
    }

  /**
   * Purpose   : Sets the value of hdnFolderName.
   * @param newHdnFolderName Value of hdnFolderName from the form
   */
    public void setHdnFolderName(String newHdnFolderName) {
        hdnFolderName = newHdnFolderName;
    }

    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            strTemp += "\n\thdnFolderName : " + hdnFolderName;
        }
        return strTemp;
    }

  /**
   * Purpose   :  Returns  hdnFolderDesc.
   * @return   :  String 
   */
    public String getHdnFolderDesc() {
      return hdnFolderDesc;
    }

  /**
   * Purpose   : Sets the value of hdnFolderDesc.
   * @param newHdnFolderDesc Value of hdnFolderDesc from the form
   */
    public void setHdnFolderDesc(String newHdnFolderDesc) {
      hdnFolderDesc = newHdnFolderDesc;
    }

}
