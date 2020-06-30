<%@ page import="rts.web.beans.user.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

 <bean:define id="users" name="users" type="java.util.ArrayList" />  

<bean:define id="pageCount" name="UserListForm" property="txtPageCount" />        

<bean:define id="pageNo" name="UserListForm" property="txtPageNo" />        

<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />
<%

//Variable declaration

boolean firstTableRow;

firstTableRow = true;

%>

<html:html>

<head>

<title><bean:message key="title.UserList" /></title>

<link href="themes/main.css" rel="stylesheet" type="text/css">

<script src="general.js" ></script>

<script src="navigationbar.js" ></script>

<script>

  var navBar=new NavigationBar("navBar");

  navBar.setPageNumber(<%=pageNo%>);

  navBar.setPageCount(<%=pageCount%>);

  navBar.onclick="callPage('page_user')";



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

<script language="JavaScript" type="text/JavaScript">

<!--

function MM_preloadImages() { //v3.0

  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();

    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)

    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}

}



function MM_swapImgRestore() { //v3.0

  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;

}



function MM_findObj(n, d) { //v4.01

  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {

    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}

  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];

  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);

  if(!x && d.getElementById) x=d.getElementById(n); return x;

}



function MM_swapImage() { //v3.0

  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)

   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}

}

//-->

</script>


<html:javascript formName="userListForm" dynamicJavascript="true" staticJavascript="false"/>

<script language="Javascript1.1" src="staticJavascript.jsp"></script>

<script language="Javascript1.1" >

function callPage(relayOperation){

  var thisForm=document.forms[0]

  if((relayOperation!="user_new") && (relayOperation!="search_user") && (relayOperation!="page_user")){

    if(checkSelected(thisForm)){

      if(relayOperation=="user_delete"){

        if (confirm("<bean:message key="msg.delete.confirm" />")){

          thisForm.operation.value=relayOperation;

          thisForm.submit() ;

        }

      }else{

        thisForm.operation.value=relayOperation;

        thisForm.submit() ;

      }

    }

  }else{

    thisForm.operation.value=relayOperation;

    thisForm.txtPageNo.value=navBar.getPageNumber();

    thisForm.submit() ;

  }

}



function checkSelected(thisForm){

  if (typeof thisForm.radSelect!="undefined"){

    if (typeof thisForm.radSelect.length !="undefined"){

      for(index = 0 ; index < thisForm.radSelect.length ;index++){  

        if(thisForm.radSelect[index].checked){   

            return true;

        }

      }

    }else{

      if(thisForm.radSelect.checked){   

          return true;

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

    return callPage('search_user');

  }

}



</script>



</head>

<body style="margin:0px" >

<html:form name="UserListForm" type="rts.web.actionforms.user.UserListForm" action="/relayAction.do" focus="txtSearchByUserName"

         onsubmit="return validateUserListForm(this);" >

    <html:hidden name="UserListForm" property="txtPageNo" /> 

    <html:hidden property="operation" />

<!-- This page contains 3 outermost tables, named 'headerIncluder', 'errorContainer' and 'tabContainer' -->

<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="headerIncluder">

  <tr>

    <td height="95px">

      <!--Header Starts-->
      <%if( userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION ){%>
        <jsp:include page="/header.jsp" />
      <%}else{%>
        <jsp:include page="/headerForTreeNav.jsp" />
      <%}%>
      <!--Header Ends-->

	</td>

  </tr>

</table>

<!--Content Starts-->

<br>

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->

<tr>

<td align="center">

<table id="tabParent" width="980"  height="10px" border="0" cellpadding="0" cellspacing="0" >

  <tr>

    <td>

      <table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0" >

        <tr>

          <td width="5px" class="imgTabLeft"></td>

          <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.UserList" /></div></td>

          <td width="5px" class="imgTabRight"></td>

        </tr>

      </table>

    </td>

  </tr>

</table>

<table width="980px" border="0" cellpadding="0" cellspacing="0" class="borderClrLvl_2" id="borderClrLvl_2" >

    

      <tr class=" bgClrLvl_3" >

        <td width="508px" height="30px">

          <a onclick="return callPage('user_new');" class="imgNewUser" onmouseout="this.className='imgNewUser'" onmouseover="this.className='imgNewUser'" style="margin-left:5px;" title="<bean:message key="tooltips.UserCreate" />" tabindex="1"></a>

          <a onclick="return callPage('user_edit')" class="imgEditUser" onmouseout="this.className='imgEditUser'" onmouseover="this.className='imgEditUser'" title="<bean:message key="tooltips.UserEdit" />" tabindex="2"></a>

          <a onclick="return callPage('user_delete')" class="imgDeleteUser" onmouseout="this.className='imgDeleteUser'" onmouseover="this.className='imgDeleteUser'" title="<bean:message key="tooltips.UserDelete" />" tabindex="3"></a>

        </td>

        <td width="480px" align="right">

          <a onclick="return callPage('search_user')" style="float:right;margin-right:5px" tabindex="5" class="imgGo" title="<bean:message key="btn.Go" />"></a>

          <span style="float:right">

            <bean:message key="txt.SearchByUserName" />

            <html:text name="UserListForm" property="txtSearchByUserName" styleClass="borderClrLvl_2 componentStyle" style="width:200px" tabindex="4" maxlength="20"  onkeypress="return enter(this,event);" titleKey="lbl.YouCanUseWildCard" />

          </span>

        </td>

      </tr>

      <tr>

        <td colspan="2" align="center" >		

          <div class="bgColor_e8e8e8" style="overflow:auto; width:100%; height:380px">
  
            <table class="bgColor_FFFFFF" width="100%" border="0"  cellspacing="1" cellpadding="0" id="data">
    
                <tr>
    
                  <th align="center" height="18px" width="2%" class="th669966"><bean:message key="tbl.head.Select" /></th>
    
                  <th align="center" width="48%" class="th669966"><bean:message key="tbl.head.Name" /></th>
    
                  <th align="center" width="10%" class="th669966"><bean:message key="tbl.head.CreateDate" /></th>
    
                  <th align="center" width="40%" class="th669966"><bean:message key="tbl.head.Owner" /></th>
    
                </tr>
    
                <% if(users.size()>0) {%>
    
                <logic:iterate name="users" id="user" >
    
                <bean:define id="name" name="user" property="name" scope="page" />
    
                <bean:define id="isDisabled" name="user" property="isDisabled" scope="page" />
    
                <%if (firstTableRow == true){ firstTableRow = false; %>
                  <tr class="trF7F7F7">
                <%}else{ firstTableRow = true; %>
                  <tr class="trD7EFD7">
                <%}%>
    
                  <td>
                    <div align="left">
                      <html:radio property="radSelect" value="<%=name%>" disabled="<%=isDisabled%>" tabindex="6"  />
                    </div>
                  </td>
      
                  <td align="left"><bean:write name="user" property="name"/> </td>
        
                  <td align="center"><bean:write name="user" property="createDate"/></td>
        
                  <td align="left"><bean:write name="user" property="owner"/></td>
      
                </tr>
    
                 </logic:iterate>
    
                <%}%>
    
            </table>
  
            <% if(users.size()==0) {%>
            <div style="position:relative; top:175px; text-align:center;" class="tabText">
              <bean:message key="info.no_item_found.no_item_found" />
            </div>
            <%}%> 
  
          </div>

        </td>

      </tr>

</table>

<!-- borderClrLvl_2 table ends above-->

</td>

</tr>

	<tr><td height="2px"></td></tr>

	<tr>

      <td align="center">

      <table class="borderClrLvl_2 imgStatusBar bgClrLvl_4 " width="980px" border="0" cellpadding="0" cellspacing="0" id="statusBar">

          <tr>

          <td width="30px"><div class="imgStatusMsg"></div></td>

          <td width="747px">

            <html:messages id="error">

              <font color="red"><bean:write name="error"/></font>            

            </html:messages>

            <html:messages id="msg" message="true">

              <bean:write  name="msg"/>

            </html:messages>

          </td>

          <td width="3px">

            <div style="float:left; width:1px;height:22px;" class="bgClrLvl_2"></div>

            <div style="float:left; width:1px;height:22px;" class="bgClrLvl_F"></div>

          </td>

          <td  align="right" width="200px">

            <script>navBar.render();</script>

          </td>

          </tr>

      </table>

      <!-- statusBar table ends above-->

      </td>

  </tr>

</table>

<table align="center" width="980px" class="bdrColor_336633 bgColor_e8e8e8" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="10px" align="center" class="bgColor_669966 textCopyRight"><bean:message key="lbl.CopyRight" /></td>
  </tr>
</table>

</html:form>

<!--Content Ends-->

</body>

</html:html>
