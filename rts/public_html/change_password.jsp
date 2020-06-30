<%@ page contentType="text/html;charset=UTF-8" language="java" %> 

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>

<head>

<title><bean:message key="title.ChangePassword" /></title>

<link href="themes/main.css" rel="stylesheet" type="text/css">

<script src="general.js"></script>

<html:javascript formName="changePasswordForm" dynamicJavascript="true" staticJavascript="false"/>

<script language="Javascript1.1" src="staticJavascript.jsp"></script>

<script language="Javascript1.1" >



function cancelActionPerformed(){

    window.close();

}



function callPage(thisForm){ 

  if(validateChangePasswordForm(thisForm)){

    if(thisForm.txtPassword.value!=thisForm.txtConfirmPassword.value){

     alert("<bean:message key="errors.password.mismatch"/>");  

     return false;

     }

    thisForm.target=opener.name;

    thisForm.submit() ;

    window.close();

  }

}

</script>

</head>

<body style="margin:0">

<!-- This page contains 2 outermost tables, named 'errorContainer' and 'tabContainer' -->

<table id="errorContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

  <tr>

    <td>

<logic:messagesPresent >

   <bean:message key="errors.header"/>

   <ul>

   <html:messages id="error">

      <li><bean:write name="error"/></li>

   </html:messages>

   </ul>

</logic:messagesPresent>

	</td>

  </tr>

</table>



<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

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

          <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.LoginPassword" /></div></td>

          <td width="5px" class="imgTabRight"></td>

      		</tr>

    		</table>

		</td>

  		</tr>

	</table>

	<table width="400px" border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" id="bdrColor_336633">

  <html:form action="/changePasswordAction" onsubmit="return validateChangePasswordForm(this);">  

    <tr>

      <td width="398px" height="25px" align="center">

        <table width="100%"  border="0">

          <tr>

            <td>&nbsp;</td>

            <td>&nbsp;</td>

          </tr>

          <tr>

            <td width="33%"><div align="right"><bean:message key="txt.UserName" /></div></td>

            <td width="67%"><html:text name="changePasswordForm" property="txtUserName" styleClass="bdrColor_336633 componentStyle componentStyleDisable" style="width:100%" readonly="true"/></td>

          </tr>

          <tr>

            <td><div align="right"><bean:message key="txt.Password" /></div></td>

            <td><html:password name="changePasswordForm" property="txtPassword" styleClass="bdrColor_336633 componentStyle " style="width:100%" redisplay="false" /></td>

          </tr>

          <tr>

            <td><div align="right"><bean:message key="txt.ConfirmPassword" /></div></td>

            <td><html:password name="changePasswordForm" property="txtConfirmPassword" styleClass="bdrColor_336633 componentStyle" style="width:100%" redisplay="false" /></td>

          </tr>

        </table>

      </td>

    </tr>

    <tr>

      <td height="25px" align="center"> 

        <div align="right"> 

          <html:button property="btnOk" styleClass="buttons" style="width:70px" onclick="return callPage(this.form);" ><bean:message key="btn.Ok" /></html:button>

          <html:button property="btnCancel" styleClass="buttons" style="width:70px" onclick="cancelActionPerformed();" ><bean:message key="btn.Cancel" /></html:button>&nbsp; 

        </div>

      </td>

    </tr>

  </html:form>

</table>

<!-- borderClrLvl_2 table ends above-->

</td>

</tr>

	<tr><td height="2px"></td></tr>

	<tr>

      <td align="center">

      <table class="bgColor_e8e8e8 bdrColor_336633 " height="20px" width="400px" border="0" cellpadding="0" cellspacing="0" id="statusBar">

          <tr>

          <td><div class="imgStatusMsg"></div></td>

          <td>

            <html:messages id="msg" message="true">

            <bean:write  name="msg"/>

            </html:messages>

          </td>

          </tr>

      </table>

      <!-- statusBar table ends above-->

      </td>

  </tr>

</table>

<!-- tabContainer table ends above-->

</body>

</html:html>