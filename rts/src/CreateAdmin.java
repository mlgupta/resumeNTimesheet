import rts.beans.*;
import rts.web.beans.utility.SearchUtil;
import java.io.File;
import java.util.Hashtable;

public class CreateAdmin  {
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
   public static String userName = "vishal";
	 public static String password = "aaaaa";
   DbsLibraryService dbsLibraryService=null;
   DbsLibrarySession dbsLibrarySession=null;
   DbsUserManager userManager=null;
  public CreateAdmin() {
  }
  public static void main(String args[]){
  
    if(args.length!=11){
      System.out.println("Insufficient number of arguments passed");
      System.exit(1);
    }
    SERVICE_NAME=args[0];
    SERVICE_CONFIGURATION=args[1];
    SCHEMA_PASSWORD=args[2];
    DOMAIN=args[3];
    SYS_AD_USER=args[4];
    SYS_AD_PASSWORD=args[5];    
    COMPANY_NAME=args[6];    
    MAIL_ADDRESS=args[7];
    QUOTA=args[8];
    userName=args[9];
    password=args[10];
    COMPANY_FOLDER_NAME="/"+COMPANY_NAME;
    CreateAdmin cr = new CreateAdmin();
    cr.createUser();
  }
  public void createUser(){
      try {      
           if(DbsLibraryService.isServiceStarted(SERVICE_NAME)){                   
                dbsLibraryService = DbsLibraryService.findService(SERVICE_NAME);
                System.out.println("Library Service Found");
            }else{              
                dbsLibraryService = DbsLibraryService.startService(SERVICE_NAME,SCHEMA_PASSWORD,SERVICE_CONFIGURATION,DOMAIN);
                System.out.println("Library Service Started ");
            }
            
            
            DbsCleartextCredential  dbsCred=new DbsCleartextCredential(SYS_AD_USER,SYS_AD_PASSWORD);
            dbsLibrarySession=dbsLibraryService.connect(dbsCred,null);
            System.out.println("Session obtained");
            ///////////////////
            
            dbsLibrarySession.setAdministrationMode(true);
            System.out.println("Administration mode set to true");
            DbsCollection userCollection=dbsLibrarySession.getDirectoryUserCollection();
            DbsDirectoryUser userExists=null;
            try{
                 userExists=(DbsDirectoryUser)userCollection.getItems(userName);
                 
                }catch(DbsException dbEx){
                    if(dbEx.containsErrorCode(12214)){
                        //do nothing when the user is not existing
                    }else{
                        throw dbEx;
                    }
                }
          
            if(!(userExists instanceof DbsDirectoryUser)){
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
                     homeDir=(DbsFolder)fpr.findPublicObjectByPath(homeFolder);
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
                
                Hashtable options = new Hashtable() ;
                options.put(DbsUserManager.ADMIN_ENABLED, DbsAttributeValue.newAttributeValue(true));
                options.put(DbsUserManager.HAS_HOME_FOLDER,DbsAttributeValue.newAttributeValue(false));
                options.put(DbsUserManager.HAS_EMAIL,DbsAttributeValue.newAttributeValue(true));
                options.put(DbsUserManager.EMAIL_ADDRESS,DbsAttributeValue.newAttributeValue(MAIL_ADDRESS));
                userManager=new DbsUserManager(dbsLibrarySession);
                DbsDirectoryUser userNew = userManager.createUser(userName,password,options);
                System.out.println("User Created");
                DbsPrimaryUserProfile pup = userNew.getPrimaryUserProfile();
                pup.setHomeFolder(homeDir);
                homeDir.setOwnerByName(userNew.getName());
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
                }
                  
                DbsDirectoryGroupDefinition dbsGroupDefaultDef = new DbsDirectoryGroupDefinition(dbsLibrarySession);
                dbsGroupDefaultDef.setAttribute(DbsDirectoryGroup.NAME_ATTRIBUTE,DbsAttributeValue.newAttributeValue(COMPANY_NAME));
                dbsGroupDefaultDef.setAttribute("COMPANY_DIR",DbsAttributeValue.newAttributeValue("/"+COMPANY_NAME));
                DbsDirectoryGroup dbsGroup=(DbsDirectoryGroup)dbsLibrarySession.createPublicObject(dbsGroupDefaultDef);
                dbsGroup.setOwnerByName(userNew.getName());
                dbsGroup.addMember(userNew);
                dbsGroup.setAttribute("QUOTA",DbsAttributeValue.newAttributeValue(QUOTA));
                DbsAccessControlEntryDefinition aceDef=new DbsAccessControlEntryDefinition(dbsLibrarySession);
                DbsPermissionBundle newPermissionBundle=(DbsPermissionBundle)SearchUtil.findObject(dbsLibrarySession,DbsPermissionBundle.CLASS_NAME,"All");
                aceDef.addPermissionBundle(newPermissionBundle);
                aceDef.setGrantee(dbsGroup);
                DbsAccessControlEntry compAce=dbsLibrarySession.createSystemObject(aceDef);        
                DbsAccessControlListDefinition aclDef = new DbsAccessControlListDefinition(dbsLibrarySession);
                aclDef.setAttribute(DbsAccessControlList.NAME_ATTRIBUTE,DbsAttributeValue.newAttributeValue(COMPANY_NAME+ACL_SUFFIX));
                DbsAccessControlList aclNew = dbsLibrarySession.createPublicObject(aclDef);
                aclNew.setOwnerByName(userNew.getName());
                aclNew.grantAccess(aceDef);
                aclNew.setOwnerByName(userNew.getName());        
                dbsGroup.setAcl(aclNew);
                companyDir.setAcl(aclNew);
                companyDir.setOwnerByName(userNew.getName());
                timesheetDir.setAcl(aclNew);
                timesheetDir.setOwnerByName(userNew.getName());
                resumeDir.setAcl(aclNew);
                resumeDir.setOwnerByName(userNew.getName());
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
              dbsLibrarySession.disconnect();
          }catch(DbsException e){e.printStackTrace();}
       
       
      }
  }
}
