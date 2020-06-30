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
 * $Id: DbsLibraryObject.java,v 1.3 2005/07/02 05:43:57 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of LibraryObject class provided by CMSDK API.
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

public class DbsLibraryObject {

    private LibraryObject libraryObject = null;  // to accept object of type LibraryObject

    protected DbsLibraryObject(){}
    
    /**
	   * Purpose : To create DbsLibraryObject using LibraryObject class
	   * @param  : libraryObject - An LibraryObject Object  
	   */    
    public DbsLibraryObject(LibraryObject libraryObject) {
        this.libraryObject = libraryObject;
    }

    public String getName()throws DbsException{
        try{
            return this.libraryObject.getName();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    public Long getId() throws DbsException{
       try{
          return this.libraryObject.getId();
       }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
    
    public DbsAttributeValue getAttribute(String attrName)throws DbsException{
        DbsAttributeValue attrValue=null;
        try{           
            if(this.libraryObject.getAttribute(attrName)!=null){
                attrValue= new DbsAttributeValue(this.libraryObject.getAttribute(attrName));
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return attrValue;
    } 
    
    public void setAttribute(String attrName,DbsAttributeValue attrValue)throws DbsException{
        try{
            this.libraryObject.setAttribute(attrName,attrValue.getAttributeValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    public void setName(java.lang.String name) throws DbsException{
        try {
          this.libraryObject.setName(name);
        }catch(IfsException ifsError) {
              throw new DbsException(ifsError);
        }
    }

    public void free()throws DbsException{
        try{
           this.libraryObject.free();
        }catch(IfsException ifsError) {
           throw new DbsException(ifsError);
        }
    }

   /**
	  * Purpose  : Used to get the object of class LibraryObject
	  * @returns : LibraryObject Object
	  */
    public LibraryObject getLibraryObject() {
        return this.libraryObject ;
    }
    
    public ClassObject getClassObject() throws DbsException {
      try{
        return this.libraryObject.getClassObject();
      }catch( IfsException ifsError ){
        throw new DbsException(ifsError);
      }
    }
    public void update( DbsLibraryObjectDefinition dbsLibObjDef ) throws DbsException {
      
      try{
        
        this.libraryObject.update(dbsLibObjDef.getLibraryObjectDefinition());
        
      }catch( IfsException ifsError ){
        
        throw new DbsException(ifsError);
        
      }
      
    }
}
