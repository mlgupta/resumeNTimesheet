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
 * $Id: QuartzInitializerServlet.java,v 1.2 2005/07/07 07:21:14 suved Exp $
 *****************************************************************************
 */
package rts.web.servlets;
/* rts package references */
import rts.web.beans.utility.ParseXMLTagUtil;
/* Java API */
import java.io.IOException;
import java.io.*;
import java.net.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/* Struts API */
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.apache.log4j.*;

/**
 *	Purpose:  A Servlet that is used to initialize Quartz Scheduler, if configured as a
 *            load-on-startup servlet in a web application.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :    
 * 	Last Modfied Date:    
 */

public class QuartzInitializerServlet extends HttpServlet {

    Logger logger= Logger.getLogger("DbsLogger");

    public void init(ServletConfig cfg) throws javax.servlet.ServletException {
        super.init(cfg);
        String prefix =  cfg.getServletContext().getRealPath("/");
        String relPath= prefix+"WEB-INF"+File.separator+"params_xmls"+File.separator+"QuartzInitializerParam.xml";

        ParseXMLTagUtil parseUtil= new ParseXMLTagUtil(relPath);
        
        String quartzFile = parseUtil.getValue("quartz-init-file","Configuration");
        String smtpHost= parseUtil.getValue("smtp-host","Configuration");
        String smtpUserId = parseUtil.getValue("smtp-userId","Configuration");
        String smtpPassword = parseUtil.getValue("smtp-password","Configuration");
        String authenticate = parseUtil.getValue("authenticate","Configuration");
        logger.info("Quartz Initializer Servlet loaded, initializing Scheduler...");

        try {
            //System.out.println((new File(new URI("")).listFiles())[0].getAbsolutePath());
            String schedulerPath=prefix+quartzFile;
            StdSchedulerFactory stFact= new StdSchedulerFactory(schedulerPath);
            Scheduler sched=stFact.getScheduler();
            sched.getContext().put("smtp-host",smtpHost);
            sched.getContext().put("smtp-userId",smtpUserId);
            sched.getContext().put("smtp-password",smtpPassword);
            sched.getContext().put("authenticate",authenticate);
            sched.start();
           
            cfg.getServletContext().setAttribute("scheduler",sched);
            cfg.getServletContext().setAttribute("smtpHost",smtpHost);
            cfg.getServletContext().setAttribute("schedulerPath",schedulerPath);
            
            logger.info("Quartz Initialized and Started successfully");
        } catch (Exception e) {
            logger.info("Quartz Scheduler failed to initialize: " + e.toString());
            throw new ServletException(e);
        }
    }

    public void destroy() {
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            if (sched != null) sched.shutdown();
        } catch (Exception e) {
            logger.error("Quartz Scheduler failed to shutdown cleanly: " + e.toString());
            e.printStackTrace();
        }

        logger.info("Quartz Scheduler successful shutdown.");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

}
