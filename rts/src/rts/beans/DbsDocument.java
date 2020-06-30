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

 * $Id: DbsDocument.java,v 1.3 2005/06/24 05:57:20 suved Exp $

 *****************************************************************************

 */

package rts.beans; 



/**

 *	Purpose: The encapsulate the functionality of Document class provided by CMSDK API.

 * 

 *  @author              Mishra Maneesh

 *  @version             1.0

 * 	Date of creation:   23-12-2003

 * 	Last Modfied by :     

 *	Last Modfied Date:    

 */



/*CMSDK API*/

import oracle.ifs.beans.* ;

import oracle.ifs.common.* ;

import oracle.ifs.common.IfsException;

import java.io.*;



public class DbsDocument extends DbsPublicObject {



    private Document document=null;  // to accept object of type Document



    /**This class name for this class.*/

    public static java.lang.String CLASS_NAME = Document.CLASS_NAME ;

    /**The ContentObject that contains this document's content.*/

    public static final java.lang.String CONTENTOBJECT_ATTRIBUTE = Document.CONTENTOBJECT_ATTRIBUTE;

    /**The ContentQuota being charged for this document's content.*/

    public static final java.lang.String CONTENTQUOTA_ATTRIBUTE = Document.CONTENTQUOTA_ATTRIBUTE;



    public static final String ENCRYPTED = "ENCRYPTED";

    public static final String ENCRYPTED_BY = "ENCRYPTED_BY";

    public static final String AUDIT_INFO = "AUDIT_INFO";

    public static final String WORKFLOW_STATUS = "WORKFLOW_STATUS";

    /**

	   * Purpose : To create DbsDocument using Document class

	   * @param  : document - An Document Object  

	   */

    public DbsDocument(Document document) {

        super(document);

        this.document=document;

    }

    /**

	   * Purpose : Used to get the object of class Document

	   * @return : Document Object

	   */

    public Document getDocument() {

        return this.document;

    }



    /**

	   * Purpose : Gets the size of this document's content. 

     *           It returns 0 (zero) if the content is zero length or if this document has a null ContentObject

	   * @return : the size of the underlying content.

     * @throws  : DbsException - if operation fails

	   */

    public long getContentSize() throws DbsException {

        try {

              return this.document.getContentSize();

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



    /**

	   * Purpose : Gets the format for this Document.

	   * @return : the Format object.

     * @throws  : DbsException - if operation fails

	   */

    public DbsFormat getFormat() throws DbsException {

        try{

            if(document.getFormat() == null){

                return null;

            }else{

                return new DbsFormat(document.getFormat());

            }

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }



    /**

	   * Purpose  : to get inputstream of a document

	   * @returns : the inputstream

     * @throws  : DbsException - if operation fails

	   */

    public InputStream getContentStream() throws DbsException {

        InputStream inputStream = null;

        try{

            inputStream = document.getContentStream();

          
       }catch(IfsException iex){

            throw new DbsException(iex);

        }

        return inputStream;

    }



    /**

	 * Purpose  : Generates a HTML or plaintext version of the document, via the Context INSO filters.

     * @throws  : DbsException - if operation fails

	 */

    public  void filterContent(boolean plaintext) throws DbsException{

        try{

            document.filterContent(plaintext);

        }catch(IfsException iex){

            throw new DbsException(iex);

        }

    }





     

    /**

	 * Purpose  : Generates a HTML or plaintext version of the document, via the Context INSO filters.

     * @throws  : DbsException - if operation fails

	 */

    public   Reader getFilteredContent() throws DbsException{

        Reader reader;

        try{

            reader = document.getFilteredContent();

        }catch(IfsException iex){

            throw new DbsException(iex);

        }

        return reader;

    }





     /* Purpose : Sets the format for this Document.	  

     * @throws  : DbsException - if operation fails

	   */

    public void setFormat(DbsFormat dbsFormat) throws DbsException {

        try{

            document.setFormat(dbsFormat.getFormat());               

        }catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

    }

    public void setSecuringPublicObject(DbsPublicObject dbsPo) throws DbsException{
      
        try{
          
          this.document.setSecuringPublicObject(dbsPo.getPublicObject());
          
        }catch( IfsException ifsError){
          
          throw new DbsException(ifsError);
          
        }
      
    }


     

}

