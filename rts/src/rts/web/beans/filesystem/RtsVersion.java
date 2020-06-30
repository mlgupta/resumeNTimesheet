package rts.web.beans.filesystem;

/*rts package references */
import rts.beans.*;
import rts.web.beans.filesystem.DateHelperForFileSystem;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;

//Java API
import java.io.*;
import java.util.*;

/* Oracle API */
import oracle.ifs.common.IfsException;
//import oracle.ifs.adk.filesystem.*;
//import oracle.ifs.beans.*;
//import oracle.ifs.common.*;
//import oracle.ifs.examples.api.utils.*;

//Struts API
import org.apache.log4j.*;

public class RtsVersion  {


    public static final String STATUS_CHECKEDOUT = "Checked Out";
    public static final String STATUS_CHECKEDIN = "Checked In";
    public static final String STATUS_CREATED = "Created";

    private DbsLibrarySession dbsLibrarySession = null;     //DbsLibrarySession Object
    private Logger logger = null;
    private Locale locale = null;
    private String[] replacementValues;
    private DbsTransaction dbsTransaction = null;
    private static String DATE_FORMAT="MM/dd/yyyy HH:mm:ss";
    private DateHelperForFileSystem dateHelper = null;
    //private String revisionComment = null;

    public RtsVersion(DbsLibrarySession dbsLibrarySession) {
        this.dbsLibrarySession = dbsLibrarySession;
        dateHelper = new DateHelperForFileSystem();
        //this.revisionComment = revisionComment;
        //Initialize logger
        logger = Logger.getLogger("DbsLogger");
        //use default if locale not set
        locale = Locale.getDefault(); //
        replacementValues = new String[5];
    }


	/**
	 * Make a DbsPublicObject Versioned
	 * Making a PO versioned involves creating a DbsVersionDescription
	 * The DbsVersionDescription is part of a DbsVersionSeries
	 * The DbsVersionSeries is part of a DbsFamily. 
   * Every versioned object has one and only one Family
	 *
	 * @param pubObjIds		    Id of the DbsPublicObject to be versioned
	 * @param currentFolderId	The parent folder id for the new family
   * @param revisionComment The initial revision comment
	 * @return		            The family of the Po that is versioned
	 *
	 * @exception DbsException,IOException if operation fails.
	 */

    public DbsFamily makeVersioned( Long pubObjIds , Long currentFolderId ,String revisionComment) 
    throws DbsException , IOException{
      
      DbsPublicObject dbsPublicObject = null;
      
      DbsFolder dbsFolder = null;
      
      DbsVersionDescriptionDefinition dbsVdd = null;
      
      try{      
      
        dbsFolder = (DbsFolder)dbsLibrarySession.getPublicObject(currentFolderId);
      
        dbsPublicObject = dbsLibrarySession.getPublicObject(pubObjIds);

        if (dbsPublicObject.isVersioned()){

          logger.debug(dbsPublicObject.getName() + " is already versioned");
          
          logger.debug("family name: "+dbsPublicObject.getFamily().getName());
          
          logger.debug("makeVersioned() request ignored");

          return null;

        }
        
        
        dbsVdd = createVersionDescriptionDefinition(dbsPublicObject,revisionComment);
      
      }catch( DbsException dbsEx ){
        logger.error("DbsException : "+dbsEx.getMessage());
        dbsEx.printStackTrace();
      }
      
      return makeVersioned(dbsPublicObject,dbsFolder,dbsVdd);
    }

    
	/**
	 * Make a DbsPublicObject versioned
	 *
	 * @param dbsPublicObject		DbsPublicObject to be versioned
	 * @param dbsFolder	        The parent folder for the new family
	 * @param dbsVdd		        Version description definition to be used
	 * @return 		              DbsFamily of the dbsPublicObject that is versioned 
	 *
	 * @exception DbsException,IOException if operation fails.
	 */

    
    private DbsFamily makeVersioned( DbsPublicObject dbsPublicObject , DbsFolder dbsFolder, 
          DbsVersionDescriptionDefinition dbsVdd) throws DbsException , IOException {
    
      DbsVersionDescription dbsVd = null;
      DbsVersionSeries dbsVs = null;
      DbsFamily dbsFam = null;
            
      dbsVd = (DbsVersionDescription)dbsLibrarySession.createPublicObject(dbsVdd);
      dbsVs = dbsVd.getVersionSeries();
      dbsFam = dbsVd.getFamily();

      logger.debug(dbsPublicObject.getName()+" is now versioned");
      logger.debug(getDisplayName(dbsFam) + " created");
      logger.debug(getDisplayName(dbsVs) + " created");
      logger.debug(getDisplayName(dbsVd) + " created");
      //DbsFileSystem dbsFs = new DbsFileSystem(dbsLibrarySession);
      
      if(dbsFolder!=null){
        
        try{
          dbsFolder.removeItem(dbsPublicObject);
          //dbsFs.delete(dbsPublicObject);
          logger.debug("publicObject removed successfully...");
        }catch( DbsException dbsError ){
          dbsError.printStackTrace();
        }
        try{
          if( dbsFam!=null ){
            dbsFolder.addItem(dbsFam);
          }else{
            logger.debug("dbsFam is null");
          }
        }catch( DbsException dbsEx ){
          logger.error("Unable to add family objectto folder...");
          dbsEx.printStackTrace();
        }
        
      }
      
      return dbsFam;
      
    }
    
	/**
	 * Creating a DbsVersionDescription
	 * The DbsVersionDescription is part of a DbsVersionSeries
	 * The DbsVersionSeries is part of a Dbsfamily. Every versioned object
	 * has one and only one family
	 *
	 * @param dbsPO 		      DbsPublicObject to be versioned
	 * @param revisionComment The initial revision comment
	 * @exception DbsException,IOException if operation fails.
	 */
    
    public DbsVersionDescriptionDefinition createVersionDescriptionDefinition( 
           DbsPublicObject dbsPO, String revisionComment)throws DbsException ,
           IOException{
      
      DbsVersionDescriptionDefinition dbsVdd = null;
      DbsVersionSeriesDefinition dbsVsd = null;
      DbsFamilyDefinition dbsFd = null;
      String dbsPOName = null;
      String vdname = null;
      String vsname = null;
      String faname = null;
      String description = null;
		      
      try{
        
        dbsPOName = dbsPO.getName().trim();
        
        logger.debug("dbsPOName: "+dbsPOName);
        
        vdname = "VD"+dbsPOName;
        logger.debug("vdname: "+vdname);
        vsname = "VS"+dbsPOName;
        logger.debug("vsname: "+vsname);
        faname = "Fam"+dbsPOName;
        logger.debug("faname: "+faname);
        description = dbsPO.getDescription();
        logger.debug("description : "+description);
        
        dbsVdd = new DbsVersionDescriptionDefinition(dbsLibrarySession);
        
        dbsVdd.setAttribute(DbsVersionDescription.NAME_ATTRIBUTE,
                            DbsAttributeValue.newAttributeValue(vdname));
                
        dbsVdd.setAttribute(DbsVersionDescription.REVISIONCOMMENT_ATTRIBUTE,  
                            DbsAttributeValue.newAttributeValue(revisionComment));
        
        dbsVdd.setAttribute(DbsVersionDescription.DESCRIPTION_ATTRIBUTE,  
                            DbsAttributeValue.newAttributeValue(description));

        dbsVdd.setAttribute(DbsVersionDescription.ACL_ATTRIBUTE,  
                            DbsAttributeValue.newAttributeValue(dbsPO.getAcl()));
        
        dbsVdd.setPublicObject(dbsPO);
        
        logger.debug("dbsVersionDescriptionDefinition PO set successfully...");
        
        dbsVsd = new DbsVersionSeriesDefinition(dbsLibrarySession);
        
        dbsVsd.setAttribute(DbsVersionSeries.NAME_ATTRIBUTE,
                            DbsAttributeValue.newAttributeValue(vsname));

        dbsVsd.setAttribute(DbsVersionSeries.DESCRIPTION_ATTRIBUTE,
                            DbsAttributeValue.newAttributeValue(description));
        
        dbsVsd.setAttribute(DbsVersionSeries.ACL_ATTRIBUTE,
                            DbsAttributeValue.newAttributeValue(dbsPO.getAcl()));
        
        dbsVdd.setVersionSeriesDefinition(dbsVsd);
        
        logger.debug("dbsVersionDescriptionDefinition VSDef set successfully...");
        
        dbsFd = new DbsFamilyDefinition(dbsLibrarySession);
        
        dbsFd.setAttribute(DbsFamily.NAME_ATTRIBUTE,
                           DbsAttributeValue.newAttributeValue(faname));

        dbsFd.setAttribute(DbsFamily.DESCRIPTION_ATTRIBUTE,
                           DbsAttributeValue.newAttributeValue(description));

        dbsFd.setAttribute(DbsFamily.ACL_ATTRIBUTE,
                           DbsAttributeValue.newAttributeValue(dbsPO.getAcl()));
        
        dbsVsd.setFamilyDefinition(dbsFd);
        
        logger.debug("dbsVersionSeriesDefinition FamilyDef set successfully...");
        
        dbsVdd.setOwnerBasedOnPublicObjectOption(true);
        
        logger.debug("dbsVersionDescriptionDefinition completed successfully...");
        
      }catch( DbsException dbsError ){
        
        logger.error("DbsError: "+dbsError.getErrorMessage());
        
        dbsError.printStackTrace();
        
      }
      
      return dbsVdd;
      
    }


    /**
     * Check out the DbsPublicObject that is passed in
     *
     * @param dbsPO		The PO to be checked out
     * @param comment	The check out comment for the version
     *
     * @exception DbsException if operation fails.
     */
  
    public void checkOut( DbsPublicObject dbsPO , String comment )
    throws DbsException {
      
      //Get the primary version series of this dbsPO
      DbsVersionSeries dbsVs = getPrimaryVersionSeries(dbsPO);
      DbsVersionDescription dbsVd = null;
      DbsPublicObject dbsLastPO = null;
      logger.debug("Checking out "+dbsPO.getName());
      //reserve it
      if( dbsVs != null ){
        logger.debug("Version Series ACL : "+dbsVs.getAcl().getName());
        logger.debug("dbsVersion series obtained successfully...");
        dbsVd = dbsVs.getLastVersionDescription();
        dbsLastPO = dbsVd.getDbsPublicObject();
        //dbsLastPO.setAcl(dbsVs.getAcl());
        dbsVs.reserveNext(null,comment);
        logger.debug(getDisplayName(dbsVs)+" has been checked out...");
        displayLockState(dbsLastPO,dbsVd,dbsVs);
        
      }else{
        logger.debug(getDisplayName(dbsPO)+" cannot be checked out(not versioned)");
      }
      
    }
    
  
  
    /**
     * cancel the check out of the Publicobject that is passed in
     *
     * @param	The dbsPO to be checked out
     *
     * @exception DbsException if operation fails.
     */
    public void cancelCheckout(DbsPublicObject dbsPO) throws DbsException{
  
      // VS must exist and be reserved by the current user
      DbsVersionSeries dbsVs = getPrimaryVersionSeries(dbsPO);
      DbsDirectoryUser user = dbsLibrarySession.getUser();
      if (dbsVs == null){
        logger.debug(getDisplayName(dbsPO) + " is not versioned; cancelCheckout() ignored");
      }
      // if it is not reserved
      else if (!dbsVs.isReserved()){
        // we could have just tried the unReserve, and checked for error
        // 34603, but instead just log the inevitable failure
        logger.debug(getDisplayName(dbsVs) + " is not reserved; cancelCheckout() ignored");
      }
      // if it is not reserved by the current user
      else if (!user.equals(dbsVs.getReservor())){
        // we could have just tried the unReserve, and checked for error
        // 34602, but instead just log the inevitable failure
        logger.debug(getDisplayName(dbsVs) + " is not reserved by current user; cancelCheckout() ignored");
      }
      else{
        // unreserve the version series
        dbsVs.unReserve();
        logger.debug(getDisplayName(dbsVs)+ ": check out is cancelled");
  
        DbsVersionDescription dbsVd = dbsVs.getLastVersionDescription();
  
        // show the lockstate of the versioning objects
        displayLockState(dbsPO, dbsVd, dbsVs);
      }
    }
  
  
  
    /**
     * Check in the Publicobject that is passed in.  Assumes that the
     * Primary VersionSeries is reserved by the current user.
     *
     * @param family 	The family for the po to be checked in
     * @param comment	The comment for the check in
     * @param po		The PublicObject to be used for the new version
     *
     * @exception IfsException if operation fails.
     */
    public DbsVersionDescription checkIn(DbsFamily dbsFam, String comment, 
      DbsPublicObject dbsPO) throws DbsException
    {
      // Get the primaryversion series of this family
      DbsVersionSeries dbsVs = getPrimaryVersionSeries(dbsFam);
      DbsVersionDescription dbsLastVd = dbsVs.getLastVersionDescription();
      DbsPublicObject dbsLastPO = dbsLastVd.getDbsPublicObject();
  
      // If the specified PublicObject is non-null, then this will be the
      // next version.
      // If the specified PublicObject is null, then the checkin will
      // fail
  
      if (dbsPO == null){
        logger.debug("Checkin failed for " + getDisplayName(dbsFam) 
          + " as no PublicObject is specified as the next version");
        return null;
      }
  
      String poname = dbsPO.getName();
      logger.debug("dbsPO ACL is : "+dbsPO.getAcl().getName());
      // derive the name of the VersionDescription; by default, let it
      // have the same names as the original PublicObject
      String vdname = poname;
  
      // create a new VersionDescription Definition
      DbsVersionDescriptionDefinition dbsVdd = 
        new DbsVersionDescriptionDefinition(dbsLibrarySession);
      
      dbsVdd.setAttribute(DbsVersionDescription.REVISIONCOMMENT_ATTRIBUTE,
                          DbsAttributeValue.newAttributeValue(comment));
      dbsVdd.setAttribute(DbsVersionDescription.NAME_ATTRIBUTE,
                          DbsAttributeValue.newAttributeValue(vdname));
  
      // set the next version
      dbsVdd.setPublicObject(dbsPO);
      
      // the new version is secured by the family too
      dbsPO.setSecuringPublicObject(dbsFam);
  
      // create the new VersionDescription 
      DbsVersionDescription dbsVd = dbsVs.newVersion(dbsVdd);
      dbsVd.setAcl(dbsPO.getAcl());
      dbsFam.setDescription(dbsPO.getDescription());
      dbsLastVd = dbsVs.getLastVersionDescription();
      dbsVd.setName(dbsPO.getName());
      dbsVd.setDescription(dbsPO.getDescription());
      
      logger.debug(" dbsVd Name : "+dbsVd.getName()+
                   " dbsVd ACL : "+dbsVd.getAcl().getName()+
                   " dbsVd Create Date : "+
                   dateHelper.format(dbsVd.getCreateDate(),DATE_FORMAT)+
                   " dbsVd LastMod Date : "+
                   dateHelper.format(dbsVd.getLastModifyDate(),DATE_FORMAT)+
                   " dbsFam description : "+dbsFam.getDescription());
      
      logger.debug(getDisplayName(dbsVd) +" created as a result of checkin()");
  
      // show the lockstate of the objects
      displayLockState(dbsLastPO, dbsLastVd, dbsVs);
  
      return dbsVd;
    }

  /**
  * Purpose : To list versions of a specific document 
  * @param  : docId whose versions are to be viewed 
  * @return : ArrayList of various versions
  */    
  

    public ArrayList getDocumentHistoryDetails(Long docId) throws DbsException{
        //docid = 158299
        DocumentHistoryDetail dhd = null;
        ArrayList documentHistoryDetails = new ArrayList();
        DbsVersionSeries dbsVersionSeries;
        DbsVersionDescription[] dbsVersionDescriptions; 
        DbsPublicObject dbsPublicObject;
        DbsFamily dbsFamily;
        DbsFileSystem dbsFileSystem;
        try{
            dbsPublicObject = dbsLibrarySession.getPublicObject(docId);
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            if(dbsPublicObject.getClassname().equals(DbsFamily.CLASS_NAME)){
                dbsFamily = (DbsFamily)dbsPublicObject;
                dbsVersionSeries = dbsFamily.getPrimaryVersionSeries();
                dbsVersionDescriptions = dbsVersionSeries.getVersionDescriptions();

                if(dbsFileSystem.isCheckedOut(dbsFamily)){
                    dhd = new DocumentHistoryDetail();
                    
                    dhd.setVersionNumber(
                      dbsVersionSeries.getLastVersionDescription().getVersionNumber() + 1);
                    
                    dhd.setDocId(null);
                    dhd.setDocName(dbsFamily.getName());
                    
                    dhd.setVersionDate(
                      GeneralUtil.getDateForDisplay(dbsVersionSeries.getReservationDate(),locale));
                    
                    dhd.setUserName(dbsVersionSeries.getReservor().getName());
                    dhd.setComment(dbsVersionSeries.getReservationComment());                    
                    dhd.setActionType(STATUS_CHECKEDOUT);
                    documentHistoryDetails.add(dhd);
                }
                for(int index = dbsVersionDescriptions.length - 1 ; index >= 0 ; index--){
                    dhd = new DocumentHistoryDetail();
                    
                    dhd.setVersionNumber(dbsVersionDescriptions[index].getVersionNumber());
                    dbsPublicObject = dbsVersionDescriptions[index].getDbsPublicObject();
                    dhd.setDocId(dbsPublicObject.getId());
                    dhd.setDocName(dbsFamily.getName());
                    
                    dhd.setVersionDate(
                      GeneralUtil.getDateForDisplay(dbsPublicObject.getCreateDate(),locale));
                    
                    dhd.setUserName(dbsPublicObject.getCreator().getName());
                    dhd.setComment(dbsVersionDescriptions[index].getRevisionComment());
                    dhd.setActionType(STATUS_CHECKEDIN);
                    documentHistoryDetails.add(dhd);
                }
                dhd = (DocumentHistoryDetail)
                        documentHistoryDetails.get(documentHistoryDetails.size() -1);
                      
                dhd.setActionType(STATUS_CREATED);
            }
        }catch(DbsException dbsException){
            throw dbsException;
        }
        return documentHistoryDetails;
    }

    /**
     * Purpose : To delete a document from the document history
     * @param  : Long id of the document to be deleted
     * @return : void
     */    
    public void deleteDocHistory(Long id) throws DbsException, Exception{
        DbsPublicObject dbsPublicObject = null;
        try{
            dbsPublicObject = dbsLibrarySession.getPublicObject(id);
            DbsFamily dbsFamily = dbsPublicObject.getFamily();
            DbsVersionSeries vs = dbsFamily.getPrimaryVersionSeries();
            DbsVersionDescription[] vd = vs.getVersionDescriptions();
            for(int index = 0; index < vd.length; index ++){
                if(vd[index].getDbsPublicObject().getId().longValue() == 
                    dbsPublicObject.getId().longValue()){
                    
                    //if vd is not latest version description then free it
                    
                    if(!vd[index].isLatestVersionDescription()){
                    
                        logger.info("Version {" + vd[index].getVersionNumber() +
                                    "} of {" + dbsPublicObject.getName() + 
                                    "} deleted successfully");
                        vd[index].free();
                    }
                    break;
                }
            }
        }catch(DbsException dbsException){
            throw dbsException;
        }
    }

    /**
    * Purpose : To rollback the history to a point where the document exist
    * @param  : Long id of the document to be deleted
    * @return : void
    */    
    public void rollbackDocHistory(Long id) throws DbsException, Exception{
        DbsPublicObject dbsPublicObject = null;
        try{
            dbsPublicObject = dbsLibrarySession.getPublicObject(id);
            DbsFamily dbsFamily = dbsPublicObject.getFamily();
            DbsVersionSeries vs = dbsFamily.getPrimaryVersionSeries();
            DbsVersionDescription[] vd = vs.getVersionDescriptions();
            for(int index = 0; index < vd.length; index ++){
                if(vd[index].getVersionNumber() > dbsPublicObject.getVersionNumber()){
                
                    logger.info("Version {" + vd[index].getVersionNumber() + 
                                "} of {" + dbsPublicObject.getName() + 
                                "} deleted successfully");        
                                
                    vd[index].free();
                }
            }
        }catch(DbsException dbsException){
//            DbsException dex = new DbsException(ex);
            throw dbsException;
        }
    }

  /**
  * Purpose : To list details of a specific version of a document 
  * @param  : familyId and docId 
  * @return : DocumentHistoryDetail
  */    
    
    public DocumentHistoryDetail getVersionedDocProperty(Long familyId, Long docId)
          throws DbsException{
          
        DocumentHistoryDetail dhd = null;
        DbsVersionSeries dbsVersionSeries;
        DbsVersionDescription[] dbsVersionDescriptions; 
        DbsPublicObject dbsPublicObject;
        DbsFamily dbsFamily;
        try{
            dbsFamily = (DbsFamily)dbsLibrarySession.getPublicObject(familyId);
            dbsVersionSeries = dbsFamily.getPrimaryVersionSeries();
            dbsVersionDescriptions = dbsVersionSeries.getVersionDescriptions();
            dhd = new DocumentHistoryDetail();
            
            if(docId.longValue() == 0){
            
                dhd.setVersionNumber(
                  dbsVersionSeries.getLastVersionDescription().getVersionNumber() + 1);
                
                dhd.setDocId(null);
                
                dhd.setDocName(dbsFamily.getName());
                dhd.setVersionDate(
                  GeneralUtil.getDateForDisplay(dbsVersionSeries.getReservationDate(),locale));
                
                dhd.setUserName(dbsVersionSeries.getReservor().getName());
                dhd.setComment(dbsVersionSeries.getReservationComment());
                dhd.setActionType(STATUS_CHECKEDOUT);
            
            }else{
                for(int index = dbsVersionDescriptions.length - 1 ; index >= 0 ; index--){
                    
                    dbsPublicObject = dbsVersionDescriptions[index].getDbsPublicObject();
                    
                    if(dbsPublicObject.getId().longValue() == docId.longValue()){
                        
                        dhd.setVersionNumber(
                          dbsVersionDescriptions[index].getVersionNumber());
                        
                        dhd.setDocId(dbsPublicObject.getId());
                        dhd.setDocName(dbsPublicObject.getName());
                        
                        logger.debug("Description: "
                                    +dbsPublicObject.getDescription());
                                    
                        dhd.setDescription(dbsPublicObject.getDescription().substring(1));
                        
                        dhd.setVersionDate(
                          GeneralUtil.getDateForDisplay(dbsPublicObject.getCreateDate(),locale));
                        
                        dhd.setUserName(dbsPublicObject.getCreator().getName());
                          dhd.setComment(dbsVersionDescriptions[index].getRevisionComment());
                        
                        dhd.setCandidateName(
                          dbsPublicObject.getAttribute("NAME1").getString(dbsLibrarySession));
                        
                        dhd.setCandidateEmailId(
                          dbsPublicObject.getAttribute("EMAIL").getString(dbsLibrarySession));
                        
                        dhd.setCandidatePhnNo(
                          dbsPublicObject.getAttribute("PHONE1").getString(dbsLibrarySession));
                        
                        dhd.setCommunicationSkills(
                          dbsPublicObject.getAttribute("COMMUNICATION_SKILL").getString(dbsLibrarySession));
                          
                        dhd.setCandidateAddress(
                          dbsPublicObject.getAttribute("ADDRESS").getString(dbsLibrarySession));
                          
                        if(index == 0){
                            dhd.setActionType(STATUS_CREATED);
                        }else{
                            dhd.setActionType(STATUS_CHECKEDIN);
                        }
                        break;
                    }
                }
            }
        }catch(DbsException dbsException){
            throw dbsException;
        }
        return dhd;
    }
    
    
    
    
    /**
     * Return the primary version series of the PublicObject.
     * Return null if the object isn't versioned.
     *
     * @param po	Publicobject 
     *
     * @exception DbsException if operation fails.
     */
  
    public DbsVersionSeries getPrimaryVersionSeries( DbsPublicObject dbsPO )
    throws DbsException {
      
      DbsVersionSeries dbsVs = null;
      DbsFamily dbsFam = null;
      
      if( dbsPO instanceof DbsFamily ){
        
        dbsFam = (DbsFamily)dbsPO;
        
      }else{
        
        dbsFam = dbsPO.getFamily();
        
      }
      // Get the primary version series of this family
      // The  primary version series is the most active/desirable
      // version series to be used in versioning operations;
      
      if( dbsFam != null ){
        
        dbsVs = dbsFam.getPrimaryVersionSeries();
        logger.debug("Primary Version Series ACL : "+dbsVs.getAcl().getName());
        logger.debug("PrimaryVersionSeries obtained successfully...");
        
      }
       
      return dbsVs; 
      
    }
    /**
     * Get the display string for an object, of the form
     * <class name> '<object name>'.  For example, 
     * this method would return "FOLDER 'Root Folder'" for an
     * input of the root folder.
     *
     * @param dbsLo		the DbsLibraryObject
     * @return			  the display string
     */	
  
    
    public String getDisplayName(DbsLibraryObject dbsLo){
      
      String displayName = null;
      
      try{
      
        if (dbsLo != null){
          displayName = dbsLo.getClassObject().getName()
            + " '" + dbsLo.getName() + "'";
        }else{
        displayName = "<null object>";
      }
        
      }catch( DbsException dbsError ){
        logger.error("dbsError: "+dbsError.getMessage());
        dbsError.printStackTrace();
      }catch(Exception error ){
        logger.error("error: "+error.getMessage());
        error.printStackTrace();
      }
      
      return displayName;
      
    }
    
    
    /**
     * Display the Lockstate of the 4 versioning objects
     * Family, Primary Versionseries, LastVersionDescription
     * and PublicObject associated with the Last VD
     *
     * @param po Publicobject of the Last VersionDescription
     * @param vd Last VersionDescription of the series
     * @param vs Primary VersionSeries of the Family
     *
     * @exception IfsException if operation fails.
     *
     */
    private void displayLockState(DbsPublicObject dbsPO,DbsVersionDescription dbsVd,
                 DbsVersionSeries dbsVs) throws DbsException {
                 
      DbsFamily f = dbsVs.getFamily();
  
      boolean isfamlocked = f.isLocked();
      boolean isvslocked = dbsVs.isLocked();
      boolean isvdlocked = dbsVd.isLocked();
      boolean ispolocked = dbsPO.isLocked();
      
      logger.debug("\tFamily " + f.getName() + "'s LockState is " + isfamlocked);
  
      logger.debug("\tVersionSeries " + dbsVs.getName() + "'s LockState is " + isvslocked);
  
      logger.debug("\tVersionDescription " + dbsVd.getName() + "'s LockState is " + isvdlocked);
  
      logger.debug("\tPublicObject " + dbsPO.getName() + "'s LockState is " + ispolocked);
    }
    
}