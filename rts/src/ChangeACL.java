import rts.beans.*;
import rts.web.beans.filesystem.FolderDoc;
import rts.web.beans.utility.SearchUtil;
import java.io.File;
import java.util.Hashtable;

public class ChangeACL  {
  
   public static String SERVICE_NAME = null;
   public static String SERVICE_CONFIGURATION = null;
   public static String SCHEMA_PASSWORD = null;   
   public static String DOMAIN = null;
   public static String SYS_AD_USER=null;
   public static String SYS_AD_PASSWORD =null;
   
   public static String COMPANY_NAME=null;
   public static String COMPANY_FOLDER_NAME=null;
   public static String RESUME_FOLDER="resumes";
   public static String TIMESHEET_FOLDER="timesheets";
   public static String PERSONAL_FOLER="personal";
   public static String MAIL_ADDRESS=null;
   public static String GROUP_SUFFIX="_group";
   public static String ACL_SUFFIX="_acl";
   public static String PERMISSION_BUNDLE_SUFFIX="_permission_bundle";
   public static String QUOTA="100";
   public static String userName = "myadmin";
	 public static String password = "aaaaa";
   DbsLibraryService dbsLibraryService=null;
   DbsLibrarySession dbsLibrarySession=null;
   DbsUserManager userManager=null;
  
  
  public ChangeACL() {
    SERVICE_NAME="IfsDefaultService";
    SERVICE_CONFIGURATION="SmallServiceConfiguration";
    SCHEMA_PASSWORD="cmsdk";
    DOMAIN="ifs://192.168.0.1:1521:cmsdk:CMSDK";
    SYS_AD_USER="myadmin";
    SYS_AD_PASSWORD="aaaaa";    
    COMPANY_NAME="tradebench";    
    MAIL_ADDRESS="myadmin@yourCompany.com";
    //QUOTA=args[8];
    userName="myadmin";
    password="aaaaa";
    COMPANY_FOLDER_NAME="/"+COMPANY_NAME;
  
  }
  
  public static void main(String args[]){
  
    /*if(args.length!=11){
      System.out.println("Insufficient number of arguments passed");
      System.exit(1);
    }*/
    ChangeACL chngACL = new ChangeACL();
    System.out.println("1");
    chngACL.changeAllAcls();
  }

  public void changeAllAcls(){
  System.out.println("2");
      try {      
           if(DbsLibraryService.isServiceStarted(SERVICE_NAME)){
                System.out.println("3");
                dbsLibraryService = DbsLibraryService.findService(SERVICE_NAME);
                System.out.println("Library Service Found");
            }else{              
                System.out.println("4");
                System.out.println("SERVICE_NAME"+SERVICE_NAME);
                System.out.println("SCHEMA_PASSWORD"+SCHEMA_PASSWORD);
                System.out.println("SERVICE_CONFIGURATION"+SERVICE_CONFIGURATION);
                System.out.println("DOMAIN"+DOMAIN);
                
                dbsLibraryService = DbsLibraryService.startService(SERVICE_NAME,SCHEMA_PASSWORD,SERVICE_CONFIGURATION,DOMAIN);
                System.out.println("Library Service Started ");
            }
            
            System.out.println("5");          
            DbsCleartextCredential  dbsCred=new DbsCleartextCredential(SYS_AD_USER,SYS_AD_PASSWORD);
            System.out.println("6");
            dbsLibrarySession=dbsLibraryService.connect(dbsCred,null);
            System.out.println("Session obtained");
            ///////////////////
            
            dbsLibrarySession.setAdministrationMode(true);
            System.out.println("Administration mode set to true");
            DbsCollection userCollection=dbsLibrarySession.getDirectoryUserCollection();
            DbsDirectoryUser userExists=dbsLibrarySession.getUser();
                      
            if(userExists !=null){
                DbsFileSystem fileSystem= new DbsFileSystem(dbsLibrarySession);
               
                DbsFolderPathResolver fpr=new DbsFolderPathResolver(dbsLibrarySession);            
                fpr.setRootfolder();            
                DbsFolder rootFolder=(DbsFolder)fpr.findPublicObjectByPath("/");
                DbsFolder companyDir=null;
                try{
                     companyDir=(DbsFolder)fpr.findPublicObjectByPath(COMPANY_FOLDER_NAME);
                }catch(DbsException folderFindException){                        
                    if(folderFindException.containsErrorCode(30619)){
                           companyDir=fileSystem.createFolder(COMPANY_FOLDER_NAME,rootFolder,true,null);
                    }
                }
              
                DbsFolder personalDir=null;
                String personalFolder=companyDir.getName()+File.separator+PERSONAL_FOLER;
                try{
                     personalDir=(DbsFolder)fpr.findPublicObjectByPath(personalFolder);
                }catch(DbsException folderFindException){
                     if(folderFindException.containsErrorCode(30619)){
                           personalDir=fileSystem.createFolder(personalFolder,rootFolder,true,null);
                     }
                }
              
                DbsFolder homeDir=null;
                String homeFolder=personalDir.getAnyFolderPath()+File.separator+userName;
                try{
                     homeDir=dbsLibrarySession.getUser().getPrimaryUserProfile().getHomeFolder();
                }catch(DbsException folderFindException){
                     if(folderFindException.containsErrorCode(30619)){
                            homeDir=fileSystem.createFolder(homeFolder,rootFolder,true,null);
                     }
                }
                
                DbsFolder resumeDir=null;
                String resumeFolder=companyDir.getName()+File.separator+RESUME_FOLDER;
                try{
                     resumeDir=(DbsFolder)fpr.findPublicObjectByPath(resumeFolder);
                }catch(DbsException folderFindException){
                     if(folderFindException.containsErrorCode(30619)){
                             resumeDir=fileSystem.createFolder(resumeFolder,rootFolder,true,null);
                     }
                }
                DbsFolder timesheetDir=null;
                String timesheetFolder=companyDir.getName()+File.separator+TIMESHEET_FOLDER;
                try{
                     timesheetDir=(DbsFolder)fpr.findPublicObjectByPath(timesheetFolder);
                }catch(DbsException folderFindException){
                     if(folderFindException.containsErrorCode(30619)){
                              timesheetDir=fileSystem.createFolder(timesheetFolder,rootFolder,true,null);
                      }
                }
                
                DbsCollection systemAclColl= dbsLibrarySession.getSystemAccessControlListCollection();
                DbsAccessControlList privateAcl=null;
                for(int index =0;index< systemAclColl.getItemCount() ; index++){
                  if(systemAclColl.getItems(index).toString().equalsIgnoreCase("Private")){            
                      privateAcl= systemAclColl.getSystemAccessControlList(systemAclColl.getItems(index));
                      break;
                  }
                }
                if(privateAcl!=null){
                  homeDir.setAcl(privateAcl);
                  changeFolderAclRecursively(homeDir,privateAcl);
                }
                
                DbsDirectoryGroup [] groups=dbsLibrarySession.getUser().getAllAncestors();
                DbsDirectoryGroup companyGroup = null;
                DbsAccessControlList compACL = null;
                if( groups!=null ){
                  companyGroup = groups[0];
                  System.out.println("Company Group: "+companyGroup.getName());
                  compACL = companyGroup.getAcl();
                  System.out.println("compACL : "+compACL.getName());
                  // set ACL to companyACL
                  resumeDir.setAcl(compACL);
                  changeFolderAclRecursively(resumeDir,compACL);
                  timesheetDir.setAcl(compACL);
                  changeFolderAclRecursively(timesheetDir,compACL);
                  personalDir.setAcl(compACL);
                }
                System.out.println("All operations completed successfully");
              
            }//end if 
      }
      catch (DbsException e) {
      System.out.println(e.getErrorMessage());
        e.printStackTrace();
      }catch (Exception e) {
      System.out.println(e);
        e.printStackTrace();
      }finally{
       
          try{
          if(dbsLibrarySession!=null){
              dbsLibrarySession.disconnect();
          }
          }catch(DbsException e){e.printStackTrace();}
       
       
      }
    
  }


    //to change dbsAccessControlList of a folder recursively
    private void changeFolderAclRecursively(DbsFolder top, 
                 DbsAccessControlList dbsAccessControlList) throws DbsException {
        // change the ACL of the specified folder,
        // and set all of the items in the folder to that same ACL
        top.setAcl(dbsAccessControlList);
        DbsPublicObject[] items = top.getItems();
        int length = (items == null) ? 0 : items.length;
        for (int i = 0; i < length; i++){
        
            System.out.println("Public Object Name : " + items[i].getName());
            // if the item is a folder, call this same method recursively
            if (items[i] instanceof DbsFolder)	{
            
                DbsFolder f = (DbsFolder)items[i];
                changeFolderAclRecursively(f, dbsAccessControlList);
                
            }else{
                // simply change the item's ACL
                String oldAclName = items[i].getAcl().getName();
                items[i].setAcl(dbsAccessControlList);
               
            }
            
        }
        
    }


}