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
 * $Id: DbsFreeFormQualification.java,v 1.1.1.1 2005/05/26 12:31:49 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of FreeFormQualification class provided by CMSDK API.
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

public class DbsFreeFormQualification extends DbsSearchQualification {
    private FreeFormQualification freeFormQualification=null; // to accept object of type FreeFormQualification

    public DbsFreeFormQualification() throws DbsException{
        try{
          this.freeFormQualification=new FreeFormQualification();
        }catch(IfsException ifsError) {
          throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose : To create DbsFreeFormQualification using FreeFormQualification class
	   * @param  : freeFormQualification - An FreeFormQualification Object  
	   */    
    public DbsFreeFormQualification(FreeFormQualification freeFormQualification) {
        super();
        this.freeFormQualification = freeFormQualification;
    }

    /**
	   * Purpose  : Used to get the object of class FreeFormQualification
	   * @returns : FreeFormQualification Object
	   */
    public FreeFormQualification getFreeFormQualification() {
        return this.freeFormQualification;
    }

    /**
	   * Purpose  : Gets the SQL expression of this object.
	   * @returns : SQL expression of this object.
	   */
    public java.lang.String getSQLExpression() throws DbsException{
        try{
            return this.freeFormQualification.getSQLExpression();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Sets the SQL expression for this object.
	   * @returns : SQL - expression for this qualification
	   */
    public void setSqlExpression(java.lang.String sqlExpression) throws DbsException{
        try{
            freeFormQualification.setSqlExpression(sqlExpression);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }    
    }

    public SearchQualification getSearchQualification() {
        return this.freeFormQualification;
    }
}
