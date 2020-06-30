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
 * $Id: DbsFolder.java,v 1.4 2005/10/20 09:52:59 suved Exp $
 *****************************************************************************
 */
package rts.beans; 



/**

 *	Purpose: The encapsulate the functionality of Folder class provided by CMSDK API.

 * 

 * @author              Mishra Maneesh

 * @version             1.0

 * 	Date of creation:   23-12-2003

 * 	Last Modfied by :     

 * 	Last Modfied Date:    

 */



/* CMSDK API*/

import oracle.ifs.beans .*;

import oracle.ifs.common.*;




public class DbsFolder extends DbsPublicObject{

    private Folder folder=null;

    /** This class name for this class. */

    public static java.lang.String CLASS_NAME = Folder.CLASS_NAME ;

 

    /**

	   * Purpose   : To encapsulate DbsFolder using Folder class

	   * @param    : folder - An Folder Object

	   */

    public DbsFolder(Folder folder) {

        super(folder);

        this.folder = folder;

    }



    /**

	   * Purpose : Used to get the object of class Folder

	   * @return Folder Object

	   */

    public Folder getFolder() {

        return folder;

    }



    /**

     * Purpose  : Returns the Folder Object pointed to by path.

     * @param   : path - Path to the FolderObject

     * @returns : Folder Object

     * @throws  : DbsException - if operation fails

     */

    public DbsFolder findFolderObjectByPath(java.lang.String path) throws DbsException {

        DbsFolder dbsFolder=null;

        try{

            dbsFolder=new DbsFolder((Folder)folder.findPublicObjectByPath(path));

        } catch(IfsException ifsError) {

            throw new DbsException(ifsError);

        }

        return dbsFolder;

    }



    /**

     * Purpose  : Gets the number of items in this Folder, including both documents and subfolders.

     * @returns : the item count

     * @throws  : DbsException - if operation fails

     */

    public int getItemCount() throws DbsException{

        try{

            return this.folder.getItemCount() ;

        }catch(IfsException ifsError){

            throw new DbsException(ifsError);

        }

    }



    /**

     * Purpose  : Gets an array containing this Folder's items. Returns null if no items in this Folder.

     * @returns : the Folder's items. Returns null if no items.

     * @throws  : DbsException - if operation fails

     */

    public DbsPublicObject[] getItems() throws DbsException{

        PublicObject[] publicObjects=null;

        DbsPublicObject[] dbsPublicObjects=null;

        try{

            if(folder.getItemCount() > 0){

                publicObjects=folder.getItems();

                dbsPublicObjects= new DbsPublicObject[publicObjects.length];

                for(int i=0; i<publicObjects.length; i++){

                    if (publicObjects[i] instanceof Folder){

                        dbsPublicObjects[i]=new DbsFolder((Folder)publicObjects[i]);

                    }else if (publicObjects[i] instanceof Document) {

                        dbsPublicObjects[i]=new DbsDocument((Document)publicObjects[i]);

                    }else if (publicObjects[i] instanceof Family) {

                        dbsPublicObjects[i]=new DbsFamily((Family)publicObjects[i]);

                    }else{

                        dbsPublicObjects[i] = new DbsPublicObject(publicObjects[i]);

                    }

                }

            }

        }catch(IfsException ifsError){

            throw new DbsException(ifsError);

        }

        return dbsPublicObjects ;

    }



    /**

     * Purpose  : Gets an array containing this Folder's items. Returns null if no items in this Folder.

     * @param   : index - int

     * @returns : the Folder's items. Returns null if no items.

     * @throws  : DbsException - if operation fails

     */

    public DbsPublicObject getItems(int index) throws DbsException{

          try{

              if (folder.getItems(index) instanceof Folder){

                  return new DbsFolder((Folder)folder.getItems(index));

              }else if (folder.getItems(index) instanceof Document) {

                  return new DbsDocument((Document)folder.getItems(index));

              }else if (folder.getItems(index) instanceof Family ) {

                  return new DbsFamily((Family)folder.getItems(index));

              }else{

                return new DbsPublicObject(folder.getItems(index));

              } 

          }catch(IfsException ifsError){

              throw new DbsException(ifsError);

          }

    }



    /**

     * Purpose  : Gets indication as to whether this folder has any subfolders in it. 

     *            This is used by GUIs as a quick determination of whether or not to indicate that this folder contains any folders. 

     *            The GUI might choose to include a plus sign in the folder's icon if there are subfolders.

     * @returns : the subfolder item count.

     * @throws  : DbsException - if operation fails

     */    

    public boolean hasSubfolders() throws DbsException{

        try{

            return folder.hasSubfolders();  

        }catch(IfsException ifsError){

              throw new DbsException(ifsError);

        }

    }

    /**

     * Purpose  : add item to this folder
     * @returns : void
     * @throws  : DbsException - if operation fails
     */    

  public void addItem(DbsPublicObject item) throws DbsException{
    try{
      if(item != null){
        if( item.getClassname().equalsIgnoreCase(DbsFamily.CLASS_NAME) ){
          //this.folder.addItem(item.getResolvedPublicObject().getPublicObject());
          DbsFamily dbsFam = (DbsFamily)item;
          this.folder.addItem(dbsFam.getIfsFamily());
        }else{
          this.folder.addItem(item.getPublicObject());
        }
      }
    }catch(IfsException ifsError){
        throw new DbsException(ifsError);
    }
  }

    /**

     * Purpose  : add family to this folder
     * @returns : void
     * @throws  : DbsException - if operation fails
     */    

  public void addItem(DbsFamily dbsFam) throws DbsException{
    try{
      if(dbsFam != null){
        System.out.println("Add item for Family");
        this.folder.addItem(dbsFam.getIfsFamily());
      }
    }catch(IfsException ifsError){
        throw new DbsException(ifsError);
    }
  }
  
  public void removeItem( DbsPublicObject item ) throws DbsException{
    try{
      if(item != null){
        this.folder.removeItem(item.getPublicObject());
      }
    }catch(IfsException ifsError){
        throw new DbsException(ifsError);
    }
  
  
  }
  
}
