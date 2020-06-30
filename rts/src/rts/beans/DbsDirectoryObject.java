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
 * $Id: DbsDirectoryObject.java,v 1.1.1.1 2005/05/26 12:31:44 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of DirectoryObject class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 *	Last Modfied Date:    
 */

/*CMSDK API*/
import oracle.ifs.beans .*;
import oracle.ifs.common.*;
public class DbsDirectoryObject extends DbsPublicObject{
    private DirectoryObject directoryObject=null; // to accept object of type DirectoryObject

    /**Class name for this class.*/
    public static final java.lang.String CLASS_NAME=DirectoryObject.CLASS_NAME;
    
    protected DbsDirectoryObject(){}
        
    /**
	   * Purpose : To create DbsDirectoryObject using DirectoryObject class
	   * @param  : directoryObject - An DirectoryObject Object  
	   */
    protected DbsDirectoryObject(DirectoryObject directoryObject) {
        super(directoryObject);
        this.directoryObject=directoryObject;
    }

    /**
	   * Purpose : Used to get the object of class DirectoryObject
	   * @return DirectoryObject Object
	   */
    public DirectoryObject getDirectoryObject() {
        return this.directoryObject;
    }
}
