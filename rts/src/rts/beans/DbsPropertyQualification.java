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
 * $Id: DbsPropertyQualification.java,v 1.1.1.1 2005/05/26 12:31:52 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of ContextQualification class provided by CMSDK API.
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    24-02-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
import oracle.ifs.search.*;

public class DbsPropertyQualification extends DbsSearchSpecification{
    protected PropertyQualification propertyQualification=null; // to accept object of type PropertyQualification

    public DbsPropertyQualification() throws DbsException{
        super();
        try{
            this.propertyQualification = new PropertyQualification();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);         
        }
    }
    /**
	   * Purpose : To create DbPropertyQualification using PropertyQualification class
	   * @param  : propertyQualification - An PropertyQualification Object  
	   */    
    public DbsPropertyQualification(PropertyQualification propertyQualification) {
        this.propertyQualification = propertyQualification;
    }

    /**
	   * Purpose  : Used to get the object of class PropertyQualification
	   * @returns : propertyQualification Object
	   */
    public PropertyQualification getPropertyQualification() {
        return this.propertyQualification;
    }

    /**
	   * Purpose  : Set the Search Classname.
     *            Class should be a valid iFS PublicObject or SubClass or an Alias. 
     *            A null class is taken to mean, use the first Result Class of the SearchSpecification.
	   * @param   : dbsClass - Search Class.
     * @throws  : DbsException - if operation fails
	   */
    public void setClassname(java.lang.String dbsClass) throws DbsException{
        try{
            propertyQualification.setClassname(dbsClass);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }      
    }

    /**
	   * Purpose  : Returns the search class for this qualification object.
	   * @param   : search class.    
     * @throws  : DbsException - if operation fails
	   */
    public java.lang.String getClassname() throws DbsException{
        try{
            return this.propertyQualification.getClassname();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Set the name of property used in the condition. Property Name cannot be null.
	   * @param   : dbsName - name of searched property.    
     * @throws  : DbsException - if operation fails
	   */
    public void setPropertyName(java.lang.String dbsName) throws DbsException{
        try{
            propertyQualification.setPropertyName(dbsName);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Return the name of the property being searched.
	   * @param   : searched property.    
	   */
    public java.lang.String getPropertyName(){
        return this.propertyQualification.getPropertyName();
    }

    /**
	   * Purpose  : Sets the comparison value. Irrespective of value, Qualification is assumed not to be late bound. 
     *            Use setLateBound to specify Qualification as Late Bound. 
     *            The dataType of the specified AttributeValue is assumed to be the dataType of the property.
	   * @param   : dbsAttributeValue - the comparison value specified as an AttrbuteValue
     * @throws  : DbsException - if operation fails
	   */
    public void setValue(DbsAttributeValue dbsAttributeValue) throws DbsException{
        try{
            propertyQualification.setValue(dbsAttributeValue.getAttributeValue());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Return the comparison value.
	   */
    public AttributeValue getValue(){
        return this.propertyQualification.getValue();
    }

    /**
	   * Purpose  : Set Qualification as Late Bound. The dataType of the property needs to be specified. 
     *            Note there are no rules for mapping property names to datatypes. 
     *            A property name can be used by different objects to mean differen things.
	   * @param   : dbsDataType - property's dataType
     * @throws  : DbsException - if operation fails
	   */
    public void setLateBoundDataType(int dbsDataType) throws DbsException{
        try{
            propertyQualification.setLateBoundDataType(dbsDataType);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose  : Get the data type if the qualification is late bound. 
     *            If the qualification is not late bound, it throws IfsException.
	   * @returns : data type of this qualification if it is late bound    
     * @throws  : DbsException - if operation fails
	   */
    public int getLateBoundDataType() throws DbsException{
        try{
            return this.propertyQualification.getLateBoundDataType();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose  : Sets the comparison operator. 
     *            This variant lets the user pass in a string to represent the operator instead on the integer.
	   * @param   : dbsOper - comparison operator string; this should be one of =, <, <=, >, >=, !=, <>, 
     *            IS NULL, IS NOT NULL, LIKE or NOT LIKE.
     * @throws  : DbsException - if operation fails
	   */
    public void setOperatorType(java.lang.String dbsOper) throws DbsException{
        try{
            propertyQualification.setOperatorType(dbsOper);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Sets the comparision operator.
	   * @param   : dbsOper - comaprison operator must be one of, AttributeQualification.EQUAL , 
     *            AttributeQualification.GREATER_THAN , AttributeQualification.GREATER_THAN_EQUAL, 
     *            AttributeQualification.IS_NOT_NULL , AttributeQualification.IS_NULL , 
     *            AttributeQualification.LESS_THAN , AttributeQualification.LESS_THAN_EQUAL , 
     *            AttributeQualification.LIKE
     * @throws  : DbsException - if operation fails
	   */
    public void setOperatorType(int dbsOper) throws DbsException{
        try{
            propertyQualification.setOperatorType(dbsOper);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Gets the operator for this qualification.
     * @throws  : DbsException - if operation fails
	   */
    public int getOperatorType() throws DbsException{
        try{
            return this.propertyQualification.getOperatorType();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Sets the date comparison value. The default is DATE_COMP_SEC. 
     *            If df is not valid, date comparison level is set to the default.
     * param    : dbsDateFormat - date comparison level. should be one of AttributeQualification.DATE_COMP_DAY, 
     *            AttributeQualification.DATE_COMP_MONTH, AttributeQualification.DATE_COMP_YEAR, 
     *            AttributeQualification.DATE_COMP_SEC, AttributeQualification.DATE_COMP_MIN, 
     *            AttributeQualification.DATE_COMP_HOUR        
     * @throws  : DbsException - if operation fails
	   */
    public void setDateComparisonLevel(int dbsDateFormat) throws DbsException{
        try{
            propertyQualification.setDateComparisonLevel(dbsDateFormat);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose  : Sets up case sensitive behavior for this object. If the value is true search with 
     *            respect to this qualification will ignore case. Default behavior is case sensitive.
     * @param   : dbsValue        
     * @throws  : DbsException - if operation fails
	   */
    public void setCaseIgnored(boolean dbsValue) throws DbsException{
        try{
            propertyQualification.setCaseIgnored(dbsValue);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Returns the date comparison level.
	   */
    public int getDateComparisonLevel(){
        return this.propertyQualification.getDateComparisonLevel();
    }

    /**
	   * Purpose  : Returns true if this object is set to be case insensitive.
	   */
    public boolean isCaseIgnored(){
        return this.propertyQualification.isCaseIgnored();
    }

    /**
	   * Purpose : Used to get the object of class PropertyQualification
	   * @return : PropertyQualification
	   */
    public SearchQualification getSearchQualification() {
        return this.propertyQualification;
    }
}
