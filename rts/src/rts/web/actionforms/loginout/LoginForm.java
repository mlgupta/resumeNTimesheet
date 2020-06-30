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
 * $Id: LoginForm.java,v 1.2 2005/10/03 07:35:30 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.loginout;
/* Java API */
import javax.servlet.http.HttpServletRequest;
//Struts API
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError; 
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class LoginForm extends ValidatorForm  {
    private String userID;              /* userId entered */
    private String userPassword;        /* userPassword entered */

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
   * Purpose   :  Returns  userID.
   * @return   :  String 
   */
    public String getUserID() {
        return userID;
    }

  /**
   * Purpose   : Sets the value of userID.
   * @param    : newUserID Value of userID from the form
   */
    public void setUserID(String newUserID) {
        userID = newUserID;
    }

  /**
   * Purpose   :  Returns  userPassword.
   * @return   :  String 
   */
    public String getUserPassword() {
        return userPassword;
    }

  /**
   * Purpose   : Sets the value of userPassword.
   * @param    : newUserPassword Value of userPassword from the form
   */
    public void setUserPassword(String newUserPassword) {
        userPassword = newUserPassword;
    }



}
