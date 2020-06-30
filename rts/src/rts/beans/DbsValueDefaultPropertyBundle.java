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
 * $Id: DbsValueDefaultPropertyBundle.java,v 1.1.1.1 2005/05/26 12:31:59 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of ValueDefaultPropertyBundle class provided by CMSDK API.
 * 
 *  @author               Sudheer Pujar
 *  @version              1.0
 * 	Date of creation:     16-03-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/* CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsValueDefaultPropertyBundle extends DbsPropertyBundle  {

    /** This class name for this class. Useful for methods that take a class name argument. */  
    public static final String CLASS_NAME=ValueDefaultPropertyBundle.CLASS_NAME;

    private ValueDefaultPropertyBundle valueDefaultPropertyBundle=null;// to accept object of type ValueDefaultPropertyBundle

    /**
     * Purpose : To create DbsValueDefaultPropertyBundle using ValueDefaultPropertyBundle class
     * @param  : valueDefaultPropertyBundle - A ValueDefaultPropertyBundle Object  
     */   
    public DbsValueDefaultPropertyBundle(ValueDefaultPropertyBundle valueDefaultPropertyBundle) {
        super(valueDefaultPropertyBundle);
        this.valueDefaultPropertyBundle=valueDefaultPropertyBundle;
    }

    /**
     * Purpose   : Gets the value of this DbsValueDefaultPropertyBundle, as an AttributeValue.
     * @returns  : the value
     * @throws   : DbsException - if operation fails
     */
    public DbsAttributeValue getPropertyValue()throws DbsException{
        try{
            return new DbsAttributeValue(this.valueDefaultPropertyBundle.getPropertyValue());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Used to get the object of class ValueDefaultPropertyBundle
     * @returns : ValueDefaultPropertyBundle Object
     */
    public ValueDefaultPropertyBundle getValueDefaultPropertyBundle(){
        return this.valueDefaultPropertyBundle;
    }
}
