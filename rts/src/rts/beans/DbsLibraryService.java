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
 * $Id: DbsLibraryService.java,v 1.1.1.1 2005/05/26 12:31:49 suved Exp $
 *****************************************************************************
 */
package rts.beans; 
/**
 *	Purpose: The encapsulate the functionality of LibraryService class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
 
/*CMSDK API*/ 
import oracle.ifs.beans.LibraryService;
import oracle.ifs.beans.LibrarySession;
import oracle.ifs.beans.resources.LibraryConstants;
import oracle.ifs.common.Credential;
import oracle.ifs.common.CleartextCredential;
import oracle.ifs.common.ConnectOptions;
import oracle.ifs.common.IfsException;


public class DbsLibraryService {
    private static LibraryService libraryService; // to accept object of type LibraryService
    private LibrarySession librarySession; // to accept object of type LibrarySession

    /**
	 * Purpose : To create DbsLibraryService using LibraryService class
	 * @param  : libraryService - An LibraryService Object  
	 */
    public DbsLibraryService(LibraryService libraryService) {
        this.libraryService=libraryService;
    }

    /**
	 * Purpose  : Connects to this iFS service, creating a new iFS session.
	 * @param   : credential - the user's credentials
	 * @param   : options - connect options; may be null
	 * @returns : true if more than one PublicObject refers to this ACL
     * @throws  : DbsException - if operation fails
	 */
    public DbsLibrarySession connect(DbsCleartextCredential dbsCredential, DbsConnectOptions dbsOptions)
                                      throws DbsException {
        DbsLibrarySession dbsSession=null;
        try{  
            if(dbsOptions == null) {
                librarySession = libraryService.connect(dbsCredential.getCredential(), null);    
            }else {
                librarySession = libraryService.connect(dbsCredential.getCredential(), dbsOptions.getConnectOptions());
            }
            if(librarySession != null){
                dbsSession=new DbsLibrarySession(librarySession) ;
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsSession;
    }

    /**
	 * Purpose  : Disposes this iFS service.
	 * @param   : schemaPassword - the password for the database schema
     * @throws  : DbsException - if operation fails
	 */
    public void dispose(String schemaPassword) throws DbsException {
        try{
            libraryService.dispose(schemaPassword);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	 * Purpose  : Finds an existing iFS service with the specified name. 
	 *          : If there is no service with that name, an exception is thrown.
	 * @param   : serviceName - the name of the service
     * @throws  : DbsException - if operation fails
	 */
    public static DbsLibraryService findService(String serviceName) throws DbsException {
        DbsLibraryService dbsLibraryService=null;
        try{
            dbsLibraryService=new DbsLibraryService(libraryService.findService(serviceName));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
       return dbsLibraryService;
    }

    /**
	 * Purpose  : Determines whether there exists an iFS service with the specified name.
	 * @param   : serviceName - the name of the service
     * @returns : whether there is a service with that name
     * @throws  : DbsException - if operation fails
	 */
    public static boolean isServiceStarted(java.lang.String serviceName) throws DbsException {
        boolean isStarted;
        try {
            isStarted=LibraryService.isServiceStarted(serviceName);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isStarted;
    }

    /**
	 * Purpose  : Starts a new iFS service. 
	 *          : To start a service, you must specify three things: 
	 *          : The name to assign to the service. Service names are unique across each iFS process. 
	 *          : 
	 *          : The password of the iFS schema. This establishes that you have permission to start an iFS service. 
	 *          : 
	 *          : The service configuration properties. Service configurations are stored in iFS as uniquely-named instances of the SERVICECONFIGURATION classobject. 
     *          : You control which service configuration is used by specifying a serviceConfigurationName and a domainName. 
	 *          : The serviceConfigurationName specifies the name of the desired SERVICECONFIGURATION object. 
	 * @param   : serviceName - the name of the service
     * @returns : whether there is a service with that name
     * @throws  : DbsException - if operation fails
	 */
    public static DbsLibraryService startService(String serviceName, String schemaPassword, 
                                                String serviceConfigurationName, String domainName) throws DbsException {
        DbsLibraryService dbsLibraryService=null;
        try{ libraryService = LibraryService.startService(serviceName,
            schemaPassword, serviceConfigurationName, domainName);
            dbsLibraryService=new DbsLibraryService(libraryService);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
      }
      return dbsLibraryService;
    }
   /**
	 * Purpose : Used to get the object of class LibraryService
	 * @return LibraryService Object
	 */
     
    public LibraryService getLibraryService() {
        return libraryService;
    }
   /* public LibrarySession getLibrarySession() {
        return m_LibrarySession;
    }*/
}
