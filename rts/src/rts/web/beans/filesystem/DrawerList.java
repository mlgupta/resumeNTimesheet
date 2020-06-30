package rts.web.beans.filesystem;

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
 * $Id: FolderDocList.java,v 1.3 2005/07/15 10:42:57 rajan Exp $
 *****************************************************************************
 */
/**
 *	Purpose:            Bean to store drawer details for display in drawer module
 *  @author             Suved Mishra 
 *  @version            1.0
 * 	Date of creation:   06-08-2005     
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class DrawerList  {
  
    private String modifiedDate;            // last modified date 
    private String name;                    // drawer name
    private Long id;                        // drawer id  
    private String description;             // drawer description 
    private String drawerPath;              // drawer path

  /**
   * Purpose   :  Returns  modifiedDate.
   * @return   :  String 
   */
    public String getModifiedDate() {
        return modifiedDate;
    }

  /**
   * Purpose   : Sets the value of modifiedDate.
   * @param    : newModifiedDate Value of modifiedDate from the form
   */
    public void setModifiedDate(String newModifiedDate) {
        modifiedDate = newModifiedDate;
    }

  /**
   * Purpose   :  Returns  name.
   * @return   :  String 
   */
    public String getName() {
        return name;
    }

  /**
   * Purpose   : Sets the value of name.
   * @param    : newName Value of name from the form
   */
    public void setName(String newName) {
        name = newName;
    }

  /**
   * Purpose   :  Returns  id.
   * @return   :  Long 
   */
    public Long getId() {
        return id;
    }

  /**
   * Purpose   : Sets the value of id.
   * @param    : newId Value of id from the form
   */
    public void setId(Long newId) {
        id = newId;
    }

  /**
   * Purpose   :  Returns  description.
   * @return   :  String 
   */
    public String getDescription() {
        return description;
    }

  /**
   * Purpose   : Sets the value of description.
   * @param    : newDescription Value of description from the form
   */
    public void setDescription(String newDescription) {
        description = newDescription;
    }

  /**
   * Purpose   :  Returns  drawerPath.
   * @return   :  String 
   */
    public String getDrawerPath() {
      return drawerPath;
    }

  /**
   * Purpose   : Sets the value of drawerPath.
   * @param    : drawerPath Value of drawerPath from the form
   */
    public void setDrawerPath(String drawerPath) {
      this.drawerPath = drawerPath;
    }
}