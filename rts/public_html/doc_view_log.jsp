<%@ page import="rts.web.beans.filesystem.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
<head>
<title><bean:message key="title.DocumentLog" /></title>
<jsp:include page="style_sheet_include.jsp" />
<script src="general.js"></script>
<script>
  function closewindow(){
    document.forms[0].submit();
    window.location.replace("blank.html");
  }
</script>
</head>

<body style="margin:7px">

<html:form action="/docDoNothingAction" name="showContentForm" type="rts.web.actionforms.filesystem.ShowContentForm" >

<table id="tabContainer" align="center" width="95%"  border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<tr><td height="5px"></td></tr>
<tr>
  <td >
    <table id="tabParent" align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="5px" class="imgTabLeft"></td>
              <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.DocumentLog" /></div></td>
              <td width="5px" class="imgTabRight"></td>            
            </tr>
          </table>
        </td>
  		</tr>
    </table>



<table id="borderClrLvl_2" align="center" width="100%"  border="0" cellpadding="0" cellspacing="0" class="imgData bgClrLvl_4 borderClrLvl_2"  >

<tr><td height="15px"></td></tr>
  
  <tr>
    
    <td align="center" >
    
    <div style="overflow:auto;width:95%;height:330px" class="bgClrLvl_4 borderClrLvl_2">
        
        <table width="97%"  style="margin:3px" align="left" border="0"  cellpadding="0" cellspacing="0" id="innerTabContainer">
          <tr><td height="3px" class="bgClrLvl_4"></td></tr>

          <tr class="bgClrLvl_3">            
              <td align="left" class="tabText" nowrap="nowrap" style="margin-left:2px;"><bean:message key="lbl.DocumentName" /><%=request.getAttribute("docName")%></td>
          </tr>
          <tr><td height="4px" class="bgClrLvl_4"></tr>
          <tr class="bgClrLvl_3">            
              <td align="left" class="tabText" nowrap="nowrap" style="margin-left:2px;"><bean:message key="lbl.DocumentPath" /><%=request.getAttribute("docPath")%></td>
          </tr>
          <tr><td height="10px" class="bgClrLvl_4"></tr>
          <tr class="bgCLrLvl_3">
              <td align="left" class="tabText" nowrap="nowrap" style="margin-left:2px;"><bean:message key="lbl.DocumentLogHeading" /></td>
          </tr>
          <tr><td height="4px" class="bgClrLvl_4"></tr> 
         
          
          <logic:iterate id="docLogBean" name="eventArrayList" type="rts.web.beans.filesystem.DocLogBean" > 
          
          
          <tr class="bgClrLvl_3">            
              <td align="left"  nowrap="nowrap" style="margin-left:2px;" width="20%" ><bean:write name="docLogBean" property="userId" /></td>
              <td align="left"  nowrap="nowrap" style="margin-left:2px;" width="35%" ><bean:write name="docLogBean" property="timeStamp" /></td>
              <td align="left"  nowrap="nowrap" style="margin-left:2px;" width="45%" ><bean:write name="docLogBean" property="actionPerformed" /></td>
          </tr>
          <tr><td height="21" class="bgClrLvl_4"></tr>
          
          </logic:iterate>
          
          <tr><td height="5px" class="bgClrLvl_4"></td></tr>
        
        </table>
    
    </div>
    
    </td>
  
  </tr>
  
  <tr><td height="10px"></td></tr>
  
  <tr>
    <td align="center" >
      <html:button property="btnOK" onclick="closewindow()" styleClass="buttons" style="width:70px"><bean:message key="btn.Ok" /></html:button>
    </td>
  </tr>
  
  <tr><td height="10px"></td></tr>

</table>

</td>

</tr>

</table>

</html:form>

</body>

</html:html>