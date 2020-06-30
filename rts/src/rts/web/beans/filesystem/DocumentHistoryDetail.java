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
 * $Id: DocumentHistoryDetail.java,v 1.5 2005/10/04 07:53:15 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;

/**
 *	Purpose:            Bean to store history details of a versioned doc
 *  @author             Suved Mishra 
 *  @version            1.0
 * 	Date of creation:   21-07-2005     
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class DocumentHistoryDetail  {
    private long versionNumber;           // version number
    private String userName;              // name of user 
    private String versionDate;           // version date
    private String actionType;            // specifies actionType
    private Long docId;                   // document Id
    private String comment;               // comment added
    private String docName;               // document name
    private String description;           // description
    private String candidateName;         // candidate name
    private String candidateEmailId;      // candidate email id  
    private String candidatePhnNo;        // candidate phone number
    private String communicationSkills;   // communication skill
    private String candidateAddress;      // candidate address
    
    public DocumentHistoryDetail() {
    }

  /**
   * Purpose   :  Returns  versionNumber.
   * @return   :  long 
   */
    public long getVersionNumber() {
        return versionNumber;
    }

  /**
   * Purpose   : Sets the value of versionNumber.
   * @param    : newVersionNumber Value of versionNumber from the form
   */
    public void setVersionNumber(long newVersionNumber) {
        versionNumber = newVersionNumber;
    }

  /**
   * Purpose   :  Returns  userName.
   * @return   :  String 
   */
    public String getUserName() {
        return userName;
    }

  /**
   * Purpose   : Sets the value of userName.
   * @param    : newUserName Value of userName from the form
   */
    public void setUserName(String newUserName) {
        userName = newUserName;
    }

  /**
   * Purpose   :  Returns  versionDate.
   * @return   :  String 
   */
    public String getVersionDate() {
        return versionDate;
    }

  /**
   * Purpose   : Sets the value of versionDate.
   * @param    : newVersionDate Value of versionDate from the form
   */
    public void setVersionDate(String newVersionDate) {
        versionDate = newVersionDate;
    }

  /**
   * Purpose   :  Returns  actionType.
   * @return   :  String 
   */
    public String getActionType() {
        return actionType;
    }

  /**
   * Purpose   : Sets the value of actionType.
   * @param    : newActionType Value of actionType from the form
   */
    public void setActionType(String newActionType) {
        actionType = newActionType;
    }

  /**
   * Purpose   :  Returns  docId.
   * @return   :  Long 
   */
    public Long getDocId() {
        return docId;
    }

  /**
   * Purpose   : Sets the value of docId.
   * @param    : newDocId Value of docId from the form
   */
    public void setDocId(Long newDocId) {
        docId = newDocId;
    }

  /**
   * Purpose   :  Returns  comment.
   * @return   :  String 
   */
    public String getComment() {
        return comment;
    }

  /**
   * Purpose   : Sets the value of comment.
   * @param    : newComment Value of comment from the form
   */
    public void setComment(String newComment) {
        comment = newComment;
    }

  /**
   * Purpose   :  Returns  docName.
   * @return   :  String 
   */
    public String getDocName() {
        return docName;
    }

  /**
   * Purpose   : Sets the value of docName.
   * @param    : newDocName Value of docName from the form
   */
    public void setDocName(String newDocName) {
        docName = newDocName;
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
   * @param    : description Value of description from the form
   */
    public void setDescription(String description) {
      this.description = description;
    }

  /**
   * Purpose   :  Returns  candidateName.
   * @return   :  String 
   */
    public String getCandidateName() {
      return candidateName;
    }

  /**
   * Purpose   : Sets the value of candidateName.
   * @param    : candidateName Value of candidateName from the form
   */
    public void setCandidateName(String candidateName) {
      this.candidateName = candidateName;
    }

  /**
   * Purpose   :  Returns  candidateEmailId.
   * @return   :  String 
   */
    public String getCandidateEmailId() {
      return candidateEmailId;
    }

  /**
   * Purpose   : Sets the value of candidateEmailId.
   * @param    : candidateEmailId Value of candidateEmailId from the form
   */
    public void setCandidateEmailId(String candidateEmailId) {
      this.candidateEmailId = candidateEmailId;
    }

  /**
   * Purpose   :  Returns  candidatePhnNo.
   * @return   :  String 
   */
    public String getCandidatePhnNo() {
      return candidatePhnNo;
    }

  /**
   * Purpose   : Sets the value of candidatePhnNo.
   * @param    : candidatePhnNo Value of candidatePhnNo from the form
   */
    public void setCandidatePhnNo(String candidatePhnNo) {
      this.candidatePhnNo = candidatePhnNo;
    }

  /**
   * Purpose   :  Returns  communicationSkills.
   * @return   :  String 
   */
    public String getCommunicationSkills() {
      return communicationSkills;
    }

  /**
   * Purpose   : Sets the value of communicationSkills.
   * @param    : communicationSkills Value of communicationSkills from the form
   */
    public void setCommunicationSkills(String communicationSkills) {
      this.communicationSkills = communicationSkills;
    }

  /**
   * Purpose   :  Returns  candidateAddress.
   * @return   :  String 
   */
    public String getCandidateAddress() {
      return candidateAddress;
    }

  /**
   * Purpose   : Sets the value of candidateAddress.
   * @param    : candidateAddress Value of candidateAddress from the form
   */
    public void setCandidateAddress(String candidateAddress) {
      this.candidateAddress = candidateAddress;
    }
}
