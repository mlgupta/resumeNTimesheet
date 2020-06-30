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
 * $Id: DbsLockObjectDefinition.java,v 1.1.1.1 2005/05/26 12:31:50 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of LockObjectDefinition class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsLockObjectDefinition {
    
    public static int  LOCKSTATE_HARDLOCK = LockObject.LOCKSTATE_HARDLOCK; 
    public static int  LOCKSTATE_UNLOCK= LockObject.LOCKSTATE_UNLOCK;
    public static int  LOCKSTATE_SOFTLOCK= LockObject.LOCKSTATE_SOFTLOCK;
    
    private LockObjectDefinition lockObjectDefinition = null;  // to accept object of type LockObjectDefinition

    public DbsLockObjectDefinition(DbsLibrarySession dbsSession)throws DbsException{
         try{
               this.lockObjectDefinition=new LockObjectDefinition(dbsSession.getLibrarySession());
         }catch(IfsException ifsError) {
           throw new DbsException(ifsError);
        }
    }
    
    /**
	   * Purpose : To create DbsLockObjectDefinition using LockObjectDefinition class
	   * @param  : lockObjectDefinition - An LockObjectDefinition Object  
	   */    
    public DbsLockObjectDefinition(LockObjectDefinition lockObjectDefinition) {
        this.lockObjectDefinition = lockObjectDefinition;
    }

    public void setLockState(int lockstate)throws DbsException{
    
          try{  
    
           this.lockObjectDefinition.setLockState(lockstate);
        }catch(IfsException ifsError) {
           throw new DbsException(ifsError);
        }
    }
    
    public int getLockState()throws DbsException{
      int lockstate;
      try{
           lockstate=this.lockObjectDefinition.getLockState();
        }catch(IfsException ifsError) {
           throw new DbsException(ifsError);
        }
        return lockstate;
    }

   
   
   /**
	  * Purpose  : Used to get the object of class LockObjectDefinition
	  * @returns : LockObjectDefinition Object
	  */
    public LockObjectDefinition getLockObjectDefinition() {
        return this.lockObjectDefinition ;
    }
}
