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
 * $Id: DbsSchemaObjectDefinition.java,v 1.1.1.1 2005/05/26 12:31:53 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of SchemaObjectDefinition class provided by CMSDK API.
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
public class DbsSchemaObjectDefinition extends DbsLibraryObjectDefinition {
    private SchemaObjectDefinition schemaObjectDef = null; // to accept object of type SchemaObjectDefinition

    /**
	   * Purpose : To create DbsSchemaObjectDefinition using SchemaObjectDefinition class
	   * @param  : dbssession - the session
	   */    
    public DbsSchemaObjectDefinition(DbsLibrarySession dbsSession) throws DbsException {
        super(dbsSession);
        try {
            this.schemaObjectDef = new SchemaObjectDefinition(dbsSession.getLibrarySession());  
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Used to get the object of class SchemaObjectDefinition
	   * @returns : SchemaObjectDefinition Object
	   */
    public SchemaObjectDefinition getSchemaObjectDefinition() {
        return this.schemaObjectDef ;
    }
}
