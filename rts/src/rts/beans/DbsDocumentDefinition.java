package rts.beans; 

/*CMSDK API*/

import oracle.ifs.beans.* ;
import oracle.ifs.common.* ;
import oracle.ifs.common.IfsException;
import java.io.*;

public class DbsDocumentDefinition extends DbsPublicObjectDefinition {
  private DocumentDefinition documentDefinition=null;  // to accept object of type Document
  
  /**
   * Purpose : To create DbsDocumentDefinition
   * @param  : dbsLibrarySession  
   */
  public DbsDocumentDefinition(DbsLibrarySession dbsLibrarySession) throws DbsException {
    super(dbsLibrarySession);
    try{
      this.documentDefinition= new DocumentDefinition(dbsLibrarySession.getLibrarySession());
    }catch(IfsException e){
      throw new DbsException(e);
    }
  }
  
  /**
  * Purpose : Sets the Content for this DocumentDefinition.	  
  * @throws rts.beans.DbsException
  * @param  string
  */
  public void setContent(java.lang.String content)throws DbsException{
    try{
      this.documentDefinition.setContent(content);
    }catch(IfsException ifsException) {
      throw new DbsException(ifsException);
    }    
  }
  /**
   * Purpose : Used to get the object of class DocumentDefinition
   * @return : Document Object
   */
  public DocumentDefinition getDocumentDefinition() {
    return this.documentDefinition;
  }
  
       
  /**
  * Purpose : Sets the Content for this DocumentDefinition.	  
  * @throws rts.beans.DbsException
  * @param contentStream
  */
  public void setContentStream(java.io.InputStream contentStream) throws DbsException {
    try{
      this.documentDefinition.setContentStream(contentStream);
    }catch(IfsException ifsException) {
      throw new DbsException(ifsException);
    }finally{
      if( contentStream != null ){
        try{
          contentStream.close();
        }catch (IOException ioEx) {
         System.err.println(ioEx.toString()); 
        }
        contentStream = null;
      }
    }
  }
  
  /**
  * Purpose : Sets the ContentObjectDefinition of the document that will be used to create/update 
  * the content object associated with this document.
  * @throws rts.beans.DbsException
  * @param contentStream
  */
  public void setContentObjectDefinition(ContentObjectDefinition contentObjectDefinition) throws DbsException {
    try{
      documentDefinition.setContentObjectDefinition(contentObjectDefinition);
    }catch(IfsException ifsException) {
      throw new DbsException(ifsException);
    }
  }
  
  /**
  * Purpose : Sets the ClassObject of the documentdefinition .
  * @throws rts.beans.DbsException
  * @param dbsClassObject
  */
   public void setClassObject(DbsClassObject dbsClassObject)throws DbsException{
      try{
        this.documentDefinition.setClassObject(dbsClassObject.getClassObject());
      }catch(IfsException ifsError) {
        throw new DbsException(ifsError);
      }      
  }
  
  /**
  * Purpose : Sets the Format of the document .
  * @throws rts.beans.DbsException
  * @param dbsFormat
  */
  public void setFormat(DbsFormat dbsFormat) throws DbsException{
      try{
        this.documentDefinition.setFormat(dbsFormat.getFormat());
      }catch( IfsException ifsError ){
        throw new DbsException(ifsError);
      }
  }
   /**
  * Purpose : Sets the attribute of the documentdefinition .
  * @throws rts.beans.DbsException
  * @param dbsAttrValue
  * @param name
  */
  public void setAttribute(String name, DbsAttributeValue dbsAttrValue)throws DbsException{
      try{
        this.documentDefinition.setAttribute(name,dbsAttrValue.getAttributeValue());
      }catch(IfsException ifsError) {
        throw new DbsException(ifsError);
      }
  }
}