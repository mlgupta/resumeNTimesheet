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
 * $Id: DbsContentObject.java,v 1.1.1.1 2005/05/26 12:31:42 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of ContentObject class provided by CMSDK API.
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    05-03-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
public class DbsContentObject extends DbsSystemObject {

    private ContentObject contentObject=null; //to accept object of type ContentObject

    /**This class name for this class. Useful for methods that take a class name argument.*/    
    public static final java.lang.String CLASS_NAME=ContentObject.CLASS_NAME;                         
    /**The Format that describes the content.*/
    public static final java.lang.String FORMAT_ATTRIBUTE=ContentObject.FORMAT_ATTRIBUTE;             
    /**The Media instance where the content is stored.*/
    public static final java.lang.String MEDIA_ATTRIBUTEE=ContentObject.MEDIA_ATTRIBUTE;              
    /**The row id pointing to the content for this ContentObject in Media's database table.*/
    public static final java.lang.String CONTENT_ATTRIBUTE=ContentObject.CONTENT_ATTRIBUTE;           
    /**The size of the content.*/
    public static final java.lang.String CONTENTSIZE_ATTRIBUTE=ContentObject.CONTENTSIZE_ATTRIBUTE;   
    /**The Java name of the character encoding of the content (if applicable).*/
    public static final java.lang.String CHARACTERSET_ATTRIBUTE=ContentObject.CHARACTERSET_ATTRIBUTE; 
    /**The Oracle name of the language of the content (if applicable).*/
    public static final java.lang.String LANGUAGE_ATTRIBUTE=ContentObject.LANGUAGE_ATTRIBUTE;         
    /**An indicator if the content is read-only or updateable.*/
    public static final java.lang.String READONLY_ATTRIBUTE=ContentObject.READONLY_ATTRIBUTE;         
 
    /**
	   * Purpose : To create DbsSearchQualification using SearchQualification class
	   * @param  : searchQualification - An SearchQualification Object  
	   */    
    public DbsContentObject(ContentObject contentObject) {
        super(contentObject);
        this.contentObject = contentObject;
    }

    /**
	   * Purpose   : Gets the Format object.
	   * @returns  : the Format object.
     * @throws   : DbsException - if operation fails
	   */
    public Format getFormat() throws DbsException{
        try{
            return this.contentObject.getFormat();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose   : Gets the size of the content.
	   * @returns  : the size of the underlying content.        
     * @throws   : DbsException - if operation fails
	   */    
    public long getContentSize() throws DbsException {
        try{
            return this.contentObject.getContentSize();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }    
    }

    /**
	   * Purpose   : Gets the Character set in which this content is encoded, or null if charset not specified.
	   * @returns  : the Character set.
     * @throws   : DbsException - if operation fails
	   */
    public java.lang.String getCharacterSet() throws DbsException{
        try{
            return this.contentObject.getCharacterSet();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose   : Gets the language of this content, or null if language not specified.
	   * @returns  : the language.
     * @throws   : DbsException - if operation fails
	   */
    public java.lang.String getLanguage() throws DbsException {
        try{
            return this.contentObject.getLanguage();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose   : Returns true if the content is read-only. The value is set when the content object 
     *             is created (you can specify it in the ContentObjectDefinition). The value is also 
     *             true if the content object is shared between two objects (Documents). 
     *             Once set, only an admin user can change it, however, a new content object can be 
     *             cloned from this one - the clone does not have to be read only.
	   * @returns  : true if the content is read-only.
     * @throws   : DbsException - if operation fails
	   */
    public boolean isReadOnly() throws DbsException{
        try{
            return this.contentObject.isReadOnly();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose   : Sets the read-only state of the content. Only an admin user can change the read-only state of this object.
	   * @param    : readonly - true if content is readonly, false otherwise
     * @throws   : DbsException - if operation fails
	   */
    public void setReadOnly(boolean readonly)throws DbsException{
        try{
            this.contentObject.setReadOnly(readonly);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose   : Gets the content as an InputStream.
	   * @param    : refObj - the LibraryObject which refers to this ContentObject and can verify access.
	   * @returns  : an InputStream of the document contents
     * @throws   : DbsException - if operation fails
	   */
    public java.io.InputStream getContentStream(LibraryObject refObj) throws DbsException{
        try{
            return this.contentObject.getContentStream(refObj);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose   : Gets the content as an Reader. For most media, this method only works if the content object has an associated CharacterSet.
	   * @param    : refObj - the LibraryObject which refers to this ContentObject and can verify access.
	   * @returns  : an InputStream of the document contents
     * @throws   : DbsException - if operation fails
	   */
    public java.io.Reader getContentReader(DbsLibraryObject dbsLibraryObject) throws DbsException{
        try{
            return this.contentObject.getContentReader(dbsLibraryObject.getLibraryObject());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose   : Generates the Themes for this content by submitting a request to InterMedia Text to generate Themes.
     *             Each Theme may either be a single Theme word/phrase or a hierarchical list of parent Themes. 
     *             If fullThemes is set to TRUE, every Theme will also have the full hierarchy of its parent Themes generated.
	   * @param    : fullThemes - generates theme hierarchy information if TRUE
	   * @param    : dbsLibraryObject - the LibraryObject which refers to this ContentObject and can verify access.
     * @throws   : DbsException - if operation fails
	   */    
    public void generateThemes(boolean fullThemes,DbsLibraryObject dbsLibraryObject) throws DbsException{
        try{
            this.contentObject.generateThemes(fullThemes, dbsLibraryObject.getLibraryObject());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose   : Gets the Themes for this content, previously generated by a call to generateThemes().
     *             Content may have up to fifty Themes. Each Theme may either be a single Theme word/phrase or 
     *             a string of parent Themes, separated by colons (':').
     *             There is a Weight associated with every Theme. A Weight is a numerical value that measures 
     *             the importance of the Theme relative to other Themes for the content.
	   * @param    : dbsLibraryObject - the LibraryObject which refers to this ContentObject and can verify access (typically a Document).
	   * @returns  : an array of objects encapsulating a InterMedia Theme and its associated Weight
     * @throws   : DbsException - if operation fails
	   */
//    public ContextTheme[] getThemes(DbsLibraryObject dbsLibraryObject) throws DbsException{}


    /**
	   * Purpose   : Gets a specific summary of this content, previously generated by a call to generateSummary().
	   * @param    : pointOfView - get the summary that is based on this particular point of view
	   * @param    : dbsLibraryObject - the LibraryObject which refers to this ContentObject and can verify access.
	   * @returns  : a Reader for fetching the summary
     * @throws   : DbsException - if operation fails
	   */
    public java.io.Reader getSummary(java.lang.String pointOfView,DbsLibraryObject dbsLibraryObject) throws DbsException{
        try{
            return this.contentObject.getSummary(pointOfView, dbsLibraryObject.getLibraryObject());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose   : Generates an HTML or plaintext version of the content, via the InterMedia INSO filters. 
     *             The format of the content to be filtered must be supported by the InterMedia filters.
	   * @param    : plaintext - when TRUE, indicates that a plaintext version of the document must be generated, 
     *             otherwise an HTML version will be generated
	   * @param    : dbsLibraryObject - the LibraryObject which refers to this ContentObject and can verify access.
	   * @returns  : a Reader for fetching the summary
     * @throws   : DbsException - if operation fails
	   */
    public void filterContent(boolean plaintext, DbsLibraryObject dbsLibraryObject) throws DbsException {
        try{
            this.contentObject.filterContent(plaintext, dbsLibraryObject.getLibraryObject());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose   : Gets the plaintext or HTML content of the document, previously generated by a call to filterContent()
	   * @param    : dbsLibraryObject - the LibraryObject which refers to this ContentObject and can verify access.
	   * @returns  : a Reader for fetching the plaintext or HTML content    
	   */
    public java.io.Reader getFilteredContent(DbsLibraryObject dbsLibraryObject) throws DbsException{
        try{
            return this.contentObject.getFilteredContent(dbsLibraryObject.getLibraryObject());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }       
    }
       
    /**
	   * Purpose  : Used to get the object of class SearchQualification
	   * @returns : SearchQualification Object
	   */
    public ContentObject getContentObject() {
        return this.contentObject;
    }   
}
