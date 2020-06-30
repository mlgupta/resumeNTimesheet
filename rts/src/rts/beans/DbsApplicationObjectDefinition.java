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
 * $Id: DbsApplicationObjectDefinition.java,v 1.1.1.1 2005/05/26 12:31:41 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of ApplicationObjectDefinition class provided by CMSDK API.
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

public class DbsApplicationObjectDefinition extends DbsPublicObjectDefinition {
    private ApplicationObjectDefinition applicationObjectDef = null;  // to accept object of type ApplicationObjectDefinition
    
    /**
     * Purpose  : Used to get the object of class ApplicationObjectDefinition
     * @param   : dbsSession - Constructs a ApplicationObjectDefinition explicitly capturing the session.
     */
    public DbsApplicationObjectDefinition(DbsLibrarySession dbsSession) throws DbsException {
        super(dbsSession);
        try {            
            this.applicationObjectDef = new ApplicationObjectDefinition(dbsSession.getLibrarySession());  
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Used to get the object of class ApplicationObjectDefinition
	   * @returns : ApplicationObjectDefinition Object
	   */
    public ApplicationObjectDefinition getApplicationObjectDefinition() {
        return this.applicationObjectDef ;
    }
}
