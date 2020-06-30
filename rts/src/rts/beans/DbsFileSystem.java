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
 * $Id: DbsFileSystem.java,v 1.2 2005/06/24 05:57:20 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of IfsFileSystem class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */ 

/* JDK API*/
import java.io.*;

/* CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
import oracle.ifs.adk.filesystem.IfsFileSystem;
//import oracle.ifs.beans.parsers.ParserCallback;

public class DbsFileSystem {
    private IfsFileSystem ifsFileSystem = null;  // to accept object of type IfsFileSystem

    /**
	   * Purpose : To create DbsFileSystem using IfsFileSystem class
	   * @param  : dbsSession - An LibrarySession Object  
	   */
    public DbsFileSystem(DbsLibrarySession dbsSession) throws DbsException {
        try {
            ifsFileSystem=new IfsFileSystem(dbsSession.getLibrarySession()); 
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
     }

    /**
	   * Purpose : Used to get the object of class IfsFileSystem
	   * @return IfsFileSystem Object
	   */
    public IfsFileSystem getIfsFileSystem() {
        return this.ifsFileSystem ;
    }

    /**
     * Purpose  : Create a reference between a Folder and a PublicObject; placing the PublicObject inside the Folder.
     * @param   : dbsFolder - Folder object that will contain dbsPo
     * @param   : dbsPo - PublicObject object
     * @throws  : DbsException - if operation fails
     */
    public void addFolderRelationship(DbsFolder dbsFolder, DbsPublicObject dbsPo)  throws DbsException {
        try{
            ifsFileSystem.addFolderRelationship(dbsFolder.getFolder(),dbsPo.getPublicObject());
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Cancel a checkout. Unreserve the Primary VersionSeries for the document.
     * @param   : dbsPo - PublicObject object to be cancelled
     * @throws  : DbsException - if operation fails
     */
    public void cancelCheckout(DbsPublicObject dbsPo) throws DbsException {
        try{
            ifsFileSystem.cancelCheckout(dbsPo.getPublicObject());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : If po is unversioned: 
     *            the session lock will be released on the object. 
     *            If po is versioned: 
     *            a new version will be created in the primary VersionSeries. 
     *            the primary VersionSeries will be unreserved.
     * @param   : dbsPo - PublicObject to be checked in
     * @param   : comment - CheckIn comments
     * @throws  : DbsException - if operation fails
     */
    public void checkIn(DbsPublicObject dbsPo, java.lang.String comment) throws DbsException {
        try{
            ifsFileSystem.checkIn(dbsPo.getPublicObject(),comment);
         } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
      }

    /**
     * Purpose  : If po is unversioned, and makeVersioned is True: po will become versioned. the primary VersionSeries will be reserved. 
     *            If po is unversioned, and makeVersioned is False: an exception will be thrown. 
     *            If po is versioned: primary VersionSeries will be reserved. 
     * @param   : dbsPo - PublicObject to be checked out
     * @param   : makeVersioned - Flag to request creation of VersionSeries for dbsPo
     * @returns : PublicObject
     * @throws  : DbsException - if operation fails
     */
    public DbsPublicObject checkOut(DbsPublicObject dbsPo,boolean makeVersioned) throws DbsException {
        DbsPublicObject  versionedPo=null;
        try{
            versionedPo = new DbsPublicObject(ifsFileSystem.checkOut(dbsPo.getPublicObject(),makeVersioned)) ;
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return versionedPo ;
    }

    /**
     * Purpose  : Return the DirectoryUser who currently has PublicObject po checked out, or null if po is not checked out.
     * @param   : dbsPo - PublicObject
     * @returns : DirectoryUser
     * @throws  : DbsException - if operation fails
     */
    public DbsDirectoryUser checkedOutBy(DbsPublicObject dbsPo) throws DbsException {
        DbsDirectoryUser directoryUser = null;
        try{
            directoryUser=new DbsDirectoryUser(ifsFileSystem.checkedOutBy(dbsPo.getPublicObject()));  
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return directoryUser;
      }

    /**
     * Purpose  : Copy the PublicObject po into the specified folder. An exception will be thrown if attempts are made to overwrite a non-Folder object with a Folder object, and vice versa.
     *            If po is a non-Folder object: 
     *            If overwrite is True, existing PublicObject in folder will be overwritten. 
     *            If overwrite is False, a new copy will be created with the naming convention "Copy of .." for the first copy, and "Copy (#) of .." for all other copies. 
     *            For po is a Folder object: 
     *            A recursive copy is performed. 
     *            If overwrite is True, the content of po will be copied over to the existing PublicObject in folder with versioning semantics. 
     *            If overwrite is False, a new copy will be created with the naming convention "Copy of .." for the first copy, and "Copy (#) of .." for all other copies. 
     * @param   : dbsPo - PublicObject to copy from
     * @param   : dbsfolder - Folder object to copy to
     * @param   : dbsacl - ACL to be applied to the newly created copy; if null, session user's default ACL is used
     * @param   : overwrite - If true, overwrite existing PublicObject in folder .
     * @returns : DirectoryUser
     * @throws  : DbsException - if operation fails
     */
    public DbsPublicObject copy(DbsPublicObject  dbsPo, DbsFolder  dbsFolder, 
                                DbsAccessControlList  dbsAcl, boolean  overwrite) throws DbsException {
        DbsPublicObject  copyPo=null;
        try{
            if (dbsAcl!=null){
                copyPo=new DbsPublicObject(ifsFileSystem.copy(dbsPo.getPublicObject(),dbsFolder.getFolder(),dbsAcl.getAccessControlList(),overwrite));
            }else{
                copyPo=new DbsPublicObject(ifsFileSystem.copy(dbsPo.getPublicObject(),dbsFolder.getFolder(),null,overwrite));            
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return copyPo;
      }

    /**
     * Purpose  : Copy a Folder into another Folder. If recursive flag is True, subfolders will be copied as well. 
     *            If there exists a Folder object in toFolder with same name as fromFolder, the contents of 
     *            fromFolder will be copied over to the existing Folder object in toFolder with versioning semantics.
     * @param   : fromFolder - Folder to copy from
     * @param   : toFolder - Folder to copy to
     * @param   : recursive - If True, subfolders will be copied
     * @param   : dbsacl - ACL to be applied to the newly created copy of fromFolder (but not its items); if null, session user's default ACL will be used
     * @returns : Folder (the new copy)
     * @throws  : DbsException - if operation fails
     */
    public DbsFolder copy(DbsFolder fromFolder, DbsFolder toFolder, 
                          boolean recursive, DbsAccessControlList dbsAcl) throws DbsException {
        DbsFolder copyFolder = null;
        try{
            if (dbsAcl!=null){
                copyFolder=new DbsFolder(ifsFileSystem.copy(fromFolder.getFolder(),toFolder.getFolder(),recursive,dbsAcl.getAccessControlList()));
            }else{
                copyFolder=new DbsFolder(ifsFileSystem.copy(fromFolder.getFolder(),toFolder.getFolder(),recursive,null));
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return copyFolder ;
    }

    /**
     * Purpose  : Create a Document in iFS. If a document already exists with the same name as name, an exception is thrown.
     * @param   : name - Name of the document to be created
     * @param   : content - InputStream to read contents from; if null, empty document is created
     * @param   : folderPath - Absolute path of folder in which to create the document
     * @param   : dbsacl - ACL to be assigned to the new document; if null, sess user's default ACL will be used
     * @returns : Document (the new Document created)
     * @throws  : DbsException - if operation fails
     */
    public DbsDocument createDocument(java.lang.String name,java.io.InputStream content,
                                      java.lang.String folderPath, DbsAccessControlList dbsAcl) 
                                      throws DbsException,java.io.IOException {
        DbsDocument dbsDocument=null;
        try{
            if(dbsAcl != null){
                dbsDocument = new DbsDocument(ifsFileSystem.createDocument(name,content,folderPath,dbsAcl.getAccessControlList()));
            }else{
                dbsDocument = new DbsDocument(ifsFileSystem.createDocument(name,content,folderPath,null));
            }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDocument ;
    }

    /**
     * Purpose  : Create a Folder in iFS.
     * @param   : name - Name of the Folder to be created; name can also be in the format of a path (relative to destFolder) if createParents is true (eg. "a/b/c")
     * @param   : destfolder - Folder in which to create the new folder
     * @param   : createParents - If true, non-existing parent folders will be created
     * @param   : acl - ACL to be assigned to new folder(s) created; if null, session user's default ACL will be used
     * @returns : Folder (the new folder created)
     * @throws  : DbsException - if operation fails
     */ 
    public DbsFolder createFolder(java.lang.String  name,DbsFolder  destfolder,
                                  boolean  createParents, DbsAccessControlList dbsAcl) 
                                  throws DbsException {
        DbsFolder dbsFolder=null;
        Folder folder=null;
        try{
            if(dbsAcl!=null){
              folder=ifsFileSystem.createFolder(name,destfolder.getFolder(),createParents,dbsAcl.getAccessControlList());
            }else{
              folder=ifsFileSystem.createFolder(name,destfolder.getFolder(),createParents,null);
            }
            dbsFolder=new DbsFolder(folder);
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsFolder ; 
    }

    /**
     * Purpose  : Delete a PublicObject. If po is a Folder, then recursive delete will be performed.
     * @param   : dbsPo - PublicObject to be deleted.
     * @throws  : DbsException - if operation fails
     */                
    public void delete(DbsPublicObject dbsPo) throws DbsException {
        try{
            ifsFileSystem.delete(dbsPo.getPublicObject());
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Delete a Folder. If recursive if True, subfolders will be deleted
     * @param   : dbsFolder - Folder object to be deleted
     * @param   : recursive - If true, subfolders will be deleted
     * @throws  : DbsException - if operation fails
     */
    public void delete(DbsFolder dbsFolder, boolean recursive) throws DbsException {
        try{
            ifsFileSystem.delete(dbsFolder.getFolder(),recursive);
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Returns the PublicObject having the specified id.
     * @param   : id - Id of the PublicObject
     * @returns : PublicObject
     * @throws  : DbsException - if operation fails
     */
    public DbsPublicObject findPublicObjectById(java.lang.Long id) throws DbsException {
         
        try{
              PublicObject publicObject=ifsFileSystem.findPublicObjectById(id);
              if (publicObject instanceof Folder){
                return new DbsFolder((Folder)publicObject);
              }else if(publicObject instanceof Document) {
                return new DbsDocument((Document)publicObject);
              }else if(publicObject instanceof Family) {
                return new DbsFamily((Family)publicObject);
              }else{
                return null;
              }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }

    }

    /**
     * Purpose  : Returns the PublicObject pointed to by path.
     * @param   : path - Path to the PublicObject
     * @returns : PublicObject
     * @throws  : DbsException - if operation fails
     */
    public DbsPublicObject findPublicObjectByPath(java.lang.String path) throws DbsException {
        PublicObject publicObject = null;

        try{
            publicObject = ifsFileSystem.findPublicObjectByPath(path);
            if(publicObject instanceof Folder){
                return new DbsFolder((Folder)publicObject);
            }else if(publicObject instanceof Document){
                return new DbsDocument((Document)publicObject);
            }else if(publicObject instanceof Family){
                return new DbsFamily((Family)publicObject);
            }else{
                return new DbsPublicObject(publicObject);
            }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
       
    }

    /**
     * Purpose  : Return a specified attribute of a PublicObject.
     * @param   : dbsPo - PublicObject from which to get the attribute
     * @param   : attrName - Name of the attribute
     * @returns : AttributeValue
     * @throws  : DbsException - if operation fails
     */ 
    public DbsAttributeValue getAttribute(DbsPublicObject dbsPo,java.lang.String attrName) throws DbsException {
        DbsAttributeValue attributeValue = null;
        try{
            attributeValue=new DbsAttributeValue(ifsFileSystem.getAttribute(dbsPo.getPublicObject(),attrName));
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return attributeValue;
    }

    /**
     * Purpose  : Return the specified attributes of a PublicObject. If attrNames is null, all attributes will be returned.
     * @param   : dbsPo - PublicObject from which to get the attribute
     * @param   : attrNames - A list of attributes to return; if null, all attributes will be returned
     * @returns : An array of AttributeValue objects
     * @throws  : DbsException - if operation fails
     */ 
    public DbsAttributeValue[] getAttributes(DbsPublicObject dbsPo, java.lang.String[] attrNames) throws DbsException {
        DbsAttributeValue dbsAttributeValue[] = null;
        try{
            AttributeValue attrValue[]=ifsFileSystem.getAttributes(dbsPo.getPublicObject(),attrNames);
            int attrCount=attrValue.length;
            dbsAttributeValue=new DbsAttributeValue[attrCount] ;
            for(int index = 0 ; index < attrCount ; index++) {
                dbsAttributeValue[index]=new DbsAttributeValue(attrValue[index]);
            }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAttributeValue ;
    }

    /**
     * Purpose  : Display the current user in the session.
     * @returns : DirectoryUser
     * @throws  : DbsException - if operation fails
     */ 
    public DbsDirectoryUser getCurrentUser() throws DbsException {
        DbsDirectoryUser dbsDirectoryUser = null;
        try{
            dbsDirectoryUser = new DbsDirectoryUser(ifsFileSystem.getCurrentUser()) ;
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDirectoryUser ;
    }

    /**
     * Purpose  : Retrieve the content of a Document as a stream.
     * @param   : doc - Document from which to retrieve content
     * @returns : DirectoryUser
     * @throws  : DbsException - if operation fails
     */
    public java.io.InputStream getDocumentContent(DbsDocument dbsDoc) throws DbsException, java.io.IOException {
        InputStream inputStream=null;
        try{
            inputStream = ifsFileSystem.getDocumentContent(dbsDoc.getDocument());
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return inputStream ;
    }

    /**
     * Purpose  : Retrieve the revision history of the specified PublicObject. The versions are ordered oldest first. If po is unversioned, return null.
     * @param   : dbsPo - PublicObject from which to get history
     * @returns : An array of VersionDescription objects. Null; if po is unversioned
     * @throws  : DbsException - if operation fails
     */
    public DbsVersionDescription[] getHistory(DbsPublicObject  dbsPo) throws DbsException {
        DbsVersionDescription dbsVersions[]=null;
        try{
            VersionDescription versions[]=ifsFileSystem.getHistory(dbsPo.getPublicObject()); 
            int versionCount=versions.length;
            dbsVersions=new DbsVersionDescription[versionCount];
            for(int index=0 ; index < versionCount ; index++) {
                dbsVersions[index]=new DbsVersionDescription(versions[index]);
            }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsVersions ; 
    }

    /**
     * Purpose  : Return a list of parent Folders that reference the specified PublicObject.
     * @param   : dbsPo - PublicObject from which to retrieve parents
     * @returns : An array of Folder objects
     * @throws  : DbsException - if operation fails
     */
    public DbsFolder[] getParents(DbsPublicObject dbsPo)  throws DbsException {
        DbsFolder dbsFolders[]=null;
        try{
            Folder folders[] = ifsFileSystem.getParents(dbsPo.getPublicObject());
            int folderCount=folders.length;
            dbsFolders=new DbsFolder[folderCount] ;
            for(int index = 0 ; index < folderCount ; index++) {
                dbsFolders[index]=new DbsFolder(folders[index]);
            }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsFolders ;
    }

    /**
     * Purpose  : Check to see if a PublicObject is currently checked out. Return 'True' if checked out, 'False' otherwise.
     * @param   : dbsPo - PublicObject
     * @returns : boolean
     * @throws  : DbsException - if operation fails
     */
    public boolean isCheckedOut(DbsPublicObject dbsPo) throws DbsException {
        boolean isCheckedOut ;
        try{
            isCheckedOut=ifsFileSystem.isCheckedOut(dbsPo.getPublicObject());
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isCheckedOut;
    }

    /**
     * Purpose  : Check to see if a PublicObject is versioned. Return 'true' if po is versioned. False otherwise.
     * @param   : dbsPo - PublicObject
     * @returns : boolean
     * @throws  : DbsException - if operation fails
     */
    public boolean isVersioned(DbsPublicObject dbsPo) throws DbsException {
        boolean isVersioned ;
        try{
            isVersioned=ifsFileSystem.isVersioned(dbsPo.getPublicObject());
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isVersioned;
    }

    /**
     * Purpose  : Return the items of the specified folder whose names match pattern. 
     *            (Subfolders are not expanded -- see searchByName()). pattern may contain wildcards. 
     *            If pattern is null, then all items will be returned. The returned array is in alphabetical order.
     * @param   : dbsFolder - PublicObject
     * @param   : pattern - Filename pattern to be matched; if null, return all
     * @returns : An array of PublicObject
     * @throws  : DbsException - if operation fails
     */
    public DbsPublicObject[] listFolder(DbsFolder dbsFolder, java.lang.String pattern) throws DbsException {
        DbsPublicObject dbsPos[]=null;
        try {
            PublicObject pos[]=ifsFileSystem.listFolder(dbsFolder.getFolder(),pattern) ;
            int poCount= pos.length;
            for(int index = 0 ; index < poCount ; index++) {
                dbsPos[index]=new DbsPublicObject(pos[index]);
            }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsPos ;
    }

    /**
     * Purpose  : Convert an unversioned PublicObject into versioned PublicObject. 
     *            All parents of po will have their references changed to point to the newly created Family object.
     * @param   : dbsPo - Unversioned document to convert
     * @returns : PublicObject
     * @throws  : DbsException - if operation fails
     */
    public DbsPublicObject makeVersioned(DbsPublicObject dbsPo) throws DbsException {
        DbsPublicObject dbsPublicObject = null;
        try{
            dbsPublicObject = new DbsPublicObject(ifsFileSystem.makeVersioned(dbsPo.getPublicObject()));
            dbsPublicObject.setDescription(dbsPo.getDescription());
            System.out.println("new dbsPublicObject.getDescription(): "+dbsPublicObject.getDescription());
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsPublicObject ;
    }
    /**
     * Purpose  : Move a PublicObject from fromFolder to toFolder with the new name newName and new access control list acl, if specified.
     *            If fromFolder and toFolder are the same: 
     *            If newName is not null: rename() will be performed, ignoring acl.. 
     *            If acl is not null: an exception will be thrown. 
     *            If there exists a po with same name in toFolder as po: 
     *            If overwrite is false: an exception will be thrown. 
     *            If overwrite is true and po is a Folder: contents of po will be copied to the existing folder in toFolder with versioning semantics. 
     *            If overwrite is true and po is not a Folder: 
     *            overwrite the existing po in toFolder with po with versioning semantics. 
     *                 
     *            Otherwise: 
     *            If newName is specified: 
     *            Move po to toFolder. 
     *            Change po's name to newName. 
     *            If newName is not specified: 
     *            Move po to toFolder. 
     *              
     *            If ACL is null, po's ACL will be left unchanged.
     * @param   : fromFolder - Folder in which the PO belongs
     * @param   : toFolder - Folder to which to move PO
     * @param   : po - PublicObject to move
     * @param   : newName - New name to be assigned to the moved PO
     * @param   : acl - ACL to be used on the moved object; if null, acl will not be changed
     * @param   : overwrite - If true, overwrite existing PublicObject in folder
     * @returns : PublicObject
     * @throws  : DbsException - if operation fails
     */    
    public DbsPublicObject move(DbsFolder fromFolder, DbsFolder toFolder, 
                                DbsPublicObject dbsPo, java.lang.String newName, 
                                DbsAccessControlList dbsAcl, boolean overwrite) throws DbsException  {
        DbsPublicObject movedObject=null;
        try{
            if (dbsAcl!=null){
                movedObject=new DbsPublicObject(ifsFileSystem.move(fromFolder.getFolder(),toFolder.getFolder(),dbsPo.getPublicObject(),newName,dbsAcl.getAccessControlList(),overwrite));
            }else{                
                movedObject=new DbsPublicObject(ifsFileSystem.move(fromFolder.getFolder(),toFolder.getFolder(),dbsPo.getPublicObject(),newName,null,overwrite));            
            }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return movedObject;
    }

    /**
     * Purpose  : Create an iFS Document named name in Folder folder with content as the body. If there is an existing Document with same name as name, 
     *            this call is equivalent to updateDocument(). 
     *            If document does not exist: 
     *              an unversioned Document will be created. 
     *              
     *            If document does exist: 
     *              updateDocument() is called. 
     *            
     *            If acl is null: 
     *            session user's default acl will be used
     *              
     *            If ACL is null, po's ACL will be left unchanged.
     * @param   : name - Name of the document
     * @param   : content - InputStream from which to read document contents
     * @param   : folderPath - Absolute path of Folder in which to create the document
     * @param   : dbsacl - ACL to be assigned to the document; if null, session user's default ACL will be used
     * @returns : Document (the new document created)
     * @throws  : DbsException - if operation fails
     */    
    public DbsDocument putDocument(java.lang.String name, java.io.InputStream content, 
                                   java.lang.String folderPath, DbsAccessControlList dbsAcl)
                                   throws DbsException, java.io.IOException  {
        DbsDocument dbsDocument=null;
        try{
            if(dbsAcl!=null) {
                dbsDocument=new DbsDocument(ifsFileSystem.putDocument(name,content,folderPath,dbsAcl.getAccessControlList()));
            }else{
                dbsDocument=new DbsDocument(ifsFileSystem.putDocument(name,content,folderPath,null));            
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDocument ;
    }

    /**
     * Purpose  : Create an iFS Document named name in Folder folder with content as the body.  
     *          : If there is an existing Document with same name as name, this call is equivalent to updateDocument().
     *          : If document does not exist: 
     *          : an unversioned Document will be created. 
     *          : 
     *          : If document does exist: 
     *          : updateDocument() is called. 
     *          : 
     *          : If doParse is true: 
     *          : the input stream will be parsed using a parser which is determined based on the file extension. 
     *          : 
     *          : If acl is null: 
     *          : session user's default acl will be used. 
     *          : 
     *          : If doParse is true and the object created from parsing the input stream is a Document or a subclass, 
     *          : then it will be returned. If the object created from parsing the input stream is not a Document or a subclass, 
     *          : null is returned (but the object is still created).
     * @param   : name - Name of the document
     * @param   : content - InputStream from which to read document contents
     * @param   : folderPath - Absolute path of Folder in which to create the document
     * @param   : dbsacl - ACL to be assigned to the document; if null, session user's default ACL will be used
     * @returns : Document (the new document created)
     * @throws  : DbsException - if operation fails
     */            
    public void removeFolderRelationship(DbsFolder dbsFolder,DbsPublicObject dbsPo, boolean rmNoRef) throws DbsException {
        try{
            ifsFileSystem.removeFolderRelationship(dbsFolder.getFolder(),dbsPo.getPublicObject(),rmNoRef);
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Rename a PublicObject. If any of po's parent Folders contains an existing PO with the same name as newName, 
     *          : the PO will not be renamed, and an exception will be thrown.
     * @param   : dbsPo - PublicObject to be renamed
     * @param   : newName - New name to be assigned
     * @throws  : DbsException - if operation fails
     */            
    public void rename(DbsPublicObject dbsPo, java.lang.String newName) throws DbsException {
        try{
            ifsFileSystem.rename(dbsPo.getPublicObject(),newName);
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
    /**
     * Purpose  : Perform a recursive search based on document content starting in Folder folder. 
     *          : The maximum depth of recursive search to be performed starting at folder can be 
     *          : specified in the maxDepth argument. If maxDepth is < 1, it is ignored and the 
     *          : default is a search with unlimited depth. Only foldered items containing keyword will be returned.
     * @param   : dbsPo - PublicObject to be renamed
     * @param   : newName - New name to be assigned
     * @throws  : DbsException - if operation fails
     */     
    public DbsPublicObject[] searchByContent(java.lang.String keyword, DbsFolder dbsFolder,int maxDepth) throws DbsException {
        DbsPublicObject dbsPos[]=null;
        try{
            PublicObject pos[]=ifsFileSystem.searchByContent(keyword,dbsFolder.getFolder(),maxDepth);
            int poCount=pos.length;
            dbsPos=new DbsPublicObject[poCount];
            for(int index=0;index < poCount ; index ++) {
                dbsPos[index]=new DbsPublicObject(pos[index]);
            }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsPos ;
    }

    /**
     * Purpose  : Perform a recursive search based on document content starting in Folder folder. 
     *          : The maximum depth of recursive search to be performed starting at folder can be specified in the maxDepth argument. 
     *          : If maxDepth is < 1, it is ignored and the default is a search with unlimited depth. 
     *          : Only foldered items containing keyword will be returned.
     *          : Only unversioned documents' content can be searched. Versioned documents will not be handled. This will be fixed in the next release.
     * @param   : keyword - A keyword on which to search
     * @param   : folder - Folder from which to start the search; if null, the search will start from the root folder
     * @param   : maxDepth - Maximum depth for search; if < 1, unlimited depth
     * @returns : An array of PublicObject objects
     * @throws  : DbsException - if operation fails
     */     
     
    public DbsPublicObject[] searchByContent(java.lang.String[] keywords,DbsFolder dbsFolder, int maxDepth) throws DbsException {
        DbsPublicObject dbsPos[]=null;
        try{
            PublicObject pos[]=ifsFileSystem.searchByContent(keywords,dbsFolder.getFolder(),maxDepth);
            int poCount=pos.length;
            dbsPos=new DbsPublicObject[poCount];
            for(int index=0;index < poCount ; index ++) {
                dbsPos[index]=new DbsPublicObject(pos[index]);
            }
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsPos ;
    }

    /**
     * Purpose  : Perform a recursive search based on document content starting in Folder folder. 
     *          : The maximum depth of recursive search to be performed starting at folder can be specified in the maxDepth argument. 
     *          : If maxDepth is < 1, it is ignored and the default is a search with unlimited depth. 
     *          : Only foldered items containing keyword will be returned.
     *          : Only unversioned documents' content can be searched. Versioned documents will not be handled. 
     *          : This will be fixed in the next release.
     * @param   : keywords - A set of keywords on which to search
     * @param   : dbsFolder - Folder from which to start the search; if null, the search will start from the root folder
     * @param   : maxDepth - Maximum depth for search; if < 1, unlimited depth
     * @returns : An array of PublicObject objects
     * @throws  : DbsException - if operation fails
     */      
    public DbsPublicObject[] searchByName(java.lang.String pattern, DbsFolder dbsFolder, int maxDepth) throws DbsException {
        DbsPublicObject dbsPos[]=null;
        try{
            PublicObject pos[]=ifsFileSystem.searchByName(pattern,dbsFolder.getFolder(),maxDepth);
            if(pos != null){
                int poCount=pos.length;
                dbsPos=new DbsPublicObject[poCount];
                for(int index=0;index < poCount ; index ++) {
                    dbsPos[index]=new DbsPublicObject(pos[index]);
                }
            }else{
                dbsPos = null;
            }
         } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsPos ;
      }
    /**
     * Purpose  : Set an attribute for a PublicObject in iFS.
     * @param   : dbsPo - PublicObject for which to set the attribute
     * @param   : name - Name of the attribute
     * @param   : dbsvalue - Attribute value to be set
     * @throws  : DbsException - if operation fails
     */       
    public void setAttribute(DbsPublicObject dbsPo, java.lang.String name, DbsAttributeValue dbsValue) throws DbsException {
        try{
            ifsFileSystem.setAttribute(dbsPo.getPublicObject(),name,dbsValue.getAttributeValue());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Set an attribute for a PublicObject in iFS.
     * @param   : dbsPo - PublicObject for which to set the attributes
     * @param   : names - Names of the attributes
     * @param   : values - Attribute values to be set
     * @throws  : DbsException - if operation fails
     */
    public void setAttributes(DbsPublicObject dbsPo, java.lang.String[] names, DbsAttributeValue[] dbsValues) throws DbsException {
        try{
            AttributeValue attrValues[]=null;
            int attrCount=dbsValues.length;
            attrValues=new AttributeValue[attrCount];
            for(int index=0 ; index < attrCount ; index++) {
                attrValues[index]=dbsValues[index].getAttributeValue();
            }
            ifsFileSystem.setAttributes(dbsPo.getPublicObject(),names,attrValues);
         }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Set a new LibrarySession to communicate with iFS.
     *          : Please note that any objects retrieved from the previous session are no longer valid.
     * @param   : dbsSession - LibrarySession object
     * @throws  : DbsException - if operation fails
     */ 
    public void setLibrarySession(DbsLibrarySession dbsSession) throws DbsException {
        try{
            ifsFileSystem.setLibrarySession(dbsSession.getLibrarySession());
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
    /**
     * Purpose  :  Update a document in iFS. Multiple update calls to the same Document have the following behavior:
     *          : If document is versioned: 
     *          : If document is checked out by the current user: 
     *          : only one new version is created upon checkin (only the last update call before check in has any permanent effect). 
     *          : If document is checked out by another user: 
     *          : an exception is thrown. 
     *          : If document is not checked out: 
     *          : a new version is created with new content. 
     *          : If document is unversioned: 
     *          : its body is overwritten by each call.
     * @param   : dbsSession - LibrarySession object
     * @throws  : DbsException - if operation fails
     */ 

    public DbsDocument updateDocument(java.lang.String name, java.io.InputStream content, java.lang.String folderPath, DbsAccessControlList dbsAcl) throws DbsException, java.io.IOException {
        DbsDocument dbsDocument=null;
        try{
            if(dbsAcl!=null){
                dbsDocument=new DbsDocument(ifsFileSystem.updateDocument(name,content,folderPath,dbsAcl.getAccessControlList()));
            }else{
                dbsDocument=new DbsDocument(ifsFileSystem.updateDocument(name,content,folderPath,null));
            }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
        return dbsDocument;
    }

/*public LibrarySession getLibrarySession() {
  try{

     } catch(IfsException ifsError)
        {
          throw new DbsException(ifsError);
        }
  }
*/
/* Deprecated
 * public Document updateDocument(java.lang.String  name,  java.io.InputStream  content, java.lang.String  folderPath,AccessControlList  acl, boolean  doParse, ParserCallback  callback)  throws DbsException,  java.io.IOException {
  try{

     } catch(IfsException ifsError)
        {
          throw new DbsException(ifsError);
        }
  }
*/
/*  Deprecated
 * public Document putDocument(java.lang.String  name, java.io.InputStream  content,java.lang.String  folderPath, AccessControlList  acl,  boolean  doParse, ParserCallback  callback)  throws DbsException, java.io.IOException {
  try{

     } catch(IfsException ifsError)
        {
          throw new DbsException(ifsError);
        }
  }
 */        

/* Deprecated
 * public java.io.InputStream renderAsStream(PublicObject  po,
                                          java.lang.String  rendererType,
                                          java.lang.String  rendererName)
                                   throws DbsException
 */

     /**
     * Purpose  : Create a Document in iFS. If a document already exists with the same name as name, an exception is thrown.
     *          : If doParse is true and the object created from parsing the input stream is a Document or a subclass, 
     *          : then it will be returned. If the object created from parsing the input stream is not a Document or a subclass, 
     *          : null is returned (but the object is still created).
     * @param   : name - Name of the document to be created
     * @param   : content - InputStream to read contents from; if null, empty document is created
     * @param   : folderPath - Absolute path of folder in which to create the document
     * @param   : dbsacl - ACL to be assigned to the new document; if null, sess user's default ACL will be used
     * @param   : doParse - If true, input stream will be parsed using a parser which is determined based on the file extension
     * @param   : dbscallback - Parser callback to be specified if doParse is true
     * @returns : Document (the new Document created)
     * @throws  : DbsException - if operation fails
     */
   /*
    * Deprecated
    * public DbsDocument createDocument(java.lang.String name,  java.io.InputStream content,
                                      java.lang.String folderPath, DbsAccessControlList dbsAcl, 
                                      boolean doParse, DbsParserCallback dbsCallback)
                                      throws DbsException,  java.io.IOException {
        DbsDocument dbsDocument=null;
        try{
            dbsDocument = new DbsDocument(ifsFileSystem.createDocument(name,content,folderPath,dbsAcl.getAccessControlList(),doParse,dbsCallback.getParserCallback()));
        } catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDocument ;
    }
*/
  
}
