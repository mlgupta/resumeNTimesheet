<%@ page errorPage="error_handler.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

 

<%@ page import="rts.web.actionforms.filesystem.*" %>

<bean:define id="folderDocPropertyBean" name="folderDocPropertyBean" type="rts.web.beans.filesystem.FolderDocPropertyBean" />

<bean:define id="folderDocPropertyForm" name="folderDocPropertyForm" type="rts.web.actionforms.filesystem.FolderDocPropertyForm" />

<script src="general.js"></script>

<html:html>

<head>

<title><bean:message key="title.FolderDocumentProperty" /></title>

<jsp:include page="/style_sheet_include.jsp" />

</head>

<body style="margin:15px">

<html:form name="folderDocPropertyForm" action="/folderDocPropertyAction" type="rts.web.actionforms.filesystem.FolderDocPropertyForm" >

<!-- This page contains 2 outermost tables, named 'errorContainer' and 'tabContainer' -->

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->



<tr>

  <td align="center">

    <table id="tabParent" width="350px"  border="0" cellpadding="0" cellspacing="0">

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

    

    <table id="borderClrLvl_2" width="350px" border="0" cellpadding="0" cellspacing="0" class="imgData bgClrLvl_4 borderClrLvl_2"  >

        <tr>

            <td >

          <table width="97%"  border="0" cellpadding="0" cellspacing="0" align="center">

            <tr>

            <td height="20px" colspan="2" ></td>

            </tr>

            <tr>

              <td width="22%" align="right"><a style="float:right" class="imgFolderDoc"></a></td>

              <td width="78%"> : 

                <bean:write name="folderDocPropertyBean" property="documentCount" /> <bean:message key="lbl.Files" />,

                <bean:write name="folderDocPropertyBean" property="folderCount" /> <bean:message key="lbl.Folders" /> 

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

              <td height="25px" colspan="2">

              <table width="100%"  border="0">

                <tr>

                  <td width="22%"><div align="right1"><bean:message key="lbl.Type" /></div></td>

                  <td width="78%"> : <bean:message key="lbl.FolderDocType" /></td>

                </tr>

                <tr>

                  <td><div align="right1"><bean:message key="lbl.Location" /></div></td>

                  <td> : <bean:write name="folderDocPropertyBean" property="folderDocPath" /></td>

                </tr>

                <tr>

                  <td><div align="right1"><bean:message key="lbl.Size" /></div></td>

                  <td> : <bean:write name="folderDocPropertyBean" property="folderDocSize" /></td>

                </tr>

                <tr>

                  <td><div align="right1"><bean:message key="lbl.Acl" /></div></td>

                  <td> : <%=folderDocPropertyBean.getAclName() %></td>

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

              <td height="30px" colspan="2" align="right"> 

                <html:button property="btnClose" onclick="window.close()" styleClass="buttons" style="width:70px"  ><bean:message key="btn.Close" /></html:button>

                <html:button property="btnHelp" styleClass="buttons" style="width:70px" onclick="openWindow('help?topic=folder_doc_property_html','Help',650,800,0,0,true);" tabindex="5"><bean:message key="btn.Help" /></html:button>                                        

              </td>

            </tr>

          </table>

        </td>

    </tr>

</table>

<!-- borderClrLvl_2 table ends above-->

</td>

</tr>

</table>

<!-- tabContainer table ends above-->

</html:form>

</body>

</html:html>