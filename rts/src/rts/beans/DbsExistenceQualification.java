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
 * $Id: DbsExistenceQualification.java,v 1.1.1.1 2005/05/26 12:31:44 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of ExistenceQualification class provided by CMSDK API.
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

public class DbsExistenceQualification extends DbsSearchQualification {
    private ExistenceQualification existenceQualification=null; // to accept object of type ExistenceQualification

    /**
     * Purpose   : To create DbsExistenceQualification using ExistenceQualification class
     * @throws   : DbsException - if operation fails.
     */
    public DbsExistenceQualification() throws DbsException {
        try {
            this.existenceQualification= new ExistenceQualification();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }    
    }

    /**
	   * Purpose : To create DbsExistenceQualification using ExistenceQualification class
	   * @param  : existenceQualification - An ExistenceQualification Object  
	   */    
    public DbsExistenceQualification(ExistenceQualification existenceQualification) {
        this.existenceQualification = existenceQualification;
    }

    /**
	   * Purpose  : Used to get the object of class ExistenceQualification
	   * @returns : ExistenceQualification Object
	   */
    public ExistenceQualification getExistenceQualification() {
        return this.existenceQualification;
    }

    /**
	   * Purpose  : Gets the left hand side Attribute name.
	   * @returns : Name of the left hand side attribute.
	   */
    public java.lang.String getLeftAttributeName() throws DbsException{
        try{
            return this.existenceQualification.getLeftAttributeName();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }    
    }

    /**
	   * Purpose  : Gets the left hand side class name.
	   * @returns : Name of the left hand side iFS class.
	   */
    public java.lang.String getLeftClassname() throws DbsException{
        try{
            return this.existenceQualification.getLeftClassname();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Gets the right hand side Attribute name.
	   * @returns : Name of the right hand side attribute.
	   */
    public java.lang.String getRightAttributeName() throws DbsException{
        try{
            return this.existenceQualification.getRightAttributeName();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose  : Gets up the Attribute value array for the right hand side.
	   * @returns : Name of the right hand side iFS class.
	   */
    public AttributeValue[] getRightAttributeValue() throws DbsException{
        try{
            return this.existenceQualification.getRightAttributeValue();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Gets the Right hand side class name.
	   * @returns : array of attribute values
	   */
    public java.lang.String getRightClassname() throws DbsException {
        try{
            return this.existenceQualification.getRightClassname();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Gets true if the ExistenceQualification was constructed by supplying an Attribute Value array for RHS.
	   * @returns : boolean True if the RHS list is a AV[] supplied by user.
	   */
    public boolean isRightAttributeValue() throws DbsException{
        try{
            return this.existenceQualification.isRightAttributeValue();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Sets the left hand side class and attribute name of this qualification. 
     *            The class name used here should be one of the search classes..
	   * @param   : dbsClass - left hand side class name
	   * @param   : dbsAttribute - left hand side attribute name
	   */
    public void setLeftAttribute(java.lang.String dbsClassname,java.lang.String dbsAttribute) throws DbsException{
        try{
            existenceQualification.setLeftAttribute(dbsClassname,dbsAttribute);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Sets the right hand side class and attribute name of this qualification. 
     *            The class used here need not be in the list of search classes because, 
     *            it is used in a separate sub query.
	   * @param   : dbsClassName - left hand side class name
	   * @param   : dbsAttribute - left hand side attribute name
	   */
    public void setRightAttribute(java.lang.String dbsClassname, java.lang.String dbsAttribute) throws DbsException{
        try{
            existenceQualification.setRightAttribute(dbsClassname,dbsAttribute);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Sets up the Attribute value array for the right hand side. 
     *            Use this method to set up user supplied values for the right hand side.
	   * @param   : avArray - array of attribute values
	   */
    public void setRightAttributeValue(DbsAttributeValue[] avArray) throws DbsException{
        try{
            if(avArray!=null && avArray.length > 0){
              int length=avArray.length;
              AttributeValue[] attrVal=new AttributeValue[length];
              for(int index=0;index < length ;index++){
                attrVal[index]=avArray[index].getAttributeValue();
              }            
             this.existenceQualification.setRightAttributeValue(attrVal);
           }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

     public SearchQualification getSearchQualification() {
        return this.existenceQualification;
    }
}
