<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="users" name="users" type="java.util.ArrayList" /> 

<bean:define id="pageCount" name="UserListSelectForm" property="txtPageCount" />        

<bean:define id="pageNo" name="UserListSelectForm" property="txtPageNo" />        





<% 

//Variable declaration

boolean firstTableRow;

firstTableRow = true;

String control=(String)request.getAttribute("control");

%>

<html:html>

<head>

<title><bean:message key="title.UserListSelect" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link href="themes/main.css" rel="stylesheet" type="text/css">

<script src="general.js" ></script>

<script src="navigationbar.js" ></script>

<script>

  var navBar=new NavigationBar("navBar");

  navBar.setPageNumber(<%=pageNo%>);

  navBar.setPageCount(<%=pageCount%>);

  navBar.onclick="callSearch('page_user_select')";



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

  

</script>

<script language="Javascript1.1" >



    function cancelActionPerformed(){

        window.close();

    }

    

    function okActionPerformed(form){

      var selection="<%=control%>";

      if(selection.substring(0,3)=="lst"){

          var isSelected=0;

          var isAlreadyPresent=0;

            

          for(index =1 ; index < form.chkSelect.length ;index++){     

              if(form.chkSelect[index].checked){   

                  isSelected=1;

                  for(openerIndex=0;openerIndex<(eval("opener.document.forms[0]." + "<%=control%>")).options.length; openerIndex++){

                      if((eval("opener.document.forms[0]." + "<%=control%>")).options[openerIndex].value==(form.chkSelect[index].value+" [U]")){                  

                          isAlreadyPresent=1;

                      }       

                  }

                  if(isAlreadyPresent==0){

                      //var opt= new Option(form.chkSelect[index].value +" [U]",form.chkSelect[index].value +" [U]",false,false);

                      var opt=opener.document.createElement("OPTION");

                      opt.text=form.chkSelect[index].value +" [U]";

                      opt.value=form.chkSelect[index].value +" [U]";

                      var newIndex=(eval("opener.document.forms[0]." + "<%=control%>")).options.length ;

                      (eval("opener.document.forms[0]." + "<%=control%>")).options[newIndex]=opt;

                  }

                  window.close();

              }

              isAlreadyPresent=0;

          }

          if(isSelected==0)

              alert("<bean:message key="errors.radSelect.required"/>");  

          }else if(selection.substring(0,3)=="txt"){

              var isSelected=0;

              var moreThanOneSelected=0;

              for(index =1 ; index < form.chkSelect.length ;index++){     

                  if(form.chkSelect[index].checked){ 

                      moreThanOneSelected=moreThanOneSelected+1;

                  }

              }

              if(moreThanOneSelected>1){

                  alert("<bean:message key="errors.radSelect.more"/>");

                  return null;

              }

              for(index =1; index < form.chkSelect.length ;index++){

                  if(form.chkSelect[index].checked){ 

                      isSelected=1;

                      (eval("opener.document.forms[0]." + "<%=control%>")).value=form.chkSelect[index].value;

                      window.close();

                  }

              }

              if(isSelected==0)

                  alert("<bean:message key="errors.radSelect.required"/>");    

        }

    }



    function callSearch(relayOperation){  

      var thisForm=document.forms[0];

      thisForm.operation.value=relayOperation;

      thisForm.txtPageNo.value=navBar.getPageNumber();

      thisForm.submit() ;

    }



    function checkAll(thisForm){

       var checkValue = (thisForm.chkAll.checked)?true:false;

       var totalRows = thisForm.chkSelect.length;

       var count=0;                

       for (count=1;count<totalRows;count++) {

         thisForm.chkSelect[count].checked=checkValue;

       }

    }



    function unCheckChkAll(me){

      var thisForm=me.form;

      var totalCount =thisForm.chkSelect.length;

      var checkValue = (me.checked)?true:false;

      var isAllSelected=false;

      if(checkValue){        

        for (var count=1;count<totalCount;count++) {

         if(thisForm.chkSelect[count].checked){

           isAllSelected=true;           

         }else{

           isAllSelected=false;

           break;

        }

       }      

      }

      thisForm.chkAll.checked=isAllSelected;

    }



  function enter(thisField,e){

    var i;

    i=handleEnter(thisField,e);

    if (i==1) {

      return callPage('search_user_select');

    }

  }    

  

</script>

</head>

<body style="margin:15px">

<html:form name="userListSelectForm" type="rts.web.actionforms.user.UserListSelectForm" action="/relayAction" focus="txtSearchByUserName" >

  <html:hidden property="control" value="<%=control%>" />

  <html:hidden property="operation" />

  <html:hidden name="UserListSelectForm" property="txtPageNo" /> 

<table id="tabParent" align="center"  width="410px"  border="0" cellpadding="0" cellspacing="0" >

  <tr>

    <td>

	<table width="170px" border="0" cellpadding="0" cellspacing="0" id="tab">

      <tr>

        <td width="5px" class="imgTabLeft"></td>

        <td width="160px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lst.SearchSelectUser" /></div></td>

        <td width="5px" class="imgTabRight"></td>

      </tr>

    </table>

	</td>

  </tr>

</table>



<table width="410px" border="0" align="center" cellpadding="0" cellspacing="0" class=" bgClrLvl_4 borderClrLvl_2" id="borderClrLvl_2">

  <tr>

    <td>

<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0" id="innerContainer">

  <tr>

    <td>

      <table width="100%" border="0" align="center">

        <tr>

          <td>

          <span style="float:left"><bean:message key="txt.SearchByUserName" />

          <html:text name="UserListSelectForm" property="txtSearchByUserName" styleClass="borderClrLvl_2 componentStyle" style="width:200px" tabindex="1" maxlength="20" onkeypress="return enter(this,event);" titleKey="lbl.YouCanUseWildCard" /> 

          </span>

          <a  onclick="return callSearch('search_user_select')" class="imgGo" title="<bean:message key="btn.Go"/>" tabindex="2" ></a>

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

      <div class="bgClrLvl_5 borderClrLvl_2" style="overflow:auto; width:100%; height:320px">

      <table class="bgColor_e8e8e8" width="100%" border="0" cellspacing="1" cellpadding="0">

          <tr> 

            <th width="4%" class="th669966"><input type=checkbox name="chkAll"  style="height:12px" value="" onclick="checkAll(this.form)"/></th>

            <th width="48%" class="th669966"><bean:message key="tbl.head.Name" /></th>

            <th width="20%" class="th669966"><bean:message key="tbl.head.Status" /></th>

          </tr>

          <html:multibox property="chkSelect" value=""  style="display:none" />

          <% if(users.size()>0) {%>

            <logic:iterate name="users" id="user" >

            <bean:define id="name" name="user" property="name"  />

            <%if (firstTableRow == true){ firstTableRow = false; %>
              <tr class="trF7F7F7">
            <%}else{ firstTableRow = true; %>
              <tr class="trD7EFD7">
            <%}%>

              <td>

                <div align="center"> 

                  <html:multibox property="chkSelect" value="<%=name%>" onclick="unCheckChkAll(this)" tabindex="3"/>

                </div>

              </td>

              <td ><bean:write name="user" property="name"/> </td>

              <td ><bean:write name="user" property="status"/></td>

            </tr>

            </logic:iterate>

            <%}%>

        </table>

        <% if(users.size()==0) {%>

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

          <html:button property="btnSelect" styleClass="buttons" style="width:70px"  onclick="okActionPerformed(this.form)" tabindex="4"><bean:message key="btn.Select" /></html:button>

          <html:button property="btnCancel" styleClass="buttons" style="width:70px" onclick="cancelActionPerformed()" tabindex="5"><bean:message key="btn.Cancel" /></html:button>

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



<table align="center" class="borderClrLvl_2 imgStatusBar bgClrLvl_4 " width="410px" border="0" cellpadding="0" cellspacing="0" id="statusBar">

    <tr>

    <td width="30px"><div class="imgStatusMsg"></div></td>

    <td width="177px">

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

    <td width="3px">

      <div style="float:left; width:1px;height:22px;" class="bgClrLvl_2"></div>

      <div style="float:left; width:1px;height:22px;" class="bgClrLvl_F"></div>

    </td>

    <td  width="200px" align="right">

     <script>navBar.render();</script> 

    </td>

    </tr>

</table>

<!-- statusBar table ends above-->

</html:form>

</body>

</html:html>