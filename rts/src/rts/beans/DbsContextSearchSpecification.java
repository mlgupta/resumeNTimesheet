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
 * $Id: DbsContextSearchSpecification.java,v 1.1.1.1 2005/05/26 12:31:43 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of ContextSearchQualification class provided by CMSDK API.
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

public class DbsContextSearchSpecification extends DbsAttributeSearchSpecification {
    protected ContextSearchSpecification contextSearchSpecification=null; // to accept object of type ContextSearchSpecification

    /**
     * Purpose   : To create DbsContextSearchSpecification using ContextSearchSpecification class
     * @throws   : DbsException - if operation fails.
     */
    public DbsContextSearchSpecification() {
        super();
        this.contextSearchSpecification = new ContextSearchSpecification();
    }

    /**
	   * Purpose : To create DbsContextSearchSpecification using ContextSearchSpecification class
	   * @param  : contextSearchSpecification - An ContextSearchSpecification Object  
	   */    
    public DbsContextSearchSpecification(ContextSearchSpecification contextSearchSpecification) {
        super(contextSearchSpecification);
        this.contextSearchSpecification = contextSearchSpecification;
    }

    /**
	   * Purpose  : Used to get the object of class ContextSearchSpecification
	   * @returns : contextSearchSpecification Object
	   */
    public ContextSearchSpecification getContextSearchSpecification() {
        return this.contextSearchSpecification;
    }

    /**
	   * Purpose  : Set the Class to be used for Text Queries. 
     *            The class should be a subClass of ContentObject or ContentObject itself.
	   * @param   : dbsContextClassName - Text Queries in ContextQualifications are applied on the content of objects of this class.
	   */
    public void setContextClassname(java.lang.String dbsContextClassName) throws DbsException{
        try{
            contextSearchSpecification.setContextClassname(dbsContextClassName);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }        
    }

    public void setSearchQualification(DbsSearchClause dbsSearchQualification) throws DbsException{
        try{
            this.contextSearchSpecification.setSearchQualification(dbsSearchQualification.getSearchClause());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    public void setSearchClassSpecification(DbsSearchClassSpecification dbsSearchClassSpecification) throws DbsException{
        try{
            this.contextSearchSpecification.setSearchClassSpecification(dbsSearchClassSpecification.getSearchClassSpecification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose  : Returns the name of the class used for Text queries.
	   * @returns : Context Search Class.
	   */
    public java.lang.String getContextClassname() throws DbsException{
        try{
            return this.contextSearchSpecification.getContextClassname();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }
}
