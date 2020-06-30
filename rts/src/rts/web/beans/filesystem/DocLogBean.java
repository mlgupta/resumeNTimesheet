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
 * $Id: DocLogBean.java,v 1.2 2005/07/15 10:42:57 rajan Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;

/** Purpose               : Bean used to store individual log entry of a document  
 * @author                : Suved Mishra
 * @version               : 1.0
 * Date Of Creation       : 15-02-2005
 * Last Modified by       :
 * Last Modification Date :
 */

public class DocLogBean  {

  private String event; /* concatination of userId,timeStamp,actionPerformed */
  private String userId;
  private String timeStamp;
  private String actionPerformed;
  
  public void setEvent(String newEvent){
    event = newEvent;
  }
  
  public String getEvent(){
    return event;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getActionPerformed() {
    return actionPerformed;
  }

  public void setActionPerformed(String actionPerformed) {
    this.actionPerformed = actionPerformed;
  }
}
