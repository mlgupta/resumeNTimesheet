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
 * $Id: DbsAccessControlListDefinition.java,v 1.1.1.1 2005/05/26 12:31:40 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of AccessControlListDefinition class provided by CMSDK API.
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

public class DbsAccessControlListDefinition extends DbsPublicObjectDefinition{
    private AccessControlListDefinition accessControlListDef = null;  // to accept object of type AccessControlListDefinition

    /**
     * Purpose  : Used to get the object of class AccessControlListDefinition
	   * @param   : dbsSession - Constructs a AccessControlListDefinition explicitly capturing the session.
     * @throws   : DbsException - if operation fails.
	   */
    public DbsAccessControlListDefinition(DbsLibrarySession dbsSession) throws DbsException {
     super(dbsSession);
        try {
            this.accessControlListDef = new AccessControlListDefinition(dbsSession.getLibrarySession());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Set an attribute for the new instance. 
	   * @param   : name - The attribute name
	   * @param   : dbsAttrValue - The attribute value.
     * @throws  : DbsException - if operation fails.
	   */
    public void setAttribute(String name, DbsAttributeValue dbsAttrValue) throws DbsException {
        try{
            this.accessControlListDef.setAttribute(name,dbsAttrValue.getAttributeValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
  
    /**
     * Purpose  : Set the indication as to whether this ACL is shared.
	   * @param   : isShared - the indication as to whether this ACL is shared.
     * @throws  : DbsException - if operation fails.
	   */
    public void setShared(boolean isShared)throws DbsException {
        try {
              this.accessControlListDef.setShared(isShared);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Used to get the object of class DirectoryGroupDefinition
     * @returns : DirectoryGroupDefinition Object
     */
    AccessControlListDefinition getAccessControlListDefinition() {
        return this.accessControlListDef ;
    }
}
