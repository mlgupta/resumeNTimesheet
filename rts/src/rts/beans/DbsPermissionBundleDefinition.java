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
 * $Id: DbsPermissionBundleDefinition.java,v 1.1.1.1 2005/05/26 12:31:50 suved Exp $
 *****************************************************************************
 */
package rts.beans; 
/**
 *	Purpose: The encapsulate the functionality of PermissionBundleDefinition class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
public class DbsPermissionBundleDefinition extends DbsSystemObjectDefinition{
    private PermissionBundleDefinition permissionBundleDef = null;// to accept object of type PermissionBundleDefinition

    /**
	 * Purpose : To create DbsPermissionBundleDefinition using PermissionBundleDefinition class
	 * @param  : dbsSession - the session
	 */
    public DbsPermissionBundleDefinition(DbsLibrarySession dbsSession) throws DbsException {
        super(dbsSession);
        try {
            this.permissionBundleDef = new PermissionBundleDefinition(dbsSession.getLibrarySession());  
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    public DbsAccessLevel getAccessLevel()throws DbsException{
        try {
            return new DbsAccessLevel(this.permissionBundleDef.getAccessLevel());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

     public void setAccessLevel(DbsAccessLevel accessLevel)throws DbsException{
        try {
            this.permissionBundleDef.setAccessLevel(accessLevel.getAccessLevel());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

   /**
	 * Purpose  : Used to get the object of class PermissionBundleDefinition
	 * @returns : PermissionBundleDefinition Object
	 */
    public PermissionBundleDefinition getPermissionBundleDefinition() {
        return this.permissionBundleDef ;
    }
}
