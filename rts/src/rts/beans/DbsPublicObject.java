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
 * $Id: DbsPublicObject.java,v 1.4 2005/10/20 09:52:59 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**

 *	Purpose: The encapsulate the functionality of PublicObject class provided by CMSDK API.

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



public class DbsPublicObject extends DbsLibraryObject{



    /**A textual description of this object.*/

    public static final java.lang.String DESCRIPTION_ATTRIBUTE = PublicObject.DESCRIPTION_ATTRIBUTE;



    /**This class name for this class. Useful for methods that take a class name argument.*/

    public static final java.lang.String CLASS_NAME=PublicObject.CLASS_NAME;



    /**The name of this object.*/

    public static final java.lang.String NAME_ATTRIBUTE=PublicObject.NAME_ATTRIBUTE; 



    /**The DirectoryUser that owns this object.*/

    public static final java.lang.String OWNER_ATTRIBUTE=PublicObject.OWNER_ATTRIBUTE;



    /**The AccessControlList that secures this object.*/

    public static final java.lang.String ACL_ATTRIBUTE=PublicObject.ACL_ATTRIBUTE; 



    /**The Family to which this object belongs if this object is versioned.*/

    public static final java.lang.String FAMILY_ATTRIBUTE=PublicObject.FAMILY_ATTRIBUTE; 



    /**The PublicObject to which this object resolves. Families, VersionSeries, and VersionDescriptions resolve to a specific PublicObject which represents the 'desired version' (e.g. the latest version).*/

    public static final java.lang.String RESOLVEDPUBLICOBJECT_ATTRIBUTE=PublicObject.RESOLVEDPUBLICOBJECT_ATTRIBUTE;



    /**The date and time (in GMT) when this object was created.*/

    public static final java.lang.String CREATEDATE_ATTRIBUTE=PublicObject.CREATEDATE_ATTRIBUTE;



    /**The DirectoryUser that created this object.*/

    public static final java.lang.String CREATOR_ATTRIBUTE=PublicObject.CREATOR_ATTRIBUTE;



    /**The date and time (in GMT) when this object was last modified.*/

    public static final java.lang.String LASTMODIFYDATE_ATTRIBUTE=PublicObject.LASTMODIFYDATE_ATTRIBUTE;



    /**The DirectoryUser that last modified this object.*/

    public static final java.lang.String LASTMODIFIER_ATTRIBUTE=PublicObject.LASTMODIFIER_ATTRIBUTE;


    /**The Lock Object used for this object.*/
    
    public static final String LOCKOBJECT_ATTRIBUTE=PublicObject.LOCKOBJECT_ATTRIBUTE;
    
    
    /**The DirectoryUser that deleted (not 'Freed') this object. Deletor will be set to null if this object is later undeleted.*/

    public static final java.lang.String DELETOR_ATTRIBUTE=PublicObject.DELETOR_ATTRIBUTE;



    /**The policies that apply specifically to this object.*/

    public static final java.lang.String POLICYBUNDLE_ATTRIBUTE=PublicObject.POLICYBUNDLE_ATTRIBUTE;



    /**The PropertyBundle associated with this object.*/

    public static final java.lang.String PROPERTYBUNDLE_ATTRIBUTE=PublicObject.PROPERTYBUNDLE_ATTRIBUTE;



    /**The AdministrationGroup to which this object belongs. The AdministrationGroup may be set to null.*/

    public static final java.lang.String ADMINISTRATIONGROUP_ATTRIBUTE=PublicObject.ADMINISTRATIONGROUP_ATTRIBUTE;



    /**A PublicObject from which this object should defer security decisions (e.g, all versions in a Family may have the Family as their securing public object).*/

    public static final java.lang.String SECURINGPUBLICOBJECT_ATTRIBUTE=PublicObject.SECURINGPUBLICOBJECT_ATTRIBUTE;



    protected PublicObject publicObject = null;  // to accept object of type PublicObject



    protected DbsPublicObject(){ }

    

    /**

	   * Purpose : To create DbsPublicObject using PublicObject class

	   * @param  : publicObject - An PublicObject Object  

	   */    

    public DbsPublicObject(PublicObject publicObject) {

        super(publicObject);

        this.publicObject = publicObject;

    }



    /**

	   * Purpose  : Returns the user that owns the object. The owner automatically has full access to this object.

	   * @returns : the DirectoryUser that owns this object, or null if this object is not owned.

     * @throws  : DbsException - if operation fails

	   */

    public DbsDirectoryUser getOwner()throws DbsException{

        DbsDirectoryUser owner=null;

        try{

            owner=new DbsDirectoryUser(this.publicObject.getOwner());

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

          }

        return owner;

    }



    /**

	   * Purpose  : Update description of this object

	   * @returns : void

       * @throws  : DbsException - if operation fails

	   */

    public void setDescription(String description)throws DbsException{

        try{

            publicObject.setDescription(description);

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



    /**

	   * Purpose  : Returns this object's AccessControlList. Returns null if no AccessControlList was specified.

	   * @returns : AccessControlList for the object.

     * @throws  : DbsException - if operation fails

	   */

    public DbsAccessControlList getAcl()throws DbsException{

        DbsAccessControlList acl=null;

        try{

            if(this.publicObject.getAcl()!=null){

                acl=new DbsAccessControlList(this.publicObject.getAcl());

            }

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

        return acl;

    }



    /**

     * Purpose  : Sets the AccessControlList for this object.

     * @returns : dbsAcl - the AccessControlList setting.

     * @throws  : DbsException - if operation fails

     */

    public void setAcl(DbsAccessControlList dbsAcl)throws DbsException{

        try{

            this.publicObject.setAcl(dbsAcl.getAccessControlList());

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



//Added By Rajan on 21/02/2004



    /**

     * Purpose  : Sets this object's PropertyBundle.

     * @param   : dbsPropertyBundle - the Properties setting.

     * @throws  : DbsException - if operation fails

     */

    public void setPropertyBundle(DbsPropertyBundle dbsPropertyBundle) throws DbsException {

        try{

            this.publicObject.setPropertyBundle(dbsPropertyBundle.getDbsPropertyBundle());

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }        

    }

    public void setSecuringPublicObject(DbsPublicObject dbsPO) throws DbsException {
      
      try{
        PublicObject po =  dbsPO.getPublicObject();
        if(po instanceof Family){
        this.publicObject.setSecuringPublicObject((Family)po);
        }else{
          this.publicObject.setSecuringPublicObject(dbsPO.getPublicObject());
        }
        
      }catch( IfsException ifsError){
        
        throw new DbsException(ifsError);
        
      }
      
      
    }
    /**

     * Purpose  : Used to get the object of class PublicObject

     * @returns : PublicObject Object

     */

    public PublicObject getPublicObject() {

        return this.publicObject ;

    }



    /**

	   * Purpose  : Gets any Folder path to the target object, starting from the Root Folder.

	   * @returns : String

     * @throws  : DbsException - if operation fails

	   */

    public String getAnyFolderPath() throws DbsException {

        String anyFolderPath;

        try{    

            anyFolderPath = this.publicObject.getAnyFolderPath() ;

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

        return anyFolderPath;    

    }



//Added By Rajan on 02/02/2004



    /**

	   * Purpose  : Returns the date this object was last modified.

	   * @returns : Date of modification

     * @throws  : DbsException - if operation fails

	   */

    public java.util.Date getLastModifyDate()throws DbsException{

        try{

            return this.publicObject.getLastModifyDate();

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



//Added By Rajan on 02/02/2004

    /**

	   * Purpose  : Returns the user that last modified this object.

	   * @returns : the last modifier

     * @throws  : DbsException - if operation fails

	   */

    public DbsDirectoryUser getLastModifier() throws DbsException {

        DbsDirectoryUser lastModifier=null;

        try{

            lastModifier=new DbsDirectoryUser(this.publicObject.getLastModifier());        

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

        return lastModifier;

    }


//Added By Rajan on 02/02/2004    

    /**

	   * Purpose  : returns the user that created this object.

	   * @returns : the creator

     * @throws  : DbsException - if operation fails

	   */

    public DbsDirectoryUser getCreator() throws DbsException {

        DbsDirectoryUser creator=null;

        try{

            creator=new DbsDirectoryUser(this.publicObject.getCreator());

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

        return creator;

    }



//Added By Rajan on 02/02/2004

    /**

	   * Purpose  : Returns the description of this object

	   * @returns : The object description

     * @throws  : DbsException - if operation fails

	   */

    public java.lang.String getDescription() throws DbsException {

        try{

            return this.publicObject.getDescription();

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



//Added By Rajan on 02/02/2004

    /**

	   * Purpose  : returns true if this object is versioned

	   * @returns : true if this object is versioned

     * @throws  : DbsException - if operation fails

	   */

    public boolean isVersioned() throws DbsException {

        try{

            return this.publicObject.isVersioned();

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



//Added By Rajan on 02/02/2004

    /**

	   * Purpose  : Returns the date this object was created.

	   * @returns : Date of creation

     * @throws  : DbsException - if operation fails

	   */

    public java.util.Date getCreateDate()throws DbsException{

        try{

            return this.publicObject.getCreateDate();

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



//Added By Rajan on 02/02/2004

    /**

	   * Purpose  : Returns this object's PropertyBundle. Returns null if no PropertyBundle was specified.

	   * @returns : PropertyBundle for the object.

     * @throws  : DbsException - if operation fails

	   */

    public DbsPropertyBundle getPropertyBundle()throws DbsException {

        DbsPropertyBundle dbsProps=null;   

        try{ 

            if(this.publicObject.getPropertyBundle()!=null){

                dbsProps=new DbsPropertyBundle(this.publicObject.getPropertyBundle());

            }

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

        return dbsProps;

    }







    /**

	   * Purpose    : Returns the PublicObject to which this object resolves. 

     *              For the non version classes (for example, Document and Folder) this simply returns itself (this). 

     *              For the version classes, (Family, VersionSeries, and VersionDescription), 

     *              this will return a non version-class object as determined by the configuration of the instance of the version class.

     *              This override exists to return the Pending PublicObject value of the primary VersionSeries if the caller is the 

     *              Reservor of that VersionSeries; in all other cases, it defers to the implementation in the superclass (PublicObject). 

     * @overrides : getResolvedPublicObject in class PublicObject           

	   * @returns   : PublicObject to which this object resolves

     * @throws    : DbsException - if operation fails.

	   */

    public DbsPublicObject getResolvedPublicObject() throws DbsException{

        DbsPublicObject dbsPublicObject;

        PublicObject publicObjectTemp;

        try{

            publicObjectTemp = publicObject.getResolvedPublicObject();

            if(publicObjectTemp instanceof Document){

                dbsPublicObject = new DbsDocument((Document)publicObjectTemp);

            }else if(publicObjectTemp instanceof Folder){

                dbsPublicObject = new DbsFolder((Folder)publicObjectTemp);

            }else{

                dbsPublicObject = new DbsPublicObject(publicObjectTemp);

            }

        }catch(IfsException iex){

            throw new DbsException(iex);

        }

        return dbsPublicObject;

    }



    /**

	   * Purpose : Adds a Property to this object's PropertyBundle. Creates a new PropertBundle if one does not already exist.

	   * @param  : name - the name of the new property

	   * @param  : dbsAttrValue - the name/value of the property to add

     * @throws : DbsException - if operation fails

	   */

    public void putProperty(String name,DbsAttributeValue dbsAttrValue)throws DbsException{

        try{    

            this.publicObject.putProperty(name,dbsAttrValue.getAttributeValue());

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }





          



    /**

	   * Purpose : Removes a Property from this object's PropertyBundle.

	   * @param  : name - the name of the new property

     * @throws : DbsException - if operation fails

	   */

    public void removeProperty(java.lang.String name) throws DbsException{

        try{    

            this.publicObject.removeProperty(name);

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }

          

    /**

     * Purpose  : Returns the Folder with which this object is associated.

     * @returns : Folder with which this Object is associated.

     */

    public DbsFolder getFolderObject(){

        return new DbsFolder((Folder)publicObject);

    }



    /**

     * Purpose  : Returns the Document with which this object is associated.

     * @returns : Document with which this Object is associated.

     */

    public DbsDocument getDocumentObject(){

        return new DbsDocument((Document)publicObject);

    }



    /**

     * Purpose  : Returns the Family with which this object is associated. A PublicObject has a Family when (and only when) 

     *            it is versioned. Most PublicObjects can be associated with a Family, call isVersionable to be sure.

     * @returns : Family with which this Object is associated.

     */

/*    public DbsFamily getFamilyObject(){

        return new DbsFamily((Family)publicObject);

    }

*/

    /**

     * Purpose  : Returns the Folder Object pointed to by path.

     * @param   : path - Path to the FolderObject

     * @returns : Folder Object

     * @throws  : DbsException - if operation fails

     */

    public DbsFolder[] getFolderReferences() throws DbsException {

        Folder[] folders;

        DbsFolder[] dbsFolders;

        try{

            folders = publicObject.getFolderReferences();

            dbsFolders = new DbsFolder[folders.length];

            for(int counter = 0; counter < folders.length; counter++){

                dbsFolders[counter] = new DbsFolder(folders[counter]);

            }

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

        return dbsFolders;

    }



    /**

	   * Purpose  : Returns folder that reference this PublicObject.

	   * @param   : the index.

	   * @returns : Folder that reference this object.

     * @throws  : DbsException - if operation fails.

	   */

    

    public DbsFolder getFolderReferences(int index) throws DbsException {

        try{

            return new DbsFolder(publicObject.getFolderReferences(index));   

        } catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }   



    public void setOwnerByName(String owner) throws DbsException {

        try{

            this.publicObject.setOwnerByName(owner);

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



    /**

	   * Purpose  : returns the Class Name of this public object.

	   * @returns : the classname.

     * @throws  : DbsException - if operation fails.

	   */

    public String getClassname() throws DbsException {

        String classname;

        try{

            classname = this.publicObject.getClassname();

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

        return classname;

    }



//Added By Jeet on 02/02/2004    

    /**

	   * Purpose  : returns the Version Number of this public object.

	   * @returns : the version number

     * @throws  : DbsException - if operation fails

	   */

    public long getVersionNumber() throws DbsException {

        long  versionNumber;

        try{

            versionNumber = publicObject.getVersionNumber();

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

        return versionNumber;

    }



    /**

	   * Purpose  : Returns true when the lockobject attribute on this PublicObject is not null

	   * @returns : true if the object is locked. This returns true when the lockobject attribute is not null.

     * @throws  : DbsException - if operation fails

	   */

    public boolean isLocked() throws DbsException{

        try{

            return this.publicObject.isLocked();

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



    /**

	   * Purpose  : Returns the Family with which this object is associated. A PublicObject has a Family when (and only when) it is versioned. 

     *            Most PublicObjects can be associated with a Family, call isVersionable to be sure.

	   * @returns : Family with which this Object is associated.

     * @throws  : DbsException - if operation fails

	   */

    public DbsFamily getFamily() throws DbsException{

        try{

            return new DbsFamily(this.publicObject.getFamily());

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }    

    }

    /**
	   * Purpose  : to create copy of the public object
	   * @returns : copy of the public object
     * @throws  : DbsException - if operation fails
	   */

    public DbsPublicObject copy(DbsLibraryObjectDefinition def) throws DbsException{
        try{
            if(def == null){
              return new DbsPublicObject(this.publicObject.copy(null));
            }else{
              return new DbsPublicObject(this.publicObject.copy(def.getLibraryObjectDefinition()));              
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }    
    }
    
    

     public void lock(DbsLockObjectDefinition ldef)throws DbsException{
       try{            
              this.publicObject.lock(ldef.getLockObjectDefinition());              
           
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }  
    }
    
    public void updateLock(DbsLockObjectDefinition ldef)throws DbsException{
       try{            
              this.publicObject.updateLock(ldef.getLockObjectDefinition());              
           
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }  
    }

  public DbsPublicObject[] getLeftwardRelationshipObjects(DbsClassObject relClass) throws DbsException{
    DbsPublicObject [] dbsPOToReturn = null;
    PublicObject [] poToReturn = null;
    try{
      poToReturn = this.publicObject.getLeftwardRelationshipObjects(relClass.getClassObject());
      dbsPOToReturn = new DbsPublicObject[poToReturn.length];
      for( int index = 0; index < poToReturn.length; index++ ){
        dbsPOToReturn[index] = new DbsPublicObject(poToReturn[index]);
      }
    }catch( IfsException e ){
      throw new DbsException(e);
    }
    return dbsPOToReturn;
  }

  public DbsPublicObject[] getLeftwardRelationshipObjects(String relClass) throws DbsException{
    DbsPublicObject [] dbsPOToReturn = null;
    PublicObject [] poToReturn = null;
    try{
      poToReturn = this.publicObject.getLeftwardRelationshipObjects(relClass);
      dbsPOToReturn = new DbsPublicObject[poToReturn.length];
      for( int index = 0; index < poToReturn.length; index++ ){
        dbsPOToReturn[index] = new DbsPublicObject(poToReturn[index]);
      }
    }catch( IfsException e ){
      throw new DbsException(e);
    }
    return dbsPOToReturn;
  }

  public DbsRelationship[] getLeftwardRelationships(DbsClassObject relClass) throws DbsException{
    DbsRelationship [] dbsRelToReturn = null;
    Relationship [] relToReturn = null;
    try{
      relToReturn = this.publicObject.getLeftwardRelationships(relClass.getClassObject());
      dbsRelToReturn = new DbsRelationship[relToReturn.length];
      for( int index = 0; index < relToReturn.length; index++ ){
        dbsRelToReturn[index] = new DbsRelationship(relToReturn[index]);
      }
    }catch( IfsException e ){
      throw new DbsException(e);
    }
    return dbsRelToReturn;
  }
  
  public DbsRelationship[] getLeftwardRelationships(String relClass) throws DbsException{
    DbsRelationship [] dbsRelToReturn = null;
    Relationship [] relToReturn = null;
    try{
      relToReturn = this.publicObject.getLeftwardRelationships(relClass);
      if( relToReturn!= null ){
        dbsRelToReturn = new DbsRelationship[relToReturn.length];
        for( int index = 0; index < relToReturn.length; index++ ){
          dbsRelToReturn[index] = new DbsRelationship(relToReturn[index]);
        }
      }
    }catch( IfsException e ){
      throw new DbsException(e);
    }
    return dbsRelToReturn;
  }
  
}
