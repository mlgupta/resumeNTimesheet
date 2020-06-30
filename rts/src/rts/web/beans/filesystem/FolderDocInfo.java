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
 * $Id: FolderDocInfo.java,v 1.11 2005/10/04 07:53:15 suved Exp $
 *****************************************************************************
 */

package rts.web.beans.filesystem;
/*rts package references */
import rts.beans.DbsLibrarySession;
import rts.beans.*;
/* Java API */
import java.util.*;
import java.util.ArrayList;
/* Struts API */
import org.apache.log4j.*;
/**
 *	Purpose: To hold information regarding folder and document
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:   20-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class FolderDocInfo  {
    
    private Long homeFolderId;
    private Long currentFolderId;
    private String currentFolderPath;
    private String jsFileName;
    private boolean backButtonDisabled;
    private boolean forwardButtonDisabled;
    private DbsArrayList nevigationHistory;
    private int nevigationPointer;
    private int pageNumber;
    private int drawerPageNumber;
    private int setNo;
    private int hierarchySetNo;
    private int rtsType;
    public static final byte RESUME_TYPE = 1;
    public static final byte TIMESHEET_TYPE = 2;
    public static final byte RESUME_DRAWER_TYPE = 3;
    public static final byte TIMESHEET_DRAWER_TYPE = 4;
    public static final byte PERSONAL_TYPE = 5;
    public static final byte PERSONAL_DRAWER_TYPE = 6;
    private boolean linkForPOs = false;
    private Integer pageCount;
    private Integer drawerPageCount;
    private Long[] clipBoard;
    private byte clipBoardContent;
    public static final byte CLIPBOARD_CONTENT_CUT = 1;
    public static final byte CLIPBOARD_CONTENT_COPY = 2;
    private int listingType;
    public static final byte SIMPLE_LISTING = 1;
    public static final byte DISPLAY_PAGE = 2;
    public static final byte SEARCH_LISTING = 3;    
    private boolean treeVisible;
    private Long docId;
    private DbsLibrarySession dbsLibrarySession;
    private ArrayList listOfParents;
    private ArrayList listOfParentsId;
    private String rtsBase;
    //private boolean boolChkDocOverWrite = false;
    //private String davPath;
    /**
     *Purpose : constructor for folderDocInfo 
     */
    public FolderDocInfo() {
      nevigationHistory = new DbsArrayList();
      nevigationPointer = 0;
      setNo = 1;
      hierarchySetNo = 1;
      backButtonDisabled = true;
      forwardButtonDisabled = true;
      listingType = FolderDocInfo.SIMPLE_LISTING;
      treeVisible = true;
      listOfParents = new ArrayList();
      listOfParentsId = new ArrayList();
      drawerPageNumber=1;
      linkForPOs = false;
    }

  /**
   * Purpose   :  Returns  currentFolderId.
   * @return   :  Long 
   */
    public Long getCurrentFolderId() {
        return currentFolderId;
    }

  /**
   * Purpose   : Sets the value of currentFolderId.
   * @param    : newCurrentFolderId Value of currentFolderId from the form
   */
    public void setCurrentFolderId(Long newCurrentFolderId) {
        currentFolderId = newCurrentFolderId;
    }

  /**
   * Purpose   :  Returns  homeFolderId.
   * @return   :  Long 
   */
    public Long getHomeFolderId() {
        return homeFolderId;
    }

  /**
   * Purpose   : Sets the value of homeFolderId.
   * @param    : newHomeFolderId Value of homeFolderId from the form
   */
    public void setHomeFolderId(Long newHomeFolderId) {
        homeFolderId = newHomeFolderId;
    }

  /**
   * Purpose   :  Returns  jsFileName.
   * @return   :  String 
   */
    public String getJsFileName() {
        return jsFileName;
    }

  /**
   * Purpose   : Sets the value of jsFileName.
   * @param    : newJsFileName Value of jsFileName from the form
   */
    public void setJsFileName(String newJsFileName) {
        jsFileName = newJsFileName;
    }

  /**
   * Purpose   :  Returns  currentFolderPath.
   * @return   :  String 
   */
    public String getCurrentFolderPath() {
        return currentFolderPath;
    }

  /**
   * Purpose   : Sets the value of currentFolderPath.
   * @param    : newCurrentFolderPath Value of currentFolderPath from the form
   */
    public void setCurrentFolderPath(String newCurrentFolderPath) {
        currentFolderPath = newCurrentFolderPath;
    }

  /**
   * Purpose   :  Returns  backButtonDisabled.
   * @return   :  boolean 
   */
    public boolean isBackButtonDisabled() {
        return backButtonDisabled;
    }

  /**
   * Purpose   : Sets the value of backButtonDisabled.
   * @param    : newBackButtonDisabled Value of backButtonDisabled from the form
   */
    public void setBackButtonDisabled(boolean newBackButtonDisabled) {
        backButtonDisabled = newBackButtonDisabled;
    }

  /**
   * Purpose   :  Returns  forwardButtonDisabled.
   * @return   :  boolean 
   */
    public boolean isForwardButtonDisabled() {
        return forwardButtonDisabled;
    }

  /**
   * Purpose   : Sets the value of forwardButtonDisabled.
   * @param    : newForwardButtonDisabled Value of forwardButtonDisabled from the form
   */
    public void setForwardButtonDisabled(boolean newForwardButtonDisabled) {
        forwardButtonDisabled = newForwardButtonDisabled;
    }

    public void addFolderDocId(Long newFolderDocId){
        //clear the nevigation history from the current position onward if user finishes the 
        //nevigation in the history and starts the nevigation again
/*        
        if (nevigationPointer > 0 && nevigationPointer < nevigationHistory.size() -1 ){
            nevigationHistory.removeRange(nevigationPointer + 1,nevigationHistory.size());
        }
*/        
        nevigationHistory.add(newFolderDocId);
        nevigationPointer = nevigationHistory.size() -1;
        //disable back and forward button when one one path is there in the nevigation history else enable back 
        //button and disable forward button
        if (nevigationHistory.size() == 1){
            backButtonDisabled = true;    
            forwardButtonDisabled = true;    
        }else{
            backButtonDisabled = false;
            forwardButtonDisabled = true;
        }
    }

    /**
	 * Purpose   : To get previous folder path
	 * @param    : no paramater
     * @logic    : this function returns the previous folder path. One point to note is that it will 
     *             disable the back button if previous to previous folder path is not available.
	 */

    public Long getPrevFolderDocId(){
        Long prevFolderDocId = null;
        DbsFolder dbsFolder = null;
        if (nevigationPointer > 0){
            nevigationPointer = nevigationPointer -1;
            prevFolderDocId = (Long)nevigationHistory.get(nevigationPointer);
            forwardButtonDisabled = false;
        }
        if (nevigationPointer == 0){
            backButtonDisabled = true;
        }
        try{
            if(prevFolderDocId != null){
                dbsFolder = (DbsFolder)dbsLibrarySession.getPublicObject(prevFolderDocId);
            }
        }catch(DbsException dex){
            nevigationHistory.remove(nevigationPointer);
            prevFolderDocId = getPrevFolderDocId();
        }catch(Exception ex){
            nevigationHistory.remove(nevigationPointer);
            prevFolderDocId = getPrevFolderDocId();
        }
        return prevFolderDocId;
    }

    /**
	 * Purpose   : To get next folder path
	 * @param    : no paramater
     * @logic    : this function returns the previous folder path. One point to note is that it will 
     *             disable the forward button if next to next folder path is not available.
	 */

    public Long getNextFolderDocId(){
        Long nextFolderDocId = null;
        DbsFolder dbsFolder = null;
        if (nevigationPointer < nevigationHistory.size() -1){
            nevigationPointer = nevigationPointer + 1;
            nextFolderDocId = (Long)nevigationHistory.get(nevigationPointer);
            backButtonDisabled = false;
        }
        if(nevigationPointer == nevigationHistory.size() -1 ){
            forwardButtonDisabled = true;
        }
        try{
            if(nextFolderDocId != null){
                dbsFolder = (DbsFolder)dbsLibrarySession.getPublicObject(nextFolderDocId);
            }else{
                nextFolderDocId = getPrevFolderDocId();
            }
        }catch(DbsException dex){
            nevigationHistory.remove(nevigationPointer);
            nextFolderDocId = getNextFolderDocId();
        }catch(Exception ex){
            nevigationHistory.remove(nevigationPointer);
            nextFolderDocId = getNextFolderDocId();
        }
        
        return nextFolderDocId;
    }

    public void initializeNevigation(){
        nevigationHistory.clear();
        nevigationPointer = 0;
        backButtonDisabled = true;
        forwardButtonDisabled = true;
    }


  /**
   * Purpose   :  Returns  pageNumber.
   * @return   :  int 
   */
    public int getPageNumber() {
        return pageNumber;
    }

  /**
   * Purpose   : Sets the value of pageNumber.
   * @param    : newPageNumber Value of pageNumber from the form
   */
    public void setPageNumber(int newPageNumber) {
        pageNumber = newPageNumber;
    }

  /**
   * Purpose   :  Returns  pageCount.
   * @return   :  Integer 
   */
    public Integer getPageCount() {
        return pageCount;
    }

  /**
   * Purpose   : Sets the value of pageCount.
   * @param    : newPageCount Value of pageCount from the form
   */
    public void setPageCount(Integer newPageCount) {
        pageCount = newPageCount;
    }

  /**
   * Purpose   :  Returns  clipBoard.
   * @return   :  Long[] 
   */
    public Long[] getClipBoard() {
        return clipBoard;
    }

  /**
   * Purpose   : Sets the value of clipBoard.
   * @param    : newClipBoard Value of clipBoard from the form
   */
    public void setClipBoard(Long[] newClipBoard) {
        clipBoard = newClipBoard;
    }

  /**
   * Purpose   :  Returns  clipBoardContent.
   * @return   :  byte 
   */
    public byte getClipBoardContent() {
        return clipBoardContent;
    }

  /**
   * Purpose   : Sets the value of clipBoardContent.
   * @param    : newClipBoardContent Value of clipBoardContent from the form
   */
    public void setClipBoardContent(byte newClipBoardContent) {
        clipBoardContent = newClipBoardContent;
    }

    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            String strArrayValues = "";
            strTemp += "\n\thomeFolderId : " + homeFolderId;
            strTemp += "\n\tcurrentFolderId : " + currentFolderId;
            strTemp += "\n\tcurrentFolderPath : " + currentFolderPath;
            strTemp += "\n\trtsBase : "+rtsBase;
            strTemp += "\n\ttreeVisible : " + treeVisible;
            strTemp += "\n\tsetNo: " + setNo;
            strTemp += "\n\thierarchical Setno: "+hierarchySetNo;
            strTemp += "\n\trtsType: "+rtsType;
            if(nevigationHistory != null){
                strArrayValues = "{";
                for(int index = 0; index < nevigationHistory.size(); index++){
                    strArrayValues += " " + (Long)nevigationHistory.get(index);
                }
                strArrayValues += "}";
                strTemp += "\n\tnavigationHistory : " + strArrayValues;
            }else{
                strTemp += "\n\tnavigationHistory : " + nevigationHistory;
            }

            strTemp += "\n\tnavigationPointer : " + nevigationPointer;
            strTemp += "\n\tbackButtonDisabled : " + backButtonDisabled;
            strTemp += "\n\tforwardButtonDisabled : " + forwardButtonDisabled;
            strTemp += "\n\tpageCount : " + pageCount;
            strTemp += "\n\tpageNumber : " + pageNumber;

            if(clipBoard != null){
                strArrayValues = "{";
                for(int index = 0; index < clipBoard.length; index++){
                    strArrayValues += " " + clipBoard[index];
                }
                strArrayValues += "}";
                strTemp += "\n\tclipBoard : " + strArrayValues;            
            }else{
                strTemp += "\n\tclipBoard : " + clipBoard;
            }
        
            switch(clipBoardContent){
                case FolderDocInfo.CLIPBOARD_CONTENT_COPY:
                    strTemp += "\n\tclipBoardContent : FolderDocInfo.CLIPBOARD_CONTENT_COPY" ;        
                    break;
                case FolderDocInfo.CLIPBOARD_CONTENT_CUT:
                    strTemp += "\n\tclipBoardContent : FolderDocInfo.CLIPBOARD_CONTENT_CUT" ;
                    break;
                default:
                    strTemp += "\n\tclipBoardContent : " + clipBoardContent;
            }

            strTemp += "\n\tlistingType : " + listingType;
            strTemp += "\n\tlinkForPOs: "+linkForPOs;
        }        
        return strTemp;
    }

  /**
   * Purpose   :  Returns  listingType.
   * @return   :  int 
   */
    public int getListingType() {
        return listingType;
    }

  /**
   * Purpose   : Sets the value of listingType.
   * @param    : newListingType Value of listingType from the form
   */
    public void setListingType(int newListingType) {
        listingType = newListingType;
    }

  /**
   * Purpose   :  Returns  treeVisible.
   * @return   :  boolean 
   */
    public boolean isTreeVisible() {
        return treeVisible;
    }

  /**
   * Purpose   : Sets the value of treeVisible.
   * @param    : newTreeVisible Value of treeVisible from the form
   */
    public void setTreeVisible(boolean newTreeVisible) {
        treeVisible = newTreeVisible;
    }
    
  /**
   * Purpose   :  Returns  docId.
   * @return   :  Long 
   */
    public Long getDocId() {
        return docId;
    }

  /**
   * Purpose   : Sets the value of docId.
   * @param    : newDocId Value of docId from the form
   */
    public void setDocId(Long newDocId) {
        docId = newDocId;
    }

  /**
   * Purpose   :  Returns  dbsLibrarySession.
   * @return   :  DbsLibrarySession 
   */
    public DbsLibrarySession getDbsLibrarySession() {
        return dbsLibrarySession;
    }

  /**
   * Purpose   : Sets the value of dbsLibrarySession.
   * @param    : newDbsLibrarySession Value of dbsLibrarySession from the form
   */
    public void setDbsLibrarySession(DbsLibrarySession newDbsLibrarySession) {
        dbsLibrarySession = newDbsLibrarySession;
    }

  /**
   * Purpose   :  Returns  listOfParents.
   * @return   :  ArrayList 
   */
    public ArrayList getListOfParents() {
        return listOfParents;
    }

  /**
   * Purpose   : Sets the value of listOfParents.
   * @param    : newListOfParents Value of listOfParents from the form
   */
    public void setListOfParents(ArrayList newListOfParents) {
        listOfParents = newListOfParents;
    }

  /**
   * Purpose   :  Returns  listOfParentsId.
   * @return   :  ArrayList 
   */
    public ArrayList getListOfParentsId() {
        return listOfParentsId;
    }

  /**
   * Purpose   : Sets the value of listOfParentsId.
   * @param    : newListOfParentsId Value of listOfParentsId from the form
   */
    public void setListOfParentsId(ArrayList newListOfParentsId) {
        listOfParentsId = newListOfParentsId;
    }

  /**
   * Purpose   :  Returns  setNo.
   * @return   :  int 
   */
    public int getSetNo() {
      return setNo;
    }

  /**
   * Purpose   : Sets the value of setNo.
   * @param    : setNo Value of setNo from the form
   */
    public void setSetNo(int setNo) {
      this.setNo = setNo;
    }

  /**
   * Purpose   :  Returns  hierarchySetNo.
   * @return   :  int 
   */
    public int getHierarchySetNo() {
      return hierarchySetNo;
    }

  /**
   * Purpose   : Sets the value of hierarchySetNo.
   * @param    : hierarchySetNo Value of hierarchySetNo from the form
   */
    public void setHierarchySetNo(int hierarchySetNo) {
      this.hierarchySetNo = hierarchySetNo;
    }

  /**
   * Purpose   :  Returns  rtsBase.
   * @return   :  String 
   */
    public String getRtsBase() {
      return rtsBase;
    }

  /**
   * Purpose   : Sets the value of rtsBase.
   * @param    : rtsBase Value of rtsBase from the form
   */
    public void setRtsBase(String rtsBase) {
      this.rtsBase = rtsBase;
    }

  /**
   * Purpose   :  Returns  rtsType.
   * @return   :  int 
   */
    public int getRtsType() {
      return rtsType;
    }

  /**
   * Purpose   : Sets the value of rtsType.
   * @param    : rtsType Value of rtsType from the form
   */
    public void setRtsType(int rtsType) {
      this.rtsType = rtsType;
    }

  /**
   * Purpose   :  Returns  drawerPageCount.
   * @return   :  Integer 
   */
    public Integer getDrawerPageCount() {
      return drawerPageCount;
    }

  /**
   * Purpose   : Sets the value of drawerPageCount.
   * @param    : drawerPageCount Value of drawerPageCount from the form
   */
    public void setDrawerPageCount(Integer drawerPageCount) {
      this.drawerPageCount = drawerPageCount;
    }

  /**
   * Purpose   :  Returns  drawerPageNumber.
   * @return   :  int 
   */
    public int getDrawerPageNumber() {
      return drawerPageNumber;
    }

  /**
   * Purpose   : Sets the value of drawerPageNumber.
   * @param    : drawerPageNumber Value of drawerPageNumber from the form
   */
    public void setDrawerPageNumber(int drawerPageNumber) {
      this.drawerPageNumber = drawerPageNumber;
    }

  /**
   * Purpose   :  Returns  linkForPOs.
   * @return   :  boolean 
   */
    public boolean isLinkForPOs() {
      return linkForPOs;
    }

  /**
   * Purpose   : Sets the value of linkForPOs.
   * @param    : linkForPOs Value of linkForPOs from the form
   */
    public void setLinkForPOs(boolean linkForPOs) {
      this.linkForPOs = linkForPOs;
    }

}


class DbsArrayList extends ArrayList{
    public DbsArrayList(){
    
    }

    public void removeRange(int fromIndex, int toIndex){
        try{
            super.removeRange(fromIndex,toIndex);            
        }catch(Exception ex){
//            System.out.println(ex);
        }
    }
}
