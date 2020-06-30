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
 * $Id: SearchUtil.java,v 1.2 2005/08/23 10:52:50 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.utility;
/* rts package references */ 
import rts.beans.*;
/* Logger API */
import org.apache.log4j.*;

/**
 *	Purpose: To Search Public Objects using Selector.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SearchUtil {
    public SearchUtil() {}
    
    /**
     * Purpose : To find the Library Object.
     * @param  : dbsSession - Library Session.
     * @param  : classname - String.
     * @param  : objectName - String.
     * @return : dbsLibraryObject - Library Object.
     */
    public static DbsLibraryObject findObject(DbsLibrarySession dbsSession,String classname,String objectname) throws DbsException {
        DbsLibraryObject dbsLibraryObject = null;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            dbsSelector.setSearchClassname(classname);
            dbsSelector.setSearchSelection("name='" + objectname + "'");
            int i = dbsSelector.getItemCount();
            if(i ==1) { 
                dbsLibraryObject = dbsSelector.getItems(0);
            }
        }catch(DbsException dbsException) {
            logger.error("findObject : " + dbsException.getErrorMessage());
            throw dbsException;
        }
        return dbsLibraryObject;
    }

    /**
     * Purpose : To find the Library Object.
     * @param  : dbsSession - Library Session.
     * @param  : classname - String.
     * @param  : id - Long.
     * @return : dbsLibraryObject - Library Object.
     */
    public static DbsLibraryObject findObject(DbsLibrarySession dbsSession,String classname,long id) throws DbsException {
        DbsLibraryObject dbsLibraryObject = null;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            dbsSelector.setSearchClassname(classname);
            dbsSelector.setSearchSelection("id=" + id  );
            int i = dbsSelector.getItemCount();
            if(i ==1) { 
                dbsLibraryObject = dbsSelector.getItems(0);
            }
        }catch(DbsException dbsException) {
            logger.error("findObject : " + dbsException.getErrorMessage());
            throw dbsException;
        }
        return dbsLibraryObject;
    }

    /**
     * Purpose : To Search Directory Users.
     * @param  : dbsSession - Library Session.
     * @param  : userName - String.
     * @param  : pageNumber - int.
     * @param  : numRecords - int.
     * @return : users - An Array of directory User.
     */
    public static DbsDirectoryUser[] listUsers(DbsLibrarySession dbsSession,String userName,int pageNumber,int numRecords) throws DbsException{
        DbsDirectoryUser users[]=new DbsDirectoryUser[0];
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            dbsSelector.setSearchClassname(DbsDirectoryUser.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsDirectoryUser.NAME_ATTRIBUTE, true);
            //dbsSelector.setSortSpecification(dbsSortSpecification);
            if(userName==null){
                userName="%";
            }else{
                userName=userName+"%";
            }
                userName = userName.trim();
                userName=userName.toUpperCase();
                userName = userName.replace('*','%');
                userName = userName.replace('?','_');
                
//                dbsSelector.setSearchSelection("name LIKE "+"'"+userName+"%'");                   
                dbsSelector.setSearchSelection("CREATOR='"+dbsSession.getUser().getId()+"' AND upper(name) Like"+"'"+userName+"' order by upper(name)");
            
            int length = dbsSelector.getItemCount();
            if(length!=0){
                int startIndex=(pageNumber*numRecords) - numRecords;
                int endIndex=(startIndex + numRecords) - 1;
                if(endIndex >= length){
                    endIndex=length-1;
                }
                users=new DbsDirectoryUser[(endIndex-startIndex)+1];
                for (int index=0; startIndex <= endIndex; ){
                    users[index++]=(DbsDirectoryUser)dbsSelector.getItems(startIndex);
                    startIndex++;
                }
            }
        }catch(DbsException dbsException) {
            logger.error("listUsers : " + dbsException.getErrorMessage());
        }
        return users;
    }

    /**
     * Purpose : To find number of pages for the given search criterea as per user preference.
     * @param  : dbsSession - Library Session.
     * @param  : userName - String.
     * @param  : numRecords - int.
     * @return : pageCount - int.
     */
    public static int listUsersPageCount(DbsLibrarySession dbsSession,String userName,int numRecords) throws DbsException{
        int pageCount=1;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification(); 
            dbsSelector.setSearchClassname(DbsDirectoryUser.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsDirectoryUser.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);
            if(userName==null){
                userName="%";
            }else{
                userName=userName+"%";
            }
                userName = userName.trim();
                userName=userName.toUpperCase();
                userName = userName.replace('*','%');
                userName = userName.replace('?','_');
            if(userName!=null){
                dbsSelector.setSearchSelection("CREATOR='"+dbsSession.getUser().getId()+"' AND upper(name) Like"+"'"+userName+"' order by upper(name)");
            }
            int itemCount=dbsSelector.getItemCount();
            if(itemCount!=0){
                int pageMod=itemCount%numRecords;
                if(pageMod==0){
                    pageCount=itemCount/numRecords;
                }else{
                    pageCount=(itemCount/numRecords) + 1 ;
                }
            }
        }catch(DbsException dbsException) {
            logger.error("listUsersPageCount : " + dbsException.getErrorMessage());
        }
        return pageCount;
    }

    /**
     * Purpose : To Search Directory Group.
     * @param  : dbsSession - Library Session.
     * @param  : userName - String.
     * @param  : pageNumber - int.
     * @param  : numRecords - int.
     * @return : groups - An Array of Directory Group.
     */
    public static DbsDirectoryGroup[] listGroups(DbsLibrarySession dbsSession,String groupName,int pageNumber,int numRecords) throws DbsException{
        DbsDirectoryGroup groups[]=new DbsDirectoryGroup[0];
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();             
            dbsSelector.setSearchClassname(DbsDirectoryGroup.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsDirectoryGroup.NAME_ATTRIBUTE, true); 
//            dbsSelector.setSortSpecification(dbsSortSpecification);

            if(groupName==null){
                groupName="%";
            }else{
                groupName=groupName+"%";
            }
            groupName = groupName.trim();
            groupName = groupName.toUpperCase();                
            groupName = groupName.replace('*','%');
            groupName = groupName.replace('?','_');                
//                dbsSelector.setSearchSelection("name LIKE "+"'"+groupName+"%'");
            dbsSelector.setSearchSelection("upper(name) Like"+"'"+groupName+"' order by upper(name)");
                
            int length = dbsSelector.getItemCount(); 
            if(length!=0){
                int startIndex=(pageNumber*numRecords) - numRecords;
                int endIndex=(startIndex + numRecords) - 1;
                if(endIndex >= length){
                   endIndex=length-1;
                }
                groups=new DbsDirectoryGroup[(endIndex-startIndex)+1];
                for(int index=0; startIndex <= endIndex; ){
                    groups[index++]=(DbsDirectoryGroup)dbsSelector.getItems(startIndex);
                    startIndex++;      
                }
            }   
        }catch(DbsException dbsException){
            logger.error("listGroups : " + dbsException.getErrorMessage());
        }
        return groups;
    }

    /**
     * Purpose : To find number of pages for the given search criterea as per user preference.
     * @param  : dbsSession - Library Session.
     * @param  : groupName - String.
     * @param  : numRecords - int.
     * @return : pageCount - int.
     */
    public static int listGroupsPageCount(DbsLibrarySession dbsSession,String groupName,int numRecords) throws DbsException {
        int pageCount=1;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                         
            dbsSelector.setSearchClassname(DbsDirectoryGroup.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsDirectoryGroup.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);
            if(groupName!=null){
                groupName = groupName.trim();
                groupName = groupName.replace('*','%');
                groupName = groupName.replace('?','_');
//                dbsSelector.setSearchSelection("name LIKE "+"'"+groupName+"%'");
                dbsSelector.setSearchSelection("name LIKE "+"'"+groupName+"'");
            }
            int itemCount=dbsSelector.getItemCount();
            if(itemCount!=0){
                int pageMod=itemCount%numRecords;
                if(pageMod==0){
                    pageCount=itemCount/numRecords;
                }else{
                    pageCount=(itemCount/numRecords) + 1 ;
                }
            }
        }catch(DbsException dbsException){
            logger.error("listGroupsPageCount : " + dbsException.getErrorMessage());
        }
        return pageCount;
    }

    /**
     * Purpose : To Search Access Control List.
     * @param  : dbsSession - Library Session.
     * @param  : userName - String.
     * @param  : pageNumber - int.
     * @param  : numRecords - int.
     * @param  : forUser - boolean.
     * @return : acls - An Array of Access Control List.
     */
    public static DbsAccessControlList[] listAcls(DbsLibrarySession dbsSession,String aclName,int pageNumber,int numRecords,boolean forUser) throws DbsException{
        DbsAccessControlList acls[]=new DbsAccessControlList[0];
        Logger logger = Logger.getLogger("DbsLogger");
        try{   
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                                                 
            if(forUser){
                dbsSelector.setSearchClassname("SYSTEMACCESSCONTROLLIST");
            }else{
                dbsSelector.setSearchClassname(DbsAccessControlList.CLASS_NAME);   
            }
            dbsSortSpecification.addSortQualifier(DbsAccessControlList.NAME_ATTRIBUTE, true); 
//            dbsSelector.setSortSpecification(dbsSortSpecification);
            if(aclName==null){
                aclName="%";
            }else{
                aclName=aclName + "%";
            }
                aclName = aclName.trim();
                aclName = aclName.toUpperCase();                
                aclName = aclName.replace('*','%');
                aclName = aclName.replace('?','_');                
//                dbsSelector.setSearchSelection("name LIKE "+"'"+aclName+"%'");
                dbsSelector.setSearchSelection("upper(name) LIKE "+"'"+aclName+"' order by upper(name)");

            int length = dbsSelector.getItemCount();   
            if(length!=0){
                int startIndex=(pageNumber*numRecords) - numRecords;
                int endIndex=(startIndex + numRecords) - 1;
                if(endIndex >= length){
                    endIndex=length-1;
                }
                acls=new DbsAccessControlList[(endIndex-startIndex)+1];
                for (int index=0; startIndex <= endIndex; ){
                    acls[index++]=(DbsAccessControlList)dbsSelector.getItems(startIndex);
                    startIndex++;      
                }
            }  
        }catch(DbsException dbsException){
            logger.error("listAcls : " + dbsException.getErrorMessage());
        }
        return acls;
    }

    /**
     * Purpose : To find number of pages for the given search criterea as per user preference.
     * @param  : dbsSession - Library Session.
     * @param  : aclName - String.
     * @param  : numRecords - int.
     * @return : pageCount - int.
     */
    public static int listAclsPageCount(DbsLibrarySession dbsSession,String aclName,int numRecords,boolean forUser) throws DbsException{
        int pageCount=1;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                                                 
            if(forUser){
                dbsSelector.setSearchClassname("SYSTEMACCESSCONTROLLIST");
            }else{
                dbsSelector.setSearchClassname(DbsAccessControlList.CLASS_NAME);   
            }
            dbsSortSpecification.addSortQualifier(DbsAccessControlList.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);
            if(aclName!=null){
                aclName = aclName.trim();
                aclName = aclName.replace('*','%');
                aclName = aclName.replace('?','_');                
//                dbsSelector.setSearchSelection("name LIKE "+"'"+aclName+"%'");
                dbsSelector.setSearchSelection("name LIKE "+"'"+aclName+"'");
            }
            int itemCount=dbsSelector.getItemCount();
            if(itemCount!=0){
                int pageMod=itemCount%numRecords;
                if(pageMod==0){
                    pageCount=itemCount/numRecords;
                }else{
                    pageCount=(itemCount/numRecords) + 1 ;
                }   
            }
        }catch(DbsException dbsException){
            logger.error("listAclsPageCount : " + dbsException.getErrorMessage());
        }
        return pageCount;
    }

    /**
     * Purpose : To Search Access Control List.
     * @param  : dbsSession - Library Session.
     * @param  : aclSelectName - String.
     * @return : acls - An Array of Access Control List.
     */
    public static DbsAccessControlList[] listAclSelect(DbsLibrarySession dbsSession,String aclSelectName) throws DbsException{
        DbsAccessControlList aclselects[]=null;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                                                                                     
            dbsSelector.setSearchClassname(DbsAccessControlList.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsAccessControlList.NAME_ATTRIBUTE, true); 
//            dbsSelector.setSortSpecification(dbsSortSpecification);                                    
            if (aclSelectName==null){
                aclSelectName="%";
            }else{
                aclSelectName=aclSelectName+"%";
            }
            aclSelectName = aclSelectName.trim();
            aclSelectName = aclSelectName.toUpperCase();                
            aclSelectName = aclSelectName.replace('*','%');
            aclSelectName = aclSelectName.replace('?','_');
            dbsSelector.setSearchSelection("upper(name) LIKE "+"'"+aclSelectName+"' order by upper(name)");            
            int length = dbsSelector.getItemCount();  
            aclselects=new DbsAccessControlList[length];
            for (int index = 0; index < length; index++) {
                aclselects[index]=(DbsAccessControlList)dbsSelector.getItems(index);
            }
        }catch(DbsException dbsException) {
            logger.error("listAclSelect : " + dbsException.getErrorMessage());
        }
        return aclselects;
    }
    
    /**
     * Purpose : To Search Permission Bundles.
     * @param  : dbsSession - Library Session.
     * @param  : userName - String.
     * @param  : pageNumber - int.
     * @param  : numRecords - int.
     * @param  : forUser - boolean.
     * @return : permissionBundles - An Array of Permission Bundles.
     */
    public static DbsPermissionBundle[] listPermissionBundles(DbsLibrarySession dbsSession,String permissionBundleName,int pageNumber,int numRecords) throws DbsException{
        DbsPermissionBundle permissionBundles[]=new DbsPermissionBundle[0];
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                                                             
            dbsSelector.setSearchClassname(DbsPermissionBundle.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsPermissionBundle.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);
            if(permissionBundleName==null){
                permissionBundleName = "%";
            }else{
                permissionBundleName = permissionBundleName + "%";            
            }
            permissionBundleName = permissionBundleName.trim();
            permissionBundleName = permissionBundleName.toUpperCase();
            permissionBundleName = permissionBundleName.replace('*','%');
            permissionBundleName = permissionBundleName.replace('?','_');                
//                dbsSelector.setSearchSelection("name LIKE "+"'"+permissionBundleName+"%'");
                dbsSelector.setSearchSelection("name LIKE "+"'"+permissionBundleName+"'");
            int length = dbsSelector.getItemCount(); 
            if(length!=0){
                int startIndex=(pageNumber*numRecords) - numRecords;
                int endIndex=(startIndex + numRecords) - 1;
                if(endIndex >= length){
                   endIndex=length-1;
                }
                permissionBundles=new DbsPermissionBundle[(endIndex-startIndex)+1];
                for (int index=0; startIndex <= endIndex; ){
                  permissionBundles[index++]=(DbsPermissionBundle)dbsSelector.getItems(startIndex);
                  startIndex++;      
                }
            }
        }catch(DbsException dbsException){
            logger.error("listPermissionBundles : " + dbsException.getErrorMessage());
        }
        return permissionBundles;
    }

    /**
     * Purpose : To find number of pages for the given search criterea as per user preference.
     * @param  : dbsSession - Library Session.
     * @param  : permissionBundleName - String.
     * @param  : numRecords - int.
     * @return : pageCount - int.
     */
    public static int listPermissionBundlesPageCount(DbsLibrarySession dbsSession,String permissionBundleName,int numRecords) throws DbsException{
        int pageCount=1;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                                                             
            dbsSelector.setSearchClassname(DbsPermissionBundle.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsPermissionBundle.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);
            if(permissionBundleName!=null){
                permissionBundleName = permissionBundleName.trim();
                permissionBundleName = permissionBundleName.replace('*','%');
                permissionBundleName = permissionBundleName.replace('?','_');                
//                dbsSelector.setSearchSelection("name LIKE "+"'"+permissionBundleName+"%'");
                dbsSelector.setSearchSelection("name LIKE "+"'"+permissionBundleName+"'");
            }
            int itemCount=dbsSelector.getItemCount();
            if(itemCount!=0){
                int pageMod=itemCount%numRecords;
                if(pageMod==0){
                    pageCount=itemCount/numRecords;
                }else{
                    pageCount=(itemCount/numRecords) + 1 ;
                }
            }
        }catch(DbsException dbsException){
            logger.error("listPermissionBundlesPageCount : " + dbsException.getErrorMessage());
        }
        return pageCount;
    }

    /**
     * Purpose : To Search Direcory Users.
     * @param  : dbsSession - Library Session.
     * @param  : userName - String.
     * @return : users - An Array of Directory Users.
     */
    public static DbsDirectoryUser[] listUsers(DbsLibrarySession dbsSession,String userName) throws DbsException{
        DbsDirectoryUser users[]=null;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                                                                         
            dbsSelector.setSearchClassname(DbsDirectoryUser.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsDirectoryUser.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);            
            if(userName == null){
                userName ="%";
            }else{
                userName =userName + "%";            
            }
                userName = userName.trim();
                userName = userName.toUpperCase();
                userName = userName.replace('*','%');
                userName = userName.replace('?','_');            
//                dbsSelector.setSearchSelection("name LIKE "+"'"+userName+"'");
                dbsSelector.setSearchSelection("upper(name) LIKE "+"'"+userName+"' order by upper(name)");
            int length = dbsSelector.getItemCount();  
            users=new DbsDirectoryUser[length];
            for(int index = 0; index < length; index++){
                users[index]=(DbsDirectoryUser)dbsSelector.getItems(index);
            }
        }catch(DbsException dbsException){
            logger.error("listUsers : " + dbsException.getErrorMessage());
        }
        return users;
    }

    /**
     * Purpose : To Search Direcory Group.
     * @param  : dbsSession - Library Session.
     * @return : groups - An Array of Directory Groups.
     */
    public static DbsDirectoryGroup[] listGroups(DbsLibrarySession dbsSession) throws DbsException{
        DbsDirectoryGroup groups[]=null;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                                                                                
            dbsSelector.setSearchClassname(DbsDirectoryGroup.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsDirectoryGroup.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);            
            int length = dbsSelector.getItemCount();  
            groups=new DbsDirectoryGroup[length];
            for (int index = 0; index < length; index++){
                groups[index]=(DbsDirectoryGroup)dbsSelector.getItems(index);
            }
        }catch(DbsException dbsException){
            logger.error("listGroups : " + dbsException.getErrorMessage());
        }
        return groups;
    }

    /**
     * Purpose  : To get the email Profile for User.
     * @param   : dbsSession - Library Session.
     * @param   : user - Directory User.
     * @returns : emailProfile - An Extended User Profile.
     */
    public static DbsExtendedUserProfile getEmailUserProfile(DbsLibrarySession dbsLibrarySession,DbsDirectoryUser user) throws DbsException{
        DbsExtendedUserProfile emailProfile =null;
        DbsSelector dbsSelector = new DbsSelector(dbsLibrarySession);
        dbsSelector.setSearchClassname(DbsExtendedUserProfile.CLASS_NAME);
        dbsSelector.setSearchSelection("DIRECTORYUSER = " + user.getId());
        int length = dbsSelector.getItemCount();  
        for(int index = 0; index < length; index++) {
            DbsExtendedUserProfile extendedProfile=(DbsExtendedUserProfile)dbsSelector.getItems(index);
            if(extendedProfile.getClassname().equals("EMAILUSERPROFILE")){
                emailProfile=extendedProfile;
                break;
            }
        }        
        return emailProfile;
    }

    /**
     * Purpose  : To get the email Profile for User.
     * @param   : dbsSession - Library Session.
     * @returns : permissionBundles - An Array of Permission Bundles.
     */
    public static DbsPermissionBundle[] listPermissionBundles(DbsLibrarySession dbsSession) throws DbsException {
        DbsPermissionBundle permissionBundles[]=null;
        Logger logger = Logger.getLogger("DbsLogger");        
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            dbsSelector.setSearchClassname(DbsPermissionBundle.CLASS_NAME);
            int length = dbsSelector.getItemCount();  
            permissionBundles=new DbsPermissionBundle[length];
            for(int index = 0; index < length; index++){
                permissionBundles[index]=(DbsPermissionBundle)dbsSelector.getItems(index);
            }
        }catch(DbsException dbsEx){
            logger.error(dbsEx.getErrorMessage());
        }
        return permissionBundles;
    }

    /*    public static DbsDirectoryUser[] listUsers(DbsLibrarySession dbsSession) throws DbsException{
        DbsDirectoryUser users[]=null;
        try{
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification(); 
            DbsSelector dbsSelector = new DbsSelector(dbsSession);

            dbsSelector.setSearchClassname(DbsDirectoryUser.CLASS_NAME);

            dbsSortSpecification.addSortQualifier("NAME", true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);
            int length = dbsSelector.getItemCount();  
            users=new DbsDirectoryUser[length];
            for (int index = 0; index < length; index++){
                users[index]=(DbsDirectoryUser)dbsSelector.getItems(index);
            }
        }catch(DbsException dbsEx){
            System.out.println(dbsEx.getErrorMessage());
        }
        return users;
    }*/

//Added by jeet
/*    public static DbsAccessControlList[] listAclSelect(DbsLibrarySession dbsSession) throws DbsException{
        DbsAccessControlList aclselects[]=null;
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                                                                         
            dbsSelector.setSearchClassname(DbsAccessControlList.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsAccessControlList.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);                        
            int length = dbsSelector.getItemCount();  
            aclselects=new DbsAccessControlList[length];
            for (int index = 0; index < length; index++) {
                aclselects[index]=(DbsAccessControlList)dbsSelector.getItems(index);
            }
        }catch(DbsException dbsEx) {
            System.out.println(dbsEx.getErrorMessage());
        }
        return aclselects;
    }
*/


    /**
     * Purpose : To Search Access Control List.
     * @param  : dbsSession - Library Session.
     * @param  : userName - String.
     * @param  : pageNumber - int.
     * @param  : numRecords - int.
     * @return : acls - An Array of Access Control List.
     */
/*    public static DbsAccessControlList[] listAcls(DbsLibrarySession dbsSession,String aclName,int pageNumber,int numRecords) throws DbsException{
        DbsAccessControlList acls[]=new DbsAccessControlList[0];
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                         
            dbsSelector.setSearchClassname(DbsAccessControlList.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsAccessControlList.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);
            if(aclName!=null){
                dbsSelector.setSearchSelection("name LIKE "+"'"+aclName+"%'");
            }
            int length = dbsSelector.getItemCount();   
            if(length!=0){
                int startIndex=(pageNumber*numRecords) - numRecords;
                int endIndex=(startIndex + numRecords) - 1;
                if(endIndex >= length){
                    endIndex=length-1;
                }
                acls=new DbsAccessControlList[(endIndex-startIndex)+1];
                for (int index=0; startIndex <= endIndex; ){
                    acls[index++]=(DbsAccessControlList)dbsSelector.getItems(startIndex);
                    startIndex++;      
                }
            }
        }catch(DbsException dbsException){
            logger.error("listAcls : " + dbsException.getErrorMessage());
        }
        return acls;
    }
*/
/*    public static int listAclsPageCount(DbsLibrarySession dbsSession,String aclName,int numRecords) throws DbsException{
        int pageCount=1;
        Logger logger = Logger.getLogger("DbsLogger");
        try{
            DbsSelector dbsSelector = new DbsSelector(dbsSession);
            DbsSortSpecification dbsSortSpecification = new DbsSortSpecification();                                     
            dbsSelector.setSearchClassname(DbsAccessControlList.CLASS_NAME);
            dbsSortSpecification.addSortQualifier(DbsAccessControlList.NAME_ATTRIBUTE, true); 
            dbsSelector.setSortSpecification(dbsSortSpecification);            
            if(aclName!=null){
                dbsSelector.setSearchSelection("name LIKE "+"'"+aclName+"%'");
            }
            int itemCount=dbsSelector.getItemCount();
            if(itemCount!=0){
                int pageMod=itemCount%numRecords;
                if(pageMod==0){
                    pageCount=itemCount/numRecords;
                }else{
                    pageCount=(itemCount/numRecords) + 1 ;
                }
            }
        }catch(DbsException dbsException){
            logger.error("listAclsPageCount : " + dbsException.getErrorMessage());
        }
        return pageCount;
    }
*/

}
