<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html>
<head>
<title>
 <bean:message key="title.Problem.in.linkgeneration"/>
</title>
<script src="general.js"></script>
<link href="themes/main.css" rel="stylesheet" type="text/css" >
<script>
    
  function closewindow(){
    window.location.replace("blank.html");
  }
</script>
</head>
<body>
<table id="tabContainer" width="100%"  height="80%" border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<br>
<tr>
<td align="center">
	<table id="tabParent" width="400px"  border="0" cellpadding="0" cellspacing="0">
		<tr>
    	<td>
			<table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0">
      		<tr>
            <td width="5px" class="imgTabLeft"></td>
            <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.Problem.in.linkgeneration" /></div></td>
            <td width="5px" class="imgTabRight"></td>
      		</tr>
    	</table>
		  </td>
  	</tr>
	</table>
	<table width="400px" border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" id="bdrColor_336633">
  <tr>
    <td >
        <table width="100%" border="0"  cellpadding="0" cellspacing="2" align="center">
          <tr><td height="25px" ></td></tr>
          <tr>
              <td align="center" class="tabText" style="color:ff0000" >
                  <html:messages id="actionError">
                    <bean:write  name="actionError"/>
                  </html:messages>             
              </td>
          </tr>
          <tr><td height="15px" ></td></tr>
          <tr>
            <td align="center">
            <html:button property="btnOK" onclick="closewindow()" styleClass="buttons" style="width:70px"><bean:message key="btn.Ok" /></html:button>
            </td>
          </tr>
          <tr><td height="25px" ></td></tr>
        </table>
    </td>
  </tr>
</table>
</td>
</tr>
</table>
</body>
</html>
