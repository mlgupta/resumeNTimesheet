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
 * $Id: DbsClassObject.java,v 1.2 2005/07/02 05:43:57 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of ClassObject class provided by CMSDK API.
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

public class DbsClassObject extends DbsSchemaObject {

    private ClassObject classObject=null; // to accept object of type ClassObject
    /**The class name for this class. Useful for methods that take a class name argument.*/
    public static final java.lang.String CLASS_NAME= ClassObject.CLASS_NAME;                                    
    /**The ClassObject that is the superclass of this ClassObject.*/    
    public static final java.lang.String SUPERCLASS_ATTRIBUTE=ClassObject.SUPERCLASS_ATTRIBUTE;                 
    /**A textual description of this ClassObject.*/
    public static final java.lang.String DESCRIPTION_ATTRIBUTE=ClassObject.DESCRIPTION_ATTRIBUTE;               
    /**The fully-qualified classname of the Server Java class used to represent instances of this ClassObject in the repository service.*/
    public static final java.lang.String SERVERCLASSPATH_ATTRIBUTE=ClassObject.SERVERCLASSPATH_ATTRIBUTE;       
    /**The fully-qualified classname of the Beans Java class used to represent instances of this ClassObject in the repository SDK.*/
    public static final java.lang.String BEANCLASSPATH_ATTRIBUTE=ClassObject.BEANCLASSPATH_ATTRIBUTE;           
    /**The fully-qualified classname of the Beans Java class used to represent instances of this ClassObject in the repository SDK.*/
    public static final java.lang.String SELECTORCLASSPATH_ATTRIBUTE=ClassObject.SELECTORCLASSPATH_ATTRIBUTE;   
    /**The base name for database objects used to store data for instances of this ClassObject.*/
    public static final java.lang.String DATABASEOBJECTNAME_ATTRIBUTE=ClassObject.DATABASEOBJECTNAME_ATTRIBUTE; 
    /**The ClassAccessControlList that determines which users and/or groups can create instances of this ClassObject. 
     * If a ClassACL is not specified, then any user can create an instance of the ClassObject (unless it is an abstract class).*/
    public static final java.lang.String CLASSACL_ATTRIBUTE=ClassObject.CLASSACL_ATTRIBUTE;                     
    /**An indicator of whether this ClassObject is abstract. Abstract ClassObjects cannot be instantiated.*/
    public static final java.lang.String ABSTRACT_ATTRIBUTE=ClassObject.ABSTRACT_ATTRIBUTE;                     
    /**An indicator of whether this ClassObject is final. Final ClassObjects cannot be subclassed.*/
    public static final java.lang.String FINAL_ATTRIBUTE=ClassObject.FINAL_ATTRIBUTE;                           
    /**An indicator of whether the database table that stores data for instances of this ClassObject is partitioned. Currently only PublicObject database tables are partitioned.*/
    public static final java.lang.String PARTITIONED_ATTRIBUTE=ClassObject.PARTITIONED_ATTRIBUTE;               
    /**A system-set attribute used to ensure each ClassObject has a unique Name.*/
    public static final java.lang.String UNIQUENAME_ATTRIBUTE=ClassObject.UNIQUENAME_ATTRIBUTE;                 

    /**
	   * Purpose : To create DbsClassObject using classObject class
	   * @param  : classObject - An ClassObject Object  
	   */    
    public DbsClassObject(ClassObject classObject) {
        super(classObject);
        this.classObject = classObject;
    }

    /**
	   * Purpose   : Gets the subclasses of this ClassObject.The subclasses are the ClassObjects that directly extend 
     *             this ClassObject and their subclasses, sub-subclasses, etc.
	   * @returns  : the subclasses
     * @throws   : DbsException - if operation fails
	   */
    public DbsClassObject[] getSubclasses() throws DbsException{
        DbsClassObject[] dbsClassObject=null;  
        try{
            int classObjectCount=this.classObject.getSubclasses().length;
            dbsClassObject=new DbsClassObject[classObjectCount];
            ClassObject[] classObject=this.classObject.getSubclasses();
            for(int index=0;index<classObjectCount;index++){
                dbsClassObject[index]=new DbsClassObject(classObject[index]);              
            }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
        return dbsClassObject;
    }

    /**
	   * Purpose   : Gets the specified subclass of this ClassObject. The subclasses are the ClassObjects 
     *             that directly extend this ClassObject and their subclasses, sub-subclasses, etc.
	   * @param    : index - the zero-based index
	   * @returns  : the subclass
     * @throws   : DbsException - if operation fails
	   */    
    public DbsClassObject getSubclasses(int index) throws DbsException{
        try{
            return new DbsClassObject(this.classObject.getSubclasses(index));
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Used to get the object of class ClassObject
	   * @returns : ClassObject Object
	   */
    public ClassObject getClassObject() {
        return this.classObject;
    } 
    
    public String getName() throws DbsException {
      try{
        return this.classObject.getName();
      }catch( IfsException ifsError ){
        throw new DbsException(ifsError);
      }
    }
}
