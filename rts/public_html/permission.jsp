<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%request.setAttribute("topic","permissions_html");%>

<html:html>

<head>

<title><bean:message key="title.Permission" /></title>

<html:javascript formName="permissionForm" dynamicJavascript="true" staticJavascript="false"/>

<script language="Javascript1.1" src="staticJavascript.jsp"></script>

<script language="Javascript1.1" >



function cancelActionPerformed(){

  window.close();

}  



function callPage(thisForm){

  if(thisForm.lstSelectedPermissionBundle.length==0){

    alert("<bean:message key="errors.lstSelectedPermissionBundle.required"/>");

  }else{

    for(index = 0 ; index < thisForm.lstSelectedPermissionBundle.length ;index++){     

      thisForm.lstSelectedPermissionBundle[index].selected=true;    

    }  

    thisForm.submit();

  }

}



function moveBetweenListBox(thisForm, flag){

  var index=0;

  if(flag=="LPB2RPB"){

    var source=thisForm.lstAvailablePermissionBundle;    

    var target=thisForm.lstSelectedPermissionBundle;

  }else {

    var source=thisForm.lstSelectedPermissionBundle;    

    var target=thisForm.lstAvailablePermissionBundle;

  }

  var length=source.length;

  while(index <length){     

    if(source[index].selected){

      var opt= new Option(source[index].value,source[index].value,false,false);

      target.options[target.options.length]=opt;   

      source.remove(index);

      length=source.length;   

    }else{

      index++;

    }

  }

}



function moveAllBetweenListBox(thisForm, flag){

  var index=0;

  if(flag=="LPB2RPB"){

    var source=thisForm.lstAvailablePermissionBundle;    

    var target=thisForm.lstSelectedPermissionBundle;

  }else {

    var source=thisForm.lstSelectedPermissionBundle;    

    var target=thisForm.lstAvailablePermissionBundle;

  }

  var length=source.length;



  while(index <length){     

    var opt= new Option(source[index].value,source[index].value,false,false);

    target.options[target.options.length]=opt;   

    source.remove(index);

    length=source.length;   

  }

}

</script>

</head>

<body style="margin:0">

<html:form action="/permissionNewEditAdminAction.do" > 

<!-- This page contains 3 outermost tables, 'headerIncluder', 'errorContainer' and 'tabContainer' -->

<table id="headerIncluder" width="100%"  border="0" cellspacing="0" cellpadding="0">

  <tr>

    <td height="95px">

		<!--Header Starts-->

		<jsp:include page="/header.jsp" />

		<!--Header Ends-->

	</td>

  </tr>

</table>

<br>

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->

<tr>

<td align="center">

	<table id="tabParent" width="600px"  border="0" cellpadding="0" cellspacing="0">

		<tr>

    	<td>

			<table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0">

      		<tr>

          <td width="5px" class="imgTabLeft"></td>

          <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.Permission" /></div></td>

          <td width="5px" class="imgTabRight"></td>

      		</tr>

    		</table>

		</td>

  		</tr>

	</table>



	<table id="borderClrLvl_2" width="600px" border="0" cellpadding="0" cellspacing="0" class="imgData borderClrLvl_2 bgClrLvl_4">

  <html:hidden name="permissionForm" property="granteeType" />

  <html:hidden name="permissionForm" property="granteeName" />

  <html:hidden name="permissionForm" property="aclName" />

  <html:hidden name="permissionForm" property="index" />

  <html:hidden name="permissionForm" property="isNew" />

  <html:hidden name="permissionForm" property="isNewAcl" />

    <tr>

			<td height="20px" colspan="2" align="center"></td>

    </tr>

    <tr>

			<td colspan="2"><div align="center">

			  <table width="500" border="0" cellspacing="0" cellpadding="0">

          <tr>

            <th>

              <div align="center"><bean:message key="lbl.State" />

                <html:radio name="permissionForm" property="radPermission" value="1" />

                <bean:message key="rad.Granted" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                <html:radio name="permissionForm" property="radPermission" value="0" />

                <bean:message key="rad.Revoked" />

              </div>

            </th>

          </tr>

          <tr>

            <td><div align="center"><bean:message key="lbl.PermissionCheck" /></div></td>

          </tr>

        </table>

			</div>

			</td>

		</tr>

		<tr><td colspan="2" height="25px"></td></tr>

		<tr>

			<td colspan="2" align="center">

			<fieldset style="width:500px;">

			<legend align="left" class="tabText"><bean:message key="lbl.PermissionBundle" /></legend>

			<table id="listParent" width="430px" border="0">

        <tr>

          <td width="200px"><div align="center"><bean:message key="lbl.AvailablePermissionsBundle" /></div></td>

          <td width="30px">&nbsp;</td>

          <td width="200px"><div align="center"><bean:message key="lbl.SelectedPermissionsBundle" /></div></td>

        </tr>

        <tr>

          <td>

            <html:select name="permissionForm" property="lstAvailablePermissionBundle" size="10"  multiple="true" style="width:200px; height:125px" styleClass="borderClrLvl_2 componentStyle" >              

              <html:options name="permissionForm" property="lstAvailablePermissionBundle" /> 

            </html:select>              

          </td>

          <td>

          <table id="nav" width="100%"  border="0">

            <tr>

              <td><html:button property="btnLeftPB2RightPB" styleClass="buttons" style="width:30px" value="&gt;"  titleKey="tooltips.Move" onclick="moveBetweenListBox(this.form, 'LPB2RPB')" /></td>

            </tr>

            <tr>

              <td><html:button property="btnLeftPB2RightPBAll" styleClass="buttons" style="width:30px" value="&gt;&gt;" titleKey="tooltips.MoveAll" onclick="moveAllBetweenListBox(this.form, 'LPB2RPB')" /></td>

            </tr>

            <tr>

              <td>&nbsp;</td>

            </tr>

            <tr>

              <td><html:button property="btnRightPB2LeftPBAll" styleClass="buttons" style="width:30px" value="&lt;&lt;" titleKey="tooltips.RemoveAll" onclick="moveAllBetweenListBox(this.form, 'RPB2LPB')" /></td>

            </tr>

            <tr>

              <td><html:button property="btnRightPB2LeftPB" styleClass="buttons" style="width:30px" value="&lt;" titleKey="tooltips.RemoveAll" onclick="moveBetweenListBox(this.form, 'RPB2LPB')"/></td>

            </tr>

          </table>

          </td>

          <td>

            <html:select name="permissionForm" property="lstSelectedPermissionBundle" size="10"  multiple="true" style="width:200px; height:125px" styleClass="borderClrLvl_2 componentStyle" >

              <html:options name="permissionForm" property="lstSelectedPermissionBundle" />

            </html:select>

          </td>

        </tr>

      </table>

			<!-- dataParent table ends here-->

			</fieldset>

			</td>

		</tr>

		<td width="549px" height="30px">

      <div align="right">

        <html:button property="btnOk" styleClass="buttons" style="width:70px"  onclick="return callPage(this.form)" ><bean:message key="btn.Ok" /></html:button>

        <html:button property="btnCancel" styleClass="buttons" style="width:70px" onclick="history.go(-1);" ><bean:message key="btn.Cancel" /></html:button>

		  </div>

    </td>

		<td width="49px">&nbsp;</td>

		</tr>		      

	</table>

<!-- borderClrLvl_2 table ends above-->

</td>

</tr>

	<tr><td height="2px"></td></tr>

	<tr>

    <td align="center">

      <table class="imgStatusBar borderClrLvl_2 bgClrLvl_4 " width="600px" height="20px" border="0" cellpadding="0" cellspacing="0" id="statusBar">

        <tr>

          <td width="30px"><div class="imgStatusMsg"></div></td>

          <td>

            <logic:messagesPresent >

              <bean:message key="errors.header"/>

              <html:messages id="error">

                <font color="red"><bean:write name="error"/></font>

              </html:messages>

            </logic:messagesPresent>

            <html:messages id="msg" message="true">

              <bean:write name="msg"/>

            </html:messages>

          </td>

        </tr>

      </table>

      <!-- statusBar table ends above-->

    </td>

  </tr>

</table>

</html:form>

<!-- tabContainer table ends above-->

</body>

</html:html>