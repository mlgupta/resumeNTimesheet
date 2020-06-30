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
 * $Id: DbsProperty.java,v 1.1.1.1 2005/05/26 12:31:51 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of Property class provided by CMSDK API.
 * 
 *  @author              Jeetendra Prasad
 *  @version             1.0
 * 	Date of creation:    
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/*CMSDK API*/
import oracle.ifs.common.*;
import oracle.ifs.beans.*;

public class DbsProperty extends DbsSystemObject{
    private Property property=null; // to accept object of type Property

    /**
	   * Purpose : To create DbsProperty using Property class
	   * @param  : property - An Property Object  
	   */  
    public DbsProperty(Property property) {
        super(property);
        this.property=property;
    }
    
    /**
	   * Purpose : Gets the BUNDLE attribute of this Property.
	   * @param  : the PropertyBundle object held in the BUNDLE attribute
     * @throws : DbsException - if operation fails
	   */
    public DbsPropertyBundle getBundle() throws DbsException{
        try{
            return new DbsPropertyBundle(this.property.getBundle());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose   : Gets the data type of this Property.
	   * @returns  : the data type
     * @throws   : DbsException - if operation fails
	   */
    public final int getDataType() throws DbsException{
        try{
            return this.property.getDataType();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose   : Sets the value and data type of this Property.
	   * @param    : dbsAttrValue - the AttributeValue containing the new value; implies the data type
     * @throws   : DbsException - if operation fails
	   */
    public void setValue(DbsAttributeValue dbsAttrValue) throws DbsException{
        try{
            this.property.setValue(dbsAttrValue.getAttributeValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose   : Gets the value of this Property as an AttributeValue. 
     *             This method returns an AttributeValue whose type is appropriate for the data type of this Property.
	   * @returns  : the dbsAttrValue
     * @throws   : DbsException - if operation fails
	   */
    public DbsAttributeValue getValue() throws DbsException{
        DbsAttributeValue dbsAttrValue=null;
        try{
            dbsAttrValue=new DbsAttributeValue(property.getValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAttrValue;
    }

    /**
	   * Purpose : Used to get the object of class Property
	   * @return : Property Object
	   */
    public Property getProperty(){
        return this.property;
    }
}
