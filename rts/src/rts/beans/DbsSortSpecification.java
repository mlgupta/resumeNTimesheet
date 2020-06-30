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
 * $Id: DbsSortSpecification.java,v 1.1.1.1 2005/05/26 12:31:57 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of SortSpecification class provided by CMSDK API.
 * 
 * @author              Rajan Kamal Gupta
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsSortSpecification {

    private SortSpecification sortSpecification=null;// to accept object of type SortSpecification

    /**
     * Purpose   : To create DbsSortSpecification using SortSpecification class
     * @param    : ace - An SortSpecification  Object
     */
    public DbsSortSpecification() throws DbsException {
        try{
            this.sortSpecification=new SortSpecification();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Used to get the object of class SortQualifier
	   * @returns : SortQualifier Object
	   */
    public SortSpecification getSortSpecification() {
        return this.sortSpecification;
    }

    /**
     * Purpose   : Add a SortQualifier.
     * @param    : dbsSortQual - the sort qualifier
     * @throws   : DbsException - if operation fails.
     */
    public void addSortQualifier(DbsSortQualifier dbsSortQual) throws DbsException {
        try{
            sortSpecification.addSortQualifier(dbsSortQual.getSortQualifier());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Add a simple SortQualifier.
     * @param    : sortAttribute - the sort attribute name
     * @param    : sortOrder - the corresponding sort order
     * @throws   : DbsException - if operation fails.
     */
    public void addSortQualifier(java.lang.String sortAttribute, boolean sortOrder) throws DbsException{
        try{
            sortSpecification.addSortQualifier(sortAttribute,sortOrder);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }
}
