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
 * $Id: DbsSortQualifier.java,v 1.1.1.1 2005/05/26 12:31:57 suved Exp $
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

public class DbsSortQualifier {
    private SortQualifier sortQualifier=null;
    public DbsSortQualifier() {}

    /**
     * Purpose   : Construct a SortQualifier for a simple qualifier of Attribute and sort direction. 
     *             The Attribute's ClassObject is assumed to be the default set on the parent SortSpecification.
     * @param    : sortAttribute - the sort attribute name
     * @param    : sortOrder - the corresponding sort order
     */
    public DbsSortQualifier(java.lang.String sortAttribute, boolean sortOrder) throws DbsException{
        try {
            this.sortQualifier = new SortQualifier(sortAttribute,sortOrder);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Used to get the object of class SortQualifier
	   * @returns : SortQualifier Object
	   */
    public SortQualifier getSortQualifier() {
        return this.sortQualifier;
    }
}
