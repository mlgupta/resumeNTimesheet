<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message  key="msg.logout.title" /></title>
<link href="themes/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript"></script>
</head>
<body>
<table id="outermost4centerring" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  	<td align="center" valign="middle">
	<table id="4outermostBorder" class="bdrColor_336633" width="750px" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
    	<td>
      <table width="750px" border="0" align="center" cellpadding="1" cellspacing="0">
        <tr>
          <td width="200px"><a href="login.jsp"><img src="themes/images/logo_site.gif" width="210" height="77" border="0"></a></td>
          <td>&nbsp;</td>
          <td width="200px" class="bgColor_669966">		
          <table width="200px" border="0" align="right" cellpadding="1" cellspacing="1">
            <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
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
        <tr bgcolor="#e8e8e8">
          <td height="300px" align="center" class="font13Bold" >
            <bean:message  key="msg.logout.successfully" />
            <html:link href="login.jsp"> <bean:message  key="msg.logout.clickhere" /></html:link> to relogin.
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
</html>
