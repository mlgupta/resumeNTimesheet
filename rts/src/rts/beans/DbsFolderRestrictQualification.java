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
 * $Id: DbsFolderRestrictQualification.java,v 1.1.1.1 2005/05/26 12:31:49 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of FolderRestrictQualification class provided by CMSDK API.
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

public class DbsFolderRestrictQualification extends DbsSearchQualification {
    private FolderRestrictQualification folderRestrictQualification=null; // to accept object of type FolderRestrictQualification

    /**
     * Purpose   : To create DbsFolderRestrictQualification using FolderRestrictQualification class
     * @throws   : DbsException - if operation fails.
     */
    public DbsFolderRestrictQualification() {
        this.folderRestrictQualification = new FolderRestrictQualification();
    }

    /**
	   * Purpose : To create DbsFolderRestrictQualification using FolderRestrictQualification class
	   * @param  : folderRestrictQualification - An FolderRestrictQualification Object  
	   */    
    public DbsFolderRestrictQualification(FolderRestrictQualification folderRestrictQualification) {
        this.folderRestrictQualification = folderRestrictQualification;
    }

    /**
	   * Purpose  : Used to get the object of class FolderRestrictQualification
	   * @returns : FolderRestrictQualification Object
	   */
    public FolderRestrictQualification getFolderRestrictQualification() {
        return this.folderRestrictQualification;
    }

    /**
	   * Purpose  : Sets the SearchClass. ClassName should be a valid iFS class. 
     *            A null Class is taken to mean, use the first Result Class of the SearchSpecification. 
     *            If the class is a FOLDER, then current folder will also be returned in the result.
	   * @param   : dbsClassName - The class of the attribute
	   */
    public void setSearchClassname(java.lang.String dbsClassName) {
        folderRestrictQualification.setSearchClassname(dbsClassName);
    }

    /**
	   * Purpose  : Returns the name of the search class.
	   * @returns : search class.    
	   */
    public java.lang.String getSearchClassname(){
        return this.folderRestrictQualification.getSearchClassname();
    }

    /**
	   * Purpose  : Sets the startFolder. AttributeValue should encapsulate a folder object.
	   * @param   : value - the start Folder.
     * @throws   : DbsException - if operation fails.
	   */
    public void setStartFolder(DbsAttributeValue dbsAttributeValue) throws DbsException {
        try{
            folderRestrictQualification.setStartFolder(dbsAttributeValue.getAttributeValue());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Sets the startFolder.
	   * @param   : value - the start Folder.
     * @throws   : DbsException - if operation fails.
	   */
    public void setStartFolder(DbsFolder dbsFolder) throws DbsException {
        try{
            folderRestrictQualification.setStartFolder(dbsFolder.getFolder());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }    
    }

    /**
	   * Purpose  : Returns the start folder
	   * @param   : dbsSession - LibrarySession
     * @returns : start folder 
     * @throws   : DbsException - if operation fails.
	   */
    public Folder getStartingFolder(DbsLibrarySession dbsLibrarySession) throws DbsException {
        try{
            return this.folderRestrictQualification.getStartingFolder(dbsLibrarySession.getLibrarySession());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose  : Sets whether the search is for items directly in the starting folder, 
     *            or at any level below the starting folder.
	   * @param   : dbsMultiLevel - if true, the search is for any level; if false, the search 
     *            is only for items directly in the starting folder.
     * @throws   : DbsException - if operation fails.
     */
    public void setMultiLevel(boolean dbsMultiLevel) throws DbsException {
        try{
            folderRestrictQualification.setMultiLevel(dbsMultiLevel);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Return whether the search is for items directly in the starting folder, 
     *            or at any level below the starting folder.
	   * @returns : true if search is for any level, and false if the search is only for 
     *            items directly in the starting folder.
     */
    public boolean isMultiLevel() {
        return this.folderRestrictQualification.isMultiLevel();
    }

    public SearchQualification getSearchQualification() {
        return this.folderRestrictQualification;
    }
}
