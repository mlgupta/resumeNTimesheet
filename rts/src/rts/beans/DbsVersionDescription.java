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
 * $Id: DbsVersionDescription.java,v 1.3 2005/07/02 05:43:57 suved Exp $
 *****************************************************************************
 */
package rts.beans; 
/**
 *	Purpose: The encapsulate the functionality of VersionDescription class provided by CMSDK API.
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

public class DbsVersionDescription extends DbsPublicObject {
    
    public static final String REVISIONCOMMENT_ATTRIBUTE = VersionDescription.REVISIONCOMMENT_ATTRIBUTE;
    private VersionDescription versionDescription; // to accept object of type VersionDescription
    /**
     * Purpose : To create VersionDescription using VersionDescription class
     * @param  : versionDescription - An VersionDescription Object  
     */
    public DbsVersionDescription(VersionDescription versionDescription) {
        super(versionDescription);
        this.versionDescription=versionDescription;
    }

    /**
     * Purpose  : Used to get the object of class VersionDescription
     * @returns : VersionDescription Object
     */
    public VersionDescription getVersionDescription() {
        return this.versionDescription;
    }


    /**
     * Purpose    : Returns the linear, system generated version number for this version description.
     * @overrides : getVersionNumber in class DbsPublicObject
     * @returns   : this version description's version number
     * @throws    : DbsException - if operation fails.
     */
    public long getVersionNumber() throws DbsException{
        try{
            return this.versionDescription.getVersionNumber();
        }catch(IfsException iex){
            throw new DbsException(iex); 
        }
    }

    /**
     * Purpose  : Returns the DbsPublicObject for this version description.
     * @returns : this version description's DbsPublicObject.
     * @throws  : DbsException - if operation fails.
     */
    public DbsPublicObject getDbsPublicObject() throws DbsException{
        try{
             PublicObject pubTemp=versionDescription.getPublicObject();
             DbsPublicObject pubObjToReturn=null;
              if(pubTemp instanceof Document){
                pubObjToReturn = new DbsDocument((Document)pubTemp);
            }else{
                pubObjToReturn = new DbsPublicObject(pubTemp);
            }            
            return pubObjToReturn;
        }catch(IfsException iex){
            throw new DbsException(iex); 
        }
    }

    /**
     * Purpose  : Returns the user specified revision comment for this version description.
     * @returns : this version description's revision comment
     * @throws  : DbsException - if operation fails.
     */
    public java.lang.String getRevisionComment() throws DbsException {
        try{
            return versionDescription.getRevisionComment();
        }catch(IfsException iex){
            throw new DbsException(iex); 
        }    
    }

    /**
     * Purpose  : Cover for getVersionSeries().isLatestVersionDescription(this)
     * @returns : true if this version description is the latest in the series.    
     * @throws  : DbsException - if operation fails.
     */
    public boolean isLatestVersionDescription() throws DbsException{
        try{
            return versionDescription.isLatestVersionDescription() ;
        }catch(IfsException iex){
            throw new DbsException(iex); 
        }   
    }
    
    public DbsVersionSeries getVersionSeries()throws DbsException{
    
        try{
          
            return new DbsVersionSeries(this.versionDescription.getVersionSeries());
          
        }catch(IfsException iex){
        
            throw new DbsException(iex);
            
        }
    
    }
}
