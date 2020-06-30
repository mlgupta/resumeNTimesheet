<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page  isErrorPage="true" session="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
    Error Page
</title>
<link href="themes/main.css" rel="stylesheet" type="text/css">
</head> 
<body style="margin:0px">
<table id="headerIncluder" width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="96px">
		<!--Header Starts-->
		<jsp:include page="/header.jsp" />
		<!--Header Ends-->
	</td>
  </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" height="498px" width="100%" >
<tr>
<td  align="center" >
<div style="width:90%">
<table border="0" cellpadding="1" cellspacing="1" >
  <tr>
    <td align="center" width="45px"><div class="imgWarning"></div></td>
    <td class="tabText"> 
    <logic:messagesPresent >
              <bean:message key="errors.header"/>
              <html:messages id="error">
                <font color="red"><bean:write name="error"/></font>
              </html:messages>              
            </logic:messagesPresent>           
    </td>
  </tr>
</table>
</div>
</td>
</tr>
</table>


</body>
</html>
