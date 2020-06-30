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
 * $Id: DbsFormat.java,v 1.2 2005/06/20 11:40:13 ias Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of Format class provided by CMSDK API.
 * 
 *  @author              Jeetendra Prasad
 *  @version             1.0
 * 	Date of creation:   05-01-2004
 * 	Last Modfied by :     
 *	Last Modfied Date:    
 */

/*CMSDK API*/
import oracle.ifs.beans.* ;
import oracle.ifs.common.* ;
//import oracle.ifs.common.IfsException;
/*Java Api*/
import java.io.*;

public class DbsFormat extends DbsSystemObject {
    private Format format = null;  // to accept object of type Format

    /** This class name for this class. */
    public static java.lang.String CLASS_NAME = Format.CLASS_NAME ;
    public static java.lang.String NAME_ATTRIBUTE = Format.NAME_ATTRIBUTE; 
    public static java.lang.String MIMETYPE_ATTRIBUTE = Format.MIMETYPE_ATTRIBUTE; 

    /**
	   * Purpose : To create DbsFormat using Format class
	   * @param  : format - An Format Object  
	   */
    public DbsFormat(Format format) {
        super(format);
        this.format = format;
    }

    /**
	   * Purpose : to get MimeType 
	   * @return : MimyType
	   */
    public String getMimeType() throws DbsException {
        String mimeType = null;
        try{
            mimeType = format.getMimeType();
        }catch(IfsException iex){
            throw new DbsException(iex);
        }
        return mimeType;
    }

    public void setMimeType(java.lang.String mimeType) throws DbsException{
        try{
            format.setMimeType(mimeType); 
        }catch(IfsException iex){
            throw new DbsException(iex);
        }
    }
    public Format getFormat(){
        return format;
    }
}
