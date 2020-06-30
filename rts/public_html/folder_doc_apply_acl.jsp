<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="aclselects" name="aclselects" type="java.util.ArrayList" />

<bean:define id="folderDocApplyAclForm" name="folderDocApplyAclForm" type="rts.web.actionforms.filesystem.FolderDocApplyAclForm" />

<bean:define id="txtApplyAclPageCount" name="folderDocApplyAclForm" property="txtApplyAclPageCount" />        

<bean:define id="txtApplyAclPageNo" name="folderDocApplyAclForm" property="txtApplyAclPageNo" />



<%

//Variable declaration

boolean firstTableRow;

firstTableRow = true;

%>



<html:html>

<head>

<title><bean:message key="title.AclListSelect" /></title>

<jsp:include page="/style_sheet_include.jsp" />

<script src="general.js" ></script>

<script src="navigationbar.js" ></script>

<script language="javascript">

    

  var navBar=new NavigationBar("navBar");

  navBar.setPageNumber(<%=txtApplyAclPageNo%>);

  navBar.setPageCount(<%=txtApplyAclPageCount%>);

  navBar.onclick="findAclByName()";



  navBar.msgPageNotExist="<bean:message key="page.pageNumberNotExists" />";

	navBar.msgNumberOnly="<bean:message key="page.enterNumbersOnly"/>";    

	navBar.msgEnterPageNo="<bean:message key="page.enterPageNo"/>";

	navBar.msgOnFirst="<bean:message key="page.onFirst"/>";

	navBar.msgOnLast="<bean:message key="page.onLast"/>";



	navBar.lblPage="<bean:message key="lbl.Page"/>";

	navBar.lblOf="<bean:message key="lbl.Of"/>";



	navBar.tooltipPageNo="<bean:message key="tooltips.PageNo"/>";

	navBar.tooltipFirst="<bean:message key="tooltips.First"/>";

	navBar.tooltipNext="<bean:message key="tooltips.Next"/>";

	navBar.tooltipPrev="<bean:message key="tooltips.Previous"/>";

	navBar.tooltipLast="<bean:message key="tooltips.Last"/>";

	navBar.tooltipGo="<bean:message key="btn.Go"/>";



function okActionPerformed(){

var selectedValue=checkSelected(document.forms[0]);

  if (selectedValue){

    document.forms[0].target = opener.name;

    document.forms[0].submit();

    window.location.replace("blank.html");

  }

}



function findAclByName(){

    document.forms[0].action="folderDocB4ApplyAclAction.do";

    document.forms[0].txtApplyAclPageNo.value=navBar.getPageNumber();

    document.forms[0].submit();

}



function checkSelected(thisForm){

  if (typeof thisForm.radAclId!="undefined"){

    if (typeof thisForm.radAclId.length !="undefined"){

      for(index = 0 ; index < thisForm.radAclId.length ;index++){  

        if(thisForm.radAclId[index].checked){   

          return thisForm.radAclId[index].value;

        }

      }

    }else{

      if(thisForm.radAclId.checked){   

        return thisForm.radAclId.value;

      }

    }

  }else{

    alert('<bean:message key="errors.radSelect.noitem"/>');         

    return false;

  }      

  alert('<bean:message key="errors.radSelect.required"/>');         

  return false;

}



function enter(thisField,e){

  var i;

  i=handleEnter(thisField,e);

 	if (i==1) {

    return findAclByName();

  }

}

</script>

</head>

<body style="margin:15px">



<html:form name="folderDocApplyAclForm" action="/folderDocApplyAclAction" type="rts.web.actionforms.filesystem.FolderDocApplyAclForm" >

  <html:hidden  property="txtApplyAclPageNo" value="<%=txtApplyAclPageNo%>" /> 

  <logic:iterate id="hdnFolderDocId" property="hdnFolderDocIds" name="folderDocApplyAclForm" >    

     <html:hidden property="hdnFolderDocIds" value="<%=hdnFolderDocId%>" />

     <html:hidden property="operation" value=""/>   

  </logic:iterate>

<table id="tabParent" align="center"  width="580px"  border="0" cellpadding="0" cellspacing="0" >

  <tr>

    <td>

	<table width="160px" border="0" cellpadding="0" cellspacing="0" id="tab">

      <tr>

        <td width="5px" class="imgTabLeft"></td>

        <td width="150px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.SelectAndSearchAcl" /></div></td>

        <td width="5px" class="imgTabRight"></td>

      </tr>

    </table>

	</td>

  </tr>

</table>

<table width="580px" border="0" align="center" cellpadding="0" cellspacing="0" class="imgData bgClrLvl_4 borderClrLvl_2" id="borderClrLvl_2">

  <tr>

    <td>

<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0" id="innerContainer">

  <tr>

    <td>

      <table width="100%" border="0" align="center">

        <tr>

          <td colspan="2" height="10px"></td>

        </tr>

        <tr>

          <td>

          <span style="float:left"><bean:message key="lbl.SearchByAclName" />: 

          <html:text name="folderDocApplyAclForm" property="txtSearchByAclName" styleClass="borderClrLvl_2 componentStyle" style="width:300px" onkeypress="return enter(this,event);" />

          </span>

          <html:link href="#" onclick="findAclByName()" styleClass="imgGo" titleKey="btn.Go"></html:link>

          </td>

        </tr>

        <tr>

          <td colspan="2"><bean:message key="lst.SearchInfo" /></td>

        </tr>

      </table>

    </td>

  </tr>

  <tr>

    <td height="10px"></td>

  </tr>

    <tr> 

      <td align="center">

	  <div class="borderClrLvl_2 bgClrLvl_4" style="overflow:auto; width:100%; height:324px">

	    <table class="bgClrLvl_F" id="data" width="100%" border="0" cellpadding="0" cellspacing="1">

          <tr>

            <th width="8%" height="18px"><bean:message key="tbl.head.Select" /> </th>

            <th width="38%"><bean:message key="tbl.head.Name" /> </th>

            <th width="38%"><bean:message key="tbl.head.Owner" /> </th>

            <th width="16%"><bean:message key="tbl.head.CreateDate" /></th>

          </tr>

               <% if(aclselects.size()>0) {%>

                <logic:iterate name="aclselects" id="aclselect">

                <bean:define id="aclId" name="aclselect" property="aclId" scope="page" />

                  <%if (firstTableRow == true){ firstTableRow = false; %>

                      <tr class="bgClrLvl_4">

                  <%}else{ firstTableRow = true; %>

                      <tr class="bgClrLvl_3">                  

                  <%}%>

                        <td><div align="center">

                          <html:radio property="radAclId" value="<%=aclId%>" />

                        </div></td>

                        <td><bean:write name="aclselect" property="aclName" /></td>

                        <td><bean:write name="aclselect" property="owner" /></td>

                        <td><bean:write name="aclselect" property="createDate" /></td>

                      </tr>

                  </logic:iterate>

              <%}%>

          </table>

          <% if(aclselects.size()==0) {%>

              <div style="position:relative; top:125px; text-align:center;" class="tabText">

                <bean:message key="info.no_item_found.no_item_found" />

              </div> 

            <%}%>

        </div>

        </td>

    </tr>

	<tr>

      <td height="5px"></td>

    </tr>

	<tr>

		<td align="center" height="30px"> 

        

        <div align="right">

            <html:checkbox property="chkApplyRecursively" name="folderDocApplyAclForm" />

            <bean:message key="chk.ApplyRecursively" />

            <html:button property="btnSelect" styleClass="buttons" style="width:70px" onclick="okActionPerformed(this.form)" ><bean:message key="btn.Select" /></html:button>  

            <html:button property="btnCancel" styleClass="buttons" style="width:70px" onclick="window.close();"><bean:message key="btn.Cancel" /></html:button>

            <html:button property="btnHelp" styleClass="buttons" style="width:70px" onclick="openWindow('help?topic=folder_doc_apply_acl_html','Help',650,800,0,0,true);" tabindex="5"><bean:message key="btn.Help" /></html:button>                                    

		</div>

		</td>

    </tr>	

</table>

		</td>

    </tr>	

</table>

 <!-- borderClrLvl_2 table ends above-->

<table border="0" cellpadding="0" cellspacing="0" >

  <tr><td height="2px"></td></tr>

</table>



<table align="center" class="imgStatusBar borderClrLvl_2 bgClrLvl_4 " width="580px" border="0" cellpadding="0" cellspacing="0" id="statusBar">

    <tr>

      <td width="30px"><div class="imgStatusMsg"></div></td>

      <td>

        <html:messages id="msg" message="true">

          <bean:write  name="msg"/>

        </html:messages>

      </td>

      <td>

        <div style="float:left; width:1px;height:22px;" class="bgClrLvl_2"></div>

        <div style="float:left; width:1px;height:22px;" class="bgClrLvl_F"></div>

      </td>

      <td width="200px" align="right">

        <script>navBar.render();</script> 

      </td>

    </tr>

</table>

<!-- statusBar table ends above-->

</html:form>

</body>

</html:html>