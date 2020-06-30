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
 * $Id: DbsPropertyBundleDefinition.java,v 1.1.1.1 2005/05/26 12:31:52 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of PropertyBundleDefinition class provided by CMSDK API.
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    20-02-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsPropertyBundleDefinition extends DbsPublicObjectDefinition {

    private PropertyBundleDefinition propertyBundleDef = null;  // to accept object of type PropertyBundleDefinition

    /**
     * Purpose  : Used to get the object of class PropertyBundleDefinition
	   * @param   : dbsSession - Constructs a PropertyBundleDefinition explicitly capturing the session.
     * @throws  : DbsException - if operation fails
	   */
    public DbsPropertyBundleDefinition(DbsLibrarySession dbsSession) throws DbsException {
        super(dbsSession);
        try {            
            this.propertyBundleDef = new PropertyBundleDefinition(dbsSession.getLibrarySession());  
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
    
    /**
     * Purpose  : Adds a Property Value to the new PropertyBundle. This results in the creation of a PropertyDefinition, 
     *            which gets added to the list of Properties to create.
	   * @param   : dbsAttributeValue - the DbsAttributeValue representing the Property value
     * @throws  : DbsException - if operation fails
	   */
    public void addPropertyValue(DbsAttributeValue dbsAttributeValue) throws DbsException {
        try{
            this.propertyBundleDef.addPropertyValue(dbsAttributeValue.getAttributeValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Adds a Property Value to the new PropertyBundle. This results in the creation of a PropertyDefinition, 
     *            which gets added to the list of Properties to create.
	   * @param   : name - String
	   * @param   : dbsAttributeValue - the DbsAttributeValue representing the Property value
     * @throws  : DbsException - if operation fails
	   */
    public void addPropertyValue(java.lang.String name,DbsAttributeValue dbsAttributeValue) throws DbsException {
        try{
            this.propertyBundleDef.addPropertyValue(name, dbsAttributeValue.getAttributeValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }        
    }

/*    public final void addPropertyDefinition(DbsPropertyDefinition dbsPropertyDef) throws DbsException{
        try{
            this.propertyBundleDef.addPropertyDefinition(dbsPropertyDef);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }       
    }
*/
  /**
	 * Purpose  : Used to get the object of class PropertyBundleDefinition
	 * @returns : PropertyBundleDefinition Object
	 */
    PropertyBundleDefinition getPropertyBundleDefinition() {
        return this.propertyBundleDef ;
    }
}
