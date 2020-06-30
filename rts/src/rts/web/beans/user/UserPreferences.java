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
 * $Id: UserPreferences.java,v 20040220.17 2005/03/29 04:41:36 manish Exp $
 *****************************************************************************
 */
package rts.web.beans.user;
import org.apache.log4j.*;
import java.io.File;
/** 
 *	Purpose: To hold UserPreferences
 *  @author              Jeetendra Prasad
 *  @version             1.0
 * 	Date of creation:   20-01-2004
 * 	Last Modfied by :   Suved Mishra  
 * 	Last Modfied Date:  27-12-2004.  
 */
 
public class UserPreferences  {

  private int treeLevel;
  private String treeIconPath;
  private int recordsPerPage;
  //private String stylePlaceHolder;
  private int navigationType;
  private int chkOpenDocInNewWin; /*variable for open doc option */
  
  public static int FLAT_NAVIGATION = 1;
  public static int TREE_NAVIGATION = 2;
  public static int BRAIN_TREE_NAVIGATION = 3;

  /* constants for open doc option */
  public static int IN_SAME_FRAME = 1;
  public static int IN_NEW_WINDOW = 2;
  
  private String custom1_lbl;                    /* 4 custom labels for group */
  private String custom2_lbl;
  private String custom3_lbl;
  private String custom4_lbl;
  
  
    public UserPreferences() {
        treeLevel = 3;
        treeIconPath = "themes/images";
        recordsPerPage = 10;
        navigationType = TREE_NAVIGATION;
        chkOpenDocInNewWin=IN_NEW_WINDOW; /* initialize to new window option */
    }

    public int getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(int newTreeLevel) {
        treeLevel = newTreeLevel;
    }

    public String getTreeIconPath() {
    return treeIconPath;
    }

    public void setTreeIconPath(String newTreeIconPath) {
    treeIconPath = newTreeIconPath;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int newRecordsPerPage) {
        recordsPerPage = newRecordsPerPage;
    }

    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
          strTemp += "\n\ttreeLevel : " + treeLevel;
          strTemp += "\n\ttreeIconPath : " + treeIconPath;
          strTemp += "\n\trecordsPerPage : " + recordsPerPage;
          strTemp += "\n\tchkOpenDocInNewWin : "+chkOpenDocInNewWin;
          strTemp += "\n\tCustom1_lbl : "+custom1_lbl;
          strTemp += "\n\tCustom2_lbl : "+custom2_lbl;
          strTemp += "\n\tCustom3_lbl : "+custom3_lbl;
          strTemp += "\n\tCustom4_lbl : "+custom4_lbl;
          strTemp += "\n\tNavigationType  :"+navigationType;  
        }
        return strTemp;
    }

    /*public String getStylePlaceHolder() {
    return stylePlaceHolder;
  }

  public void setStylePlaceHolder(String newStylePlaceHolder) {
    stylePlaceHolder = newStylePlaceHolder;
  }*/

  /**
   * Purpose  : get custom1 label value
   * @return  : String custom1 label value.      
   */
  public String getCustom1_lbl() {
    return custom1_lbl;
  }

  /**
   * Purpose  : Sets custom1 label value
   * @param   : String custom1 label value.
   */
  public void setCustom1_lbl(String custom1_lbl) {
    this.custom1_lbl = custom1_lbl;
  }

  /**
   * Purpose  : get custom2 label value
   * @return  : String custom2 label value.      
   */
  public String getCustom2_lbl() {
    return custom2_lbl;
  }

  /**
   * Purpose  : Sets custom2 label value
   * @param   : String custom2 label value.
   */
  public void setCustom2_lbl(String custom2_lbl) {
    this.custom2_lbl = custom2_lbl;
  }

  /**
   * Purpose  : get custom3 label value
   * @return  : String custom3 label value.      
   */
  public String getCustom3_lbl() {
    return custom3_lbl;
  }

  /**
   * Purpose  : Sets custom3 label value
   * @param   : String custom3 label value.
   */
  public void setCustom3_lbl(String custom3_lbl) {
    this.custom3_lbl = custom3_lbl;
  }

  /**
   * Purpose  : get custom4 label value
   * @return  : String custom4 label value.      
   */
  public String getCustom4_lbl() {
    return custom4_lbl;
  }

  /**
   * Purpose  : Sets custom4 label value
   * @param   : String custom4 label value.
   */
  public void setCustom4_lbl(String custom4_lbl) {
    this.custom4_lbl = custom4_lbl;
  }


    public int getNavigationType() {
        return navigationType;
    }

    public void setNavigationType(int newNavigationType) {
        navigationType = newNavigationType;
    }

    /**
     * Purpose  : Returns Open Document Option.
     * @return  : int value.
     */
    public int getChkOpenDocInNewWin(){
        return chkOpenDocInNewWin;      
    }

    /**
     * Purpose  : Sets Open Document Option
     * @param   : checkbox status.
     */
    public void setChkOpenDocInNewWin(int chkOpenDocInNewWin){
        this.chkOpenDocInNewWin=chkOpenDocInNewWin;      
    }

    
}
