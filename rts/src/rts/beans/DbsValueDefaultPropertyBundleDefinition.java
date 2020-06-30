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
 * $Id: DbsValueDefaultPropertyBundleDefinition.java,v 1.1.1.1 2005/05/26 12:32:00 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of ValueDefaultPropertyBundleDefinition class provided by CMSDK API.
 * 
 *  @author              Sudheer Pujar
 *  @version             1.0
 * 	Date of creation:    
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsValueDefaultPropertyBundleDefinition extends DbsPropertyBundleDefinition  {

    private ValueDefaultPropertyBundleDefinition valueDefaultPropertyBundleDefinition;// to accept object of type ValueDefaultPropertyBundleDefinition

    /**
     * Purpose : To create DbsValueDefaultPropertyBundleDefinition using ValueDefaultPropertyBundleDefinition class
     * @param  : valueDefaultPropertyBundleDefinition - A ValueDefaultPropertyBundleDefinition Object  
     * @throws : DbsException - if operation fails
     */ 
    public DbsValueDefaultPropertyBundleDefinition(DbsLibrarySession dbsLibrarySession) throws DbsException{
        super(dbsLibrarySession);
        try{
            this.valueDefaultPropertyBundleDefinition = new ValueDefaultPropertyBundleDefinition(dbsLibrarySession.getLibrarySession());      
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Gets the value of this DbsValueDefaultPropertyBundle, as an AttributeValue.
     * @returns  : the value
     * @throws   : DbsException - if operation fails
     */
    public DbsAttributeValue getPropertyValue()throws DbsException{
        try{
            return new DbsAttributeValue(this.valueDefaultPropertyBundleDefinition.getPropertyValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose : Sets the value of this ValueDefaultPropertyBundle. 
     *           This ValueDefaultPropertyBundle will have a PUBLICOBJECT data type.
     * @param  : value - the value
     * @throws : DbsException - if operation fails
     */
    public void setValue(DbsPublicObject dbsPublicObject)throws DbsException{
        try{
            this.valueDefaultPropertyBundleDefinition.setValue(dbsPublicObject.getPublicObject());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Used to get the object of class ValueDefaultPropertyBundleDefinition
     * @returns : ValueDefaultPropertyBundleDefinition Object
     */
    public ValueDefaultPropertyBundleDefinition getValueDefaultPropertyBundleDefinition(){
        return this.valueDefaultPropertyBundleDefinition;
    }
}
