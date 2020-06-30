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
 * $Id: DbsDirectoryUserManager.java,v 1.1.1.1 2005/05/26 12:31:44 suved Exp $
 *****************************************************************************
 */
package rts.beans;  
/**
 *	Purpose: The encapsulate the functionality of DirectoryUser class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/*CMSDK API*/ 
import oracle.ifs.beans.DirectoryUser;
import oracle.ifs.beans.DirectoryUserDefinition;
import oracle.ifs.beans.PrimaryUserProfile;
import oracle.ifs.beans.LibrarySession;
import oracle.ifs.common.IfsException;

public class DbsDirectoryUserManager {

    public DbsDirectoryUserManager() throws IfsException {}

    public DbsDirectoryUserManager(LibrarySession session)
        throws IfsException {
        m_DirectoryUserDefinition = new DirectoryUserDefinition(session);
    }

    public DirectoryUser[] getAllUserMembers() throws IfsException {
        return m_DirectoryUser.getAllUserMembers();
    }

    public DirectoryUser getAllUserMembers(int index) throws IfsException {
        return m_DirectoryUser.getAllUserMembers(index);
    }
    public java.lang.String getCredentialManager() throws IfsException {
        return m_DirectoryUser.getCredentialManager();
    }

    public java.lang.String getDistinguishedName() throws IfsException {
        return m_DirectoryUser.getDistinguishedName();
    }

    public PrimaryUserProfile getPrimaryUserProfile() throws IfsException {
        return m_DirectoryUser.getPrimaryUserProfile();
    }

    public boolean isAdminEnabled() throws IfsException {
        return m_DirectoryUser.isAdminEnabled();
    }

    public boolean isSystemAdminEnabled() throws IfsException {
        return m_DirectoryUser.isSystemAdminEnabled();
    }

    public void setCredentialManager(java.lang.String credentialManager)
            throws IfsException {
        m_DirectoryUser.setCredentialManager(credentialManager);
    }

    public void setDistinguishedName(java.lang.String name)
            throws IfsException {
        m_DirectoryUser.setDistinguishedName(name);
    }

    public void setSystemAdminEnabled(boolean value) throws IfsException {
        m_DirectoryUser.setSystemAdminEnabled(value);
    }

    public DirectoryUser getDirectoryUser() {
        return m_DirectoryUser;
    }

    public void setDirectoryUser(DirectoryUser v_DirectoryUser) {
        m_DirectoryUser = v_DirectoryUser;
    }

    public DirectoryUserDefinition getDirectoryUserDefinition() {
        return m_DirectoryUserDefinition;
    }

    private DirectoryUser m_DirectoryUser;

    private DirectoryUserDefinition m_DirectoryUserDefinition;
}
