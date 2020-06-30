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
 * $Id: UserListBean.java,v 1.1 2005/08/23 11:12:49 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.user; 
/**
 *	Purpose: To Populate the User List Table in user_list.jsp
 *  @author              Sudheer Pujar
 *  @version             1.0
 * 	Date of creation:   05-01-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class UserListBean  {
    private String name;
    private String status;
    private String quota;
    private String createDate;
    private String owner;
    private String acl;
    private String group[];
    private String pageCount;
    private String isDisabled;

    /**
     * Purpose : Returns Name of the User.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Purpose : Sets Name of the User.
     * @param newName Value of name 
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Purpose : Returns Status of the User.
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Purpose : Sets Status of the User.
     * @param newStatus Value of status 
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    }

    /**
     * Purpose : Returns Content Quota of the User.
     * @return String
     */
    public String getQuota() {
        return quota;
    }

    /**
     * Purpose : Sets Content Quota of the User.
     * @param newQuota Value of quota 
     */
    public void setQuota(String newQuota) {
        quota = newQuota;
    }

    /**
     * Purpose : Returns Creation Date of the User.
     * @return String
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Purpose : Sets Creation Date of the User.
     * @param newCreateDate Value of createDate 
     */
    public void setCreateDate(String newCreateDate) {
        createDate = newCreateDate;
    }

    /**
     * Purpose : Returns Owner of the User.
     * @return String
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Purpose : Sets Owner of the User.
     * @param newOwner Value of owner 
     */
    public void setOwner(String newOwner) {
        owner = newOwner;
    }

    /**
     * Purpose : Returns Access Control List of the User.
     * @return String
     */
    public String getAcl() {
        return acl;
    }

    /**
     * Purpose : Sets Access Control List of the User.
     * @param newAcl Value of acl 
     */    

    public void setAcl(String newAcl) {
        acl = newAcl;
    }

    /**
     * Purpose : Returns Groups of the User.
     * @return String
     */
     
    public String[] getGroup() {
        return group;
    }

    /**
     * Purpose : Sets Groups of the User.
     * @param newGroup Value of group 
     */
    public void setGroup(String[] newGroup) {
        group = newGroup;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String newPageCount) {
        pageCount = newPageCount;
    }

    public String getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(String newIsDisabled) {
        isDisabled = newIsDisabled;
    }
}
