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
 * $Id: DbsAttributeQualification.java,v 1.1.1.1 2005/05/26 12:31:42 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of AttributeQualification class provided by CMSDK API.
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

public class DbsAttributeQualification extends DbsSearchQualification {

    /**Represents an 'equality' comparison.*/
    public static final int EQUAL=AttributeQualification.EQUAL; 
    /**Represents an 'less-than' comparison.*/
    public static final int LESS_THAN=AttributeQualification.LESS_THAN; 
    /**Represents an 'less-than-equal' comparison.*/
    public static final int LESS_THAN_EQUAL=AttributeQualification.LESS_THAN_EQUAL; 
    /**Represents an 'greater-than' comparison.*/
    public static final int GREATER_THAN=AttributeQualification.GREATER_THAN; 
    /**Represents an 'greater-than-equal' comparison.*/
    public static final int GREATER_THAN_EQUAL=AttributeQualification.GREATER_THAN_EQUAL; 
    /**Represents an 'not-equal' comparison.*/
    public static final int NOT_EQUAL=AttributeQualification.NOT_EQUAL; 
    /**Represents an 'is-null' comparison.*/
    public static final int IS_NULL=AttributeQualification.IS_NULL; 
    /**Represents an 'is-not-null' comparison.*/
    public static final int IS_NOT_NULL=AttributeQualification.IS_NOT_NULL; 
    /**Represents an 'like' comparison.*/
    public static final int LIKE=AttributeQualification.LIKE; 
    /**Represents an 'not-like' comparison.*/
    public static final int NOT_LIKE=AttributeQualification.NOT_LIKE; 
    /**Compare dates only based on the year.*/
    public static final int DATE_COMP_YEAR=AttributeQualification.DATE_COMP_YEAR; 
    /**Compare dates based on the year & month.*/
    public static final int DATE_COMP_MONTH=AttributeQualification.DATE_COMP_MONTH; 
    /**Compare dates on the year, month and day.*/
    public static final int DATE_COMP_DAY=AttributeQualification.DATE_COMP_DAY; 
    /**Compare dates on the year, month, day and hour.*/
    public static final int DATE_COMP_HOUR=AttributeQualification.DATE_COMP_HOUR; 
    /**Compare dates on the year, month, day, hour and minute.*/
    public static final int DATE_COMP_MIN=AttributeQualification.DATE_COMP_MIN; 
    /**Compare dates on the year, month, day, hour, minute and second. This is the default.*/
    public static final int DATE_COMP_SEC=AttributeQualification.DATE_COMP_SEC; 

    protected AttributeQualification attributeQualification=null;// to accept object of type AttributeQualification

    /**
     * Purpose  : Used to get the object of class AttributeQualification
     */
    public DbsAttributeQualification() {
        super();
        this.attributeQualification=new AttributeQualification();
    }

    /**
	   * Purpose : To create DbsAttributeQualification using AttributeQualification class
	   * @param  : attributeQualification - An AttributeQualification Object  
	   */    
    public DbsAttributeQualification(AttributeQualification attributeQualification) {
        this.attributeQualification = attributeQualification;
    }

    /**
	   * Purpose  : Used to get the object of class AttributeQualification
	   * @returns : AttributeQualification Object
	   */
    public AttributeQualification getAttributeQualification() {
        return this.attributeQualification;
    }

    /**
	   * Purpose  : Return the search class of this object.
	   * @returns : the Search Class.
	   */
    public java.lang.String getAttributeClassname() throws DbsException{
        try{
            return this.attributeQualification.getAttributeClassname();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Return the attribute name of this object.
	   * @returns : the Attribute.
	   */
    public java.lang.String getAttributeName(){
        return this.attributeQualification.getAttributeName();
    }

    /**
	   * Purpose  : Returns the date comparison level.
	   * @returns : date comparison level.
	   */
    public int getDateComparisonLevel(){
        return this.attributeQualification.getDateComparisonLevel();
    }

    /**
	   * Purpose  : Gets the operator type of this attribute qualification.
	   * @returns : the operator of the Qualification.
	   */
    public int getOperatorType() throws DbsException{
        try{
            return this.attributeQualification.getOperatorType();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Returns the string representation of the comparison value.
	   * @returns : the comparison value.
	   */
    public java.lang.String getValue(){
        return this.attributeQualification.getValue();
    }

    /**
	   * Purpose  : Returns true if this AttributeQualification is case insensitive.
	   * @returns : boolean true or false.
	   */
    public boolean isCaseIgnored() throws DbsException{
        try{
            return this.attributeQualification.isCaseIgnored();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Sets the Attribute Name. Uses the first Result class from search specification as the class name.
	   * @param   : dbsAttrName - The attribute used for the condition.
	   */
    public void setAttribute(java.lang.String dbsAttrName){
        attributeQualification.setAttribute(dbsAttrName);
    }

    /**
	   * Purpose  : Sets the class name and attribute name for this AttributeQualification. 
     *            If the className is null, then it's default value is, name of the first
     *            Result class. className should be name of valid iFS class. 
     *            dbsAttrName should be the name of a valid attribute of that class.
	   * @param   : dbsAttrName - The attribute used for the condition.
	   */
    public void setAttribute(java.lang.String dbsClassName,java.lang.String dbsAttrName){
        attributeQualification.setAttribute(dbsClassName,dbsAttrName);
    }

    /**
	   * Purpose  : Sets up case sensitive behavior based on the parameter. 
     *            Default behavior is case sensitive.
	   * @param   : dbsValue - boolean 
	   */
    public void setCaseIgnored(boolean dbsValue) throws DbsException{
        try{
            attributeQualification.setCaseIgnored(dbsValue);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Sets the date comparison value. The default is DATE_COMP_SEC.
	   * @param   : df - int
	   */
    public void setDateComparisonLevel(int df){
        attributeQualification.setDateComparisonLevel(df);
    }

   /**
	  * Purpose  : Set the comparision operator.
	  * @param   : dbsOper - comparision operator.
	  */
    public void setOperatorType(int dbsOper) throws DbsException{
        try{
            attributeQualification.setOperatorType(dbsOper);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

   /**
	  * Purpose  : Set the comparision operator.
	  * @param   : dbsOper - comparision operator.
	  */
    public void setOperatorType(java.lang.String dbsOper) throws DbsException{
        try{
            attributeQualification.setOperatorType(dbsOper);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

   /**
	  * Purpose  : Sets the comparison value. 
	  * @param   : dbsAttributeValue - the comparison value specified as an AttrbuteValue
	  */
    public void setValue(DbsAttributeValue dbsAttributeValue) throws DbsException{
        try{
            attributeQualification.setValue(dbsAttributeValue.getAttributeValue());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

   /**
    * Purpose  : Sets the comparison Value. The specified value must be a scalar type, 
    *           and it cannot be null. Supplied attribute value is used to generate a 
    *           appropriate query at search execution time. For Dates if the session is 
    *           null, an exception will be thrown, because a Locale is needed to convert
    *           dates to Strings.
	  * @param   : dbsAttributeValue - the comparison value specified as an AttrbuteValue
	  */
    public void setValue(DbsAttributeValue dbsAttributeValue, DbsLibrarySession dbsLibrarySession) throws DbsException{
        try{
            attributeQualification.setValue(dbsAttributeValue.getAttributeValue() , dbsLibrarySession.getLibrarySession());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

   /**
	  * Purpose  : Sets the comparison Value. The preferred style is to use the other variant which
    *            uses AttributeValue as parameter type. If value is SearchQualification.LATE_BIND_OPER, 
    *            then the qualification is late bound and values will have to be supplied at search execution time.
	  * @param   : dbsAttributeValue - the comparison value specified as an AttrbuteValue
	  */
    public void setValue(java.lang.String dbsValue){
        attributeQualification.setValue(dbsValue);
    }

    /**
     * Purpose  : Used to get the object of class SearchQualification
	   * @returns : SearchQualification Object
	   */
     public SearchQualification getSearchQualification() {
        return this.attributeQualification;
    }
}
