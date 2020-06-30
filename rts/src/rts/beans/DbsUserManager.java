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
 * $Id: DbsUserManager.java,v 1.1.1.1 2005/05/26 12:31:59 suved Exp $
 *****************************************************************************
 */
package rts.beans; 
/**
 *	Purpose: The encapsulate the functionality of UserManager class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*JDK API*/ 
import java.util.*;

/*CMSDK API*/
import oracle.ifs.adk.user.*;
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsUserManager {

    /**UserName is required to be defined. Use a String object.*/
    public static final java.lang.String USERNAME=UserManager.USERNAME;

    /**Password is required to be defined. Use a String object.*/    
    public static final java.lang.String PASSWORD=UserManager.PASSWORD;

    /**Defaults to TRUE. Use a Boolean object.*/    
    public static final java.lang.String SHOULD_CREATE_CREDENTIAL_MANAGER_USER =UserManager.SHOULD_CREATE_CREDENTIAL_MANAGER_USER;

    /**Defaults to FALSE. If using an existing credential manager user, set to TRUE to override the old password. Use a Boolean object.*/    
    public static final java.lang.String REPLACE_CREDENTIAL_MANAGER_PASSWORD=UserManager.REPLACE_CREDENTIAL_MANAGER_PASSWORD;

    /**Defaults to 'Ifs' for which credential manager to use for access to the authentication engine. Use a String object.*/    
    public static final java.lang.String CREDENTIAL_MANAGER =UserManager.CREDENTIAL_MANAGER;

    /**Defaults to TRUE. Use a Boolean object.*/
    public static final java.lang.String CAN_CHANGE_PASSWORD =UserManager.CAN_CHANGE_PASSWORD;

    /**Defaults to TRUE. Use a Boolean object.*/
    public static final java.lang.String HAS_EMAIL =UserManager.HAS_EMAIL;

    /**Defaults to FALSE. Set to TRUE if user should have admin privileges. Use a Boolean object.*/
    public static final java.lang.String ADMIN_ENABLED=UserManager.ADMIN_ENABLED;

    /**Defaults to FALSE. Set to TRUE if user should have system admin privileges. Use a Boolean object.*/
    public static final java.lang.String SYSTEM_ADMIN_ENABLED =UserManager.SYSTEM_ADMIN_ENABLED;

    /**Defaults to TRUE. Use a Boolean object.*/
    public static final java.lang.String HAS_PRIMARY_USER_PROFILE=UserManager.HAS_PRIMARY_USER_PROFILE;

    /**Defaults to TRUE. Use a Boolean object.*/
    public static final java.lang.String HAS_HOME_FOLDER =UserManager.HAS_HOME_FOLDER;

    /**Defaults to '/home'. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_ROOT=UserManager.HOME_FOLDER_ROOT;

    /**Defaults to TRUE. Admin privileges are required to free the home folder. Use a Boolean object.*/
    public static final java.lang.String HOME_FOLDER_HAS_POLICY_BUNDLE =UserManager.HOME_FOLDER_HAS_POLICY_BUNDLE;

    /**Defaults to TRUE. Use a Boolean object.*/
    public static final java.lang.String HAS_CONTENT_QUOTA=UserManager.HAS_CONTENT_QUOTA;

    /**Defaults to FALSE. Set to TRUE to enable the implemented content quota. Use a Boolean object.*/
    public static final java.lang.String CONTENT_QUOTA_ENABLED=UserManager.CONTENT_QUOTA_ENABLED;

    /**Defaults to 25 MB. Use a Long object.*/
    public static final java.lang.String CONTENT_QUOTA_ALLOCATED_STORAGE=UserManager.CONTENT_QUOTA_ALLOCATED_STORAGE;

    /**Defaults to FALSE. Set to TRUE if user should have read indication enabled. Use a Boolean object.*/
    public static final java.lang.String READ_INDICATION_ENABLED =UserManager.READ_INDICATION_ENABLED;

    /**No default. Use a String object.*/
    public static final java.lang.String EMAIL_ADDRESS=UserManager.EMAIL_ADDRESS;

    /**Used if SHOULD_CREATE_CREDENTIAL_MANAGER_USER is false. No default. Use a String object.*/
    public static final java.lang.String DISTINGUISHED_NAME =UserManager.DISTINGUISHED_NAME;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String PRIMARY_USER_PROFILE_NAME =UserManager.PRIMARY_USER_PROFILE_NAME;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String EMAIL_USER_PROFILE_NAME =UserManager.EMAIL_USER_PROFILE_NAME;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String CONTENT_QUOTA_NAME =UserManager.CONTENT_QUOTA_NAME;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_NAME= UserManager.HOME_FOLDER_NAME;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_DESCRIPTION =UserManager.HOME_FOLDER_DESCRIPTION;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String DEFAULT_ACLS_BUNDLE_NAME =UserManager.DEFAULT_ACLS_BUNDLE_NAME;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_POLICY_BUNDLE_NAME = UserManager.HOME_FOLDER_POLICY_BUNDLE_NAME;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String PRIMARY_USER_PROFILE_LANGUAGE = UserManager.PRIMARY_USER_PROFILE_LANGUAGE;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String PRIMARY_USER_PROFILE_CHARACTERSET=UserManager.PRIMARY_USER_PROFILE_CHARACTERSET;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String PRIMARY_USER_PROFILE_LOCALE=UserManager.PRIMARY_USER_PROFILE_LOCALE;

    /**If none specified, name is constructed from prefix + username + suffix. Use a String object.*/
    public static final java.lang.String PRIMARY_USER_PROFILE_TIMEZONE=UserManager.PRIMARY_USER_PROFILE_TIMEZONE;
    /**No default. Use a String object.*/
    public static final java.lang.String PRIMARY_USER_PROFILE_NAME_PREFIX=UserManager.PRIMARY_USER_PROFILE_NAME_PREFIX;
    /**    No default. Use a String object.*/
    public static final java.lang.String EMAIL_USER_PROFILE_NAME_PREFIX=UserManager.EMAIL_USER_PROFILE_NAME_PREFIX;
    /**No default. Use a String object.*/
    public static final java.lang.String CONTENT_QUOTA_NAME_PREFIX=UserManager.CONTENT_QUOTA_NAME_PREFIX;
    /**No default. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_NAME_PREFIX=UserManager.HOME_FOLDER_NAME_PREFIX;
    /**No default. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_DESCRIPTION_PREFIX =UserManager.HOME_FOLDER_DESCRIPTION_PREFIX;
    /**No default. Use a String object.*/
    public static final java.lang.String DEFAULT_ACLS_BUNDLE_NAME_PREFIX=UserManager.DEFAULT_ACLS_BUNDLE_NAME_PREFIX;
    /**No default. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_POLICY_BUNDLE_NAME_PREFIX=UserManager.HOME_FOLDER_POLICY_BUNDLE_NAME_PREFIX;
    /**Defaults to ' Primary Profile'. Use a String object.*/
    public static final java.lang.String PRIMARY_USER_PROFILE_NAME_SUFFIX =UserManager.PRIMARY_USER_PROFILE_NAME_SUFFIX;
    /**Defaults to ' Email Profile'. Use a String object.*/
    public static final java.lang.String EMAIL_USER_PROFILE_NAME_SUFFIX =UserManager.EMAIL_USER_PROFILE_NAME_SUFFIX;
    /**Defaults to ' Content Quota'. Use a String object.*/
    public static final java.lang.String CONTENT_QUOTA_NAME_SUFFIX =UserManager.CONTENT_QUOTA_NAME_SUFFIX;
    /**No default. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_NAME_SUFFIX =UserManager.HOME_FOLDER_NAME_SUFFIX;
    /**Defaults to ''s home folder'. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_DESCRIPTION_SUFFIX =UserManager.HOME_FOLDER_DESCRIPTION_SUFFIX;
    /**Defaults to ' DefaultACLs'. Use a String object.*/
    public static final java.lang.String DEFAULT_ACLS_BUNDLE_NAME_SUFFIX=UserManager.DEFAULT_ACLS_BUNDLE_NAME_SUFFIX;
    /**Defaults to 'Policy Bundle for Homefolder and Inbox'. Use a String object.*/
    public static final java.lang.String HOME_FOLDER_POLICY_BUNDLE_NAME_SUFFIX =UserManager.HOME_FOLDER_POLICY_BUNDLE_NAME_SUFFIX;
    /**Defaults to 'mail'. Use a String object.*/
    public static final java.lang.String EMAIL_SUBFOLDER_NAME=UserManager.EMAIL_SUBFOLDER_NAME;
    /**Defaults to 'inbox'. Use a String object.*/
    public static final java.lang.String INBOX_NAME=UserManager.INBOX_NAME;
    /**Defaults to home folder. Use a String object.*/
    public static final java.lang.String EMAIL_FOLDER_ROOT =UserManager.EMAIL_FOLDER_ROOT;
    /**Defaults to null. Use a String object.*/
    public static final java.lang.String DIRECTORY_USER_DESCRIPTION=UserManager.DIRECTORY_USER_DESCRIPTION;
    /**    Defaults to Published. Use an AccessControlList object.*/
    public static final java.lang.String DIRECTORY_USER_ACL =UserManager.DIRECTORY_USER_ACL;
    /**Defaults to Published. Use an AccessControlList object.*/
    public static final java.lang.String HOME_FOLDER_POLICY_BUNDLE_ACL=UserManager.HOME_FOLDER_POLICY_BUNDLE_ACL;
    /**Defaults to Private. Use an AccessControlList object.*/
    public static final java.lang.String HOME_FOLDER_ACL =UserManager.HOME_FOLDER_ACL;
    /**Defaults to Published. Use an AccessControlList object.*/
    public static final java.lang.String DEFAULT_ACLS_BUNDLE_ACL=UserManager.DEFAULT_ACLS_BUNDLE_ACL;
    /**Defaults to Private. Use an AccessControlList object.*/
    public static final java.lang.String CONTENT_QUOTA_ACL =UserManager.CONTENT_QUOTA_ACL;
    /**Defaults to Private. Use an AccessControlList object.*/
    public static final java.lang.String PRIMARY_USER_PROFILE_ACL=UserManager.PRIMARY_USER_PROFILE_ACL;
    /**Defaults to Private. Use an AccessControlList object.*/
    public static final java.lang.String EMAIL_USER_PROFILE_ACL =UserManager.EMAIL_USER_PROFILE_ACL;
    /**Defaults to Private. Use an AccessControlList object.*/
    public static final java.lang.String EMAIL_SUBFOLDER_ACL=UserManager.EMAIL_SUBFOLDER_ACL;
    /**Defaults to Private. Use an AccessControlList object.*/
    public static final java.lang.String INBOX_ACL =UserManager.INBOX_ACL;
    /**Defaults to 'AclBundleAllPublished' for non-Admin and 'AclBundleForAdmin' for Admin users. Use a String object.*/
    public static final java.lang.String DEFAULT_ACLS=UserManager.DEFAULT_ACLS;
    /**Delete user option - defaults to FALSE. Use a Boolean object.*/
    public static final java.lang.String FREE_CREDENTIAL_MANAGER_USER=UserManager.FREE_CREDENTIAL_MANAGER_USER;
    /**Delete user option - defaults to FALSE. Use a Boolean object.*/
    public static final java.lang.String FREE_HOME_FOLDER =UserManager.FREE_HOME_FOLDER;
    /**Delete user option - defaults to FALSE. Use a Boolean object.*/
    public static final java.lang.String CHANGE_OWNER=UserManager.CHANGE_OWNER;
    /**Delete user option - no default. Use a String object.*/
    public static final java.lang.String NEW_OWNER_USER_NAME=UserManager.NEW_OWNER_USER_NAME;
    /**No default. Use a PropertyBundle object.*/
    public static final java.lang.String ACL_BUNDLE_ALL_PUBLISHED=UserManager.ACL_BUNDLE_ALL_PUBLISHED;
    /**No default. Use a PropertyBundle object.*/
    public static final java.lang.String ACL_BUNDLE_FOR_ADMIN=UserManager.ACL_BUNDLE_FOR_ADMIN;
    private UserManager userManager; // to accept object of type UserManager

    /**
	   * Purpose : To create DbsUserManager using UserManager class
	   * @param  : userManager - An UserManager Object  
	   */
    public DbsUserManager(DbsLibrarySession dbsSession) throws DbsException {
        try{            
            userManager= new UserManager(dbsSession.getLibrarySession());            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Creates a new iFS user.
	   * @param   : username - user name; can also be specified in the Hashtable as option UserName
	   * @param   : password - password; can also be specified in the Hashtable as option Password
	   * @param   : options - Hashtable of options which override the options contained in the named PropertyBundle IFS.ADK.CreateUserDefinitions
	   * @returns : The newly created user.
     * @throws  : DbsException - if operation fails
	   */
    public DbsDirectoryUser createUser(String username, String password, Hashtable options) throws DbsException {
        DbsDirectoryUser dbsDirectoryUser=null;
        try{ 
            Hashtable userOptions=new Hashtable();
            Enumeration keys=options.keys();
            for( ;keys.hasMoreElements(); ){
              String userKey=(String)keys.nextElement();
              DbsAttributeValue dbsAttrValue=(DbsAttributeValue)options.get(userKey);                         
              userOptions.put(userKey,dbsAttrValue.getAttributeValue());                        
            }
            dbsDirectoryUser=new DbsDirectoryUser(userManager.createUser(username,password,userOptions));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDirectoryUser;
    }

    /**
	   * Purpose  : Deletes an iFS user.
	   * @param   : username - user name; can also be specified in the Hashtable as option UserName
	   * @param   : options - Hashtable of options which override the options contained in the named PropertyBundle IFS.ADK.CreateUserDefinitions.
     * @throws  : DbsException - if operation fails
	   */
    public void deleteUser(String username, Hashtable options) throws DbsException {
       DbsDirectoryUser dbsDirectoryUser=null;
        try{ 
            Hashtable userOptions=new Hashtable();
            Enumeration keys=options.keys();
            for( ;keys.hasMoreElements(); ){
              String userKey=(String)keys.nextElement();
              DbsAttributeValue dbsAttrValue=(DbsAttributeValue)options.get(userKey);             
              userOptions.put(userKey,dbsAttrValue.getAttributeValue());                        
            }
            userManager.deleteUser(username,userOptions);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Normalizes the options Hashtable so that the keys are all uppercase.
	   * @param   : oldTable - original mixed-case keys Hashtable
	   * @returns : new Hashtable with keys in uppercase.
     * @throws  : DbsException - if operation fails
	   */
    public Hashtable normalizeOptionsHashtable(Hashtable oldTable) throws DbsException {
        Hashtable normalizedOptions=null;
        try{
            normalizedOptions=userManager.normalizeOptionsHashtable(oldTable);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return normalizedOptions;
    }

    /**
	   * Purpose : Used to get the object of class UserManager
	   * @return : UserManager
	   */        
    public UserManager getUserManager()  {
        return userManager;
    }
}

