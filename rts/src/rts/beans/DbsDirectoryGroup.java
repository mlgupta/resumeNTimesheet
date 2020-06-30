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
 * $Id: DbsDirectoryGroup.java,v 1.1.1.1 2005/05/26 12:32:03 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of ConnectOptions class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 *	Last Modfied Date:    
 */

/*CMSDK API*/
import oracle.ifs.common.*;
import oracle.ifs.beans.*;

public class DbsDirectoryGroup extends DbsDirectoryObject{
    private DirectoryGroup directoryGroup=null; // to accept object of type DirectoryGroup

    /** A system-set attribute used to ensure no groups owned by a user have duplicate names. */
    public static final java.lang.String OWNERUNIQUENAME_ATTRIBUTE=DirectoryGroup.OWNERUNIQUENAME_ATTRIBUTE;
    /** Class name for this class.Useful for methods that take a class name argument. */    
    public static final java.lang.String CLASS_NAME=DirectoryGroup.CLASS_NAME;
    /** The ContentQuota associated with this group for the purpose of adminstering quota on a group-wise basis. */    
    public static final java.lang.String CONTENTQUOTA_ATTRIBUTE=DirectoryGroup.CONTENTQUOTA_ATTRIBUTE;

    public DbsDirectoryGroup(){}
    
    /**
	   * Purpose : To create DbsConnectOptions using ConnectOptions class
	   * @param  : connectOptions - An ConnectOptions Object  
	   */
    public DbsDirectoryGroup(DirectoryGroup directoryGroup) {
        super(directoryGroup);
        this.directoryGroup=directoryGroup;
    }

    /**
	   * Purpose   : Gets the group's ContentQuota object. Returns null if there is no quota object associated with this group.
	   * @returns  : the ContentQuota assigned to this group
     * @throws   : DbsException - if operation fails
	   */    
    public DbsContentQuota getContentQuota() throws DbsException {
        DbsContentQuota dbsContentQuota;
        ContentQuota contentQuota;
        try {
            contentQuota = directoryGroup.getContentQuota();
            if(contentQuota != null){
                dbsContentQuota=new DbsContentQuota(contentQuota);    
            }else{
                dbsContentQuota = null;
            }
            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsContentQuota;
    }

    /**
	   * Purpose   : Sets the group's ContentQuota attribute.
	   * @param    : dbsContentQuota - the ContentQuota to be associated with this group
     * @throws   : DbsException - if operation fails
	   */
    public void setDbsContentQuota(DbsContentQuota dbsContentQuota) throws DbsException {
        try {
            directoryGroup.setContentQuota(dbsContentQuota.getContentQuota());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose   : Tests whether this group is the World group (the virtual group that contains all users).
	   * @returns  : true if the group is the World group
     * @throws   : DbsException - if operation fails
	   */
    public boolean isWorldGroup() throws DbsException {
        boolean isWorldGroup;
        try {
            isWorldGroup= directoryGroup.isWorldGroup();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isWorldGroup;
    }

    /**
	   * Purpose   : Gets the DirectoryGroup that represents World access
	   * @param    : dbssession - the session
	   * @returns  : the World group
     * @throws   : DbsException - if operation fails
	   */ 
    public static DbsDirectoryGroup getWorldDirectoryGroup(DbsLibrarySession dbsSession) throws DbsException {
        DbsDirectoryGroup dbsWorldDirectoryGroup=null;
        try {
            LibrarySession session=dbsSession.getLibrarySession();
            dbsWorldDirectoryGroup=new DbsDirectoryGroup(session.getWorldDirectoryGroup());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsWorldDirectoryGroup;
    }

    /**
     * Purpose   : Adds a direct member to the group. 
     *           : The member must not be the World group, nor can it already be a member of this group. 
     *           : If the specified DirectoryObject is null, no action will be taken and no exception will be thrown.
     * @param    : dbsMember - the member to add to this DirectoryGroup
     * @throws   : DbsException - if operation fails
     */

    public void addMember(DbsDirectoryUser dbsMember) throws DbsException {
        try {
            DirectoryUser member=dbsMember.getDirectoryUser();
            directoryGroup.addMember(member);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    public void addMember(DbsDirectoryGroup dbsMember) throws DbsException {
        try {
            DirectoryGroup member=dbsMember.getDirectoryGroup();
            directoryGroup.addMember(member);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Adds a set of direct members to the group. 
     *             The membership restrictions are the same as outlined in the addMember method. If any member specified does not qualify as a valid member, the entire operation is not performed. 
     *             If any of the specified DirectoryObjects is null, that array element will be ignored.
     * @param    : dbsMembers - the members to add as direct members to this instance
     * @throws   : DbsException - if operation fails
     */ 
    public void addMembers(DbsDirectoryObject[] dbsMembers) throws DbsException {
        try {
            int memberCount=dbsMembers.length;
            DirectoryObject members[]=new DirectoryObject[memberCount];
            for(int index = 0; index < memberCount ; index++) {
                members[index]= dbsMembers[index].getDirectoryObject();
            }
            directoryGroup.addMembers(members);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Removes a direct member from the group. 
     *           : If the specified DirectoryObject is not a member of this group or is null, no action will be taken and no exception will be thrown.
     * @param    : dbsMember -  the member to remove from this DirectoryGroup
     * @throws   : DbsException - if operation fails
     */    
    public void removeMember(DbsDirectoryUser dbsMember) throws DbsException {
        try {
            DirectoryObject directoryObject=dbsMember.getDirectoryUser();
            directoryGroup.removeMember(directoryObject);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Removes a direct member from the group. 
     *           : The specified member must not be the World group. 
     *           : If the specified DirectoryObject is not a member of this group or is null, no action will be taken and no exception will be thrown.
     * @param    : dbsMember -  the member to remove from this DirectoryGroup
     * @throws   : DbsException - if operation fails
     */
    public void removeMember(DbsDirectoryGroup dbsMember) throws DbsException {
        try {
            DirectoryObject directoryObject=dbsMember.getDirectoryGroup();
            directoryGroup.removeMember(directoryObject);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Removes a set of direct members from the group. 
     *             The member specified must comply with the restrictions outlined in the removeMember method. If any member specified is not valid, the entire operation is not performed. 
     *             If any of the specified DirectoryObjects is not a member of this group or is null, that array element will be ignored.
     * @param    : dbsMembers -  the members to add as direct members to this instance
     * @throws   : DbsException - if operation fails
     */        
    public void removeMembers(DbsDirectoryObject[] dbsMembers) throws DbsException {
        try {
            int memberCount=dbsMembers.length;
            DirectoryObject members[]=new DirectoryObject[memberCount];
            for(int index = 0; index < memberCount ; index++) {
                members[index]= (dbsMembers[index]).getDirectoryObject();
            }
            directoryGroup.removeMembers(members);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose    : Returns array of DirectoryObject that define the list of all members for this instance. 
     *              This list is the full set of DirectoryObject that are members (directly or indirectly) of this DirectoryGroup.
     * @Overrides : getAllMembers in class DirectoryObject           
     * @returns   : array of member DirectoryObject
     * @throws    : DbsException - if operation fails
     */
    public void removeAllMembers() throws DbsException {
        try {            
            directoryGroup.removeMembers(directoryGroup.getAllMembers());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose    : Removes the direct member from the Group
     * @throws    : DbsException - if operation fails
     */
    public void removeDirectMembers() throws DbsException {
        try{ 
            if(directoryGroup.getDirectMembers()!=null){
                directoryGroup.removeMembers(directoryGroup.getDirectMembers());
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns array of DirectoryObject that define the list of all members for this instance. 
     *             This list is the full set of DirectoryObject that are members (directly or indirectly) of this DirectoryGroup.
     * @returns  : array of member DirectoryObject
     * @throws   : DbsException - if operation fails
     */               
    public DbsDirectoryObject[] getAllMembers() throws DbsException {
        DbsDirectoryObject dbsMembers[]=null;
        try {
            int memberCount= (directoryGroup.getAllMembers()).length;
            dbsMembers=new DbsDirectoryObject[memberCount];
            DirectoryObject members[]=directoryGroup.getAllMembers();
            for(int index=0 ; index< memberCount ; index++) {
                if(members[index] instanceof DirectoryUser){
                    dbsMembers[index]=new DbsDirectoryUser((DirectoryUser)members[index]);
                }else if(members[index] instanceof DirectoryGroup){
                    dbsMembers[index]=new DbsDirectoryGroup((DirectoryGroup)members[index]);                                   
                }else{
                    dbsMembers[index]=new DbsDirectoryObject(members[index]);
                }                
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsMembers ;
    }

    /**
     * Purpose   : Gets the array of DirectoryUser that define the list of all DirectoryUser members for this instance. 
     *           : This list is the full set of DirectoryUser that are members (directly or indirectly) of this DirectoryGroup.
     * @param    : index - index into the array of DirectoryObjects
     * @returns  : array of member DirectoryUser
     * @throws   : DbsException - if operation fails
     */
    public DbsDirectoryUser[] getAllUserMembers() throws DbsException {
        DbsDirectoryUser dbsUserMembers[]=null;
        try {
            int memberCount= (directoryGroup.getAllUserMembers()).length;
            dbsUserMembers=new DbsDirectoryUser[memberCount];
            DirectoryUser userMembers[]=directoryGroup.getAllUserMembers();
            for(int index=0 ; index< memberCount ; index++) {
                dbsUserMembers[index]=new DbsDirectoryUser(userMembers[index]);
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsUserMembers ;
    }

    /**
     * Purpose   : Gets the member DirectoryUser at the specified index of this group.
     * @param    : index - index into the array of AllUserMembers
     * @returns  : the requested DirectoryUser
     * @throws   : DbsException - if operation fails
     */    
    public DbsDirectoryUser getAllUserMembers(int index) throws DbsException {
        DbsDirectoryUser dbsUserMember=null;
        try {
            dbsUserMember=new DbsDirectoryUser(directoryGroup.getAllUserMembers(index));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsUserMember;
    }
    
    /**
     * Purpose   : Gets the direct members of this instance.
     * @returns  : array of direct member DirectoryObjects
     * @throws   : DbsException - if operation fails
     */                
   public DbsDirectoryObject[] getDirectMembers() throws DbsException {
        DbsDirectoryObject dbsMembers[]=null;
        try {
            if(!(directoryGroup.getDirectMembers()==null)){
            int memberCount= (directoryGroup.getDirectMembers()).length;                    
            dbsMembers=new DbsDirectoryObject[memberCount];
            DirectoryObject members[]=directoryGroup.getDirectMembers();
            for(int index=0 ; index< memberCount ; index++) {
                if(members[index] instanceof DirectoryUser){
                  dbsMembers[index]=new DbsDirectoryUser((DirectoryUser)members[index]);
                }else if(members[index] instanceof DirectoryGroup){
                  dbsMembers[index]=new DbsDirectoryGroup((DirectoryGroup)members[index]);                                                            
                }else{
                  dbsMembers[index]=new DbsDirectoryObject(members[index]);
                }                
            }
        }
        }catch(IfsException ifsError) {
                throw new DbsException(ifsError);
        }
        return dbsMembers ;
    }

    /**
     * Purpose   : Determines if the specified DirecotryObject is a direct member of the group.
     * @param    : dbsmember - the DirectoryObject that will be checked for membership
     * @returns  : whether the specified DirectoryObject is a member of the group
     * @throws   : DbsException - if operation fails
     */        
    public boolean isDirectMember(DbsDirectoryObject dbsMember) throws DbsException {
        boolean isDirectMember;
        try {
            DirectoryObject member=dbsMember.getDirectoryObject();
            isDirectMember=directoryGroup.isDirectMember(member);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isDirectMember;
    }

    /**
     * Purpose   : Determines if the specified DirecotryObject is a member of the group.
     * @param    : dbsmember - a DirectoryObject
     * @returns  : whether the specified DirectoryObject is a member of the group
     * @throws   : DbsException - if operation fails
     */    
    public boolean isMember(DbsDirectoryObject dbsMember) throws DbsException {
        boolean isMember;
        try {
            DirectoryObject member=dbsMember.getDirectoryObject();
            isMember=directoryGroup.isMember(member);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isMember;
    }

    /**
	   * Purpose : Used to get the object of class DirectoryGroup
	   * @return : DirectoryGroupObject
	   */
    public DirectoryGroup getDirectoryGroup() {
        return this.directoryGroup;
    }
}

/* Deprecated
public DbsDirectoryObject getDirectMembers(int index) throws DbsException
{
   DbsDirectoryObject dbsDirectMember=null;
  try
  {
    dbsDirectMember=new DbsDirectoryObject(directoryGroup.getAllMembers(index));
  }catch(IfsException ifsError)
        {
          throw new DbsException(ifsError);
        }
        return dbsDirectMember;
}
*/

/* Deprecated
 public DbsDirectoryObject getAllMembers(int index) throws DbsException
{
   DbsDirectoryObject dbsMember=null;
  try
  {
    dbsMember=new DbsDirectoryObject(directoryGroup.getAllMembers(index));
  }catch(IfsException ifsError)
        {
          throw new DbsException(ifsError);
        }
        return dbsMember;
}
*/

   /* public void addMember(DbsDirectoryObject dbsMember) throws DbsException {
        try {
            DirectoryObject directoryObject=dbsMember.getDirectoryObject();
            directoryGroup.addMember(directoryObject);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
*/
