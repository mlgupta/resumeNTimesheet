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
 * $Id: AdminLoginBean.java,v 1.1.1.1 2005/05/26 12:32:49 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.utility;

/**
 *	Purpose: To get the admin session where administration mode is required for Non - Admin user.
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* rts package references */ 
import rts.beans.*;
import rts.web.beans.user.UserInfo;
/* Java API */
import javax.servlet.http.HttpSession;
/* Logger API */
import org.apache.log4j.*;

public class AdminLoginBean {
    private String systemAdminUserID;
    private String systemAdminUserPassword;

    public AdminLoginBean() {}

    /**
     * Purpose : Returns system user ID.
     * @return : String
     */
/*    public String getSystemAdminUserID() {
        return systemAdminUserID;
    }

    public void setSystemAdminUserID(String newSystemAdminUserID) {
        systemAdminUserID = newSystemAdminUserID;
    }

    public String getSystemAdminUserPassword() {
        return systemAdminUserPassword;
    }

    public void setSystemAdminUserPassword(String newSystemAdminUserPassword) {
        systemAdminUserPassword = newSystemAdminUserPassword;
    }
*/
    public DbsLibrarySession getAdminLibrarySession(HttpSession httpSession, DbsLibrarySession dbsLibrarySession){
        Logger logger = Logger.getLogger("DbsLogger");
        DbsLibrarySession dbsAdminLibrarySession;
        try{
            DbsDirectoryUser dbsUser= dbsLibrarySession.getUser();
        
            String systemAdminUserID = httpSession.getServletContext().getInitParameter("SystemUserId");
            String systemAdminUserPassword = httpSession.getServletContext().getInitParameter("SystemUserPassword");
            DbsCleartextCredential systemAdminCredential = new DbsCleartextCredential(systemAdminUserID,systemAdminUserPassword);
        
            String ifsService = httpSession.getServletContext().getInitParameter("IfsService");
            
            DbsLibraryService dbsLibraryService = DbsLibraryService.findService("IfsDefaultService");
            dbsAdminLibrarySession = dbsLibraryService.connect(systemAdminCredential,null);
            dbsAdminLibrarySession.impersonateUser(dbsUser);
            dbsAdminLibrarySession.setAdministrationMode(true);        
        }catch(DbsException dex){
            logger.error(dex.getMessage());
            dbsAdminLibrarySession = null;
        }
        return dbsAdminLibrarySession;
    }

    public void closeAdminSession(DbsLibrarySession dbsLibrarySession) throws DbsException{
        if (dbsLibrarySession!=null){
            dbsLibrarySession.impersonateUser(null);
            dbsLibrarySession.setAdministrationMode(false);
            dbsLibrarySession.disconnect();
        }
    }
}
