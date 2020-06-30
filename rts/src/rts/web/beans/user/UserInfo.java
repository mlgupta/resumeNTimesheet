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
 * $Id: UserInfo.java,v 20040220.18 2005/05/18 13:38:37 rajan Exp $
 *****************************************************************************
 */
package rts.web.beans.user; 

/**
 *	Purpose: To store the Logged in User information.
 *  @author              Jeetendra Prasad
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
import rts.beans.*;

//Java API
import java.util.*;

//Struts API
import org.apache.log4j.*;

public class UserInfo{
    private String userID;
    private DbsLibrarySession dbsLibrarySession;
    protected boolean admin;
    private boolean systemAdmin;
    private Locale locale=null;
    private Logger logger;
    private TimeZone timeZone=null;
    private String charSet=null;
    private String language=null;
    private DbsLibrarySession adminLibrarySession;
    private String davPath;
    private double allocatedQuota;
    private double usedQuota;
    private String resumeFolderName;
    private String timesheetFolderName;
    private String personalFolderName;
    
    public UserInfo() {
      this.resumeFolderName="resumes";
      this.timesheetFolderName="timesheets";
      this.personalFolderName="personal";
    }

    /**
     * Purpose : Returns ID of the User.
     * @return : String
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Purpose : Sets userID of the User.
     * @param  : newUserID Value of userID 
     */
    public void setUserID(String newUserID) {
        userID = newUserID;
    }

    /**
     * Purpose : Returns Session for the Logged-In User.
     * @return : LibrarySession
     */
    public DbsLibrarySession getDbsLibrarySession() {
        return dbsLibrarySession;
    }

    /**
     * Purpose : Sets LibrarySession for the User.
     * @param  : newDbsLibrarySession Value of dbsLibrarySession
     */
    public void setDbsLibrarySession(DbsLibrarySession newDbsLibrarySession) {
        dbsLibrarySession = newDbsLibrarySession;
    }

    /**
     * Purpose   : Sets isAdmin when the User is Admin.
     * @returns  : boolean.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Purpose : Sets User as Admin when the user is Admin User.
     * @param  : newAdmin Value of admin.
     */
    public void setAdmin(boolean newAdmin) {
        admin = newAdmin;
    }

    /**
     * Purpose   : Sets isSystemAdmin when the User is System Admin.
     * @returns  : boolean.
     */
    public boolean isSystemAdmin() {
        return systemAdmin;
    }

    /**
     * Purpose : Sets User as SystemAdmin when the user is system Admin User.
     * @param  : newSystemAdmin Value of systemAdmin.
     */
    public void setSystemAdmin(boolean newSystemAdmin) {
        systemAdmin = newSystemAdmin;
    }

    /**
     * Purpose : Returns Locale for Logged-In the User.
     * @return : locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Purpose : Sets the Locale for the Logged-In User.
     * @param  : newLocale Value of locale.
     */
    public void setLocale(Locale newLocale) {
        locale = newLocale;
    }

    /**
     * Purpose : Returns Logger for Logging the Activity.
     * @return : logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Purpose : Sets the Logger for the Logged-In User.
     * @param  : newLogger Value of logger.
     */
    public void setLogger(Logger newLogger) {
        logger = newLogger;
    }

    /**
     * Purpose : Returns Timezone for the Logged-In User.
     * @return : TimeZone.
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Purpose : Sets the timeZone for the Logged-In User.
     * @param  : newTimeZone Value of timeZone.
     */
    public void setTimeZone(TimeZone newTimeZone) {
        timeZone = newTimeZone;
    }

    /**
     * Purpose : Returns Charset for the Logged-In User.
     * @return : String
     */
    public String getCharSet() {
        return charSet;
    }

    /**
     * Purpose : Sets the charSet for the Logged-In User.
     * @param  : newCharSet Value of charSet.
     */
    public void setCharSet(String newCharSet) {
        charSet = newCharSet;
    }

    /**
     * Purpose : Returns language for the Logged-In User.
     * @return : String
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Purpose : Sets the language for the Logged-In User.
     * @param  : newLanguage Value of language.
     */
    public void setLanguage(String newLanguage) {
        language = newLanguage;
    }
    
    /**
     * Purpose : Returns davPath for the Logged-In User.
     * @return : String
     */
    public String getDavPath(){
        return davPath;
    }
  
    /**
     * Purpose : Sets the davPath for the Logged-In User.
     * @param  : newDavPath Value of davPath.
     */
    public void setDavPath(String davPath) {
        this.davPath = davPath;
    }

    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            strTemp += "\n\tuserID : " + userID;
            strTemp += "\n\tadmin : " + admin;
            strTemp += "\n\tsystemAdmin : " + systemAdmin;
            strTemp += "\n\tresumeFolderName : "+resumeFolderName;
            strTemp += "\n\ttimesheetFolderName : "+timesheetFolderName;
            strTemp += "\n\tpersonalFolderName : "+personalFolderName;
            strTemp += "\n\tlocale : " + locale;
            strTemp += "\n\tlogger : " + logger.getName();
            strTemp += "\n\ttimeZone : " + timeZone;
            strTemp += "\n\tcharSet : " + charSet;
            strTemp += "\n\tlanguage : " + language;
            strTemp += "\n\tdavPath : " + davPath;
        }
        return strTemp;
    }

    public double getAllocatedQuota() {
      return allocatedQuota;
    }
  
    public void setAllocatedQuota(double allocatedQuota) {
      this.allocatedQuota = allocatedQuota;
    }
  
    public double getUsedQuota() {
      return usedQuota;
    }
  
    public void setUsedQuota(double usedQuota) {
      this.usedQuota = usedQuota;
    }
  
    public String getResumeFolderName() {
      return resumeFolderName;
    }
  
    public void setResumeFolderName(String resumeFolderName) {
      this.resumeFolderName = resumeFolderName;
    }
  
    public String getTimesheetFolderName() {
      return timesheetFolderName;
    }
  
    public void setTimesheetFolderName(String timesheetFolderName) {
      this.timesheetFolderName = timesheetFolderName;
    }
  
    public String getPersonalFolderName() {
      return personalFolderName;
    }
  
    public void setPersonalFolderName(String personalFolderName) {
      this.personalFolderName = personalFolderName;
    }
}
