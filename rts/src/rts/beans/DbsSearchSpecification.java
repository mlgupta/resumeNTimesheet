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
 * $Id: DbsSearchSpecification.java,v 1.1.1.1 2005/05/26 12:31:57 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of SearchSpecification class provided by CMSDK API.
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    25-02-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
import oracle.ifs.search.*;

public class DbsSearchSpecification {
    protected SearchSpecification searchSpecification=null;// to accept object of type SearchSpecification
    protected DbsSearchSpecification() {}

    /**
	   * Purpose : To create DbsSearchSpecification using SearchSpecification class
	   * @param  : searchSpecification - An SearchSpecification Object  
	   */    
    public DbsSearchSpecification(SearchSpecification searchSpecification) {
        this.searchSpecification = searchSpecification;
    }

    /**
	   * Purpose  : Used to get the object of class SearchSpecification
	   * @returns : SearchSpecification Object
	   */
    public SearchSpecification getSearchSpecification() {
        return this.searchSpecification;
    }

    /**
	   * Purpose  : Returns all Result Classes
	   * @returns : Array
	   */
    public java.lang.String[] getResultClasses() throws DbsException{
        try{
            return this.searchSpecification.getResultClasses();
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }
    }

    /**
	   * Purpose  : Returns the SearchClassSpecification associated with this SearchSpecification.
	   * @returns : SearchClassSpecification associated with this SearchSpecification.
	   */
    public DbsSearchClassSpecification getSearchClassSpecification() throws DbsException{
        try{
            return new DbsSearchClassSpecification(this.searchSpecification.getSearchClassSpecification());
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }        
    }
} 
