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
 * $Id: ContextListener.java,v 1.2 2005/10/20 09:56:53 suved Exp $
 *****************************************************************************
 */
package rts.web.listeners; 

/* rts package references */
import rts.beans.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
import rts.web.beans.utility.ParseXMLTagUtil;
/* Java API */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;

/**
 *	Purpose: This class is used to  dispose CMSDK Library service in correct manner.
 *           This is a listener class whose contextInitialized() and contextDestroyed() methods are 
 *           called corrospondingly whenever the servlet context is loaded into the memory and destroyed.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */ 
public class ContextListener implements ServletContextListener
{
   ServletContext servletContext;
   Logger logger= Logger.getLogger("DbsLogger");

   /**
     * Purpose : To provide definition for contextInitialized(ServletContextEvent) function in ServletContextListener
     *           interface.This function is called whenever the servlet context is loaded.
     * @param  : sce - ServletContextEvent 
     * 
     */
   public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing Servlet Context");
        servletContext = sce.getServletContext();
        logger.info("Initializing Servlet Context Complete");
   }

   /**
     * Purpose : To provide definition for contextDestroyed(ServletContextEvent) function in ServletContextListener
     *           interface.This function is called whenever the servlet context is destroyed.
     *           Here the CMSDK Library Service is disposed off as soon as servlet context is destroyed.
     * @param  : sce - ServletContextEvent 
     * 
     */ 
   public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Destroying Servlet Context...");
        servletContext = sce.getServletContext();
        String prefix =  servletContext.getRealPath("/");
        String relPath=prefix+"WEB-INF"+File.separator+"params_xmls"+File.separator+"GeneralActionParam.xml";
        ParseXMLTagUtil parseUtil= new ParseXMLTagUtil(relPath);
        DbsLibraryService dbsLibraryService=null;
        String ifsService = parseUtil.getValue("IfsService","Configuration"); 
        String ifsSchemaPassword = parseUtil.getValue("IfsSchemaPassword","Configuration"); 
       
        try{
            if(DbsLibraryService.isServiceStarted(ifsService)){
                logger.info(ifsService + " is running");
                logger.info(ifsService + " going to stop");
                DbsLibrarySession sysLibSession = (DbsLibrarySession)
                                                servletContext.getAttribute(
                                                "sysLibSession");
                if( sysLibSession != null ){
                  sysLibSession.disconnect();
                }
                dbsLibraryService = DbsLibraryService.findService("IfsDefaultService");
                dbsLibraryService.dispose(ifsSchemaPassword);
                logger.info("Library Service '"+ifsService+"' successfully disposed.");
            }
        }catch(DbsException dbsException){
            ExceptionBean exceptionBean = new ExceptionBean(dbsException);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
        }                  
        logger.info("Destroying Servlet Context complete");
   }

  
}
