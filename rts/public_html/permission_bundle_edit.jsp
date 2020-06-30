<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%request.setAttribute("topic","permission_bundle_edit_html");%>

<html:html>

<head>

<title><bean:message key="title.PermissionBundleEdit" /></title>

<html:javascript formName="permissionBundleNewEditForm" dynamicJavascript="true" staticJavascript="false"/>

<script language="Javascript1.1" src="staticJavascript.jsp"></script>

<script language="Javascript1.1" >



function cancelActionPerformed(){

  window.location.replace("permissionBundleListAdminAction.do");

}

    

function callPage(thisForm){ 

  for(index = 0 ; index < thisForm.lstSelectedPermission.length ;index++){     

    thisForm.lstSelectedPermission[index].selected=true;    

  }

  if(validatePermissionBundleNewEditForm(thisForm)){    

    thisForm.submit();

  }

}



function moveBetweenListBox(thisForm, flag){

  var index=0;

  if(flag=="L2R"){

    var source=thisForm.lstAvailablePermission;    

    var target=thisForm.lstSelectedPermission;

  }else{

    var source=thisForm.lstSelectedPermission;    

    var target=thisForm.lstAvailablePermission;

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

  if(flag=="L2R"){

    var source=thisForm.lstAvailablePermission;    

    var target=thisForm.lstSelectedPermission;

  }else{

    var source=thisForm.lstSelectedPermission;    

    var target=thisForm.lstAvailablePermission;

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

<html:form action="/permissionBundleEditAdminAction" onsubmit="return validatePermissionBundleNewEditForm(this);" focus="txaPermissionBundleDescription" >  

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

            <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.EditPermission" /></div></td>

            <td width="5px" class="imgTabRight"></td>

      		</tr>

    		</table>

      </td>

  	</tr>

	</table>

	<table id="borderClrLvl_2" width="600px" border="0" cellpadding="0" cellspacing="0" class="imgData bgClrLvl_4 borderClrLvl_2">

    <tr>

			<td height="10px" colspan="2" align="center"><div align="right">&nbsp;</div></td>

    </tr>

    <tr>

			<td colspan="2" align="center">

			  <table id="general" width="520px" border="0" >

          <tr>

            <td width="35%"><div align="right"><bean:message key="txt.PermissionBundleName" /></div></td>

            <td width="65%"><html:text name="permissionBundleNewEditForm" property="txtPermissionBundleName" styleClass="borderClrLvl_2 componentStyle componentStyleDisable" style="width:300px" readonly="true" /></td>

			    </tr>

          <tr>

            <td valign="top"><div align="right"><bean:message key="txa.Description" /></div></td>

            <td rowspan="3" valign="top"><html:textarea name="permissionBundleNewEditForm" property="txaPermissionBundleDescription" rows="4" styleClass="borderClrLvl_2 componentStyle" style="width:300px" tabindex="1" /></td>

          </tr>

        </table>

			</td>

		</tr>

    <tr>

      <td height="5px" colspan="2" align="center"></td>

    </tr>

		<tr>

			<td colspan="2" align="center">

			<fieldset style="width:500px;">

			<legend align="left" class="tabText"><bean:message key="lbl.AccessLevel" />:</legend>

			<table id="listParent" width="430px" border="0">

        <tr>

          <td width="200px"><div align="center"><bean:message key="lbl.AvailablePermissions" /></div></td>

          <td width="30px">&nbsp;</td>

          <td width="200px"><div align="center"><bean:message key="lbl.SelectedPermissions" /></div></td>

        </tr>

        <tr>

          <td>

            <html:select name="permissionBundleNewEditForm" property="lstAvailablePermission" size="10"  multiple="true" style="width:200px; height:175px" styleClass="borderClrLvl_2 componentStyle" tabindex="2" >

              <html:options name="permissionBundleNewEditForm" property="lstAvailablePermission" /> 

            </html:select>                 

          </td>

          <td>

            <table id="nav" width="100%"  border="0">

              <tr>

                <td><html:button property="btnLeft2Right" styleClass="buttons" style="width:30px" value="&gt;" titleKey="tooltips.Move" onclick="moveBetweenListBox(this.form, 'L2R')" tabindex="3" /></td>

              </tr>

              <tr>

                <td><html:button property="btnLeft2RightAll" styleClass="buttons" style="width:30px" value="&gt;&gt;" titleKey="tooltips.MoveAll" onclick="moveAllBetweenListBox(this.form, 'L2R')" tabindex="4" /></td>

              </tr>

              <tr>

                <td>&nbsp;</td>

              </tr>

              <tr>

                <td><html:button property="btnRight2LeftAll" styleClass="buttons" style="width:30px" value="&lt;&lt;" titleKey="tooltips.RemoveAll" onclick="moveAllBetweenListBox(this.form, 'R2L')" tabindex="5" /></td>

              </tr>

              <tr>

                <td><html:button property="btnRight2Left" styleClass="buttons" style="width:30px" value="&lt;" titleKey="tooltips.RemoveAll" onclick="moveBetweenListBox(this.form, 'R2L')" tabindex="6" /></td>

              </tr>

            </table>

          </td>

          <td>

            <html:select name="permissionBundleNewEditForm" property="lstSelectedPermission" size="10"  multiple="true" style="width:200px; height:175px" styleClass="borderClrLvl_2 componentStyle" tabindex="7" >

               <html:options name="permissionBundleNewEditForm" property="lstSelectedPermission" /> 

            </html:select>

          </td>

        </tr>

      </table>

			<!-- dataParent table ends here-->

		</fieldset>

	</td>

</tr>

		<tr>

		<td width="549px" height="25px"><div align="right">

        <html:button property="btnSave" styleClass="buttons" style="width:70px" onclick="return callPage(this.form)" tabindex="8" ><bean:message key="btn.Save" /></html:button>

        <html:button property="btnCancel" styleClass="buttons" style="width:70px" onclick="history.go(-1);" tabindex="9" ><bean:message key="btn.Cancel" /></html:button>

		  </div></td>

		<td width="41px">&nbsp;</td>

		</tr>		      

	</table>

<!-- borderClrLvl_2 table ends above-->

</td>

</tr>

	<tr><td height="2px"></td></tr>

	<tr>

    <td align="center">

    <table class="imgStatusBar borderClrLvl_2 bgClrLvl_4 " height="20px" width="600px" border="0" cellpadding="0" cellspacing="0" id="statusBar">

      <tr>

        <td width="30px"><div class="imgStatusMsg"></div></td>

        <td>

          <logic:messagesPresent>

            <bean:message key="errors.header"/>

            <html:messages id="error">

              <font color="red"><bean:write name="error"/></font>

            </html:messages>

          </logic:messagesPresent>

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

</html:form>   

<!-- tabContainer table ends above-->

</body>

</html:html>