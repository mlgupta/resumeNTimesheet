<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script name="javascript">
//This is very important statement many functionality on folder_doc_list.jsp depends on this
    window.name="rts"
</script>
<html>
<head>
<title><bean:message key="title.Login" /></title>
<link href="themes/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>

<html:javascript formName="loginForm" dynamicJavascript="true" staticJavascript="false"/>
<script language="Javascript1.1" src="staticJavascript.jsp"></script>
</head>
<body>
<html:form action="/loginAction" name="loginForm" type="rts.web.actionforms.loginout.LoginForm" focus="userID" 
  onsubmit="return validateLoginForm(this);">
<table>
  <tr>
    <td>
    <script language="JavaScript">
      <logic:messagesPresent>
        <html:messages id="actionMessage" message="true">
            alert("<bean:write  name="actionMessage"/>")
        </html:messages>
          
        <html:messages id="actionError">
            alert("<bean:write  name="actionError"/>")
        </html:messages>
      </logic:messagesPresent>
    
        <logic:present parameter="sessionExpired" >
              alert("<bean:message key="session.expired" />")
        </logic:present>
    </script>
    <script language="JavaScript">
    <!--
    var visitortime = new Date();
    //document.write('<input type="hidden" name="x-VisitorTimeZoneOffset" ');
    if(visitortime) {
    //alert('value="' + visitortime.getTimezoneOffset()/60 + '">');
    }
    else {
    //alert('value="JavaScript not Date() enabled">');
    }// -->
    </script>
    
    </td>
  </tr>
</table>
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
        <tr>
          <td height="300px" valign="top" bgcolor="#e8e8e8">
          <table width="200px" border="0" align="right" class="bdrColor_669966">
            <tr>
              <td colspan="2" height="8px"></td>
            </tr>
            <tr>
              <td width="50px" align="right"><bean:message key="txt.UserID" />:</td>
              <td width="145px">
      				  <html:text property="userID" styleClass="bdrColor_336633" style="width:120px" tabindex="1"></html:text>
              </td>
            </tr>
            <tr>
              <td align="right"><bean:message key="txt.Password" /></td>
              <td><html:password property="userPassword" styleClass="bdrColor_336633" style="width:120px" tabindex="2"></html:password></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td><html:submit property="btnOk" styleClass="buttons" style="width:58px; height:20px" tabindex="3"><bean:message key="btn.Login" /></html:submit>
                <html:reset property="btnReset" styleClass="buttons" style="width:58px; height:20px" tabindex="4"><bean:message key="btn.Reset" /></html:reset>
    				  </td>
            </tr>
            <tr>
              <td colspan="2" height="8px"></td>
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

</html:form>
</body>
</html>