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
 * $Id: DbsFamily.java,v 1.1.1.1 2005/05/26 12:31:45 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of AccessLevel class provided by CMSDK API.
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

public class DbsFamily extends DbsPublicObject {
    private Family family; // to accept object of type Family

    /**This class name for this class.*/
    public static final String CLASS_NAME = Family.CLASS_NAME;

    /**
     * Purpose   : To create DbsFamily using Family class
     */
    public Family getIfsFamily(){
        return family;
    }

    /**
	   * Purpose : To create DbsFamily using Family class
	   * @param  : family - An Family Object  
	   */
    public DbsFamily(Family family) {
        super(family);
        this.family = family;
    }

    /**
	   * Purpose    : Returns the PublicObject to which this object resolves. 
     *              For the non version classes (for example, Document and Folder) this simply returns itself (this). 
     *              For the version classes, (Family, VersionSeries, and VersionDescription), 
     *              this will return a non version-class object as determined by the configuration of the instance of the version class.
     *              This override exists to return the Pending PublicObject value of the primary VersionSeries if the caller is the 
     *              Reservor of that VersionSeries; in all other cases, it defers to the implementation in the superclass (PublicObject). 
     * @overrides : getResolvedPublicObject in class PublicObject           
	   * @returns   : PublicObject to which this object resolves
     * @throws    : DbsException - if operation fails.
	   */
    public DbsPublicObject getResolvedPublicObject() throws DbsException{
        DbsPublicObject dbsPublicObject;
        PublicObject publicObject;
        try{
            publicObject = family.getResolvedPublicObject();
            if(publicObject instanceof Document){
                   dbsPublicObject = new DbsDocument((Document)publicObject);
            }else{
                dbsPublicObject = new DbsPublicObject(publicObject);
            }
        }catch(IfsException iex){
            throw new DbsException(iex);
        }
        return dbsPublicObject;
    }

    /**
	   * Purpose    : Gets the PrimaryVersionSeries in this Family.
     * @returns   : the PrimaryVersionSeries.
     * @throws    : DbsException - if operation fails.
	   */
    public DbsVersionSeries getPrimaryVersionSeries() throws DbsException{
        DbsVersionSeries dbsVersionSeries;
        try{
            dbsVersionSeries = new DbsVersionSeries(family.getPrimaryVersionSeries());
        }catch(IfsException iex){
            throw new DbsException(iex);
        }
        return dbsVersionSeries;    
    }
}
