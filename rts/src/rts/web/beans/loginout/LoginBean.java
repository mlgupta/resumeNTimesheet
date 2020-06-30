package rts.web.beans.loginout;
import java.io.InputStream;
import java.io.StringWriter;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rts.beans.DbsCleartextCredential;
import rts.beans.DbsComparator;
import rts.beans.DbsDirectoryGroup;
import rts.beans.DbsDirectoryUser;
import rts.beans.DbsDocument;
import rts.beans.DbsException;
import rts.beans.DbsFileSystem;
import rts.beans.DbsFolder;
import rts.beans.DbsLibraryService;
import rts.beans.DbsLibrarySession;
import rts.beans.DbsPrimaryUserProfile;
import rts.beans.DbsPublicObject;


public class LoginBean  {
  /* member variables declaration goes here */
  public DbsLibraryService dbsService;
  public DbsLibrarySession dbsSession;
  private DbsCleartextCredential dbsCtC;
  public DbsDirectoryUser dbsUser;
  private DbsPrimaryUserProfile userProfile;
  private DbsFolder userHomeFolder;
  private DbsFolder resumeFolder;
  public DbsFileSystem dbsFs;
  public String userCompanyName;
  private String serviceName;
  private String schemaPassword;
  private String serviceConfiguration;
  private String domain;
  private String userName;
  private String userPassword;
  public String resumeFolderPath;
  public static final String RESUME_FOLDER_NAME = "resumes";
  //public String localFSPath = "/home/ias/";
  //public final static int BUFFER_SIZE = 9*1024;

  public LoginBean() {
  }

  /**
   * Constructs a new LoginBean object with the specified userName
   * and userPassword.
   * @param userName.
   * @param userPassword.
   */
  public LoginBean(String userName, String userPassword ) {
    if( (userName!= null || userName.trim().length()!=0) && 
        (userPassword!= null || userPassword.trim().length()!=0 ) ){
      this.userName = new String(userName);
      this.userPassword = new String(userPassword);
    }else{
      System.err.println("UserName or Password Not Correctly Specified.Terminating...");
      System.exit(1);
    }
  }

  /**
   * set the service parameters necessary for starting library service.
   * @param String serviceName
   * @param String schemaPassword
   * @param String serviceConfiguration
   * @param String domain
   */
  public void setServiceParams(String serviceName, String schemaPassword, 
          String serviceConfiguration, String domain) throws Exception{
    try{
      this.serviceName = serviceName;
      this.serviceConfiguration = serviceConfiguration;
      this.schemaPassword = schemaPassword;
      this.domain = domain;
    }catch(Exception exc){
      throw exc;
    }
  }

  /**
   * start the library service.
   * @param none
   */
  public void startDbsService() throws DbsException{
    try{
      if(DbsLibraryService.isServiceStarted(serviceName)){                   
        this.dbsService = DbsLibraryService.findService(serviceName);
        System.out.println("Library Service Found");
      }else{              
        this.dbsService = DbsLibraryService.startService(serviceName,
                          schemaPassword, serviceConfiguration, domain);
        System.out.println("Library Service Started ");
      }
      
    }catch(DbsException dbsEx){
      throw dbsEx;
    }
  }
  
  /**
   * set the user cleartext credentials object.
   * @param none
   */
  public void setdbsCtc() throws DbsException{
    try{
      dbsCtC = new DbsCleartextCredential(this.userName,this.userPassword);
      System.out.println("ClearText Credentials Set Successfully");
    }catch( DbsException dbsEx ){
      throw dbsEx;
    }
  }

  /**
   * set the user library session.this can be done only after library service has 
   * been found or started. 
   * @param none
   */
  public void setUserSession() throws DbsException{
    try{
      this.dbsSession = dbsService.connect(dbsCtC,null);
      System.out.println("User Session Obtained Successfully");
      this.dbsUser = dbsSession.getUser();
    }catch( DbsException dbsEx ){
      throw dbsEx;
    }
  }  
  
  /**
   * set the user's resume folder . this corresponds to her company's resume 
   * folder. 
   * @param none
   */
  public void setResumeFolder() throws DbsException{
    try{
      this.resumeFolderPath = "/"+this.userCompanyName+"/"+RESUME_FOLDER_NAME;
      System.out.println("ResumeFolderPath : "+resumeFolderPath);
      dbsFs = new DbsFileSystem(dbsSession);
      this.resumeFolder = (DbsFolder)dbsFs.findPublicObjectByPath(
                          resumeFolderPath);
      System.out.println("Resume Folder obtained successfully.");
      System.out.println("ResumeFolder ACL : "+resumeFolder.getAcl().getName());
    }catch(DbsException dbsEx){
      throw dbsEx;
    }
  }
  
  /**
   * obtain string representation for the XML directory hierarchy corresponding 
   * to the user's company resume folder. 
   * @param none
   * @returns xmlstring.
   */
  public String getXMLAsString(){
    //boolean isFileCreated = false;
    String xmlstring = null;
    try{
      DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
      //String fileName = dbsUser.getName()+dbsUser.getId()+".xml";
      //String absoluteFilePath = localFSPath+fileName;

      DocumentBuilder builder =  factory.newDocumentBuilder();
      Document document = builder.newDocument();
      Element element = (Element)document.createElement("ROOT");
      getFolderContentRecursive(document,element,resumeFolder);
      document.appendChild(element);

      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();

      DOMSource source = new DOMSource(document);
      //ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFFER_SIZE);
      //StreamResult result = new StreamResult(baos);
      //transformer.transform(source, result);
      
      // store data in xml file
      /*File file  = new File(absoluteFilePath);
      if(file.exists()){
        file.delete();
      }
      file.createNewFile();
      FileOutputStream fos = new FileOutputStream(file);
      StreamResult result = new StreamResult(fos);*/
      StreamResult result = new StreamResult(new StringWriter());
      transformer.transform(source, result);
      //isFileCreated = true;
      
      // return xml file data as String 
      //StreamSource streamSrc = new StreamSource(file);
      //StringWriter stringWriter = new StringWriter();
      //StreamResult streamRes = new StreamResult(stringWriter);
      //transformer.transform(streamSrc, streamRes);
      //String string = stringWriter.toString();
      xmlstring = result.getWriter().toString();
      xmlstring =  xmlstring.replaceAll("<","&lt");
      xmlstring = xmlstring.replaceAll(">","&gt");
      System.out.println(xmlstring);
      result.getWriter().close();
      
    }catch( ParserConfigurationException pcEx ){
      pcEx.printStackTrace();
    }catch( TransformerConfigurationException tcEx ){
      tcEx.printStackTrace();
    }catch( TransformerException tEx ){
      tEx.printStackTrace();
    }catch (DbsException dbsEx) {
      dbsEx.printStackTrace();
    }catch( Exception ex ){
      ex.printStackTrace();
    }
    return xmlstring;
  }

  /**
   * recursive function for obtaining folder hierarchy and building the DOM 
   * structure. 
   * @param Document document (DOM Document)
   * @param Element element (DOM Element)
   * @param DbsFolder dbsFolder (custom DbsFolder)
   */
  public void getFolderContentRecursive(Document document, Element element, 
               DbsFolder dbsFolder) throws DbsException{
    
    if( dbsFolder.hasSubfolders() ){
      DbsPublicObject [] dbsPOs = dbsFolder.getItems();
      ArrayList arrFldrs = new ArrayList();
      // select only folders for constructing XML file
      if( dbsPOs!= null ){
        for( int index = 0; index < dbsPOs.length; index++  ){
          if( dbsPOs[index] instanceof DbsFolder ){
            arrFldrs.add(dbsPOs[index]);
          }
        }
        Object[] dbsFldrs = new Object[ arrFldrs.size() ];
        for( int index = 0; index < dbsFldrs.length; index++  ){
          dbsFldrs[index] = arrFldrs.get(index);
        }
        Arrays.sort(dbsFldrs, new DbsComparator());
        int itemSize = dbsFldrs.length;
    
        for(int index = 0; index < itemSize; index++){
          if(dbsFldrs[index] instanceof DbsFolder){
            DbsFolder dbsFolderTemp = (DbsFolder)dbsFldrs[index];
            Element childElement = getDomElement(document,dbsFolderTemp);
            element.appendChild(childElement);
            getFolderContentRecursive(document,childElement,dbsFolderTemp);
          }
        }
      }
    }
  }

  /**
   * Obtaining DOM Element for a particular PO. 
   * @param Document document (DOM Document)
   * @param DbsPublicObject dbsPO (custom DbsPublicObject)
   * @returns DOM Element
   */
  public Element getDomElement(Document document,DbsPublicObject dbsPO){
    try{
      if(dbsPO instanceof DbsDocument){
        DbsDocument dbsDoc = (DbsDocument)dbsPO;
        Element element = (Element)document.createElement("FILE");
        element.setAttribute("PATH", dbsPO.getAnyFolderPath());
        element.setAttribute("NAME", dbsPO.getName());
        element.setAttribute("SIZE", String.valueOf(dbsDoc.getContentSize()));
        InputStream is = dbsDoc.getContentStream();
        element.setAttribute("MD5SUM",generateMD5Sum(is));
        is.close();
        element.setAttribute("MODIFIED_DATE", dbsDoc.getLastModifyDate().toString());
        element.setAttribute("ID", dbsDoc.getId().toString());
        return element;
    }else{
      Element element = (Element)document.createElement("FOLDER");
      element.setAttribute("PATH", dbsPO.getAnyFolderPath());
      //element.setAttribute("NAME", dbsPO.getName());
      //element.setAttribute("ID", dbsPO.getId().toString());
      return element;
    }
    }catch(DbsException dbsEx){
      dbsEx.printStackTrace();
    }catch(Exception ex){
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * Generate MD5 Sum using the I/PStream. 
   * @param InputStream io
   * @returns hex encoded String 
   */
  public String generateMD5Sum(InputStream io){
     byte[] md5Sum = null;
     try{
        MessageDigest  digest = MessageDigest.getInstance("MD5");
        byte[] inData = new byte[1024];
        int i = io.read(inData);
        while (i != -1) {
          digest.update(inData, 0, i);
          i = io.read(inData);
        }
        io.close();
        md5Sum=digest.digest();
      }catch(Exception ex){
        ex.printStackTrace();
      }
      return hexEncode(md5Sum);
  }

  /**
   * Generate hex encoded String for the byte[] passed from i/p stream. 
   * @param byte[] aInput 
   * @returns hex encoded String 
   */
  public String hexEncode( byte[] aInput){
    StringBuffer result = new StringBuffer();
    char[] digits = 
          {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
          
    for ( int idx = 0; idx < aInput.length; ++idx) {
      byte b = aInput[idx];
      result.append( digits[ (b&0xf0) >> 4 ] );
      result.append( digits[ b&0x0f] );
    }
    return result.toString();
  }

  /**
   * Terminates the user library session after the XML file has been created
   * successfully on the user's local FileSystem.
   * @param none 
   */
  public void terminateService(){
    try{
      this.dbsSession.disconnect();
    }catch( DbsException dbsEx ){
      dbsEx.printStackTrace();
    }finally{
      try {
        if( this.dbsSession != null ){
          this.dbsSession.disconnect();        
        }
      }catch (DbsException dbsEx) {
        dbsEx.printStackTrace();
      }
    }
  }

  public DbsDirectoryUser getDbsUser() {
    return dbsUser;
  }

  public void setDbsUser(DbsDirectoryUser dbsUser) {
    this.dbsUser = dbsUser;
  }

  public String getUserCompanyName() {
    return userCompanyName;
  }

  public void setUserCompanyName(String userCompanyName) {
    this.userCompanyName = userCompanyName;
  }
}