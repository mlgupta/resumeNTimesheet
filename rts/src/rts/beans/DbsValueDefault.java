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
 * $Id: DbsValueDefault.java,v 1.1.1.1 2005/05/26 12:31:59 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of ValueDefault class provided by CMSDK API.
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

public class DbsValueDefault extends DbsSchemaObject{

    /** This class name for this class. Useful for methods that take a class name argument. */
    public static final java.lang.String CLASS_NAME=ValueDefault.CLASS_NAME;
    /** The name of this object.*/    
    public static final java.lang.String NAME_ATTRIBUTE=ValueDefault.NAME_ATTRIBUTE;

    private ValueDefault valueDefault=null; // to accept object of type ValueDefault

    /**
     * Purpose : To create DbsValueDefault using ValueDefault class
	   * @param  : valueDefault - An ValueDefault Object  
	   */    
    public DbsValueDefault(ValueDefault valueDefault) {
        super(valueDefault);
        this.valueDefault=valueDefault;
    }

    /**
     * Purpose   : Gets the value of this Property, as an AttributeValue.
     * @returns  : the attrValue - DbsAttributeValue
     * @throws   : DbsException - if operation fails
     */
    public DbsAttributeValue getPropertyValue() throws DbsException {
        DbsAttributeValue attrValue=null;
        try{
            attrValue=new DbsAttributeValue(this.valueDefault.getPropertyValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return attrValue;
    }

    /**
     * Purpose   : Returns the description of this object
     * @returns  : The object description
     * @throws   : DbsException - if operation fails
     */
    public String getDescription() throws DbsException{
        try{
            return this.valueDefault.getDescription();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose : Updates the description of this object.
     * @param  : description - New description
     * @throws : DbsException - if operation fails
     */
    public void setDescription(String description) throws DbsException{
        try{
            this.valueDefault.setDescription(description);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Gets the value of this DbsValueDefaultPropertyBundle
     * @returns  : the valueDefaultPropertyBundle
     * @throws   : DbsException - if operation fails
     */
    public DbsValueDefaultPropertyBundle getValueDefaultPropertyBundle() throws DbsException{
        try{
            return new DbsValueDefaultPropertyBundle(this.valueDefault.getValueDefaultPropertyBundle());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose : Sets the value of this DbsValueDefaultPropertyBundle
     * @param  : dbsValueDefaultPropertyBundle - DbsValueDefaultPropertyBundle
     * @throws : DbsException - if operation fails
     */
    public void setValueDefaultPropertyBundle(DbsValueDefaultPropertyBundle dbsValueDefaultPropertyBundle) throws DbsException{
        try{
            this.valueDefault.setValueDefaultPropertyBundle(dbsValueDefaultPropertyBundle.getValueDefaultPropertyBundle());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
    
   /**
	  * Purpose  : Used to get the object of class ValueDefault
	  * @returns : ValueDefault Object
	  */
    public ValueDefault getValueDefault() {
      return this.valueDefault ;
    }
}
