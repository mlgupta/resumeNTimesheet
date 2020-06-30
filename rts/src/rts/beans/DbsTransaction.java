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
 * $Id: DbsTransaction.java,v 1.1.1.1 2005/05/26 12:31:59 suved Exp $
 *****************************************************************************
 */
package rts.beans; 
/**
 *	Purpose: The encapsulate the functionality of Transaction class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
 
/*CMSDK API*/ 
import oracle.ifs.common.Transaction;

public class DbsTransaction {
    private Transaction transaction=null;// to accept object of type Transaction

    /**
	   * Purpose : To create DbsTransaction using Transaction class
	   * @param  : transaction - An Transaction Object  
	   */
    public DbsTransaction(Transaction transaction) {
        this.transaction=transaction;  
    }

    /**
	   * Purpose  : Gets whether this Transaction is writeable. 
	   *          : Creating, altering, or removing LibraryObjects in a non-writeable transaction throws an exception.
	   * @returns : whether it is writeable
	   */
    public boolean isWriteable() {
        return transaction.isWriteable();
    }

    /**
	   * Purpose  : Gets whether this Transaction is completeable. 
	   *          : Invoking LibrarySession.completeTransaction on an uncompleteable transaction throws an exception.
	   * @returns : whether it is completeable
	   */    
    public boolean isCompleteable() {
        return transaction.isCompleteable();
    }

    /**
	   * Purpose  : Gets whether this Transaction is abortable. 
	   *          : Invoking LibrarySession.abortTransaction on an unabortable transaction throws an exception
	   * @returns : whether it is abortable
	   */
    public boolean isAbortable() {
        return transaction.isAbortable();
    }

   /**
	  * Purpose : Used to get the object of class Transaction
	  * @return : Transaction Object
	  */
    public Transaction getTransaction() {
        return transaction;
    }
}
