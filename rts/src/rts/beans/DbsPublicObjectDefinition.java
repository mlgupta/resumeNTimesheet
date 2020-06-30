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
 * $Id: DbsPublicObjectDefinition.java,v 1.2 2005/06/24 05:57:20 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of PublicObjectDefinition class provided by CMSDK API.
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

public class DbsPublicObjectDefinition extends DbsLibraryObjectDefinition {
    private PublicObjectDefinition publicObjectDef = null;  // to accept object of type PublicObjectDefinition

    /**
     * Purpose  : Used to get the object of class PublicObjectDefinition
     * @param   : dbsSession - Constructs a PublicObjectDefinition explicitly capturing the session.
     * @throws  : DbsException - if operation fails
     */
    public DbsPublicObjectDefinition(DbsLibrarySession dbsSession) throws DbsException {
        super(dbsSession);
        try {            
            this.publicObjectDef = new PublicObjectDefinition(dbsSession.getLibrarySession());  
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Used to get the object of class PublicObjectDefinition
	   * @returns : PublicObjectDefinition Object
	   */
    public PublicObjectDefinition getPublicObjectDefinition() {
        return this.publicObjectDef ;
    }
    
    public void setAddToFolderOption( DbsFolder dbsFolder )throws DbsException{
        try{
          this.publicObjectDef.setAddToFolderOption(dbsFolder.getFolder());
        }catch( IfsException ifsError){
          throw new DbsException(ifsError);
        }
    }
}
