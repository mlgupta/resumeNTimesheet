<%@ page import="rts.web.actionforms.filesystem.*" %>



<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="documentHistoryDetail" name="documentHistoryDetail" type="rts.web.beans.filesystem.DocumentHistoryDetail" />

<html:html>

<head>

<script src="general.js"></script>

<title><bean:message key="title.DocumentHistoryDetail" /></title>

<link href="themes/main.css" rel="stylesheet" type="text/css">

</head>

<body style="margin:0">



<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->

<tr><td height="12px"></td></tr>

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

<table id="bdrColor_336633" width="350px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" >
  
  <tr>

    <td align="center">

      <table width="97%" border="0" cellpadding="0" cellspacing="0" >

        <tr><td height="20px" colspan="2" ></td></tr>

        <tr>

          <td width="22%"><bean:message key="lbl.Document" /></td>

          <td width="78%">&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="docName" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>
        
        <tr>

          <td><bean:message key="lbl.Desc" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="description" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.Status" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="actionType" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.Date" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="versionDate" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.VersionedNumber" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="versionNumber" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.UserName" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="userName" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.Comment" /></td>

          
          
          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="comment" /></td>
          

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.Candidate" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="candidateName" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.EmailId" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="candidateEmailId" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.Phone" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="candidatePhnNo" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.CommSkills" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="communicationSkills" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>

        <tr>

          <td><bean:message key="lbl.Address" /></td>

          <td>&nbsp;:&nbsp;<bean:write name="documentHistoryDetail" property="candidateAddress" /></td>

        </tr>

        <tr><td height="10px" colspan="2" ></td></tr>


        <tr>

          <td height="30px" colspan="2" align="right"> 
          
            <bean:define id="resumeId" name="documentHistoryDetail" property="docId" />
          
            <a target=newwin href="b4DocDownloadAction.do?documentId=<%=resumeId%>">Open This Version</a>

            <html:button property="btnClose" onclick="window.close()" styleClass="buttons" style="width:70px"  ><bean:message key="btn.Close" /></html:button>

          </td>

        </tr>

      </table>

    </td>

    </tr>

    </table>

    <!-- blueBorder table ends above-->

</td>

</tr>

</table>

<!-- tabContainer table ends above-->

</body>

</html:html>