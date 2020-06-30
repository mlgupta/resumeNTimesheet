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
 * $Id: DbsContentQuota.java,v 1.1.1.1 2005/05/26 12:31:42 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of ContentQuota class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 *	Last Modfied Date:    
 */

/*CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsContentQuota extends DbsApplicationObject {

    /**This class name for this class. Useful for methods that take a class name argument.*/
    public static final java.lang.String CLASS_NAME=ContentQuota.CLASS_NAME;
    /**The DirectoryUser that has allocated/consumed the storage space represented by this ContentQuota.*/
    public static final java.lang.String ASSOCIATEDPUBLICOBJECT_ATTRIBUTE=ContentQuota.ASSOCIATEDPUBLICOBJECT_ATTRIBUTE;
    /**The amount of storage space allocated by this ContentQuota to the DirectoryUser.*/
    public static final java.lang.String ALLOCATEDSTORAGE_ATTRIBUTE=ContentQuota.ALLOCATEDSTORAGE_ATTRIBUTE;
    /**The amount of storage space currently consumed by the DirectoryUser.*/    
    public static final java.lang.String CONSUMEDSTORAGE_ATTRIBUTE=ContentQuota.CONSUMEDSTORAGE_ATTRIBUTE; 
    /**An indicator of whether the ContentQuota is enabled or disabled.*/    
    public static final java.lang.String ENABLED_ATTRIBUTE=ContentQuota.ENABLED_ATTRIBUTE;
    /**A system-set attribute used to ensure each ContentQuota has a unique name.*/        
    public static final java.lang.String UNIQUENAME_ATTRIBUTE=ContentQuota.UNIQUENAME_ATTRIBUTE;

    private ContentQuota contentQuota=null; // to accept object of type ContentQuota

    /**
     * Purpose : To create DbsContentQuota using ContentQuota class
	   * @param  : contentQuota - An ContentQuota Object  
	   */    
    public DbsContentQuota(ContentQuota contentQuota) {
        super(contentQuota);
        this.contentQuota=contentQuota;
    }

    /**
     * Purpose   : Gets the amount of allocated storage. Returns 0 if no storage value is set.
	   * @returns  : allocated storage (in bytes)
     * @throws   : DbsException - if operation fails.
	   */
    public long getAllocatedStorage()throws DbsException{
        try{
            return this.contentQuota.getAllocatedStorage();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Sets the amount of allocated storage.
	   * @param    : allocated - storage (in bytes)
     * @throws   : DbsException - if operation fails.
	   */
    public void setAllocatedStorage(long value) throws DbsException {
        try{
            this.contentQuota.setAllocatedStorage(value);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Determines whether quota enforcement is enabled.
	   * @returns  : true if quota enabled
     * @throws   : DbsException - if operation fails.
	   */
    public boolean isEnabled() throws DbsException{
        try{
            return this.contentQuota.isEnabled();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
    
    /**
     * Purpose   : ContentQuota can be enabled or disabled.
	   * @param    : isEnabled - true if quota should be effective
     * @throws   : DbsException - if operation fails.
	   */
    public void setEnabled(boolean isEnabled)throws DbsException{
        try{
            this.contentQuota.setEnabled(isEnabled);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
  
   /**
	  * Purpose : Used to get the object of class ContentQuota
	  * @return ContentQuota Object
	  */
    public ContentQuota getContentQuota() {
        return contentQuota;
    }
}
