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
 * $Id: DbsContextQualification.java,v 1.1.1.1 2005/05/26 12:31:43 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of ContextQualification class provided by CMSDK API.
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

public class DbsContextQualification extends DbsSearchQualification{
    private ContextQualification contextQualification=null; // to accept object of type ContextQualification

    /**Searches can be ordered based on the Text Scores.*/
    public static java.lang.String ORDER_PREFIX = ContextQualification.ORDER_PREFIX;

    public DbsContextQualification() {
        super();
        this.contextQualification= new ContextQualification();
    }

    /**
	   * Purpose : To create DbsContextQualification using ContextQualification class
	   * @param  : contextQualification - An ContextQualification Object  
	   */    
    public DbsContextQualification(ContextQualification contextQualification) {
        super();
        this.contextQualification = contextQualification;
    }

    /**
	   * Purpose  : Used to get the object of class ContextQualification
	   * @returns : contextQualification Object
	   */
    public ContextQualification getContextQualification() {
        return this.contextQualification;
    }

    /**
	   * Purpose  : Set the name of the Qualification. This name is used to specify sorting by Context Scores, and to retrieve Context Scores.
	   * @param   : dbsName - qualification name, should be unique inside the search tree.
	   */
    public void setName(java.lang.String dbsName) throws DbsException{
        try{
            contextQualification.setName(dbsName);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Get the Qualification name.
	   * @param   : Qualification name.
	   */
    public java.lang.String getName(){
        return this.contextQualification.getName();
    }

    /**
	   * Purpose  : Set the Text Query Expression. The expression cannot be null.
	   * @param   : dbsQuery - text query expression. Is not parsed, is passed onto 
     *            InterMedia; hence user can specify any Text Query Expression. 
     *            See InterMedia documentation for details.
	   */
    public void setQuery(java.lang.String dbsQuery) throws DbsException{
        try{
            contextQualification.setQuery(dbsQuery);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Returns the text query expression
	   * @param   : text query expression.
	   */
    public java.lang.String getQuery(){
        return this.contextQualification.getQuery();
    }

    public SearchQualification getSearchQualification() {
        return this.contextQualification;
    }
}
