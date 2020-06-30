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
 * $Id: DbsPropertyBundle.java,v 1.1.1.1 2005/05/26 12:31:52 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of PropertyBundle class provided by CMSDK API.
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

public class DbsPropertyBundle extends DbsApplicationObject{

    /**Name of this class.*/
    public static final java.lang.String CLASS_NAME=PropertyBundle.CLASS_NAME;

    PropertyBundle propertyBundle=null; // to accept object of type SystemObject

    /**
	   * Purpose : To create DbsPropertyBundle using PropertyBundle class
	   * @param  : propertyBundle - An PropertyBundle Object  
	   */
    public DbsPropertyBundle(PropertyBundle propertyBundle) {
        super(propertyBundle);
        this.propertyBundle=propertyBundle;
    }

    /**
	   * Purpose  : Gets the value of a specific DbsProperty by name.
	   * @param   : name - name of the DbsProperty
	   * @returns : DbsAttributeValue containing the value of the DbsProperty. Returns null if the DbsProperty is not found.
     * @throws  : DbsException - if operation fails
	   */
    public DbsAttributeValue getPropertyValue(String name) throws DbsException{
        try{     
          if(propertyBundle.getPropertyValue(name)!=null){
              return new DbsAttributeValue(propertyBundle.getPropertyValue(name));
          }else{
              return null;
          }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
    
    /**
	   * Purpose  : Gets the value of a specific DbsProperty by name, where the name must be in all uppercase. 
     *            This is equivalent to getPropertyValue , but avoids an unnecessary conversion of the name to upper case.
	   * @param   : name - uppercased name of the DbsProperty
	   * @returns : DbsAttributeValue object containing the value of the DbsProperty. Returns null if the DbsProperty is not found.
     * @throws  : DbsException - if operation fails
	   */
    public DbsAttributeValue getPropertyValueByUpperCaseName(String name) throws DbsException{
        try{     
            if(propertyBundle.getPropertyValueByUpperCaseName(name)!=null){
                return new DbsAttributeValue(propertyBundle.getPropertyValueByUpperCaseName(name));
            }else{
                return null;
            }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Puts a new value for a DbsProperty, replacing any attribute of the same name (analogous to Hashtable.put()).
	   * @param   : name - the name of the DbsProperty
	   * @param   : dbsAttrValue - the DbsAttributeValue containing the new value;
     * @throws  : DbsException - if operation fails
	   */
    public void putPropertyValue(String name,DbsAttributeValue dbsAttrValue)throws DbsException {
        try{         
            this.propertyBundle.putPropertyValue(name,dbsAttrValue.getAttributeValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Puts a new value for a DbsProperty, replacing any attribute of the same name (analogous to Hashtable.put()).
	   * @param   : dbsAttrValue - the DbsAttributeValue containing the new value; implies the name & data type
     * @throws  : DbsException - if operation fails
	   */
    public void putPropertyValue(DbsAttributeValue dbsAttrValue)throws DbsException {
        try{         
            this.propertyBundle.putPropertyValue(dbsAttrValue.getAttributeValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Gets a specified DbsProperty by name.
	   * @param   : name - the name of the DbsProperty
	   * @returns : the DbsProperty, or null if no such DbsProperty
     * @throws  : DbsException - if operation fails
	   */
    public DbsProperty getProperty(String name)throws DbsException{
        try{
            if(this.propertyBundle.getProperty(name)!=null){
                return new DbsProperty(this.propertyBundle.getProperty(name));
            }else{
                return null;
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Gets a specified DbsProperty by name, where the name must be in all uppercase. 
     *            This is equivalent to getProperty, but avoids an unnecessary conversion of the name to upper case.
	   * @param   : name - the uppercased name of the DbsProperty
	   * @returns : the DbsProperty, or null if no such DbsProperty
     * @throws  : DbsException - if operation fails
	   */
    public DbsProperty getPropertyByUpperCaseName(String name)throws DbsException{
        try{
            if(this.propertyBundle.getPropertyByUpperCaseName(name)!=null){
                return new DbsProperty(this.propertyBundle.getPropertyByUpperCaseName(name));
            }else{
                return null;
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Gets the DbsProperty at the specified index.
	   * @param   : index - index into the DbsProperty array
	   * @returns : the requested DbsProperty
     * @throws  : DbsException - if operation fails
	   */    
    public DbsProperty getProperties(int index)throws DbsException{
        try{
            if(this.propertyBundle.getProperties(index)!=null){
                return new DbsProperty(this.propertyBundle.getProperties(index));
            }else{
                return null;
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Gets all the properties in this DbsPropertyBundle.
	   * @returns : array of DbsProperty objects or null if there are no properties in the DbsPropertyBundle
     * @throws  : DbsException - if operation fails
	   */    
    public DbsProperty[] getProperties()throws DbsException{
        try{
            if(this.propertyBundle.getProperties()!=null){
                Property[] property=this.propertyBundle.getProperties();
                DbsProperty[] dbsProperty = new DbsProperty[property.length];
                for(int i=0; i<property.length;i++){
                  dbsProperty[i]=new DbsProperty(property[i]);
                }
                return dbsProperty;
            }else{
                return null;
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Removes a DbsProperty, if it exists in this PropertyBundle (analogous to Hashtable.remove())
	   * @param   : name - the name of the DbsProperty
     * @throws  : DbsException - if operation fails
	   */
    public void removePropertyValue(String name)throws DbsException{
        try{         
            this.propertyBundle.removePropertyValue(name); 
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Removes all Properties from this PropertyBundle.
     * @throws  : DbsException - if operation fails
	   */
    public void removeAllPropertyValues()throws DbsException{
        try{         
            this.propertyBundle.removeAllPropertyValues();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

   /**
	  * Purpose  : Used to get the object of class PropertyBundle
	  * @returns : PropertyBundle Object
	  */
    public PropertyBundle getDbsPropertyBundle() {
        return this.propertyBundle ;
    }
}
