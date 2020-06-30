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
 * $Id: ShowContentForm.java,v 1.2 2005/10/03 07:23:34 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.filesystem;

/*java API */
import javax.servlet.http.*;
/*Struts API */
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

/**
 *	Purpose: To Store control values of doc_generate_url.jsp
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    13-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */


public class ShowContentForm extends ActionForm 
{ 

    private boolean showContent=true;       // dummy variable
    private String[] txtLinkGenerated;      /* link String[] */

  /**
   * Purpose   : Sets the value of showContent.
   * @param    : showContent Value of showContent from the form
   */ 
    public void setShowContent(boolean showContent){
      this.showContent=showContent;
    }

  /**
   * Purpose   :  Returns  showContent.
   * @return   :  boolean 
   */ 
    public boolean isShowContent(){
      return showContent;
    }

  /**
   * Purpose   :  Returns  txtLinkGenerated.
   * @return   :  String[] 
   */
    public String[] getTxtLinkGenerated() {
        return txtLinkGenerated;
    }

  /**
   * Purpose   : Sets the value of txtLinkGenerated.
   * @param    : newTxtLinkGenerated Value of txtLinkGenerated from the form
   */
    public void setTxtLinkGenerated(String[] newTxtLinkGenerated) {
        txtLinkGenerated = newTxtLinkGenerated;
    }
  
    public String toString(){
      String strTemp=new String();
      strTemp+=new Boolean(showContent).toString();
      return strTemp;
    }
    /**
     * Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request){
      super.reset(mapping, request);
    }
  
    /**
     * Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
      return super.validate(mapping, request);
    }
}
