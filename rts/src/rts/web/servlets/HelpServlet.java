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
 * $Id: HelpServlet.java,v 1.1.1.1 2005/05/26 12:32:52 suved Exp $
 *****************************************************************************
 */
package rts.web.servlets;
/* Java API */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;

/**
 *	Purpose:  A Servlet that is used to initialize Help Servlet, if configured as a
 *            load-on-startup servlet in a web application.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class HelpServlet extends oracle.help.web.OHWController {
    Logger logger= Logger.getLogger("DbsLogger");
    HttpSession httpSession=null;    
    public void init(ServletConfig cfg)  {
        super.init(cfg);        
        logger.debug("Inside help init");
        logger.info("Help Servlet loaded...");
         
        try {
            
        } catch (Exception e) {
            logger.debug("Help Servlet failed to initialize: " + e.toString());
           
        }
    }    

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           // System.out.println("1");
        //httpSession = request.getSession(false);
        //System.out.println("2");
        //if(httpSession!=null){
           super.doPost(request,response);
         //  System.out.println("3");
       // }else
       // {   System.out.println("4");
        //    response.sendRedirect("session_expired_4_pop_up.jsp");
        //    System.out.println("5");
            
        //}
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //System.out.println("6");
        //httpSession = request.getSession(false);   
       // System.out.println("7");
        //if(httpSession!=null){
       // System.out.println("8");
           super.doGet(request,response);
         //  System.out.println("9");
       // }else
      //  {
      //  System.out.println("10");
        //    response.sendRedirect("session_expired_4_pop_up.jsp");
        //    System.out.println("11");
           
        //}
    }

}
