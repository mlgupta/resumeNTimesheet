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
 * $Id: ResetBean.java,v 1.3 2005/07/15 10:42:57 rajan Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;

/**
 *	Purpose: To reset the values of the html controls of
 *  AdvanceSearchForm when Reset button is clicked.
 * 
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   18-12-2004
 * 	Last Modified by :     
 * 	Last Modified Date:    
 */


/* rts package references */
import rts.web.actionforms.filesystem.AdvanceSearchForm;
import rts.web.actions.filesystem.AdvanceSearchAction;
import rts.web.beans.user.UserPreferences;
/* Struts API */
import org.apache.log4j.*;

public class ResetBean  {
  public AdvanceSearchForm resetAll(AdvanceSearchForm advsForm, int navType){

        Logger logger = Logger.getLogger("DbsLogger");

        //advsForm.setTxtContainingText(new String());
        advsForm.setTxtDocDescription(new String());
        if(navType == UserPreferences.TREE_NAVIGATION){
          //advsForm.setAdvancedOptionEnabled(false);
        }

          //advsForm.setChkDateSelected(false);
          //advsForm.setCboDateOption((byte)AdvanceSearchAction.LASTMODIFIEDDATE);
          //advsForm.setTxtFromDate(new String());
          //advsForm.setTxtToDate(new String());
          //advsForm.setChkDocTypeSelected(false);
          //advsForm.setCboDocType(null);
          //advsForm.setChkSizeSelected(false);
          //advsForm.setCboSizeOption((byte)AdvanceSearchAction.ATLEAST);
          //advsForm.setTxtDocSize(0);
          //advsForm.setChkAdvanceFeatureSelected(true);
          //advsForm.setChkSubFoldersSearch(true);
          //advsForm.setChkCaseSensitiveSearch(false);
          logger.info("advanceSearchform exiting ResetBean: "+advsForm);
        

        
        return advsForm;
  }

}
