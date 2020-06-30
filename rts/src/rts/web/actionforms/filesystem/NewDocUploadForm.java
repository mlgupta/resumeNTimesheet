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
 * $Id: NewDocUploadForm.java,v 1.3 2005/10/03 07:19:08 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.filesystem;

/**
 *	Purpose: To store the values of the html controls of
 *           doc_upload_new.jsp. 
 * 
 * @author              Suved Mishra
 * @version             1.0 
 * 	Date of creation:   13-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*java API*/
import java.util.*;
import java.util.ArrayList;
import javax.servlet.http.*;
/*Struts API*/
import org.apache.struts.upload.FormFile;
import org.apache.log4j.*;
import org.apache.struts.action.*;


public class NewDocUploadForm extends ActionForm {
    private String txtPath;                   /* target folder path for file upload */
    private Map fileMap = new HashMap();      /* hashmap for storing file objects  */
    private Map fileDescMap = new HashMap();  /* hashmap for storing file desc */
    private FormFile fleFile;                 /* formfile obj */
    private String txaFileDesc;               /* corressponding file desc */
    
    /**
     * Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
      public void reset(ActionMapping mapping, HttpServletRequest request) {
          super.reset(mapping, request);
      }

      /**
       * Validate all properties to their default values.
       * @param mapping The ActionMapping used to select this instance.
       * @param request The HTTP Request we are processing.
       * @return ActionErrors A list of all errors found.
       */
      public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
          return super.validate(mapping, request);
      }

    /**
     * Purpose   :  Returns  txtPath.
     * @return   :  String 
     */
      public String getTxtPath() {
          return txtPath;
      }

    /**
     * Purpose   : Sets the value of txtPath.
     * @param    : newTxtPath Value of txtPath from the form
     */
      public void setTxtPath(String newTxtPath) {
          txtPath = newTxtPath;        
      }

    /**
     * Purpose   :  Returns  fleFile.
     * @return   :  FormFile 
     */
      public FormFile getFleFile(int index) {
        return (FormFile) fileMap.get( new Integer( index ) );
      }

    /**
     * Purpose   : Sets the value of fleFile.
     * @param    : index,newFleFile from the form
     */
      public void setFleFile(int index ,FormFile newFleFile) {
          fileMap.put( new Integer( index ), newFleFile );
      }

    /**
     * Purpose   :  Returns  fleFiles.
     * @return   :  FormFile[] 
     */
      public FormFile[] getFleFiles(){
        return (FormFile[]) fileMap.values().toArray( new FormFile[fileMap.size()] );
      }

    /**
     * Purpose   :  Returns  txaFileDesc.
     * @param    :  index
     * @return   :  String 
     */
      public String getTxaFileDesc(int index) {
        return  (String) fileDescMap.get( new Integer( index ) );
      }

    /**
     * Purpose   :  Returns  txaFileDescs.
     * @return   :  String[] 
     */          
      public String[] getTxaFileDescs() {
        return  (String[]) fileDescMap.values().toArray( new String[fileDescMap.size()] );
      }

    /**
     * Purpose   : Sets the value of txaFileDesc.
     * @param    : index,newTxaFileDesc from the form
     */
      public void setTxaFileDesc(int index ,String newTxaFileDesc) {
        fileDescMap.put( new Integer( index ), newTxaFileDesc );
      }
       
      public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            strTemp += "\n\ttxtPath : " + txtPath;            
        }
        return strTemp;
    }
}