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
 * $Id: TotalSizeFoldersDocs.java,v 1.3 2005/10/04 07:53:15 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;

/**
 *	Purpose: To hold information regarding size of folder and document
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:   26-08-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class TotalSizeFoldersDocs  {
    private long size;                   // size of folder/doc
    private int folderCount;             // number of subfolders within a folder  
    private int documentCount;           // number of documents within a folder
    private int folderDocCount;          // total folder doc count    

    public TotalSizeFoldersDocs() {
    }

  /**
   * Purpose   :  Returns  size.
   * @return   :  long 
   */
    public long getSize() {
        return size;
    }

  /**
   * Purpose   : Sets the value of size.
   * @param    : newSize Value of size from the form
   */
    public void setSize(long newSize) {
        size = newSize;
    }

  /**
   * Purpose   :  Returns  folderCount.
   * @return   :  int 
   */
    public int getFolderCount() {
        return folderCount;
    }

  /**
   * Purpose   : Sets the value of folderCount.
   * @param    : newFolderCount Value of folderCount from the form
   */
    public void setFolderCount(int newFolderCount) {
        folderCount = newFolderCount;
    }

  /**
   * Purpose   :  Returns  documentCount.
   * @return   :  int 
   */
    public int getDocumentCount() {
        return documentCount;
    }

  /**
   * Purpose   : Sets the value of documentCount.
   * @param    : newDocumentCount Value of documentCount from the form
   */
    public void setDocumentCount(int newDocumentCount) {
        documentCount = newDocumentCount;
    }

  /**
   * Purpose   :  Returns  folderDocCount.
   * @return   :  int 
   */
    public int getFolderDocCount() {
        return folderDocCount;
    }

  /**
   * Purpose   : Sets the value of folderDocCount.
   * @param    : newFolderDocCount Value of folderDocCount from the form
   */
    public void setFolderDocCount(int newFolderDocCount) {
        folderDocCount = newFolderDocCount;
    }
}
