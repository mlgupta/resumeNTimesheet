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
 * $Id: DbsPrimaryUserProfile.java,v 1.1.1.1 2005/05/26 12:31:51 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of PrimaryUserProfile class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*CMSDK API*/
import oracle.ifs.beans.PrimaryUserProfile;
import oracle.ifs.common.*;
import oracle.ifs.beans.*;
/*JDK API*/
import java.util.*;

public class DbsPrimaryUserProfile extends DbsUserProfile{
    private PrimaryUserProfile primaryUserProfile=null; // to accept object of type PrimaryUserProfile

    /**
	 * Purpose : To create DbsPrimaryUserProfile using PrimaryUserProfile class
	 * @param  : primaryUserProfile - An PrimaryUserProfile Object  
	 */
    public DbsPrimaryUserProfile(PrimaryUserProfile primaryUserProfile) {
        super(primaryUserProfile);
        this.primaryUserProfile=primaryUserProfile;
    }
    
    /**
	 * Purpose  : Gets the DirectoryUser's home folder. This is one of the user profile settings.
	 * @returns : DirectoryUser's home folder
     * @throws  : DbsException - if operation fails
	 */
    public DbsFolder getHomeFolder() throws DbsException {
        DbsFolder dbsFolder=null;
        try {
          if(primaryUserProfile!=null){
            if(primaryUserProfile.getHomeFolder()!=null){
            dbsFolder=new DbsFolder(primaryUserProfile.getHomeFolder());
            }else
            {
              return null;
            }
          }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsFolder;
    }

    /**
	 * Purpose  : Sets the DirectoryUser's home folder.
	 * @param   : dbsFolder - the Folder that will be the home folder
     * @throws  : DbsException - if operation fails
	 */
    public void setHomeFolder(DbsFolder dbsFolder) throws DbsException {
        try {
            primaryUserProfile.setHomeFolder(dbsFolder.getFolder());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	 * Purpose  : Gets the DirectoryUser's ContentQuota object.
	 * @returns : the DirectoryUser's ContentQuota or null if the DirectoryUser does not have any quota
     * @throws  : DbsException - if operation fails
	 */
    public DbsContentQuota getContentQuota() throws DbsException {
        DbsContentQuota dbsContentQuota=null;
        try {
            ContentQuota quota=primaryUserProfile.getContentQuota();
            if(quota!=null){
            dbsContentQuota=new DbsContentQuota(quota);
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsContentQuota;
    }

    /**
	 * Purpose  : Sets the DirectoryUser's ContentQuota attribute.
	 * @param   : quota - the DirectoryUser's ContentQuota
     * @throws  : DbsException - if operation fails
	 */
    public void setContentQuota(DbsContentQuota dbsContentQuota)  throws DbsException {
        try {
            primaryUserProfile.setContentQuota(dbsContentQuota.getContentQuota());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	 * Purpose  : Sets the default language setting to use when uploading a document. 
     *          : If left or set to NULL, uploads will use the default system settings.
	 * @param   : language - Identifier code of language to use.
     * @throws  : DbsException - if operation fails
	 */
    public void setLanguage(java.lang.String language) throws DbsException {
        try {
            primaryUserProfile.setLanguage(language);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	 * Purpose  : Retrieve the default language setting used when uploading a document.
	 * @returns : Identifier code of the language being used.
     * @throws  : DbsException - if operation fails
	 */
    public java.lang.String getLanguage() throws DbsException {
        String language=null;
        try {
            language=primaryUserProfile.getLanguage();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return language ;
    }

    /**
	 * Purpose  : Sets the default character set setting to use when uploading a document. 
     *          : If left or set to NULL, uploads will use the default system settings. 
     *          : Be sure to use the Java naming convention and not the Iana or Oracle name 
     *          : for character set values (i.e. ISO8859_1 is valid, ISO-8859-1 is invalid).
	 * @param   : charSet - Identifier code of Java character set to use.
     * @throws  : DbsException - if operation fails
	 */
    public void setCharacterSet(java.lang.String charSet)  throws DbsException {
        try {
            primaryUserProfile.setCharacterSet(charSet);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	 * Purpose  : Retrieves the default character set setting used when uploading a document.
	 * @returns : Identifier code of the Java character set used.
     * @throws  : DbsException - if operation fails
	 */
    public java.lang.String getCharacterSet() throws DbsException {
        String charSet=null;
        try {
            charSet=primaryUserProfile.getCharacterSet();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return charSet;
    }

  /**
	 * Purpose  : Sets the user preferred time zone.
	 * @param   : timeZone - TimeZone which user would like to use.
   * @throws  : DbsException - if operation fails
	 */
    public void setTimeZone(TimeZone timeZone) throws DbsException {
        try {
            primaryUserProfile.setTimeZone(timeZone);      
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

  /**
	 * Purpose  : Retrieves the user preferred time zone.
	 * @returns : TimeZone which user prefers to use.
   * @throws  : DbsException - if operation fails
	 */
    public java.util.TimeZone getTimeZone() throws DbsException {
        java.util.TimeZone timeZone=null;
        try {
            timeZone=primaryUserProfile.getTimeZone();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return timeZone;
    }

    /**
	 * Purpose  : Sets the user preferred locale.
	 * @param   : locale - User preferred locale setting.
     * @throws  : DbsException - if operation fails
	 */
    public void setLocale(java.util.Locale locale) throws DbsException {
        try {
            primaryUserProfile.setLocale(locale);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	 * Purpose  : Retrieves the user preferred locale.
	 * @returns : User preferred locale setting.
     * @throws  : DbsException - if operation fails
	 */
    public java.util.Locale getLocale() throws DbsException {
        Locale locale=null;
        try {
            locale=primaryUserProfile.getLocale();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return locale;
    }

    /**
	 * Purpose  : Determines whether read indication is enabled.
	 * @returns : true if read indication is enabled
     * @throws  : DbsException - if operation fails
	 */
    public boolean isReadIndicationEnabled() throws DbsException { 
        boolean isEnabled;
        try {
            isEnabled=primaryUserProfile.isReadIndicationEnabled();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isEnabled;
    }

    public void setDefaultAcls(DbsPropertyBundle dbsProps) throws DbsException{
       try { 
            this.primaryUserProfile.setDefaultAcls(dbsProps.getDbsPropertyBundle());     
          }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

     public DbsPropertyBundle getDefaultAcls() throws DbsException{
      DbsPropertyBundle dbsProps=null;
       try {
            if(primaryUserProfile.getDefaultAcls()!=null){
            dbsProps=new DbsPropertyBundle(primaryUserProfile.getDefaultAcls());     
            }else
            {
              return null;
            }
          }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsProps;
    }
    
    public PrimaryUserProfile getPrimaryUserProfile(){
      return this.primaryUserProfile;
    }
}
