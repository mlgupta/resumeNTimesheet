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
 * $Id: Treeview.java,v 1.12 2005/11/25 14:08:50 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;
/**
 *	Purpose: To generate Tree Stucture for Folder for a given Librarysession
 *  @author              Sudheer Pujar
 *  @version             1.0
 * 	Date of creation:   16-01-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/*rts package references */
import rts.beans.*;
/* Java API */ 
import java.io.*;
import java.util.ArrayList;
/* Struts API */
import org.apache.log4j.*;

public class Treeview
{
  private DbsLibrarySession dbsLibrarySession = null;    // LibrarySession object 
  private DbsDirectoryUser dbsDirectoryUser=null;        // DirectoryUser object
  private DbsFileSystem dbsFileSystem=null;           //IfsFileSystem object
  private String jsFileName="";                       //A String object that defines the dynamically genreated jsFileName by User object Id
  private String jsFilePath="";                       //A String object that defines the physicalpath for jsFileName
  private String treeId="";                           //A String object that defines the Id for the TreeView
  private long userTreeLevel= 0;                      //A long data that defines level of tree to be generated in one stroke
  private String iconPath="";                         //A String object that defines the relative path for icons and images in the tree
  private String tempFolderPath ="temp"+File.separator;             //A temporary folder name
  private String tempFolderPath4User="";              //A temporary folder name   
  private ArrayList persistentFolders;                //An ArrayList stores the data of Folders Persisting in .js File
  private ArrayList persistentScriptTags;             //An ArrayList stores the data of script tag Persisting in .jsp File
  private ArrayList jsFiles2BDeleted;                 //An ArrayList stores the js File that r to be refreshed  and to delete       
  private ArrayList ancesstorIds;                     //An ArrayList stores ancesstors  for Address Bar
  private final static int FOLDERID=0;                //      Array Index Constants for the array object of length 3 (0,1,2) containted in the  ----
  private final static int PARENT=1;                  //  --  ArrayList named persistentFolders FOLDERID=0 is for the Folder Id ;               ----
  private final static int JSFILE=2;                  //  --  PARENT=1 Parent of the Folder ; JSFILE=2  contained Js File
  private Logger logger=null;
  private DbsFolder rootFolder=null;                  //Root Folder of the File System 
  private boolean foldersOnly=true;                   //To display only folders

/**
 * Purpose : Contructs a Treeview Object for Given Librarysession
 * @param dbsLibrarySession - A Librarysession object to generate Tree
 * @param userTreeLevel - A long data defines the level of tree to generate
 * @param jsFilePath - A String object defines the Physical path for jsFile
 * @param iconPath - A String object defines the relative path for icons and images in the tree
 * @param sessionId - A String object defines the relative path jsp Session Id
 */
  public Treeview(DbsLibrarySession dbsLibrarySession, String treeId,long userTreeLevel,String jsFilePath,String iconPath, String sessionId,boolean foldersOnly,String rtsBase,boolean isForMail,int rtsType)throws DbsException,Exception{
    try{

      this.dbsLibrarySession=dbsLibrarySession; //.getLibrarySession();
      this.dbsDirectoryUser = this.dbsLibrarySession.getUser();
      this.dbsFileSystem = new DbsFileSystem(this.dbsLibrarySession);
      this.jsFilePath=jsFilePath;
      this.treeId=treeId;
      this.userTreeLevel=userTreeLevel;
      this.iconPath=iconPath;
      this.persistentFolders = new ArrayList();
      this.persistentScriptTags = new ArrayList();
      this.jsFiles2BDeleted = new ArrayList();
      this.ancesstorIds = new ArrayList();
      this.foldersOnly=foldersOnly;
      Long userId = this.dbsDirectoryUser.getId();

      logger = Logger.getLogger("DbsLogger");
      
      //Temp Folder Path generation to store js and .jsp files 
      this.tempFolderPath4User=this.tempFolderPath + userId + "-" + sessionId + "-" + treeId +File.separator; 
      logger.info("Temp Folder Path :" + tempFolderPath4User);

      //Filename generation for .js file which inclues tree Data
      /*this.jsFileName= tempFolderPath4User+ userId + ".js";
      logger.info("Js File :" + jsFileName);*/
      
      //Fetches Root Folder
      DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
      //this.rootFolder =(DbsFolder)dbsFileSystem.findPublicObjectByPath("/"+rtsBase);
      if( isForMail ){
        this.rootFolder =(DbsFolder)dbsFileSystem.findPublicObjectByPath("/"+rtsBase+"/resumes");
        //Filename generation for .js file which inclues tree Data
        this.jsFileName= tempFolderPath4User+ userId +"-resumes" + ".js";
        logger.info("Js File :" + jsFileName);
        
      }else{
        if( rtsType == 1 || rtsType == 3){
          this.rootFolder =(DbsFolder)dbsFileSystem.findPublicObjectByPath("/"+rtsBase+"/resumes");
          //Filename generation for .js file which inclues tree Data
          this.jsFileName= tempFolderPath4User+ userId +"-resumes"+ ".js";
          logger.info("Js File :" + jsFileName);
          
        }else if( rtsType == 2 || rtsType == 4){
          this.rootFolder =(DbsFolder)dbsFileSystem.findPublicObjectByPath("/"+rtsBase+"/timesheets");
          //Filename generation for .js file which inclues tree Data
          this.jsFileName= tempFolderPath4User+ userId +"-timesheets"+".js";
          logger.info("Js File :" + jsFileName);
          
        }else{
          String homeFolderName = dbsDirectoryUser.getPrimaryUserProfile().getHomeFolder().getName();
          this.rootFolder =(DbsFolder)dbsFileSystem.findPublicObjectByPath("/"+rtsBase+"/personal/"+homeFolderName);
          //Filename generation for .js file which inclues tree Data
          this.jsFileName= tempFolderPath4User+ userId +"-personal"+ ".js";
          logger.info("Js File :" + jsFileName);
          
        }
      }
      logger.info("Root Folder  :" + rootFolder.getName());
      dbsFileSystem = null;
      //Initializes Tree
      logger.info("Calling initTree()");
      initTree();

      logger.info("Instance of the Treeview is Constructed");

    }catch(Exception e){
      logger.info("Exception Caught In Contructor of Treeview.java");
      logger.fatal(e.getMessage());
      throw e;
    }
    
  }

 /**
 * Purpose : Initialize the Folders Tree .
 * @return void
 */
  private void initTree()throws DbsException,IOException,Exception {
    try{

      logger.info("Treeview.initTree() Starts");
      String rootFolderName = this.rootFolder.getName();
      StringBuffer initTreeString = new StringBuffer();
      initTreeString.append("USETEXTLINKS = 1 \n");  
      initTreeString.append("STARTALLOPEN = 0" + "\n");
      initTreeString.append("USEFRAMES = 0" + "\n");
      initTreeString.append("HIGHLIGHT = 1" + "\n");
      initTreeString.append("PERSERVESTATE = 1" + "\n");
      initTreeString.append("ICONPATH = '" + this.iconPath + "'" + "\n");
      initTreeString.append("foldersTree = gFld(\"/"+rootFolderName+"\");" + "\n");
      initTreeString.append("foldersTree.treeID = \""+ this.treeId + "\";" + "\n");
      initTreeString.append("foldersTree.xID = \"" + rootFolder.getId() + "\";"  + "\n");
      initTreeString.append("foldersTree.isChildExist = true;"  + "\n");
      initTreeString.append("foldersTree.isNextLevel = true;"  + "\n");

      logger.info("Calling getTreeOfFoldersName");  
      
      initTreeString.append(getTreeOfFoldersName(this.rootFolder,"foldersTree",1,dbsDirectoryUser.getId()));
      
      //Creates the temp dir
      if (!(new File(this.jsFilePath + this.tempFolderPath).exists())){
        if (!(new File(this.jsFilePath + this.tempFolderPath).mkdir())){    
          logger.error("Unable to Create "+ tempFolderPath + " Directory"); 
        }else{
          logger.info("Temp Directory " + tempFolderPath + " Created");
        }
      }

      //Creates the <user> dir
      if (!(new File(this.jsFilePath + this.tempFolderPath4User).exists())){ 
        if (!(new File(this.jsFilePath + this.tempFolderPath4User).mkdir())){    
          logger.error("Unable to Create "+ tempFolderPath4User + " Directory"); 
        }else{
          logger.info("User On Session Temp Directory "+ tempFolderPath4User + " Created"); 
        }
      }

            
      //Creates .js File 
      if (!(writeToFile(initTreeString,this.jsFilePath+this.jsFileName,false))){
        logger.error("Unable to Create " + this.jsFilePath+this.jsFileName + " File"); 
      }else{
        logger.info("Master Js File " + this.jsFilePath+this.jsFileName + " Created"); 
      }

        
    }catch(Exception e){
      logger.info("Exception Caught In initTree() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
    }

    logger.info("Treeview.initTree() Ends");
  } 
  
/**
* Purpose : Creates given file with a given String
* @param singleBuffer - A StringBuffer object defines the content for the file 
* @param fileName - A String object defines fullpath with name of file
* @param append - A boolean data defines whether to append the file or overwrite  
* @return A booean data results file created or not
*/

  private boolean writeToFile(StringBuffer singleBuffer, String fileName, boolean append)throws IOException,Exception{
    FileOutputStream out; 
    PrintStream p; 
    String toFile = singleBuffer.toString();
    try{
      out = new FileOutputStream(fileName,append);
      p = new PrintStream( out );
      if(toFile.endsWith("\n"))
        p.println (toFile.substring(0,toFile.lastIndexOf("\n")));   
      else
        p.println (toFile);   
      p.close();
      return true;
    }catch(Exception e){
      logger.info("Exception Caught In writeToFile() of Treeview.java");
      logger.fatal(e.getMessage());            
      throw e;
    }
          
  } 

/**
* Purpose : Returns dynamically generated js file Name.
* @return String object defines js File Name
*/
  public String getJsFileName(){
    return this.jsFileName;
  }

/**
* Purpose : Returns whether folderOnly tree Or not.
* @return boolean value true or false
*/
  public boolean isFoldersOnly(){
    return this.foldersOnly;
  }

/**
* Purpose : Returns returns dynamically generated jsp file Name.
* @return String object defines jsp File Name
*/
  public String getJsFileLinks(){
    String jsFileLinksString="";
    for(int i=0; i<this.persistentScriptTags.size();i++){
    jsFileLinksString= jsFileLinksString+"<script src=\"" + this.tempFolderPath4User + ((Long)persistentScriptTags.get(i)).toString() + ".js" + "\"></script>\n"; 

    }
    return jsFileLinksString;
  }
  
/**
 * Purpose : To append the tree with child folders .
 * @param id - A Long object defines the id of a Folder
 * @return void
 */  
  public void appendTree(Long id)throws DbsException,IOException,Exception{
    DbsFolder folder;
    DbsPublicObject dbsPublicObject=null;
    StringBuffer nextLevelFoldersString ;
    StringBuffer scriptTag;
    boolean isScriptTagPersists=false;
    try{
      logger.info("Treeview.appendTree() Starts");

      try{
        dbsPublicObject=dbsFileSystem.findPublicObjectById(id);
      }catch(DbsException e){
        logger.info("No Folder found for the ID : " + id);
      }
      if (dbsPublicObject!=null){

        folder=(DbsFolder)dbsPublicObject;
      
        nextLevelFoldersString=getTreeOfFoldersName(folder,"obj" + id,1,id) ;      
     
        // nextLevelFoldersString is empty do not create the .js file  
        if (nextLevelFoldersString.toString().trim().length() > 0) {

          // Creates Folder wise .js Files
          if (!(writeToFile(nextLevelFoldersString,this.jsFilePath+this.tempFolderPath4User+id+".js",false))){  
            logger.error("Unable to Create " + this.jsFilePath+this.tempFolderPath4User+id+".js" + " File"); 
          }else{         
            logger.info("File" + this.jsFilePath+this.tempFolderPath4User+id+".js" + " Created"); 
          }
            
          // Checks the Persistance of the script tag 
          for(int i=0; i<persistentScriptTags.size(); i++){
            if (isScriptTagPersists=(persistentScriptTags.get(i)==id)){
              break;
            }
          }
          // Adds scripttags to .jsp Files
          if (!(isScriptTagPersists)){  
            persistentScriptTags.add(id);
          
          }
        }
      }
    }catch(Exception e){
      logger.info("Exception Caught In appendTree() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
    }
    logger.info("Treeview.appendTree() Ends");
  }

/**
 * Purpose : Creates Tree String for Folders .
 * @param folder - A Folder object 
 * @param parent - A String object defined by concatation of "obj" and folder id 
 * @param level - A long data defined by the current level running in the Tree
 * @param jsFileName4Folder - A Long object defined by the jsFile which contains the Folder object Definition
 * @return A StringBuffer object  containing Tree String of Folders
 */
  private StringBuffer getTreeOfFoldersName(DbsFolder folder, String parent, long level, Long jsFileName4Folder) throws DbsException,IOException,Exception{
    StringBuffer treeOfFoldersName = new StringBuffer();
    DbsPublicObject folderItem;
    String child; 
    boolean isFolderPersists=false;
    String description = null;
    try{
        for(int i=0; i<folder.getItemCount();i++){
            folderItem=folder.getItems(i);
            description = folderItem.getDescription(); 
            /*if (folderItem instanceof DbsFolder && 
                (description!=null && description.startsWith("R"))){*/
              if (folderItem instanceof DbsFolder ){
                logger.debug("persistentFolders.size(): "+persistentFolders.size());
                for(int j=0; j<persistentFolders.size(); j++){
                  if (isFolderPersists=((((Long[])persistentFolders.get(j))[FOLDERID]).longValue()==folderItem.getId().longValue())){
                    break;
                  }
                }
                if (!(isFolderPersists)) {  
                  persistentFolders.add(new Long[] {folderItem.getId(),((parent!="foldersTree")?new Long(parent.substring(3)):this.rootFolder.getId()),jsFileName4Folder});
                  child="obj" + folderItem.getId();
                  treeOfFoldersName.append("\n");
                  treeOfFoldersName.append(child + "= gFld(\"" + folderItem.getName() + "\");");
                  treeOfFoldersName.append("\n");
                  treeOfFoldersName.append(child + ".xID=\""+ folderItem.getId() + "\";");
                  treeOfFoldersName.append("\n");
                  treeOfFoldersName.append(child + ".isChildExist="+ ((DbsFolder)folderItem).hasSubfolders() + ";");
                  treeOfFoldersName.append("\n");
                  treeOfFoldersName.append(child + ".isNextLevel="+ (level < this.userTreeLevel) + ";");
                  treeOfFoldersName.append("\n");
                  treeOfFoldersName.append(child + ".path=\""+  folderItem.getAnyFolderPath() + "\";");
                  treeOfFoldersName.append("\n");
                  treeOfFoldersName.append("insFld(" + parent + "," + child + ");"  + "\n" );
                }
            }else{
                  
                  if ((!this.foldersOnly) && (folderItem !=null) && (folderItem.getResolvedPublicObject() instanceof DbsDocument)){
                    child="obj" + folderItem.getId();
                    treeOfFoldersName.append("\n");
                    treeOfFoldersName.append(child + "= gLnk(\"S\",\"" + folderItem.getName() + "\"," + (folderItem instanceof DbsFamily) + ");");
                    treeOfFoldersName.append("\n");
                    treeOfFoldersName.append(child + ".xID=\""+ folderItem.getId() + "\";");
                    treeOfFoldersName.append("\n");
                    treeOfFoldersName.append(child + ".path=\""+  folderItem.getAnyFolderPath() + "\";");
                    treeOfFoldersName.append("\n");
                    treeOfFoldersName.append("insDoc(" + parent + "," + child + ");"  + "\n" );
                  }
            }
        }

        if ( level < this.userTreeLevel) {
            for(int i=0; i<folder.getItemCount();i++){
                folderItem=folder.getItems(i);
                description = folderItem.getDescription();
                /*if (folderItem instanceof DbsFolder && 
                (description!=null && description.startsWith("R")) ){*/
                if (folderItem instanceof DbsFolder ){
                    treeOfFoldersName.append(getTreeOfFoldersName((DbsFolder)folderItem,"obj" + folderItem.getId(),level+1,jsFileName4Folder));                
                }
            }
        }
      
      return treeOfFoldersName;
    }catch(Exception e){
      logger.info("Exception Caught In getTreeOfFoldersName() of Treeview.java");
      logger.fatal(e.getMessage()); 
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Purpose : Finds the Folder Deleted, Deletes The  respective Child Folder(s) 
   * @param id - A Long object defined by the Id of the Folder to be Delete
   * @return void
   */
  public void ifFolderDeleted(Long id, Long parentId)throws DbsException,IOException,Exception{
    Long  jsFile2BRecreated=null;
    DbsPublicObject dbsPublicObject=null;
    StringBuffer scriptTag;
    DbsFolder parentFolder;
    Long siblingOrParentId=null;
    int counter=0;
    try{

      logger.info("Treeview.ifFolderDeleted() Starts");

      logger.info("Deleted Folder : " + id );

      try{
        dbsPublicObject=dbsFileSystem.findPublicObjectById(parentId);
        logger.info(" Parent Of Deleted Folder : " + parentId + "-" + dbsPublicObject.getName() );
      }catch(DbsException e){
        logger.info("No Folder found for the ID : " + parentId);
      }

      if (dbsPublicObject!=null){
            
        //1. Finds Sibling Id Or ParentId (If no siblings available) Of the Deleted Folder
        parentFolder=((DbsFolder)dbsPublicObject);
        if (parentFolder.getItemCount()>0){
          for(int i=0; i< parentFolder.getItemCount();i++){    
              if(parentFolder.getItems(i) instanceof DbsFolder){
                  siblingOrParentId=parentFolder.getItems(i).getId();
                  break;
              }
          }
        }
      
        if(siblingOrParentId==null){
          siblingOrParentId=parentFolder.getId();  
        }
        
      
        logger.info("Sibling Or Parent  Of Deleted Folder : " + siblingOrParentId + "-" + dbsFileSystem.findPublicObjectById(siblingOrParentId).getName() );  

        //2. JsFile Capturing To be Recreated
        for (int i=0; i<this.persistentFolders.size() ;i++){
          if (((Long[])this.persistentFolders.get(i))[FOLDERID].longValue()==siblingOrParentId.longValue()){
            jsFile2BRecreated = ((Long[])this.persistentFolders.get(i))[JSFILE];
            break;
          }  
        }   

        if (jsFile2BRecreated!=null){

          logger.info("jsFile of Deleted Folder is : " + jsFile2BRecreated);

    
          //3. Searchs the jsFiles of deleted Folders and Stores The File Names in files2BDeleted ArrayList
          jsFileSearch4FolderDeletion(id);

          //4. Remove Folder Ids contained in the .js File From "persistentFolders" Arraylist
          while(counter<this.persistentFolders.size()){
            if (((Long[])this.persistentFolders.get(counter))[JSFILE].longValue()==jsFile2BRecreated.longValue()){
              this.persistentFolders.remove(counter);
            }else{  
              counter++;
            }  
          }    
           
          for (int i=0; i< this.jsFiles2BDeleted.size();i++){

            //5. Deleting the jsFiles of Child Folders of the Deleted Folder
            logger.info("Delete : " + this.jsFiles2BDeleted.get(i) + ".js");
        
            if (new File(this.jsFilePath+this.tempFolderPath4User+this.jsFiles2BDeleted.get(i)+".js").exists()){
              new File(this.jsFilePath+this.tempFolderPath4User+this.jsFiles2BDeleted.get(i)+".js").delete();
            }

            //6. Remove Child Ids of the Deleted From "persistentFolders" Arraylist          
            counter=0;
            while(counter<this.persistentFolders.size()){
              if (((Long[])this.persistentFolders.get(counter))[PARENT].longValue()==((Long)this.jsFiles2BDeleted.get(i)).longValue()){
                this.persistentFolders.remove(counter);
              }else{  
                counter++;
              }  
            }    

            //7. Remove the Child Ids of the Deleted From "persistentScriptTags" Arraylist
            counter=0;
            while(counter<this.persistentScriptTags.size()){
              if (((Long)this.persistentScriptTags.get(counter)).longValue()==((Long)this.jsFiles2BDeleted.get(i)).longValue()){
                this.persistentScriptTags.remove(counter);
              }else{  
                counter++;
              }  
            }     
          }
    
          //8. Recreating the Js File of the Deleted Folder
          if (jsFile2BRecreated.longValue()==dbsDirectoryUser.getId().longValue()){
            initTree();
          }else{
              new File(this.jsFilePath+this.tempFolderPath4User+jsFile2BRecreated+".js").delete();
              appendTree(jsFile2BRecreated);
          }   
        }
      }
    }catch(Exception e){

      logger.info("Exception Caught In ifFolderDeleted() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
      
    }    

    logger.info("Treeview.ifFolderDeleted() Ends");
  }

  /**
   * Purpose : Finds the Child Folders of a Deleted Folder up to last Level, and Stores correspondign jsfiles in arraylist jsFiles2BDeleted
   * @param id - A Long object defined by the Id of the Folder to be Delete
   * @return void
   */
  private void jsFileSearch4FolderDeletion(Long id)throws DbsException,Exception{

        Long  jsFilesOfChildOfDeletedFolder;
        boolean isJsFileExists = false;
        try{
          for (int i=0; i<this.persistentFolders.size();i++){
         
            jsFilesOfChildOfDeletedFolder = null;
                                  
            //JsFile Capturing for Childs Of Deleted Folder

              if (((Long[])this.persistentFolders.get(i))[PARENT].longValue()==id.longValue()){   //Checks If Files Is Parent File for any Child
                jsFilesOfChildOfDeletedFolder=((Long[])this.persistentFolders.get(i))[JSFILE] ;  //Stores Js File   
              }

            //Adding Js File Name to an Array jsFiles2BDeleted
            if(jsFilesOfChildOfDeletedFolder!=null){
              for (int j=0; j< jsFiles2BDeleted.size();j++){
                if (isJsFileExists=(((Long)jsFiles2BDeleted.get(j)).longValue()==jsFilesOfChildOfDeletedFolder.longValue())){
                  break;
                }
              }
              if (!isJsFileExists){
                jsFiles2BDeleted.add(jsFilesOfChildOfDeletedFolder);       
              }
            }
            
          }
          //Recursive Call for jsFileSearch4FolderDeletion(Long id)
          for (int i=0; i<this.persistentFolders.size();i++){
              if (((Long[])this.persistentFolders.get(i))[PARENT].longValue()==id.longValue()){
                jsFileSearch4FolderDeletion(((Long[])this.persistentFolders.get(i))[FOLDERID]);
              }
          }    
        logger.debug("jsFiles2BDeleted.size: "+jsFiles2BDeleted.size());      
        }catch(Exception e){
          logger.info("Exception Caught In jsFileSearch4FolderDeletion() of Treeview.java");
          logger.fatal(e.getMessage()); 
          throw e;
        }                  
  }

  /**
   * Purpose : To Add the Folder and its Children to Tree 
   * @param id - A Long object defined by the Id of the Folder to be Added
   * @return void
   */
  public void ifFolderAdded(Long id, Long parentId) throws DbsException,IOException,Exception{
    Long jsFile4AddedFolder=null;
    int counter=0;
    
    try{

      logger.info("Treeview.ifFolderAdded() Starts");

      logger.info("Added Folder : " + id + " - " + dbsFileSystem.findPublicObjectById(id).getName());
      
      //Searchs Files for Recreation
      jsFile4AddedFolder=jsFileSearch4FolderAddition(id,parentId);

      if (jsFile4AddedFolder!=null){  
      
        logger.info("jsFile for Added Folder is : " + jsFile4AddedFolder);

             
        //Remove Folder Ids contained in the .js File contained by siblings of Folder Added From "persistentFolders" Arraylist
        while(counter<this.persistentFolders.size()){
          if (((Long[])this.persistentFolders.get(counter))[JSFILE].longValue()==jsFile4AddedFolder.longValue()){
            this.persistentFolders.remove(counter);
          }else{
            counter++;
          }
        }

        //Recreating the Js File of siblings of Added Folder
        if (jsFile4AddedFolder.longValue()==dbsDirectoryUser.getId().longValue()){
          initTree();
        }else{
            new File(this.jsFilePath+this.tempFolderPath4User+jsFile4AddedFolder+".js").delete();
            appendTree(jsFile4AddedFolder);         
        }
      }
      
    }catch(Exception e){
      logger.info("Exception Caught In ifFolderAdded() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
    }

    logger.info("Treeview.ifFolderAdded() Ends");
    
  }

    /**
   * Purpose : Finds js files containing the Sibling Folders of an Added Folder and recreates the same js File
   *           If Sibling folders doesn't exist the jsFile that has to recreated will the js File containing parent will be recreated
   * @param id - A Long object defined by the Id of the Folder to be Delete
   * @return void
   */
  private Long jsFileSearch4FolderAddition(Long id, Long parentId)throws DbsException,Exception{
    DbsFolder parentFolder=null;
    Long jsFileOfAddedFolder=null;
    int siblingCount=0;
    Long siblingOrParentId=null;
    logger.info("Treeview.jsFileSearch4FolderAddition() Starts");
    try{
      parentFolder=(DbsFolder)dbsFileSystem.findPublicObjectById(parentId);
      siblingCount=parentFolder.getItemCount();
      if (siblingCount>1){
        for(int i=0; i<siblingCount;i++){
            if(parentFolder.getItems(i) instanceof DbsFolder){
                if ((siblingOrParentId=parentFolder.getItems(i).getId())!=id){
                    break;
                }
            }
        }
      }
      if(siblingOrParentId==null){
        siblingOrParentId=parentFolder.getId();  
      }

     //JsFile Capturing for Added Folder
      for (int i=0; i<this.persistentFolders.size() ;i++){
        if (((Long[])this.persistentFolders.get(i))[FOLDERID].longValue()==siblingOrParentId.longValue()){
          jsFileOfAddedFolder = ((Long[])this.persistentFolders.get(i))[JSFILE];
          break;
        }  
      }   

    
    }catch(Exception e){
      logger.info("Exception Caught In jsFileSearch4FolderAddition() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
    }

    logger.info("Treeview.jsFileSearch4FolderAddition() Ends");   
    return jsFileOfAddedFolder;  
    
  }

  /**
   * Purpose : To Recreate the respective Js File contained by the Folder that Renamed
   * @param id - A Long object defined by the Id of the Folder to be Added
   * @return void
   */
  public void ifFolderRenamed(Long id)throws DbsException,Exception{
    Long jsFileOfRenamedFolder=null;
    int counter=0;

    try{
    logger.info("Treeview.ifFolderRenamed Starts");

    logger.info("Renamed Folder : " + id + " - " + dbsFileSystem.findPublicObjectById(id).getName());
    
      //JsFile Capturing for Renamed Folder                 
      for (int i=0; i<this.persistentFolders.size() ;i++){
        if (((Long[])this.persistentFolders.get(i))[FOLDERID].longValue()==id.longValue()){
          jsFileOfRenamedFolder = ((Long[])this.persistentFolders.get(i))[JSFILE];
          break;
        }  
      }

      if (jsFileOfRenamedFolder!=null){
        logger.info("jsFile for Renamed Folder is : " + jsFileOfRenamedFolder);

        //Remove Folder Ids contained in the .js File contained by Folder Renamed From "persistentFolders" Arraylist
        while(counter<this.persistentFolders.size()){
          if (((Long[])this.persistentFolders.get(counter))[JSFILE].longValue()==jsFileOfRenamedFolder.longValue()){
            this.persistentFolders.remove(counter);
          }else{  
            counter++;
          }
        }

        //Recreating the Js File of the Renamed Folder
        if (jsFileOfRenamedFolder.longValue()==dbsDirectoryUser.getId().longValue()){
          initTree();
        }else{
            new File(this.jsFilePath+this.tempFolderPath4User+jsFileOfRenamedFolder+".js").delete();
            appendTree(jsFileOfRenamedFolder);
        }
      }
    }catch(Exception e){
      logger.info("Exception Caught In ifFolderRenamed() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
    }
    
    logger.info("Treeview.ifFolderRenamed Ends");
  }

  /**
   * Purpose : To Refresh the Treeview as per the address of the address bar
   * @param id - A Long object defined by the Id of the current Folder in the addressbar
   * @return void
   */
  public void forAddressBar(Long id)throws DbsException,Exception{
    logger.info("Treeview.forAddressBar Starts");
    Long ancesstorId =null;
    
    try{
      logger.info("End Folder in Address Bar : " + dbsFileSystem.findPublicObjectById(id).getName());    
      this.recursion4AddressBar(id);
      logger.info("Size After Addressbar Call First Time : " + ancesstorIds.size() );
      int j=this.ancesstorIds.size()-1;
      while(j>=0){
        ancesstorId=(Long)this.ancesstorIds.get(j);
        logger.info("Ancesstor Is : " + dbsFileSystem.findPublicObjectById(ancesstorId).getName());
        this.appendTree(ancesstorId);
        logger.info("Size Before Clear :" + this.ancesstorIds.size() );
        this.ancesstorIds.clear();
        logger.info("Size After Clear :" + this.ancesstorIds.size() );
        this.recursion4AddressBar(id);
        logger.info("Size After AddressbarCall :" + this.ancesstorIds.size() );
        j=this.ancesstorIds.size()-1;
      }
      
    }catch(Exception e){
      logger.info("Exception Caught In forAddressBar() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
    }

    logger.info("Treeview.forAddressBar Ends");
  }

  /**
   * Purpose : To Find recursively whether the Js File is created for this id or not 
   * @param id - A Long object defined by the Id of the current Folder in the addressbar
   * @return void
   */
  private void recursion4AddressBar(Long id)throws DbsException,Exception{
    boolean isJsFileCreated4Folder=false;
    
    try{
      if (!id.equals(this.rootFolder.getId()) ) {
      
        logger.info("recursion4AddressBar Starts For : " + dbsFileSystem.findPublicObjectById(id).getName());    
        for (int i=0; i<this.persistentFolders.size(); i++){
          if(isJsFileCreated4Folder=(((Long[])persistentFolders.get(i))[FOLDERID].longValue()==id.longValue())){
            break;
          }
        }
  
        if(!isJsFileCreated4Folder){ 
          DbsFolder folder = (DbsFolder)this.dbsFileSystem.findPublicObjectById(id);
          for(int i=0; i<folder.getFolderReferences().length;i++){
            Long ancesstorId=folder.getFolderReferences(i).getId();
            this.logger.debug("FolderName: "
                            +((DbsFolder)this.dbsFileSystem.findPublicObjectById(
                              ancesstorId)).getName());
            /* */
            if(!ancesstorId.equals(this.rootFolder.getId())){                  
              this.ancesstorIds.add(ancesstorId);
              recursion4AddressBar(ancesstorId);
            }else{
              logger.debug("Root Folder not added to list");
            }
            /* */
          }
        }
  
        logger.info("recursion4AddressBar Ends For : " + dbsFileSystem.findPublicObjectById(id).getName());    
        
      }
    }catch(Exception e){
      logger.info("Exception Caught In recursion4AddressBar() of Treeview.java");
      logger.fatal(e.getMessage());
      e.printStackTrace();
    }

  }
  /**
   * Purpose : To free up the objects created in the constructor and 
   * delete the temp folder pertaining to the user  
   * @return void
   */
  public void free()throws DbsException,IOException,Exception{
    try{  
      this.dbsLibrarySession=null;
      this.dbsDirectoryUser =null;
      this.dbsFileSystem =null;
      this.persistentFolders = null;
      this.persistentScriptTags = null;
      this.jsFiles2BDeleted =null;
      this.freeTempFolder();  
    }catch(Exception e){
      logger.info("Exception Caught In free() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
    }

    logger.info("Instance of the Treeview is Freed");
    logger=null;
  }

  /**
   * Purpose : To  delete the temp folder pertaining to the user  
   * @return void
   */
  private void freeTempFolder()throws DbsException,IOException,Exception{
    try{  
 
      File tempFolder2BDeleted=new File(this.jsFilePath + this.tempFolderPath4User);

      if ((tempFolder2BDeleted.exists())){

        // Deleting the files from the temp Folder
        File[] filesInTempFolder=tempFolder2BDeleted.listFiles();
        for(int index=0;index<filesInTempFolder.length;index++){
          if (!(filesInTempFolder[index].delete())){
            logger.error("Unable to Delete "+ filesInTempFolder[index].getName() + " File"); 
          }
        }

        // Deleting the Temp Folder
        if (!(tempFolder2BDeleted.delete())){    
          logger.error("Unable to Delete "+ this.tempFolderPath4User + " Directory"); 
        }else{
          logger.info("User On Session Temp Directory "+ this.tempFolderPath4User + " Deleted");      
        }  

      }else{
        logger.error("Unable to Find "+ this.jsFilePath + this.tempFolderPath4User + " Directory"); 
      }
 
    }catch(Exception e){
      logger.info("Exception Caught In freeTempFolder() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
    }
  }
  
  /**
   * Purpose : To Refresh the treeview 
   * @return void
   */
  public void refresh() throws DbsException,IOException,Exception {
    logger.info("refresh Starts ");
    ArrayList tempPersistentFolders = new ArrayList();
    ArrayList tempPersistentScriptTags = new ArrayList();
    try{
      //Copying the arraylists PersistentFolders and persistentScriptTags in Temp arraylist
      tempPersistentFolders = (ArrayList)this.persistentFolders.clone();
      tempPersistentScriptTags = (ArrayList)this.persistentScriptTags.clone();
      
      //Clearing the arraylists PersistentFolders and persistentScriptTags
      this.persistentFolders.clear();
      this.persistentScriptTags.clear();

      logger.info("size of persistentFolder is : " + tempPersistentFolders.size());

      //Deleting the temp folders
      this.freeTempFolder();

      this.foldersOnly=true;
      
      this.initTree();

      for (int i=0; i<tempPersistentFolders.size();i++){
        Long folderId=null;
        folderId= ((Long [])tempPersistentFolders.get(i))[FOLDERID];
        this.appendTree(folderId);
        logger.info("Folder : " + this.dbsFileSystem.findPublicObjectById(folderId).getName());
      }

    }catch(Exception e){
      logger.info("Exception Caught In refresh() of Treeview.java");
      logger.fatal(e.getMessage()); 
      throw e;
    }
    logger.info("refresh ends ");    
  }
  

}

