import rts.beans.DbsAttributeValue;
import rts.beans.DbsCleartextCredential;
import rts.beans.DbsCollection;
import rts.beans.DbsDirectoryGroup;
import rts.beans.DbsDirectoryUser;
import rts.beans.DbsException;
import rts.beans.DbsLibraryService;
import rts.beans.DbsLibrarySession;
import rts.beans.DbsUserManager;

public class UpdateQuota  {

  public static String SERVICE_NAME = null;
  public static String SERVICE_CONFIGURATION = null;
  public static String SCHEMA_PASSWORD = null;   
  public static String DOMAIN = null;
  public static String SYS_AD_USER=null;
  public static String SYS_AD_PASSWORD =null;
  //public static String COMPANY_NAME=null;
  //public static String COMPANY_FOLDER_NAME=null;
  //public static String MAIL_ADDRESS=null;
  public static String QUOTA="100";
  public static String userName = null;
  public static String password = null;
  DbsLibraryService dbsLibraryService=null;
  DbsLibrarySession dbsLibrarySession=null;
  DbsUserManager userManager=null;

  public UpdateQuota() {
  }

  public static void main(String args[]){
  
    if(args.length!=9){
      System.out.println("Insufficient number of arguments passed");
      System.exit(1);
    }
    SERVICE_NAME=args[0];
    SERVICE_CONFIGURATION=args[1];
    SCHEMA_PASSWORD=args[2];
    DOMAIN=args[3];
    SYS_AD_USER=args[4];
    SYS_AD_PASSWORD=args[5];    
    //COMPANY_NAME=args[6];    
    //MAIL_ADDRESS=args[7];
    QUOTA=args[6];
    userName=args[7];
    password=args[8];
    //COMPANY_FOLDER_NAME="/"+COMPANY_NAME;
    UpdateQuota uq = new UpdateQuota();
    uq.updateQuota();
  }
  public void updateQuota(){
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
      DbsDirectoryUser companyAdmin=null;
      try{
        companyAdmin=(DbsDirectoryUser)userCollection.getItems(userName);
      }catch(DbsException dbEx){
        throw dbEx;
      }
      DbsDirectoryGroup [] listOfGroups = companyAdmin.getAllAncestors();
      DbsDirectoryGroup companyGroup = null;
      String currentQuota = null;
      if( listOfGroups!=null ){
        companyGroup = listOfGroups[0];
        currentQuota = companyGroup.getAttribute("QUOTA").getString(
                                                          dbsLibrarySession);
        System.out.println("Current Quota is :  "+currentQuota);
        System.out.println("Updating Quota now...");
        try{
          companyGroup.setAttribute("QUOTA",
                                    DbsAttributeValue.newAttributeValue(
                                    QUOTA));
          System.out.println("Quota updated successfully!!!");
          System.out.println("Updated Quota is :  "+
                              companyGroup.getAttribute("QUOTA").getString(
                              dbsLibrarySession));
        }catch( DbsException dbsEx ){
          System.out.println("Unable to update Quota. See the dump below...");
          dbsEx.printStackTrace();
        }
      }else{
        System.out.println(" List of Groups cannot be found.Hence quitting!!! ");
      }
      
    }catch (DbsException e) {
      System.out.println(e.getErrorMessage());
      e.printStackTrace();
    }catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }finally{
      try{
        dbsLibrarySession.disconnect();
      }catch(DbsException e){
        e.printStackTrace();
      }
    }
  }

}