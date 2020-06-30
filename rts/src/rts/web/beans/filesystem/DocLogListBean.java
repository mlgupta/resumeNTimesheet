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
 * $Id: DocLogListBean.java,v 1.2 2005/07/15 10:42:57 rajan Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;

class DocLogList  {

    private String user;
    private String docLogDate;





    /**
     * Purpose : Returns the user.
     * @return : String
     */
    public String getUser() {
        return user;
    }

    /**
     * Purpose : Sets the user.
     * @param  : newUser Value of user 
     */
    public void setUser(String newUser) {
        user = newUser;
    }

    /**
     * Purpose : Returns the createdDate.
     * @return : String
     */
    public String getDocLogDate() {
    return docLogDate;
    }

    /**
     * Purpose : Sets the createdDate.
     * @param  : newCreatedDate Value of createdDate 
     */
    public void setDocLogDate(String docLogDate) {
    this.docLogDate = docLogDate;
    }





}
