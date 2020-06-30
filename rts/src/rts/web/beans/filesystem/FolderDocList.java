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
 * $Id: FolderDocList.java,v 1.6 2005/10/04 07:53:15 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;

/**
 *	Purpose: Bean to hold information regarding folder and document
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:   20-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
 
public class FolderDocList  {
    private String modifiedDate;
    private String name;
    private String type;
    private String size;
    private Long id;
    private int item=0;
    private String treeFileName;
    private String path;
    private boolean checkedOut;
    private String className;
    boolean encripted;
    String description;
    private String txtLinkGenerated;
    private String davPath;
    /* following fields are added to account for resume class */    
    private String name1;
    private String phone1;
    private String address;
    private String email;
    private String trimAddress;
    private String trimEmail;
    /* following fields are added to account for timesheet class */
    private String csv;
    private String trimCSV;
    
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
   * Purpose   :  Returns  type.
   * @return   :  String 
   */
    public String getType() {
        return type;
    }

  /**
   * Purpose   : Sets the value of type.
   * @param    : newType Value of type from the form
   */
    public void setType(String newType) {
        type = newType;
    }

  /**
   * Purpose   :  Returns  size.
   * @return   :  String 
   */
    public String getSize() {
        return size;
    }

  /**
   * Purpose   : Sets the value of size.
   * @param    : newSize Value of size from the form
   */
    public void setSize(String newSize) {
        size = newSize;
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
   * Purpose   :  Returns  item.
   * @return   :  int 
   */
    public int getItem() {
        return item;
    }

  /**
   * Purpose   : Sets the value of items.
   * @param    : items Value of items from the form
   */
    public void setItem(int items) {
        item = items;
    }

  /**
   * Purpose   :  Returns  treeFileName.
   * @return   :  String 
   */
    public String getTreeFileName() {
        return treeFileName;
    }

  /**
   * Purpose   : Sets the value of treeFileName.
   * @param    : newTreeFileName Value of treeFileName from the form
   */
    public void setTreeFileName(String newTreeFileName) {
        treeFileName = newTreeFileName;
    }

  /**
   * Purpose   :  Returns  path.
   * @return   :  String 
   */
    public String getPath() {
        return path;
    }

  /**
   * Purpose   : Sets the value of path.
   * @param    : newPath Value of path from the form
   */
    public void setPath(String newPath) {
        path = newPath;
    }

  /**
   * Purpose   :  Returns  checkedOut.
   * @return   :  boolean 
   */
    public boolean isCheckedOut() {
        return checkedOut;
    }

  /**
   * Purpose   : Sets the value of checkedOut.
   * @param    : newCheckedOut Value of checkedOut from the form
   */
    public void setCheckedOut(boolean newCheckedOut) {
        checkedOut = newCheckedOut;
    }

  /**
   * Purpose   :  Returns  className.
   * @return   :  String 
   */
    public String getClassName() {
        return className;
    }

  /**
   * Purpose   : Sets the value of className.
   * @param    : newClassName Value of className from the form
   */
    public void setClassName(String newClassName) {
        className = newClassName;
    }

  /**
   * Purpose   :  Returns  encripted.
   * @return   :  boolean 
   */
    public boolean isEncripted() {
        return encripted;
    }

  /**
   * Purpose   : Sets the value of encripted.
   * @param    : newEncripted Value of encripted from the form
   */
    public void setEncripted(boolean newEncripted) {
        encripted = newEncripted;
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
   * Purpose   :  Returns  txtLinkGenerated.
   * @return   :  String 
   */
    public String getTxtLinkGenerated(){
        return txtLinkGenerated;
    }

  /**
   * Purpose   : Sets the value of txtLinkGenerated.
   * @param    : newTxtLinkGenerated Value of txtLinkGenerated from the form
   */
    public void setTxtLinkGenerated(String newTxtLinkGenerated){
        txtLinkGenerated = newTxtLinkGenerated;
    }

  /**
   * Purpose   :  Returns  davPath.
   * @return   :  String 
   */
    public String getDavPath(){
      return davPath;
    }

  /**
   * Purpose   : Sets the value of davPath.
   * @param    : davPath Value of davPath from the form
   */
    public void setDavPath(String davPath){
      this.davPath = davPath;
    }

  /**
   * Purpose   :  Returns  name1.
   * @return   :  String 
   */
    public String getName1() {
      return name1;
    }

  /**
   * Purpose   : Sets the value of name1.
   * @param    : name1 Value of name1 from the form
   */
    public void setName1(String name1) {
      this.name1 = name1;
    }

  /**
   * Purpose   :  Returns  phone1.
   * @return   :  String 
   */
    public String getPhone1() {
      return phone1;
    }

  /**
   * Purpose   : Sets the value of phone1.
   * @param    : phone1 Value of phone1 from the form
   */
    public void setPhone1(String phone1) {
      this.phone1 = phone1;
    }

  /**
   * Purpose   :  Returns  address.
   * @return   :  String 
   */
    public String getAddress() {
      return address;
    }

  /**
   * Purpose   : Sets the value of address.
   * @param    : address Value of address from the form
   */
    public void setAddress(String address) {
      this.address = address;
    }

  /**
   * Purpose   :  Returns  email.
   * @return   :  String 
   */
    public String getEmail() {
      return email;
    }

  /**
   * Purpose   : Sets the value of email.
   * @param    : email Value of email from the form
   */
    public void setEmail(String email) {
      this.email = email;
    }

  /**
   * Purpose   :  Returns  csv.
   * @return   :  String 
   */
    public String getCsv() {
      return csv;
    }

  /**
   * Purpose   : Sets the value of csv.
   * @param    : csv Value of csv from the form
   */
    public void setCsv(String csv) {
      this.csv = csv;
    }

  /**
   * Purpose   :  Returns  trimAddress.
   * @return   :  String 
   */
    public String getTrimAddress() {
      return trimAddress;
    }

  /**
   * Purpose   : Sets the value of trimAddress.
   * @param    : trimAddress Value of trimAddress from the form
   */
    public void setTrimAddress(String trimAddress) {
      this.trimAddress = trimAddress;
    }

  /**
   * Purpose   :  Returns  trimEmail.
   * @return   :  String 
   */
    public String getTrimEmail() {
      return trimEmail;
    }

  /**
   * Purpose   : Sets the value of trimEmail.
   * @param    : trimEmail Value of trimEmail from the form
   */
    public void setTrimEmail(String trimEmail) {
      this.trimEmail = trimEmail;
    }

  /**
   * Purpose   :  Returns  trimCSV.
   * @return   :  String 
   */
    public String getTrimCSV() {
      return trimCSV;
    }

  /**
   * Purpose   : Sets the value of trimCSV.
   * @param    : trimCSV Value of trimCSV from the form
   */
    public void setTrimCSV(String trimCSV) {
      this.trimCSV = trimCSV;
    }
}