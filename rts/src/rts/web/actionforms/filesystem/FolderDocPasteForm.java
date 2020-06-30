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
 * $Id: FolderDocPasteForm.java,v 1.2 2005/10/03 06:40:07 suved Exp $
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

public class FolderDocPasteForm extends ActionForm  {
    private boolean hdnOverWrite;       /* boolean overwrite existing content */
    private Integer radPasteOption = new Integer(0);

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
     * Purpose   :  Returns  hdnOverWrite.
     * @return   :  boolean 
     */
    public boolean isHdnOverWrite() {
        return hdnOverWrite;
    }

    /**
     * Purpose   : Sets the value of hdnOverWrite.
     * @param newHdnOverWrite Value of hdnOverWrite from the form
     */
    public void setHdnOverWrite(boolean newHdnOverWrite) {
        hdnOverWrite = newHdnOverWrite;
    }

    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            strTemp += "\n\thdnOverWrite : " + hdnOverWrite;
        }
        return strTemp;
    }

    /**
     * Purpose   :  Returns  radPasteOption.
     * @return   :  Integer 
     */
    public Integer getRadPasteOption() {
        return radPasteOption;
    }

    /**
     * Purpose   : Sets the value of radPasteOption.
     * @param newRadPasteOption Value of radPasteOption from the form
     */
    public void setRadPasteOption(Integer newRadPasteOption) {
        radPasteOption = newRadPasteOption;
    }
    
}
