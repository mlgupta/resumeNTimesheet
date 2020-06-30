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
 * $Id: DbsSearchClause.java,v 1.1.1.1 2005/05/26 12:31:55 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of SearchClause class provided by CMSDK API.
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    26-02-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
import oracle.ifs.search.*;

public class DbsSearchClause extends DbsSearchQualification {
    /**Represents the 'NOT' operator*/
    public static final int NOT = SearchClause.NOT; 
    /**Represents the 'AND' operator.*/    
    public static final int AND = SearchClause.AND; 
    /**Represents the 'OR' operator.*/    
    public static final int OR  = SearchClause.OR;
    private SearchClause searchClause=null;// to accept object of type SearchClause

    /**
	   * Purpose : To create DbsSearchClause using SearchClause class
	   */
    public DbsSearchClause() {
       this.searchClause=new SearchClause();
    }

    /**
	   * Purpose : To create DbsSearchClause using SearchClause class
	   * @param  : searchClause - An SearchClause Object  
	   */    
    public DbsSearchClause(SearchClause searchClause) {
        this.searchClause = searchClause;
    }

    /**
	   * Purpose  : Used to get the object of class SearchClause
	   * @returns : SearchClause Object
	   */
    public SearchClause getSearchClause() {
        return this.searchClause;
    }

    /**
	   * Purpose  : Constructs a SearchClause. Based on the Qualifications and operator.
	   * @param   : l - LHS Qualification
	   * @param   : r - RHS Qualification
	   * @param   : dbsOper - composing operator, should be AND, OR or NOT.
	   */
    public DbsSearchClause(DbsSearchQualification l,DbsSearchQualification r,java.lang.String dbsOper) throws DbsException{
        try{
            if(l instanceof DbsSearchClause){
              this.searchClause = new SearchClause(((DbsSearchClause)l).getSearchClause() ,r.getSearchQualification(),dbsOper);  
            }else if(l instanceof DbsContextQualification){
              this.searchClause = new SearchClause(((DbsContextQualification)l).getContextQualification() ,r.getSearchQualification(),dbsOper);  
            }else if(l instanceof DbsJoinQualification){
              this.searchClause = new SearchClause(((DbsJoinQualification)l).getJoinQualification() ,r.getSearchQualification(),dbsOper);  
            }else{            
              this.searchClause = new SearchClause(l.getSearchQualification(),r.getSearchQualification(),dbsOper);
            }
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }           
    }

    /**
	   * Purpose  : Constructs a SearchClause. Based on the Qualifications and operator.
	   * @param   : l - LHS Qualification
	   * @param   : r - RHS Qualification
	   * @param   : dbsOper - composing operator, should be AND, OR or NOT.
	   */
    public DbsSearchClause(DbsSearchQualification l,DbsSearchQualification r,int dbsOper) throws DbsException{
        try{
            this.searchClause = new SearchClause(l.getSearchQualification(),r.getSearchQualification(),dbsOper);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }           
    }

    /**
	   * Purpose  : Set the left hand side SearchQualification. Null value allowed only for NOT operation.
	   * @param   : dbsSearchQualification - the composed qualification
	   */
    public void setLeftSearchQualification(DbsSearchQualification dbsSearchQualification) throws DbsException{
        try{
            this.searchClause.setLeftSearchQualification(dbsSearchQualification.getSearchQualification());
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose  : Returns the left side SearchQualification.
	   * @param   : left SearchQualification.    
	   */    
    public DbsSearchQualification getLeftSearchQualification() throws DbsException{
        try{
            return new DbsSearchQualification(this.searchClause.getLeftSearchQualification());
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }
    
    /**
	   * Purpose  : Set the right hand side SearchQualification. Null value allowed only for NOT operation.
	   * @param   : dbsSearchQualification - the composed qualification
	   */
    public void setRightSearchQualification(DbsSearchQualification dbsSearchQualification) throws DbsException{
        try{
            this.searchClause.setRightSearchQualification(dbsSearchQualification.getSearchQualification());
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose  : Return the right side SearchQualification.
	   * @param   : right SearchQualification.    
	   */    
    public DbsSearchQualification getRightSearchQualification(){
        return new DbsSearchQualification(this.searchClause.getRightSearchQualification());
    }

    /**
	   * Purpose  : Set the operator to put together SearchQualifications.
	   * @param   : dbsOper - composing operator; Must be one of, AND, OR, NOT.
	   */
    public void setOperatorType(java.lang.String dbsOper) throws DbsException{
        try{
            this.searchClause.setOperatorType(dbsOper);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }           
    }

    /**
	   * Purpose  : Set the composing operator.
	   * @param   : dbsOper - composing operator; Must be one of, SearchClause.AND SearchClause.OR SearchClause.NOT    
	   */
    public void setOperatorType(int dbsOper) throws DbsException{
        try{
            this.searchClause.setOperatorType(dbsOper);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose  : Return the composing operator.
	   */
    public int getOperatorType(){
        return this.searchClause.getOperatorType();
    }

    /**
	   * Purpose  : Returns the operator type string for a given operator.
	   * @param   : dbsOper - composing operator; Must be one of, SearchClause.AND SearchClause.OR SearchClause.NOT    
	   * @param   : int - integer representing the operator; must be one of SearchClause.AND , SearchClause.OR , SearchClause.NOT 
     *            String if a valid operator, null otherwise.
	   */
    public static java.lang.String operatorTypeName(int dbsOper){
        return SearchClause.operatorTypeName(dbsOper);
    }

    public SearchQualification getSearchQualification() {
        return this.searchClause;
    }
}
