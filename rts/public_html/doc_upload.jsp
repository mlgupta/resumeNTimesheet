<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<bean:define id="FolderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />

<html:html>
<head>
<title><bean:message key="title.UploadDocument" /></title>
<jsp:include page="/style_sheet_include.jsp" />
<script src="general.js"></script>

<script name="javascript">
//This is to submit the request for copying
function docUpload(){
  var thisForm=document.forms[0];

    if ((trim(thisForm.fileOne.value)=="" && trim(thisForm.fileTwo.value)=="" && trim(thisForm.fileThree.value)=="")) {
      alert("<bean:message key="msg.document.FileToUpload.required"/>");
      thisForm.hdnExtractToLocation.focus();
    } else {
      document.forms[0].target = opener.name;
      document.forms[0].submit();
      window.location.replace("blank.html");
    }
}

</script>
</head>
<body topmargin="10px"; leftmargin="5px">
<html:form  action="/docUploadAction" enctype="multipart/form-data" >
<html:hidden property="txtPath" value="<%=FolderDocInfo.getCurrentFolderPath()%>" />
<table id="tabContainer" align="center" width="100%"  border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<tr>
  <td >
    <table id="tabParent" align="center" width="430px"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="5px" class="imgTabLeft"></td>
              <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.UploadDocuments" /></div></td>
              <td width="5px" class="imgTabRight"></td>            
            </tr>
          </table>
        </td>
  		</tr>
    </table>
    
<table id="borderClrLvl_2" align="center" width="430px" border="0" cellpadding="0" cellspacing="0" class="imgData bgClrLvl_4 borderClrLvl_2"  >
  <tr>
    <td >
      <table width="97%"  align="center" border="0" cellpadding="0" cellspacing="0" align="center">
          <tr><td height="20px" ></td></tr>
          <tr>
            <td height="20px" valign="top" align="right">
              <bean:message key="lbl.UploadingLocation" />:&nbsp;
            </td>   
            <td>
              <bean:write name="FolderDocInfo" property="currentFolderPath" />
            </td>
          </tr>
          <tr><td colspan="2" height="10px"></td></tr>
          <tr>
            <td height="20px" valign="top" align="right">
              <bean:message key="txt.LocateFile" />:&nbsp;
             </td>   
            <td>
              <html:file property="fileOne"  size="38"  onkeypress="return false;" style="width:321px" ><bean:message key="btn.Browse" /></html:file>
            </td>
          </tr>
            <td height="20px" valign="top" align="right">
              <bean:message key="lbl.Description" />:&nbsp;
            </td>   
            <td>
              <html:textarea property="txaFileOneDesc" styleClass="borderClrLvl_2 componentStyle" style="width:323px;height:57px" />
            </td>
          </tr>
          <tr><td colspan="2" height="5px"></td></tr>
          <tr><td colspan="2" height="10px"></td></tr>
          <tr>
            <td colspan="2" height="30px" align="right">
              <html:button property="btnOk" onclick="docUpload()" styleClass="buttons" style="width:70px"><bean:message key="btn.Ok" /></html:button>
              <html:button property="btnCancel" onclick="window.close()" styleClass="buttons" style="width:70px"><bean:message key="btn.Cancel" /></html:button>
              <html:button property="btnHelp" styleClass="buttons" style="width:70px" onclick="openWindow('help?topic=document_upload_html','Help',650,800,0,0,true);" tabindex="5"><bean:message key="btn.Help" /></html:button>                            
              &nbsp;&nbsp;&nbsp;
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
</html:html>

