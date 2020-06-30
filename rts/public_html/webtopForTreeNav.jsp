<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%request.setAttribute("topic","user_introduction_html");%>

<% if(session.getAttribute("UserInfo")==null){ %>

  <jsp:forward page="login.jsp" >

       <jsp:param name='sessionExpired' value='true' />

  </jsp:forward>

<% }; %>

<bean:define id="userInfo" name="UserInfo" type="rts.web.beans.user.UserInfo"/>

<html:html>

<head>

<title><bean:message key="title.WebTop" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link href="themes/main.css" rel="stylesheet" type="text/css">

<script src="general.js"></script>

<script language="JavaScript" type="text/JavaScript">

<!--
function MM_preloadImages() { //v3.0

  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
  
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    
}

function MM_swapImgRestore() { //v3.0

  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
  
}

function MM_findObj(n, d) { //v4.01

  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
  
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
    
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  
  if(!x && d.getElementById) x=d.getElementById(n); return x;
  
}

function MM_swapImage() { //v3.0

  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
  
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
   
}
//-->
</script>

</head>

<body onLoad="MM_preloadImages('themes/images/webtop_resumes_over.gif','themes/images/webtop_scheduler_over.gif','themes/images/webtop_mailing_over.gif','themes/images/webtop_faxing_over.gif','themes/images/webtop_timesheet_over.gif','themes/images/webtop_opportunity_over.gif')">

<table id="outermost4centerring" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  	<td align="center" valign="middle">
      <table id="4outermostBorder" class="bdrColor_336633" width="750px" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="210px" valign="bottom"><a href="webtopForTreeNav.jsp"><img src="themes/images/logo_site.gif" width="210" height="77" border="0"></a></td>
                <td align="right" valign="top">&nbsp;</td>
                <td width="200px" valign="top">
                  <table width="200px" border="0" align="right" cellpadding="0" cellspacing="1">
                    <tr>
                      <td width="13">&nbsp;</td>
                      <td width="43"><a href="#" onClick="openWindow('help','Help',650,800,0,0,true);"><img src="themes/images/butt_help.gif" width="43" height="17" border="0"></a></td>
                      <td width="75"><a href="userPreferenceProfileB4Action.do"><img src="themes/images/butt_preferences.gif" width="75" height="17" border="0"></a></td>
                      <td width="64"><a href="logoutAction.do"><img src="themes/images/butt_logout.gif" width="54" height="17" border="0"></a></td>
                    </tr>
                    <tr>
                      <td colspan="4" height="20px"  class="texte8e8e8"></td>
                    </tr>
                    <tr>
                      <td colspan="4" align="right">
                        <span><bean:message key="lbl.WelcomeMsg" />&nbsp;</span>
                        <span style="margin-right:13px" class="textFF8300Bold"><bean:write name="UserInfo" property="userID" /></span>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="4" align="center" class="texte8e8e8" height="5px">&nbsp;</td>
                    </tr>        
                  </table>
                </td>
              </tr>
            </table>
            <table width="750px" border="0" align="center" cellpadding="1" cellspacing="0" style="margin-top:1px">
              <tr>
                <td height="20px" class="bgColor_669966"></td>
              </tr>
            </table>
            <table width="750px" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:1px">
              <tr>
                <td width="750px"><img src="themes/images/human_capital.jpg" width="750" height="130"></td>
              </tr>
              <tr>
                <td height="1px"></td>
              </tr>
              <tr>
                <td height="1px" class="bgColor_669966"></td>
              </tr>
            </table>
            <table width="100%"  border="0" cellspacing="1" cellpadding="1">
              <tr>
                <td height="300px" bgcolor="#e8e8e8">
                  <table width="400px" border="0" align="center" class="bdrColor_669966">
                    <tr>
                      <td colspan="4" height="8px"></td>
                    </tr>
                    <tr>
                      <td width="97"><img src="themes/images/webtop_drawers_disable.gif" alt="Drawers" name="Drawers" width="75" height="60" border="0" style="margin-left:20px"></td>
                      <td width="90"><a href="folderDocTreeMenuClickAction.do?type=resume" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Resumes','','themes/images/webtop_resumes_over.gif',1)"><img src="themes/images/webtop_resumes.gif" alt="Resumes" name="Resumes" width="75" height="60" border="0" style="margin-left:8px"></a></td>
                      <td width="90"><a href="folderDocTreeMenuClickAction.do?type=timesheet" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Timesheets','','themes/images/webtop_timesheet_over.gif',1)"><img src="themes/images/webtop_timesheet.gif" alt="Timesheets" name="Timesheets" width="75" height="60" border="0" style="margin-left:5px"></a></td>
                      <td width="97"><a href="folderDocTreeMenuClickAction.do?type=personal" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('My Documents','','themes/images/webtop_mydoc_over.gif',1)"><img src="themes/images/webtop_mydoc.gif" alt="My Documents" name="My Documents" width="75" height="60" border="0" style="margin-left:2px"></a></td>
                    </tr>
                    <tr>
                      <td colspan="4" height="6px"></td>
                    </tr>
                    <tr>
                      <% if( userInfo.isAdmin() || userInfo.isSystemAdmin() ){%>
                        <td><a href="userListAdminAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('User','','themes/images/webtop_user_over.gif',1)"><img src="themes/images/webtop_user.gif" alt="User" name="User" width="75" height="60" border="0" style="margin-left:20px"></a></td>
                      <%}else{%>
                        <td><img src="themes/images/webtop_user_disable.gif" alt="User" name="User" width="75" height="60" border="0" style="margin-left:20px"></td>
                      <%}%>
                      <td><a href="schedulerListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Scheduler','','themes/images/webtop_scheduler_over.gif',1)"><img src="themes/images/webtop_scheduler.gif" alt="Scheduler" name="Scheduler" width="75" height="60" border="0" style="margin-left:8px"></a></td>
                      <td><a href="mailB4Action.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Mailing','','themes/images/webtop_mailing_over.gif',1)"><img src="themes/images/webtop_mailing.gif" alt="Mailing" name="Mailing" width="75" height="60" border="0" style="margin-left:5px"></a></td>
                      <td><a href="faxB4Action.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Faxing','','themes/images/webtop_faxing_over.gif',1)"><img src="themes/images/webtop_faxing.gif" alt="Faxing" name="Faxing" width="75" height="60" border="0" style="margin-left:2px"></a></td>
                    </tr>
                    <tr>
                      <td colspan="4" height="8px"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html:html>
