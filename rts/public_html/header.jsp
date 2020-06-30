<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="userInfo" name="UserInfo" type="rts.web.beans.user.UserInfo" />

<% double allocatedQuota = 0; 
   double usedQuota = 0; %>

<% if(session.getAttribute("UserInfo")==null){ %>

  <jsp:forward page="login.jsp" >

       <jsp:param name='sessionExpired' value='true' />

  </jsp:forward>

<% }else{ 

  allocatedQuota = userInfo.getAllocatedQuota();
  usedQuota = userInfo.getUsedQuota();
} %>

<link href="themes/main.css" rel="stylesheet" type="text/css" >
<script src="general.js"></script>
<script src="quotaprogressbar.js"></script>
<script>
// ProgressBar Definition
  var allocQuota = <%=allocatedQuota%>;
  var usedQuota = <%=usedQuota%>;
  var scale = allocQuota/170;
  varPGBar = new QuotaProgessBar('varPGBar',170,3,scale);
  varPGBar.pgPatelWidth=usedQuota;
  varPGBar.patelClassName='bgColor_FF8300';  
  
</script>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="210px" valign="bottom"><a href="webtop.jsp"><img src="themes/images/logo_site.gif" width="210" height="77" border="0"></a></td>
    <td align="right" valign="top">
      <div style="margin-top:7px">
        <span><bean:message key="lbl.WelcomeMsg" />&nbsp;</span>
        <span style="margin-right:10px" class="textFF8300Bold"><bean:write name="UserInfo" property="userID" /></span>
      </div>
    </td>
    <td width="200px" valign="top">
      <table width="200px" border="0" align="right" cellpadding="0" cellspacing="1">
        <tr>
          <td width="13">&nbsp;</td>
          <td width="43"><a href="webtop.jsp"><img src="themes/images/butt_home.gif" width="43" height="17" border="0"></a></td>
          <td width="75"><a href="userPreferenceProfileB4Action.do"><img src="themes/images/butt_preferences.gif" width="75" height="17" border="0"></a></td>
          <td width="64"><a href="logoutAction.do"><img src="themes/images/butt_logout.gif" width="54" height="17" border="0"></a></td>
        </tr>
        <tr>
          <td colspan="4" height="20px"  class="texte8e8e8"></td>
        </tr>
        <tr>
          <td colspan="4" align="center" class="textStorage">
            <span class="textStorageBold"><bean:write name="UserInfo" property="usedQuota" /></span> MB Of&nbsp;&nbsp;
            <span class="textStorageBold"><bean:write name="UserInfo" property="allocatedQuota" /></span> MB Used
          </td>
        </tr>
        <tr>
          <td colspan="4" align="center">
            <div class="bgColor_FFFFFF bdrColor_336633" style="width:172px; height:5px; font-size:0px;" title="Storage Allocation Status" >
            <div style="width:170px; height:3px; font-size:0px; margin:1px;">
              <script>
                varPGBar.renderPgBar();
                varPGBar.start();
              </script>
            </div>
            </div>
          </td>
        </tr>
        <tr> 
          <%if( usedQuota > allocatedQuota ){%>
            <td colspan="4" align="center" class="textFF8300Bold" height="5px">Quota exceeded by
              <%=(Math.round(usedQuota - allocatedQuota)*100)/100.00%>MB
            </td>
          <%}else{%>
            <td colspan="4" align="center" class="texte8e8e8" height="5px">&nbsp;</td>
          <%}%>
        </tr>        
        
      </table>
    </td>
  </tr>

</table>

<table id="textMenu" width="100%" border="0" align="center" cellpadding="1" cellspacing="0" style="margin-top:1px">
  <tr>
  <!-- Kindly keep the order of this menu as it is -->
    <td height="20px" class="bgColor_669966 bdrBottomColor_336633 bdrTopColor_336666" align="right" valign="middle">
      <a class="textMenuLink" href='<bean:message key="lnk.BugReport"  />' target='_blank' style="float:right"><bean:message key="mnu.ReportBug" /></a>
      <div class="textMenuSeparator"></div>
      <a class="textMenuLink" href="faxB4Action.do" style="float:right"><bean:message key="mnu.Fax" /></a>
      <div class="textMenuSeparator"></div>
      <a class="textMenuLink" href="mailB4Action.do" style="float:right"><bean:message key="mnu.Mail" /></a>
      <div class="textMenuSeparator"></div>
      <a class="textMenuLink" href="schedulerListAction.do" style="float:right"><bean:message key="mnu.Scheduler" /></a>
      <div class="textMenuSeparator"></div>
      <a class="textMenuLink" href="folderDocMenuClickAction.do?type=personal" style="float:right"><bean:message key="mnu.MyDocs" /></a>      
      <div class="textMenuSeparator"></div>
      <a class="textMenuLink" href="folderDocMenuClickAction.do?type=timesheet" style="float:right"><bean:message key="mnu.TimeSheet" /></a>
      <div class="textMenuSeparator"></div>
      <a class="textMenuLink" href="folderDocMenuClickAction.do?type=resume" style="float:right"><bean:message key="mnu.Resume" /></a>
      <% if( userInfo.isAdmin() || userInfo.isSystemAdmin() ){%>
        <div class="textMenuSeparator"></div>
        <a class="textMenuLink" href="b4DrawerListingAction.do?type=list" style="float:right"><bean:message key="mnu.Drawers" /></a>
        <div class="textMenuSeparator"></div>
        <a class="textMenuLink" href="userListAdminAction.do" style="float:right"><bean:message key="mnu.User" /></a>        
      <%}else{%>
        <div class="textMenuSeparator"></div>
        <a class="textMenuLink" href="b4DrawerListingAction.do?type=personalDrawer" style="float:right"><bean:message key="mnu.Drawers" /></a>      
      <%}%>
    </td>
  </tr>
</table>
