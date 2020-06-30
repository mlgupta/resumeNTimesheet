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
 * $Id: DbsSystemObjectDefinition.java,v 1.1.1.1 2005/05/26 12:31:57 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of SystemObjectDefinition class provided by CMSDK API.
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
public class DbsSystemObjectDefinition extends DbsLibraryObjectDefinition{

    private SystemObjectDefinition systemObjectDef = null;// to accept object of type SystemObjectDefinition

    /**
	   * Purpose : To create DbsSystemObjectDefinition using SystemObjectDefinition class
	   * @param  : dbssession - the session
	   */
    public DbsSystemObjectDefinition(DbsLibrarySession dbsSession) throws DbsException {
        super(dbsSession);
        try{
            this.systemObjectDef = new SystemObjectDefinition(dbsSession.getLibrarySession());  
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

   /**
	  * Purpose  : Used to get the object of class SystemObjectDefinition
	  * @returns : SystemObjectDefinition Object
	  */
    public SystemObjectDefinition getSystemObjectDefinition() {
        return this.systemObjectDef ;
    }
}
