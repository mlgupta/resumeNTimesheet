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
 * $Id: DbsVersionSeries.java,v 1.4 2005/07/02 05:43:57 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of VersionSeries class provided by CMSDK API.
 * 
 *  @author              Jeetendra Prasad
 *  @version             1.0
 * 	Date of creation:    
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsVersionSeries extends DbsPublicObject {
    private VersionSeries versionSeries; // to accept object of type VersionSeries

    /**
	   * Purpose : To create DbsVersionSeries using VersionSeries class
	   * @param  : versionSeries - An VersionSeries Object  
	   */
    public DbsVersionSeries(VersionSeries versionSeries) {
        super(versionSeries);
        this.versionSeries = versionSeries;
    }

    /**
	   * Purpose : Used to get the object of class VersionSeries
	   * @return : VersionSeriesObject
	   */    
    public VersionSeries getVersionSeries(){
        return versionSeries;
    }

    /**
     * Purpose   : Returns the first VersionDescription of this series.
     * @returns  : the first VersionDescription of this series.
     * @throws   : DbsException - if operation fails
     */
    public DbsVersionDescription getFirstVersionDescription() throws DbsException{
        DbsVersionDescription dbsVersionDescription = null;
        try{
            dbsVersionDescription = new DbsVersionDescription(versionSeries.getFirstVersionDescription());
        }catch(IfsException iex){
            throw new DbsException(iex);
        }
        return dbsVersionDescription;
    }

    /**
     * Purpose   : Returns the last VersionDescription of this series.
     * @returns  : the last VersionDescription of this series.
     * @throws   : DbsException - if operation fails
     */
    public DbsVersionDescription getLastVersionDescription() throws DbsException{
        DbsVersionDescription dbsVersionDescription;
        try{
            dbsVersionDescription = new DbsVersionDescription(versionSeries.getLastVersionDescription());
        }catch(IfsException iex){
            throw new DbsException(iex);
        }
        return dbsVersionDescription;
    }

    /**
     * Purpose   : Returns an array of all VersionDescriptions in this VersionSeries.
     * @returns  : all VersionDescriptions in this VersionSeries.
     * @throws   : DbsException - if operation fails
     */
    public DbsVersionDescription[] getVersionDescriptions() throws DbsException{
        DbsVersionDescription[] dbsVersionDescription=null;
        try{
            int versionDescriptionCount=versionSeries.getVersionDescriptions().length;
            dbsVersionDescription=new DbsVersionDescription[versionDescriptionCount];            
            VersionDescription[] versionDescriptions=this.versionSeries.getVersionDescriptions();
            for(int index=0;index<versionDescriptionCount;index++){
                dbsVersionDescription[index]=new DbsVersionDescription(versionDescriptions[index]);              
            }            
        }catch(IfsException iex){
            throw new DbsException(iex);
        }
        return dbsVersionDescription;
    }

    /**
     * Purpose    : Returns the Date that this VersionSeries was reserved, null if not reserved.
     * @overrides : getReservationDate in class PublicObject
     * @returns   : the Date that this VersionSeries was reserved, null if not reserved.
     * @throws    : DbsException - if operation fails
     */
    public java.util.Date getReservationDate() throws DbsException{
        try{
            return this.versionSeries.getReservationDate();
        }catch(IfsException iex){
            throw new DbsException(iex);
        }
    }

    /**
     * Purpose    : Returns the User that reserved this VersionSeries, null if not reserved
     * @overrides : getReservor in class DbsPublicObject
     * @returns   : the User that reserved this VersionSeries, null if not reserved
     * @throws    : DbsException - if operation fails
     */
    public DbsDirectoryUser getReservor() throws DbsException{
       try{
            return new DbsDirectoryUser(this.versionSeries.getReservor());  
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        } 
    }

    
    /**
     * Purpose    : Returns the comment when this series was reserved, or null if this series is not reserved.
     * @overrides : getReservationComment in class DbsPublicObject
     * @returns   : the reservation comment.
     * @throws    : DbsException - if operation fails
     */
    public java.lang.String getReservationComment()throws DbsException{
       try{
            return this.versionSeries.getReservationComment();  
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }     
    }

    /**
     * Purpose    : Returns true if this VersionSeries is reserved by any user.
     * @overrides : isReserved in class DbsPublicObject
     * @returns   : true if this VersionSeries is reserved by any user.
     * @throws    : DbsException - if operation fails
     */
    public boolean isReserved() throws DbsException{
       try{
            return this.versionSeries.isReserved();  
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }  
    }


    public DbsVersionDescription newVersion(DbsVersionDescriptionDefinition dbsVdd) throws DbsException{
      
      DbsVersionDescription dbsVd = null;
      try{
        
        VersionDescription vd = this.versionSeries.newVersion(dbsVdd.getVersionDescriptionDefinition());
        dbsVd = new DbsVersionDescription(vd);
        
      }catch( IfsException ifsError ){
        throw new DbsException(ifsError);
      }
      
      return dbsVd;
    }
    public void unReserve() throws DbsException{
      
      try{
        
          this.versionSeries.unReserve();
        
      }catch( IfsException ifsError ){
        throw new DbsException(ifsError);
      }
      
    }
    /**
     * Purpose    : Reserves the right to add the next version. This locks the series against additional versions by anyone else. 
     *              A locked VersionSeries cannot be deleted; Checking in or canceling checkout will unreserve the series. 
     *              Path being passed as parameter, is for convenience only and is not used or validated.
     * @overrides : reserveNext in class DbsPublicObject
     * @param     : contentPath - path to local file of reserved content
     * @param     : Comment - Check in comment.
     * @throws    : DbsException - if operation fails
     */
    public void reserveNext(java.lang.String contentPath,java.lang.String comment) throws DbsException{
       try{
             this.versionSeries.reserveNext(contentPath,comment);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }      
    }
    
    public DbsFamily getFamily() throws DbsException{
       
       try{

          return new DbsFamily(this.versionSeries.getFamily());

       }catch( IfsException ifsError ){
         
         throw new DbsException(ifsError);
         
       }
    
    }
}
