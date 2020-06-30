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
 * $Id: DbsJoinQualification.java,v 1.1.1.1 2005/05/26 12:31:49 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of JoinQualification class provided by CMSDK API.
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

public class DbsJoinQualification extends DbsSearchQualification{
    private JoinQualification joinQualification=null;
    public DbsJoinQualification() {
        this.joinQualification = new JoinQualification();
    }

    /**
	   * Purpose : To create DbsJoinQualification using JoinQualification class
	   * @param  : joinQualification - An JoinQualification Object  
	   */    
    public DbsJoinQualification(JoinQualification joinQualification) {
        this.joinQualification = joinQualification;
    }

    /**
	   * Purpose  : Used to get the object of class JoinQualification
	   * @returns : joinQualification Object
	   */
    public JoinQualification getJoinQualification() {
        return this.joinQualification;
    }

    /**
	   * Purpose  : Gets the classname of left side of this join qualification.
	   * @returns : classname of left hand side
	   */
    public java.lang.String getLeftAttributeClassname() throws DbsException{
        try{
            return this.joinQualification.getLeftAttributeClassname();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Gets the classname of right side of this join qualification.
	   * @returns : classname of right hand side
	   */
    public java.lang.String getRightAttributeClassname() throws DbsException{
        try{
            return this.joinQualification.getRightAttributeClassname();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }    
    }

    /**
	   * Purpose  : Set left hand side Join Attribute. 
     *            A null attrName implies that the Object's id must be used in the Join. 
     *            A null Class is taken to mean, use the first Result Class of the SearchSpecification. 
     *            Otherwise the className should be a valid iFS class, 
     *            and attrName should be an attribute of the class.
	   * @param   : dbsClassName - The class of the attribute
	   * @param   : dbsAttrName - The joining Attribute
	   */
    public void setLeftAttribute(java.lang.String dbsClassName,java.lang.String dbsAttrName){
        this.joinQualification.setLeftAttribute(dbsClassName, dbsAttrName);
    }

    /**
	   * Purpose  : Set right hand side Join Attribute. 
     *            A null attrName implies that the Object's id must be used in the Join. 
     *            A null Class is taken to mean, use the first Result Class of the SearchSpecification. 
     *            Otherwise the className should be a valid iFS class, 
     *            and attrName should be an attribute of the class.
	   * @param   : dbsClassName - The class of the attribute
	   * @param   : dbsAttrName - The joining Attribute
	   */
    public void setRightAttribute(java.lang.String dbsClassName,java.lang.String dbsAttrName){
        this.joinQualification.setRightAttribute(dbsClassName,dbsAttrName);
    }

    /**
	   * Purpose  : Gets the unqualified attribute name of the left side of this join qualification.
	   * @returns : unqualified attribute name of left hand side
	   */
    public java.lang.String getLeftAttributeName() throws DbsException{
        try{
            return this.joinQualification.getLeftAttributeName();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose  : Gets the unqualified attribute name of the right side of this join qualification.
	   * @returns : unqualified attribute name of right hand side
	   */
    public java.lang.String getRightAttributeName() throws DbsException{
        try{
            return this.joinQualification.getRightAttributeName();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    public SearchQualification getSearchQualification() {
        return this.joinQualification;
    }
}
