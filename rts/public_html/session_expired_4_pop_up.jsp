<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="title.SessionExpired" /></title>
<link href="themes/main.css" rel="stylesheet" type="text/css">
</head>
<body style="margin:0px" >
<table border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8" height="100%" width="100%" >
<tr>
<td  align="center" >
<div style="width:90%">
<table border="0" cellpadding="1" cellspacing="1" >
  <tr>
    <td align="center" width="45px"><div class="imgWarning"></div></td>
    <td class="tabText">
    <bean:message key="session.expired.popUp" />
    </td>
  </tr>
</table>
</div>
</td>
</tr>
</table>
</body>
</html>
