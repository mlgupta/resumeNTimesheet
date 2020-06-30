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
 * $Id: DbsCleartextCredential.java,v 1.1.1.1 2005/05/26 12:31:42 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of ClearTextCredential class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 *	Last Modfied Date:    
 */

/*CMSDK API*/
import oracle.ifs.common.*;

public class DbsCleartextCredential  {
    private CleartextCredential credential= null; // to accept object of type CleartextCredential

    /**
	   * Purpose : To create DbsCleartextCredential using CleartextCredential class
	   * @param  : name - User Name
	   * @param  : name - Password
     * @throws : DbsException - if operation fails
	   */
    public DbsCleartextCredential(String name,String password)  throws DbsException{
        try{
            credential=new CleartextCredential(name,password);     
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Gets the name of the user
     * @returns : the name of the user
     * @throws  : DbsException - if operation fails
     */
    public String getName() throws DbsException {
        String name=null;
        try {
            name=credential.getName();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
        return name;
    }

    /**
     * Purpose  : Gets the password.
     * @returns : the password
     * @throws  : DbsException - if operation fails
     */
    public String getPassword() throws DbsException {
        String password=null;
        try {
            password=credential.getPassword();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
        return password;
    }

    /**
	   * Purpose : Used to get the object of class CleartextCredential
	   * @return : CleartextCredential Object
     * @throws  : DbsException - if operation fails
	   */
    public CleartextCredential getCredential() throws DbsException {
        return credential;
    }
}
