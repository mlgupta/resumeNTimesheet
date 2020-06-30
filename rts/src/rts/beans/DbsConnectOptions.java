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
 * $Id: DbsConnectOptions.java,v 1.1.1.1 2005/05/26 12:31:42 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of ConnectOptions class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 *	Last Modfied Date:    
 */

/*CMSDK API*/
import oracle.ifs.common.*;
/*JDK API*/
import java.util.*;

public class DbsConnectOptions {
    private ConnectOptions connectOptions=null; // to accept object of type ConnectOptions

    /**
	   * Purpose : To create DbsConnectOptions using ConnectOptions class
	   * @param  : connectOptions - An ConnectOptions Object  
	   */
    public DbsConnectOptions(ConnectOptions connectOptions) {
        this.connectOptions=connectOptions;
    }

    /**
	   * Purpose : Sets the name of the application creating the session. 
     *           The value is not used internally; it is purely informational.
	   * @param  : name - the application name
     * @throws : DbsException - if operation fails
	   */
    public void setApplicationName(String name) throws DbsException {
        try{
            connectOptions.setApplicationName(name);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Gets the name of the application creating the session. 
     *            The value is not used internally; it is purely informational.
	   * @returns : the application name
     * @throws  : DbsException - if operation fails
	   */
    public java.lang.String getApplicationName() throws DbsException {
        String name=null;
        try{
            name=connectOptions.getApplicationName();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return name;
    }

    /**
	   * Purpose  : Sets the Locale of the new session.
	   * @param   : locale - the Locale
     * @throws  : DbsException - if operation fails
	   */
    public void setLocale(java.util.Locale locale) throws DbsException {
        try{
            connectOptions.setLocale(locale);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Gets the Locale of the new session.
	   * @returns : the Locale
     * @throws  : DbsException - if operation fails
	   */          
    public java.util.Locale getLocale() throws DbsException {   
        Locale locale=null;
        try {
            locale=connectOptions.getLocale();   
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return locale;
    }

    /**
	   * Purpose : Used to get the object of class ConnectOptions
	   * @return ConnectOptions Object
	   */
    public ConnectOptions getConnectOptions() {
        return connectOptions;
    }
}
