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
 * $Id: RelayAction.java,v 1.1.1.1 2005/05/26 12:32:12 suved Exp $
 *****************************************************************************
 */
package rts.web.actions;
/* rts package references */
import rts.beans.*;

//Java API
import java.io.*;
import java.util.*;

//Servlet API
import javax.servlet.ServletException;
import javax.servlet.http.*;

//Struts API
import org.apache.commons.beanutils.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.apache.log4j.*;
/**
 *	Purpose: It looks for an "operation" parameter and uses it to find a forward
 *           in the mapping and then uses it.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class RelayAction extends Action {
    Logger logger = Logger.getLogger("DbsLogger");
   /**
    * This is the main action called from the Struts framework.
    * @param mapping The ActionMapping used to select this instance.
    * @param form The optional ActionForm bean for this request.
    * @param request The HTTP Request we are processing.
    * @param response The HTTP Response we are processing.
    */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String operation = request.getParameter("operation");
        HttpSession httpSession = request.getSession(false);

        if(operation.equals("user_new")
          ||operation.equals("user_edit")
          ||operation.equals("user_delete")
          ||operation.equals("user_select_for_log")
          ||operation.equals("page_user")
          ||operation.equals("page_user_select")
          ||operation.equals("search_user")
          ||operation.equals("search_user_select")
          ||operation.equals("cancel_user")) {
              httpSession.setAttribute("radSelect",request.getParameter("radSelect"));
              if(request.getParameter("txtSearchByUserName")!=null){
                  httpSession.setAttribute("txtSearchByUserName",request.getParameter("txtSearchByUserName"));                
              }
              if(operation.equals("search_user_select")
                ||operation.equals("page_user_select")
                ||operation.equals("user_select_for_log")){
                  httpSession.setAttribute("control",request.getParameter("control"));
              }
              httpSession.setAttribute("pageNumber",request.getParameter("txtPageNo"));       
            
        }else if(operation.equals("group_new")
          ||operation.equals("group_edit")
          ||operation.equals("group_delete")
          ||operation.equals("page_group")
          ||operation.equals("page_group_select")
          ||operation.equals("search_group")
          ||operation.equals("search_group_select")) {

            httpSession.setAttribute("radSelect",request.getParameter("radSelect"));
            if(request.getParameter("txtSearchByGroupName")!=null){
                httpSession.setAttribute("txtSearchByGroupName",request.getParameter("txtSearchByGroupName"));                
            }
            if(operation.equals("search_group_select")
              ||operation.equals("page_group_select")){
                httpSession.setAttribute("control",request.getParameter("control"));
            }
            httpSession.setAttribute("pageNumber",request.getParameter("txtPageNo"));       
            
        }else if(operation.equals("acl_new")
          ||operation.equals("acl_edit")
          ||operation.equals("acl_delete")
          ||operation.equals("page_acl")
          ||operation.equals("page_acl_select")
          ||operation.equals("search_acl")
          ||operation.equals("search_acl_select")) {

            httpSession.setAttribute("radSelect",request.getParameter("radSelect"));
            if(request.getParameter("txtSearchByAclName")!=null){
                httpSession.setAttribute("txtSearchByAclName",request.getParameter("txtSearchByAclName"));                
            }
            if(request.getParameter("forUser")!=null){
                if(request.getParameter("forUser").equals("true"));
                httpSession.setAttribute("forUser","true");                
            }
            
            if(operation.equals("search_acl_select")
              ||operation.equals("page_acl_select")){
                httpSession.setAttribute("control",request.getParameter("control"));
            }
            if(!operation.equals("cancel_acl")){               
                httpSession.setAttribute("pageNumber",request.getParameter("txtPageNo"));       
            }
            
        }else if(operation.equals("permission_bundle_new")
          ||operation.equals("permission_bundle_edit")
          ||operation.equals("permission_bundle_delete")
          ||operation.equals("search_permission_bundle") 
          ||operation.equals("page_permission_bundle")) {
            httpSession.setAttribute("radSelect",request.getParameter("radSelect"));
            if(request.getParameter("txtSearchByPermissionBundleName")!=null){
                httpSession.setAttribute("txtSearchByPermissionBundleName",request.getParameter("txtSearchByPermissionBundleName"));                
            }
            httpSession.setAttribute("pageNumber",request.getParameter("txtPageNo"));
            
        }else if(operation.equals("theme_new")
          ||operation.equals("theme_edit")
          ||operation.equals("theme_delete")
          ||operation.equals("search_theme") 
          ||operation.equals("page_theme")) {
            httpSession.setAttribute("radSelect",request.getParameter("radSelect"));
            if(request.getParameter("txtSearchByThemeName")!=null){
                httpSession.setAttribute("txtSearchByThemeName",request.getParameter("txtSearchByThemeName"));                
            }
            httpSession.setAttribute("pageNumber",request.getParameter("txtPageNo"));
        }
        ActionErrors errors=new ActionErrors();
        
        if (operation==null){
            ActionError editError=new ActionError("errors.operation");
            errors.add(ActionErrors.GLOBAL_ERROR,editError);            
        }   
        if (mapping.findForward(operation)==null){
        ActionError editError=new ActionError("errors.operation");
            errors.add(ActionErrors.GLOBAL_ERROR,editError);            
        }
        if(!errors.isEmpty()) {      
            saveErrors(request, errors);
            return (mapping.getInputForward());
        }
        logger.debug("operation: "+operation);
        return mapping.findForward(operation);
    }
}
