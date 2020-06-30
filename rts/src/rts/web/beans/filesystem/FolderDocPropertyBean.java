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
 * $Id: FolderDocPropertyBean.java,v 1.2 2005/07/15 10:42:57 rajan Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;
 
public class FolderDocPropertyBean {
    private String folderDocType;
    private String folderDocDescription;
    private String docWorkFlowStatus;
    private String folderDocLocation;
    private String folderDocSize;
    private String createdDate;
    private String createdBy;
    private String modifiedDate;
    private String modifiedBy;
    private String aclName;
    private String versioned;
    private String folderDocName;
    private String folderDocPath;
    private Long[] folderDocIds;
    private boolean folderShared;
    private int folderCount;
    private int folderDocCount;
    private boolean shared;
    private int documentCount;
    Boolean aclError;

    public String getFolderDocType() {
        return folderDocType;
    }

    public void setFolderDocType(String newFolderDocType) {
        folderDocType = newFolderDocType;
    }

    public String getFolderDocDescription() {
        return folderDocDescription;
    }

    public void setFolderDocDescription(String newFolderDocDescription) {
        folderDocDescription = newFolderDocDescription;
    }

    public String getFolderDocLocation() {
        return folderDocLocation;
    }

    public void setFolderDocLocation(String newFolderDocLocation) {
        folderDocLocation = newFolderDocLocation;
    }

    public String getFolderDocSize() {
        return folderDocSize;
    }

    public void setFolderDocSize(String newFolderDocSize) {
        folderDocSize = newFolderDocSize;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String newCreatedDate) {
        createdDate = newCreatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String newCreatedBy) {
        createdBy = newCreatedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String newModifiedDate) {
        modifiedDate = newModifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String newModifiedBy) {
        modifiedBy = newModifiedBy;
    }

    public String getVersioned() {
        return versioned;
    }

    public void setVersioned(String newVersioned) {
        versioned = newVersioned;
    }

    public String getFolderDocName() {
        return folderDocName;
    }

    public void setFolderDocName(String newFolderDocName) {
        folderDocName = newFolderDocName;
    }

    public String getFolderDocPath() {
      return folderDocPath;
    }

    public void setFolderDocPath(String newFolderDocPath) {
      folderDocPath = newFolderDocPath;
    }

    public String getAclName() {
        return aclName;
    }

    public void setAclName(String newAclName) {
        aclName = newAclName;
    }



    public boolean isFolderShared() {
        return folderShared;
    }

    public void setFolderShared(boolean newFolderShared) {
        folderShared = newFolderShared;
    }

    public int getFolderCount() {
        return folderCount;
    }

    public void setFolderCount(int newFolderCount) {
        folderCount = newFolderCount;
    }

    public int getFolderDocCount() {
        return folderDocCount;
    }

    public void setFolderDocCount(int newFolderDocCount) {
        folderDocCount = newFolderDocCount;
    }

    public Long[] getFolderDocIds() {
        return folderDocIds;
    }

    public void setFolderDocIds(Long[] newFolderDocIds) {
        folderDocIds = newFolderDocIds;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean newShared) {
        shared = newShared;
    }

    public int getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(int newDocumentCount) {
        documentCount = newDocumentCount;
    }

    public Boolean isAclError() {
        return aclError;
    }

    public void setAclError(Boolean newAclError) {
        aclError = newAclError;
    }

  public String getDocWorkFlowStatus() {
    return docWorkFlowStatus;
  }

  public void setDocWorkFlowStatus(String docWorkFlowStatus) {
    this.docWorkFlowStatus = docWorkFlowStatus;
  }
}
