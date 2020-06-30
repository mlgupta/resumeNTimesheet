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
 * $Id: ParseXMLTagUtil.java,v 1.1.1.1 2005/05/26 12:32:49 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.utility;
/* java API */
import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.*;
import java.net.*;
import java.sql.*;
import java.util.*;
/* DOM API */
import org.w3c.dom.*;
import org.w3c.dom.Node;
/* Oracle XML Parser API */
import oracle.xml.parser.schema.*;
import oracle.xml.parser.v2.*;

public class ParseXMLTagUtil  {

  private  XMLDocument xmlDoc=null;

  public ParseXMLTagUtil(){
   }

  public ParseXMLTagUtil(String fileName){
      try{
         DOMParser dmParser = new DOMParser();      
         URL url = createURL(fileName);
         dmParser.setErrorStream(System.err);
         dmParser.showWarnings(true);
         dmParser.parse(url);
         xmlDoc = dmParser.getDocument();
         //System.err.println("parsed MESSAGE parsetag successfully");
       }catch(DOMException DomExp){
          System.err.println("Exception in constructer(DOM Exception:)"+DomExp.toString());
       }catch(XMLParseException ex) {
          System.err.println("Exception in constructer"+ex.toString());
       }catch(Exception e){
         System.err.println("Error while establshing connection "+e.toString());
       }
  }

  public  String getValue(String sTAG,String pattern){
       try{
         String sPattern="";
         //sPattern="/XMLTag[child::"+sTAG+"]";
         sPattern="/"+pattern+"/*["+sTAG+"]";
         //sPattern="/XMLTag/*["+sTAG+"]";
          XMLNode connNode  = (XMLNode) xmlDoc.selectSingleNode(sPattern);
          if (connNode != null) {
               String sValue     = connNode.valueOf(sTAG);
               //System.err.println("Tag :"+sTAG+" "+"sValue :"+sValue);
               //System.out.println("Tag :"+sTAG+" "+"sValue :"+sValue);
               return  sValue;
          } else {
            System.err.println("Value for TAG "+sTAG +" not Found  in MESSAGE");
          
               return null;
          }
       }catch(Exception e){
         System.err.println("Exception while getting value from XML "+e.toString());
       
         return null;
      }
  }  // end getNodeValue();


  private  URL createURL(String fileName)  {
        URL url = null;
        try
        {
           url = new URL(fileName);
        }
        catch (MalformedURLException ex)
        {
           File f = new File(fileName);
           try
           {
              String path = f.getAbsolutePath();
              String fs = System.getProperty("file.separator");
              if (fs.length() == 1)
              {
                 char sep = fs.charAt(0);
                 if (sep != '/')
                    path = path.replace(sep, '/');
                 if (path.charAt(0) != '/')
                    path = '/' + path;
              }
              path = "file://" + path;
              url = new URL(path);
           }
           catch (MalformedURLException e)
           {
              System.err.println("Cannot create url for: " + fileName);
           
           }
        }
        return url;  
  }
}
