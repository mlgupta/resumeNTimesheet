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
 * $Id: DbsDirectoryObjectDefinition.java,v 1.1.1.1 2005/05/26 12:31:44 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of DirectoryGroupDefinition class provided by CMSDK API.
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

public class DbsDirectoryObjectDefinition extends DbsPublicObjectDefinition{
    private DirectoryObjectDefinition directoryObjectDef = null;  // to accept object of type DirectoryGroupDefinition

    /**
	   * Purpose  : Used to get the object of class DirectoryObjectDefinition
	   * @param   : dbsSession - Constructs a DirectoryObjectDefinition explicitly capturing the session.
	   */
    public DbsDirectoryObjectDefinition(DbsLibrarySession dbsSession) throws DbsException {
        super(dbsSession);
        try {
            this.directoryObjectDef = new DirectoryObjectDefinition(dbsSession.getLibrarySession());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Used to get the object of class DirectoryGroupDefinition
	   * @returns : DirectoryGroupDefinition Object
	   */
    DirectoryObjectDefinition getDirectoryObjectDefinition() {
        return this.directoryObjectDef ;
    }
}
