<%@ page errorPage="error_handler.jsp" %>

<%@ page import="rts.web.actionforms.filesystem.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="folderDocPropertyBean" name="folderDocPropertyBean" type="rts.web.beans.filesystem.FolderDocPropertyBean" />

<bean:define id="folderDocPropertyForm" name="folderDocPropertyForm" type="rts.web.actionforms.filesystem.FolderDocPropertyForm" />



<html:html>

<head>

<title><bean:message key="title.DocumentProperty" /></title>

<jsp:include page="/style_sheet_include.jsp" />

<script src="general.js"></script>

<script language="JavaScript" >

var docOldName = "<%=folderDocPropertyBean.getFolderDocName()%>"

var aclOldName = "<%=folderDocPropertyBean.getAclName()%>"



function submitProperty(){

    if(docOldName != document.forms[0].txtFolderDocName.value || aclOldName != document.forms[0].txtAclName.value){

        document.forms[0].target = opener.name;

        document.forms[0].submit();

        window.location.replace("blank.html");    

    }else{

        window.close();

    }

}

    

</script>



</head>

<body style="margin:15px">

<html:form name="folderDocPropertyForm" action="/folderDocPropertyAction" type="rts.web.actionforms.filesystem.FolderDocPropertyForm" >

<!-- This page contains 2 outermost tables, named 'errorContainer' and 'tabContainer' -->

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->

<tr>

<td >

	<table id="tabParent" align="center" width="350px"  border="0" cellpadding="0" cellspacing="0">

		<tr>

    	<td>

			<table id="tab" width="110px" border="0" cellpadding="0" cellspacing="0">

      		<tr>

           <td width="5px" class="imgTabLeft"></td>

           <td width="100px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.Property" /></div></td>

           <td width="5px" class="imgTabRight"></td>  

      		</tr>

    		</table>

		</td>

  		</tr>

	</table>



    <logic:iterate id="folderDocId" name="folderDocPropertyBean" property="folderDocIds" >

        <html:hidden property="hdnFolderDocIds" name="folderDocId" value="<%=folderDocId%>" />

    </logic:iterate>

    <html:hidden property="aclError" value="<%=folderDocPropertyBean.isAclError().booleanValue()%>" />



<table id="borderClrLvl_2" width="350px" border="0" align="center" cellpadding="0" cellspacing="0" class="imgData bgClrLvl_4 borderClrLvl_2" >

  <tr>

    <td align="center">

      <table width="97%" border="0" cellpadding="0" cellspacing="0" >

        <tr>

          <td height="20px" colspan="2" ></td>

        </tr>

        <tr>

          <td width="25%" align="right"><a style="float:right" class="imgDocument"></a></td>

          <td width="75%">&nbsp;

          <%if(folderDocPropertyForm.getHdnPropertyType() == FolderDocPropertyForm.ONE_DOCUMENT){%>

          <html:text property="txtFolderDocName" value="<%=folderDocPropertyBean.getFolderDocName() %>" styleClass="borderClrLvl_2 componentStyle" style="width:235px" />

          <%}else{%>

          <bean:write name="folderDocPropertyBean" property="documentCount" /> <bean:message key="lbl.Files" />,

          <bean:write name="folderDocPropertyBean" property="folderCount" /> <bean:message key="lbl.Folders" /> 

          <%}%>      

          </td>

        </tr>

        <tr>

          <td height="20px" colspan="2" ></td>

        </tr>        

        <tr>

              <td class="bgClrLvl_2" height="1px" width="100%" colspan="2" align="center"></td>

        </tr>

        <tr>

          <td height="10px" colspan="2" ></td>

        </tr>        

        <tr>

          <td colspan="2">

          <table width="100%"  border="0">

            <tr>

              <td width="25%" align="right"><bean:message key="lbl.Type" /></td>

              <td width="75%"> : 

              <%if(folderDocPropertyForm.getHdnPropertyType() == FolderDocPropertyForm.ONE_DOCUMENT ) {%>

              <%if(folderDocPropertyBean.getFolderDocType().equals("")) {%>

                <bean:message key="lbl.type.unknown" />

              <%}else{%>

                <bean:write name="folderDocPropertyBean" property="folderDocType" />

              <%}%>

              <%}else{%>

                <bean:message key="lbl.Files" />              

              <%}%>

              </td>

            </tr>

            <tr>

              <td><div align="right"><bean:message key="lbl.Description" /></div></td>

              <td> : <bean:write name="folderDocPropertyBean" property="folderDocDescription" /></td>

            </tr>

            <tr>

              <td><div align="right"><bean:message key="lbl.WorkFlowStatus" /></div></td>

              <td> : <bean:write name="folderDocPropertyBean" property="docWorkFlowStatus" /></td>

            </tr>

            <tr>

              <td><div align="right"><bean:message key="lbl.Location" /></div></td>

              <td> : <bean:write name="folderDocPropertyBean" property="folderDocPath" /></td>

            </tr>

            <tr>

              <td><div align="right"><bean:message key="lbl.Size" /></div></td>

              <td> : <bean:write name="folderDocPropertyBean" property="folderDocSize" /></td>

            </tr>

          </table>

          </td>

        </tr>

        <tr>

          <td height="10px" colspan="2" ></td>

        </tr>        

        <tr>

          <td class="bgClrLvl_2" height="1px" width="100%" colspan="2" align="center"></td>

        </tr>

        <tr>

          <td height="10px" colspan="2" ></td>

        </tr>        

        <tr>

          <td colspan="2" >

            <table width="100%"  align="center" border="0">

            <tr>

            <td width="25%"><div align="right"><bean:message key="lbl.CreatedDate" /></div></td>

            <td width="75%"> : <bean:write name="folderDocPropertyBean" property="createdDate" /></td>

            </tr>

            <tr>

            <td><div align="right"><bean:message key="lbl.CreatedBy" /></div></td>

            <td> : <bean:write name="folderDocPropertyBean" property="createdBy" /></td>

            </tr>

            <tr>

            <td><div align="right"><bean:message key="lbl.ModifiedDate" /></div></td>

            <td> : <bean:write name="folderDocPropertyBean" property="modifiedDate" /></td>

            </tr>

            <tr>

            <td><div align="right"><bean:message key="lbl.ModifiedBy" /></div></td>

            <td> : <bean:write name="folderDocPropertyBean" property="modifiedBy" /></td>

            </tr>

            </table>

          </td>

        </tr>

        <tr>

          <td height="10px" colspan="2" ></td>

        </tr>        

        <tr>

          <td class="bgClrLvl_2" height="1px" width="100%" colspan="2" align="center"></td>

        </tr>

        <tr>

          <td height="10px" colspan="2" ></td>

        </tr>        

        <tr>

            <td colspan="2" >

              <table width="100%"  border="0" align="center">

                <tr>

                  <td width="25%"><div align="right"><bean:message key="lbl.Acl" /></div></td>

                  <td width="75%"> :

                  <%if(folderDocPropertyForm.getHdnPropertyType() == FolderDocPropertyForm.ONE_DOCUMENT){%>

                    <html:text property="txtAclName" readonly="true" value="<%=folderDocPropertyBean.getAclName() %>" styleClass="borderClrLvl_2 componentStyle" style="width:210px" />                  

                    <%if(!folderDocPropertyBean.isAclError().booleanValue()){%>

                        <html:button property="btnShowAclList" styleClass="buttons" style="width:20px;height:17px;" value="..." onclick="openWindow('relayAction.do?operation=page_acl_select&control=txtAclName','',500,620,0,0,true);" />                  

                    <%}%>

                  <%}else{%>

                    <%=folderDocPropertyBean.getAclName()%>

                  <%}%>      

                  </td>

                  </tr>

                  <tr>

                  <td align="right"><bean:message key="lbl.Versioned" /></td>

                  <td> : <%=folderDocPropertyBean.getVersioned()%></td>

                </tr>

              </table>

            </td>

        </tr>

        <tr>

          <td height="10px" colspan="2" ></td>

        </tr>        

        <tr>

          <td class="bgClrLvl_2" height="1px" width="100%" colspan="2" align="center"></td>

        </tr>

        <tr>

          <td height="10px" colspan="2" ></td>

        </tr>        

        <tr>

          <td colspan="2" align="right"> 

          <html:button property="btn.Ok" onclick="submitProperty()" styleClass="buttons" style="width:70px" ><bean:message key="btn.Ok" /></html:button>

          <html:cancel property="btnCancel" onclick="window.close()" styleClass="buttons" style="width:70px" ><bean:message key="btn.Cancel" /></html:cancel>

          <html:button property="btnHelp" styleClass="buttons" style="width:70px" onclick="openWindow('help?topic=folder_doc_property_html','Help',650,800,0,0,true);" tabindex="5"><bean:message key="btn.Help" /></html:button>

          </td>

        </tr>

        <tr>

          <td height="10px" colspan="2" ></td>

        </tr>

      </table>

    </td>

  </tr>

</table>



<!-- borderClrLvl_2  table ends above-->

</td>

</tr>

</table>

<!-- tabContainer table ends above-->

</html:form>

</body>

</html:html>