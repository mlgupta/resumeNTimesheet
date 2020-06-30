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
 * $Id: DocFaxForm.java,v 1.2 2005/10/03 05:42:43 suved Exp $
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


public class DocFaxForm extends ActionForm  {
    private transient String hdnZipFileName;    /* hidden zip file name */
    private Long[] chkFolderDocIds;             /* list of doc ids */

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
   * Purpose   :  Returns  hdnZipFileName.
   * @return   :  String 
   */
    public String getHdnZipFileName() {
        return hdnZipFileName;
    }

  /**
   * Purpose   : Sets the value of hdnZipFileName.
   * @param newHdnZipFileName Value of hdnZipFileName from the form
   */
    public void setHdnZipFileName(String newHdnZipFileName) {
        hdnZipFileName = newHdnZipFileName;
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
}
