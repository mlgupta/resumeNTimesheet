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
 * $Id: DbsSearchQualification.java,v 1.1.1.1 2005/05/26 12:31:55 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of SearchQualification class provided by CMSDK API.
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

public class DbsSearchQualification {
    /**Represents the LATE BINDING character. Values set to this are replaced by bind Values when the Search is executed.*/
    public static final java.lang.String LATE_BIND_OPER = SearchQualification.LATE_BIND_OPER;
    protected SearchQualification searchQualification = null;  // to accept object of type SearchQualification
    protected DbsSearchQualification(){}

    /**
	   * Purpose : To create DbsSearchQualification using SearchQualification class
	   * @param  : searchQualification - An SearchQualification Object  
	   */    
    public DbsSearchQualification(SearchQualification searchQualification) {
        this.searchQualification = searchQualification;
    }

    /**
	   * Purpose  : Used to get the object of class SearchQualification
	   * @returns : SearchQualification Object
	   */
    public void setSearchQualification(AttributeQualification attrQual) {
        this.searchQualification=attrQual;
    }   

    /**
	   * Purpose : Used to get the object of class SearchQualification
	   * @return : searchQualification object.
	   */
    public SearchQualification getSearchQualification() {
        return this.searchQualification;
    }
    
}
