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
 * $Id: DbsLibrarySession.java,v 1.6 2005/07/08 11:11:51 suved Exp $
 *****************************************************************************
 */

package rts.beans; 
/**
 *	Purpose: The encapsulate the functionality of LibrarySession class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*Java API*/
import java.io.*;
import java.util.*;
/*CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.beans.resources.*;
import oracle.ifs.common.*;
import oracle.ifs.common.Collection;

public class DbsLibrarySession {
    private LibrarySession librarySession; // to accept object of type LibrarySession

    /**
     * Purpose : To create DbsLibrarySesion using LibrarySesion class
     * @param  : librarySesion - An LibrarySesion Object  
     */
    public DbsLibrarySession(LibrarySession librarySession) {
        this.librarySession=librarySession;
    }

    /**
	   * Purpose  : Aborts a transaction.
	   * @param   : dbsTransaction - the transaction to abort
     * @throws  : DbsException - if operation fails
	   */
    public final void abortTransaction(DbsTransaction dbsTransaction) throws DbsException {
        try{
            librarySession.abortTransaction(dbsTransaction.getTransaction());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Starts a writeable transaction.
	   * @returns : a token that can be passed to either completeTransaction or abortTransaction
     * @throws  : DbsException - if operation fails
	   */  
    public final DbsTransaction beginTransaction() throws DbsException {
        DbsTransaction dbsTransaction=null;
        try{
            dbsTransaction=new DbsTransaction( librarySession.beginTransaction());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsTransaction;
    }

    /**
	   * Purpose  : Starts a writeable transaction.
	   * @param   : Completes a transaction.
     * @throws  : DbsException - if operation fails
	   */  
    public final void completeTransaction(DbsTransaction dbsTransaction) throws DbsException{
        try{
            librarySession.completeTransaction(dbsTransaction.getTransaction());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
   
    public DbsDirectoryGroup createPublicObject(DbsDirectoryGroupDefinition dgDef) throws DbsException {
        DbsDirectoryGroup dbsDirGroup=null;
        try{
            DirectoryGroupDefinition dirGroupDef=dgDef.getDirectoryGroupDefinition(); 
            DirectoryGroup dirGroup=(DirectoryGroup)this.librarySession.createPublicObject(dirGroupDef);
            dbsDirGroup = new DbsDirectoryGroup(dirGroup);            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDirGroup ;
    }

    public DbsAccessControlList createPublicObject(DbsAccessControlListDefinition dbsAclDef) throws DbsException {
        DbsAccessControlList dbsAcl=null;
        try{
            AccessControlListDefinition aclDef=dbsAclDef.getAccessControlListDefinition(); 
            AccessControlList acl=(AccessControlList)this.librarySession.createPublicObject(aclDef);
            dbsAcl = new DbsAccessControlList(acl);            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAcl ;
    }

    public DbsPropertyBundle createPublicObject(DbsPropertyBundleDefinition dbsPropertyBundleDef) throws DbsException {
        DbsPropertyBundle dbsPropertyBundle=null;
        try{
            PropertyBundleDefinition propertyBundleDef=dbsPropertyBundleDef.getPropertyBundleDefinition();
            PropertyBundle propertyBundle=(PropertyBundle)this.librarySession.createPublicObject(propertyBundleDef);
            dbsPropertyBundle = new DbsPropertyBundle(propertyBundle);            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsPropertyBundle;
    }

    public DbsValueDefaultPropertyBundle createPublicObject(DbsValueDefaultPropertyBundleDefinition dbsValueDefaultPropertyBundleDefinition) throws DbsException {
        DbsValueDefaultPropertyBundle dbsValueDefaultPropertyBundle=null;
        try{
            ValueDefaultPropertyBundleDefinition valueDefaultPropertyBundleDefinition=dbsValueDefaultPropertyBundleDefinition.getValueDefaultPropertyBundleDefinition();
            ValueDefaultPropertyBundle valueDefaultPropertyBundle=(ValueDefaultPropertyBundle)this.librarySession.createPublicObject(valueDefaultPropertyBundleDefinition);
            dbsValueDefaultPropertyBundle = new DbsValueDefaultPropertyBundle(valueDefaultPropertyBundle);            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsValueDefaultPropertyBundle;
    }

    public DbsAccessControlEntry createSystemObject(DbsAccessControlEntryDefinition dbsAceDef) throws DbsException {
        DbsAccessControlEntry dbsAce=null;
        try{
            AccessControlEntryDefinition aceDef=dbsAceDef.getAccessControlEntryDefinition(); 
            AccessControlEntry ace=(AccessControlEntry)this.librarySession.createSystemObject(aceDef);
            dbsAce = new DbsAccessControlEntry(ace);            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAce ;
    }

    public DbsPermissionBundle createSystemObject(DbsPermissionBundleDefinition dbsPermissionBundleDef) throws DbsException {
        DbsPermissionBundle dbsPermissionBundle=null;
        try{
            PermissionBundleDefinition permissionBundleDef=dbsPermissionBundleDef.getPermissionBundleDefinition(); 
            PermissionBundle permissionBundle=(PermissionBundle)this.librarySession.createSystemObject(permissionBundleDef);
            dbsPermissionBundle = new DbsPermissionBundle(permissionBundle);            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsPermissionBundle ;
    }

    public DbsContentQuota createPublicObject(DbsContentQuotaDefinition dbsContentQuotaDef) throws DbsException {
        DbsContentQuota dbsQuota=null;
        try{
            ContentQuotaDefinition quotaDef=dbsContentQuotaDef.getContentQuotaDefinition(); 
            ContentQuota quota=(ContentQuota)this.librarySession.createPublicObject(quotaDef);
            dbsQuota = new DbsContentQuota(quota);            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsQuota ;
    }

   
 /*
  * 
  * public DbsPublicObject createPublicObject(DbsPublicObjectDefinition dbsPubDef) throws DbsException{
    DbsPublicObject dbsPubObj=null;
        try{
            dbsPubObj=new DbsPublicObject(librarySession.createPublicObject(dbsPubDef.getPublicObjectDefinition()));      
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsPubObj ;
    }

    */
 
    /**
	   * Purpose  : Create a new schema object.
	   * @param   : soDef - the SchemaObjectDefinition for the new system object
     * @throws  : DbsException - if operation fails
	   */  
/*
    public DbsSchemaObject createSchemaObject(DbsSchemaObjectDefinition soDef) throws DbsException {
        DbsSchemaObject dbsSo=null;
        try{
            dbsSo=new DbsSchemaObject(librarySession.createSchemaObject(soDef.getSchemaObjectDefinition()));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsSo ;
    }
*/

    public DbsValueDefault createSchemaObject(DbsValueDefaultDefinition dbsValueDefaultDefinition) throws DbsException {
        DbsValueDefault dbsValueDefault=null;
        try{
            ValueDefaultDefinition  valueDefaultDefinition = dbsValueDefaultDefinition.getValueDefaultDefinition();
            ValueDefault valueDefault=(ValueDefault)librarySession.createSchemaObject(valueDefaultDefinition);
            dbsValueDefault=new DbsValueDefault(valueDefault);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsValueDefault ;
    }

    /**
	   * Purpose  : Create a new system object.
	   * @param   : soDef - the SystemObjectDefinition for the new system object
     * @throws  : DbsException - if operation fails
	   */  
   /* public DbsSystemObject createSystemObject(DbsSystemObjectDefinition soDef) throws DbsException {
        DbsSystemObject dbsSo=null;
        try{
            dbsSo=new DbsSystemObject(librarySession.createSystemObject(soDef.getSystemObjectDefinition()));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsSo ;
    }

    */

   /**
	   * Purpose  : Disconnects the session, effectively disposing the instance.
	   * @returns : status of the disconnect operation (always true)
     * @throws  : DbsException - if operation fails
	   */  
    public boolean disconnect() throws DbsException{
        boolean isDisconnected;
        try{
            isDisconnected=librarySession.disconnect();     
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isDisconnected;
    }

    /**
	   * Purpose  : Gets a reference to the ClassAccessControlList collection.
	   * @returns : the CachedSelectorCollection
     * @throws  : DbsException - if operation fails
	   */  
    public final Collection getClassAccessControlListCollection() throws DbsException {
        Collection collection = null;
        try{
            collection = librarySession.getClassAccessControlListCollection();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return collection ;
    }

    /**
	   * Purpose  : Gets the session's default ContentQuota, set via setDefaultContentQuota().
	   * @returns : the quota object being used by the session, or null if the user's quota is being used.
     * @throws  : DbsException - if operation fails
	   */
    public DbsContentQuota getDefaultContentQuota() throws DbsException {
        DbsContentQuota dbsQuota=null;
        try{
            dbsQuota=new DbsContentQuota(librarySession.getDefaultContentQuota());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsQuota;
    }

    /**
	   * Purpose  : Lookup a DirectoryObject by its id.
	   * @param   : id - identifier for the object
     * @throws  : DbsException - if operation fails
	   */
    public DbsDirectoryObject getDirectoryObject(Long id) throws DbsException {
        DbsDirectoryObject dbsDirObj=null;
        try{
            dbsDirObj=new DbsDirectoryObject(librarySession.getDirectoryObject(id));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDirObj;
    }

    /**
	   * Purpose  : Gets a reference to the DirectoryUser collection.
	   * @returns : the UncachedSelectorCollection
     * @throws  : DbsException - if operation fails
	   */
    public final DbsCollection getDirectoryUserCollection() throws DbsException {
        DbsCollection collection = null;
        try{
            collection = new DbsCollection(librarySession.getDirectoryUserCollection());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return collection ;
    }

    /**
	   * Purpose  : Gets the id of this session.
     * @throws  : DbsException - if operation fails
	   */
    public java.lang.Long getId() throws DbsException{
        Long id=null; 
        try{
            id=librarySession.getId();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return id;
    }

    /**
	   * Purpose  : Gets a reference to the PermissionBundle collection.
	   * @returns : the CachedSelectorCollection
     * @throws  : DbsException - if operation fails
	   */
    public final Collection getPermissionBundleCollection() throws DbsException{
        Collection collection = null;
        try{
            collection = librarySession.getPermissionBundleCollection();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return collection ;
    }

    /**
	   * Purpose  : Gets a reference to the Format collection.
	   * @returns : the CachedResolverCollection
     * @throws  : DbsException - if operation fails
	   */
    public final Collection getFormatCollection() throws DbsException{
        Collection collection = null;
        try{
            collection = librarySession.getFormatCollection();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return collection ;
    }

    /**
	   * Purpose  : Gets a reference to the Policy collection.
	   * @returns : the CachedSelectorCollection
     * @throws  : DbsException - if operation fails
	   */
    public final Collection getPolicyCollection() throws DbsException {
        Collection collection = null;
        try{
            collection = librarySession.getPolicyCollection();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return collection ;
    }

    /**
	   * Purpose  : Lookup a PublicObject by its id.
	   * @param   : id - identifier for the object
	   * @returns : the PublicObject
     * @throws  : DbsException - if operation fails
	   */
    public DbsPublicObject getPublicObject(java.lang.Long id) throws DbsException {
        DbsPublicObject dbsPublicObject=null;
        PublicObject publicObject;
        try{
            publicObject = librarySession.getPublicObject(id);
            if(publicObject instanceof Folder){
                return new DbsFolder((Folder)publicObject);
            }else if(publicObject instanceof Document){
                return new DbsDocument((Document)publicObject);
            }else if(publicObject instanceof Family){
                return new DbsFamily((Family)publicObject);
            }else if(publicObject instanceof AccessControlList){
                return new DbsAccessControlList((AccessControlList)publicObject);
            }else{
                return new DbsPublicObject(publicObject);
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Return the top-most Folder in the iFS folder hierarchy.
	   * @returns : the top-most root folder
     * @throws  : DbsException - if operation fails
	   */
    public DbsFolder getRootFolder() throws DbsException {
        DbsFolder dbsFolder=null;
        try{
            dbsFolder=new DbsFolder(librarySession.getRootFolder());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsFolder;
    }

    /**
     * Purpose  : Lookup a SchemaObject by its id.
	   * @param   : id - identifier for the object
	   * @returns : the SchemaObject
     * @throws  : DbsException - if operation fails
	   */
    public DbsSchemaObject getSchemaObject(java.lang.Long id) throws DbsException{
        DbsSchemaObject dbsSo=null;
        try{
            dbsSo= new DbsSchemaObject(librarySession.getSchemaObject(id));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsSo;
    }

    /**
	   * Purpose  : Gets the specified service configuration property.
	   * @param   : name - the property name
	   * @returns : the property, or null if there is no such property
     * @throws  : DbsException - if operation fails
	   */
    public DbsAttributeValue getServiceConfigurationProperty(java.lang.String name) throws DbsException{
      DbsAttributeValue dbsAttributeValue=null;
      try{
        dbsAttributeValue=new DbsAttributeValue(librarySession.getServiceConfigurationProperty(name));
      }catch(IfsException ifsError){
        throw new DbsException(ifsError);
      }
      return dbsAttributeValue;        
    }

   /**
	   * Purpose  : Gets the value of the specified service configuration property, as a string.
	   * @param   : name - the property name
	   * @param   : defaultValue - the default value
	   * @returns : the property value
     * @throws  : DbsException - if operation fails
	   */
    public java.lang.String getServiceConfigurationProperty(java.lang.String name,
                            java.lang.String defaultValue) throws DbsException{
    String configProp=null;
    try{
      configProp=librarySession.getServiceConfigurationProperty(name,defaultValue);
    }catch(IfsException ifsError) {
      throw new DbsException(ifsError);
    }
    return configProp;
    } 

    /**
	   * Purpose  : Gets the value of the specified service configuration property, as a boolean.
	   * @param   : name - the property name
	   * @param   : defaultValue - the default value
	   * @returns : the property value
     * @throws  : DbsException - if operation fails
	   */
    public boolean getServiceConfigurationProperty(java.lang.String name,boolean defaultValue) 
                                                                        throws DbsException{
        boolean configProp;
        try{
            configProp=librarySession.getServiceConfigurationProperty(name,defaultValue);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return configProp;
    } 

    /**
	   * Purpose  : Gets the value of the specified service configuration property, as a integer.
	   * @param   : name - the property name
	   * @param   : defaultValue - the default value
	   * @returns : the property value
     * @throws  : DbsException - if operation fails
	   */
    public int getServiceConfigurationProperty(java.lang.String name,int defaultValue)
                                                                throws DbsException{
        int configProp=0;
        try{
            configProp=librarySession.getServiceConfigurationProperty(name,defaultValue);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return configProp;
    } 

    /**
	   * Purpose  : Gets the id of this session's service.
     * @throws  : DbsException - if operation fails
	   */    
    public java.lang.Long getServiceId() throws DbsException{
        Long id = null;
        try{
            id=librarySession.getServiceId();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return id;
    }

    /**
	   * Purpose  : Gets the name of the iFS service to which this session is connected.
     * @returns : the service name
     * @throws  : DbsException - if operation fails
	   */
    public java.lang.String getServiceName() throws DbsException{
        String serviceName=null;
        try{
            serviceName=librarySession.getServiceName();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return serviceName;
    }

    /**
	   * Purpose  : Gets a reference to the SharedAccessControlList collection
     * @returns : the UncachedSelectorCollection
     * @throws  : DbsException - if operation fails
	   */
    public final DbsCollection getSharedAccessControlListCollection() throws DbsException{
        DbsCollection dbsAcl=null;
        try{
            dbsAcl=new DbsCollection(librarySession.getSharedAccessControlListCollection());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAcl ;
    }

    /**
	   * Purpose  : Gets a reference to the SystemAccessControlList collection.
     * @returns : the CachedSelectorCollection
     * @throws  : DbsException - if operation fails
	   */
    public final DbsCollection getSystemAccessControlListCollection() throws DbsException{
        DbsCollection dbsAcl=null;
        try{
          dbsAcl=new DbsCollection(librarySession.getSystemAccessControlListCollection());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAcl ;
    }

    /**
	   * Purpose  : Gets the DirectoryUser who is the permanent admin user.
     * @returns : the system user
     * @throws  : DbsException - if operation fails
	   */
    public DbsDirectoryUser getSystemDirectoryUser() throws DbsException{
        DbsDirectoryUser dbsDirectoryUser=null;
        try{
            dbsDirectoryUser=new DbsDirectoryUser(librarySession.getSystemDirectoryUser());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDirectoryUser;
    }

    /**
	   * Purpose  : Lookup a SystemObject by its id.
     * @returns : id - identifier for the object
     * @throws  : DbsException - if operation fails
	   */
    public DbsSystemObject getSystemObject(java.lang.Long id) throws DbsException{
        DbsSystemObject dbsSystemObject=null;
        SystemObject systemObject=null;
        try{
            systemObject = librarySession.getSystemObject(id);
            if(systemObject instanceof Format){
                dbsSystemObject = new DbsFormat((Format)systemObject);
            }else{
                dbsSystemObject = new DbsSystemObject(systemObject);
            }
            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsSystemObject;
    }

    /**
	   * Purpose  : Return the user associated with the current session. 
	   *          : If this session is impersonating a user that is not the same user who authenticated, 
	   *          : this method returns the impersonating user.
     * @returns : the current user
     * @throws  : DbsException - if operation fails
	   */
    public DbsDirectoryUser getUser() throws DbsException{
        DbsDirectoryUser directoryUser=null;
        try{
            directoryUser=new DbsDirectoryUser(librarySession.getUser());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return directoryUser;
    }

    /**
	   * Purpose  : Grant the Administrator Mode state for the specified Directory User.
     * @returns : user - the user to receive the admin grant
     * @throws  : DbsException - if operation fails
	   */
    public void grantAdministration(DbsDirectoryUser dbsUser)throws DbsException{
        try {
            librarySession.grantAdministration(dbsUser.getDirectoryUser());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Assume the identity of another user. New objects created will be owned by this impersonated user. 
     *          : This operation requires that the authenticated user has the ability to enable administration mode, 
     *          : but will also work if the user is currently in not in administration mode. In this case, the impersonation 
     *          : will also not be in administration mode. Also note that since admin mode is required for this operation, 
     *          : admin mode can be enabled while impersonating the new user (who may not normally have admin privileges).
     * @param   : dbsuser - new User to impersonate, or null to cancel a previous impersonation.
     * @throws  : DbsException - if operation fails
     */
    public void impersonateUser(DbsDirectoryUser dbsUser) throws DbsException{
        try {
            if(dbsUser == null){
                librarySession.impersonateUser(null);
            }else{
                librarySession.impersonateUser(dbsUser.getDirectoryUser());
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Return connection state of this LibrarySession
     * @param   : the connection state; true if connected.
     * @throws  : DbsException - if operation fails
	   */
    public boolean isConnected(){
        boolean isConnected;
        isConnected=librarySession.isConnected();
        return isConnected ;
    }

    /**
	   * Purpose  : Return administrationMode state of this LibrarySession
     * @param   : the administrationMode ; true if administrationMode enabled.
     * @throws  : DbsException - if operation fails
	   */
    public boolean isAdministrationMode(){
        boolean isAdministrationMode;
        isAdministrationMode = librarySession.isAdministrationMode();
        return isAdministrationMode;
    }

    /**
	   * Purpose  : Set the administrator mode state for this session
     * @param   : mode - new state of administrator mode; true to enable administration mode
     * @throws  : DbsException - if operation fails
	   */
    public void setAdministrationMode(boolean mode) throws DbsException{
        try{
            librarySession.setAdministrationMode(mode);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Sets the session's default ContentQuota. This ContentQuota object will be used for every 
	   *          : Document that this session creates. If the specified value is null, then the user's ContentQuota 
	   *          : object will be used by default.
     * @param   : quota - the quota object to use by default
     * @throws  : DbsException - if operation fails
	   */  
    public void setDefaultContentQuota(DbsContentQuota dbsQuota) throws DbsException {
        try {
            librarySession.setDefaultContentQuota(dbsQuota.getContentQuota());  
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    public Serializable invokeServerMethod(String methodName,Serializable payload)throws DbsException {
        try {
            return librarySession.invokeServerMethod(methodName,payload);  
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Gets a reference to the ClassObject collection.
	   * @returns : the CachedSelectorCollection
     * @throws  : DbsException - if operation fails
	   */  
    public final DbsCollection getClassObjectCollection() throws DbsException{
        DbsCollection dbsCollection=null;
        try{
            dbsCollection=new DbsCollection(librarySession.getClassObjectCollection());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsCollection;    
    }

  /**
   * Returns a class object given a class name. This uses the ClassObjectCollection and is simply a convenience method.
   * @throws rts.beans.DbsException
   * @return dbsClassObject
   * @param className
   */
    public final DbsClassObject getClassObjectByName(String className) throws DbsException{
        DbsClassObject dbsClassObject=null;
        try{
            dbsClassObject= new DbsClassObject(librarySession.getClassObjectByName(className));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsClassObject;    
    }
 
    public DbsPublicObject createPublicObject(DbsPublicObjectDefinition dbsPublicObjectDefinition) throws DbsException{
        DbsPublicObject dbsPublicObject=null;
        PublicObject publicObject=null;
        try{
            
            if (dbsPublicObjectDefinition instanceof DbsDocumentDefinition){
              DbsDocumentDefinition dbsDocDef= (DbsDocumentDefinition)dbsPublicObjectDefinition;
              Document document= (Document)this.librarySession.createPublicObject(dbsDocDef.getDocumentDefinition());
              DbsDocument dbsDocument=new DbsDocument(document);
              return dbsDocument;
            }else if (dbsPublicObjectDefinition instanceof DbsFolderDefinition){
              DbsFolderDefinition dbsFolderDefinition= (DbsFolderDefinition)dbsPublicObjectDefinition;
              Folder folder= (Folder)this.librarySession.createPublicObject(dbsFolderDefinition.getFolderDefinition());
              DbsFolder dbsFolder=new DbsFolder(folder);
              return dbsFolder;
            }else if (dbsPublicObjectDefinition instanceof DbsVersionDescriptionDefinition){
              DbsVersionDescriptionDefinition dbsVersionDescriptionDefinition = (DbsVersionDescriptionDefinition)dbsPublicObjectDefinition;
              VersionDescription versionDescription = (VersionDescription)(this.librarySession.createPublicObject(dbsVersionDescriptionDefinition.getVersionDescriptionDefinition()));
              DbsVersionDescription dbsVersionDescription = new DbsVersionDescription(versionDescription);
              return dbsVersionDescription;
            }
            else{                    
              PublicObject pubObject= this.librarySession.createPublicObject(dbsPublicObjectDefinition.getPublicObjectDefinition());              
              dbsPublicObject = new DbsPublicObject(pubObject);
              return dbsPublicObject;
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
      }
      public DbsCollection getFormatExtensionCollection()throws DbsException{
         Collection coll=null;
         DbsCollection dbsCollection=null;
         try{
             coll= this.librarySession.getFormatExtensionCollection();
            dbsCollection  = new DbsCollection(coll);
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
       
        } 
        return dbsCollection;
      }
 
   /**
    * Purpose  : Used to get the object of class LibrarySession
	  * @returns : LibrarySession Object
	  */
    public LibrarySession getLibrarySession(){
        return this.librarySession ;
    }
}