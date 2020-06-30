<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="rts.web.beans.user.*" %>
<%@ page import="rts.web.beans.filesystem.*" %>
<%@ page import="rts.web.actions.filesystem.*" %>
<%@ page import="rts.web.actionforms.filesystem.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.struts.action.*" %>
<%@ page import="java.util.ArrayList" %>

<bean:define id="folderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />
<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />
<bean:define id="currentFolderPath" name="FolderDocInfo" property="currentFolderPath" type="String" />
<bean:define id="currentFolderId" name="FolderDocInfo" property="currentFolderId" />
<bean:define id="userInfo" name="UserInfo" type="rts.web.beans.user.UserInfo" />

<% 

int rowsInList = 0;
ArrayList drawerLists = (ArrayList)request.getAttribute("drawerLists");
if(drawerLists != null){
  rowsInList = drawerLists.size();
}

//Variable declaration

boolean firstTableRow = true;
boolean upButtonDisabled = false;
boolean isRootFolder = ((Boolean)request.getAttribute("isRootFolder")).booleanValue();

if(currentFolderPath.equals("/")){
  upButtonDisabled = true;
}

ActionMessages actionMessages = (ActionMessages)request.getAttribute("actionMessages");

if(actionMessages != null)
  out.print(actionMessages.size());
%>


<html:html>
  <head>
    <title><bean:message key="title.Personal.Drawer.List" /></title>
    <link href="themes/main.css" rel="stylesheet" type="text/css">
    <script src="navigationbar.js"></script>
    <script src="general.js"></script>
    <script>
    
  var navBar=new NavigationBar("navBar");

  navBar.setPageNumber(<%=folderDocInfo.getDrawerPageNumber()%>);    
  navBar.setPageCount(<%=folderDocInfo.getDrawerPageCount()%>);
  navBar.onclick="folderDocNavigate('nav')";
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
    
<script>
  //Called When Navigation bar buttons Clicked
  function folderDocNavigate(param){
    var thisForm=document.forms[0]
    thisForm.hdnActionType.value=<%=FolderOperation.NAVIGATE%>
    if( param == 'nav'){
      thisForm.txtPageNo.value=navBar.getPageNumber();
    }
    else{
      thisForm.txtPageNo.value=navBar.getPageNumber()-1;
    }
    thisForm.target = "_self"
    thisForm.action = "drawerNavigateAction.do";
    thisForm.submit();
  }

</script>
    
    <script language="JavaScript" type="text/JavaScript">

    <!--
    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }
    //-->
    
    /*********************MM_findObj starts***********************************/    
    function MM_findObj(n, d) { //v4.01
      var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
      d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
      for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
      if(!x && d.getElementById) x=d.getElementById(n); return x;
    }
    /************************ MM_findObj text ends ****************************/
    
    /************************MM_showHideLayers starts**************************/
    function MM_showHideLayers() { //v6.0
      var i,p,v,obj,args=MM_showHideLayers.arguments;
      for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
        if (obj.style) { obj=obj.style; v=(v=='show')?'':(v=='hide')?'none':v; }
        obj.display=v; }
    }
    /******************** MM_showHideLayers text ends *************************/
    
    var theNewDocLayerTimer;
    function newDocument_onclick() {
      My_showHideLayers('lyrNewDocument','','show')
    }
  
    function newdoclayer_onmouseover() {
      clearTimeout(theNewDocLayerTimer);
    }
  
    function newdoclayer_onmouseout() {
      theNewDocLayerTimer=setTimeout("My_showHideLayers('lyrNewDocument','','hide')",500);
    }
    
    </script>
    
    <script>
    
      //Called when up button is clicked
      function folderUp() { 
        document.forms[0].hdnActionType.value=<%=FolderOperation.UP_FOLDER%>;
        document.forms[0].target = "_self";
        document.forms[0].action = "folderDocParentAction.do";
        document.forms[0].submit();
      }
      
      //Called when back button is clicked
      function folderBack() {
        document.forms[0].hdnActionType.value=<%=FolderOperation.BACK%>;
        document.forms[0].target = "_self";    
        document.forms[0].action = "folderDocBackAction.do";
        document.forms[0].submit();
      }
      
      //Called when forward button is clicked
      function folderForward() { 
        document.forms[0].hdnActionType.value=<%=FolderOperation.FORWARD%>;
        document.forms[0].target = "_self";
        document.forms[0].action = "folderDocForwardAction.do";
        document.forms[0].submit();
      }

      //Called when refresh button is clicked
      function folderRefresh(){
        document.forms[0].hdnActionType.value=<%=FolderOperation.REFRESH%>;
        document.forms[0].target = "_self";    
        document.forms[0].action = "drawerPersonalListingAction.do";
        document.forms[0].submit();
      }
    
    //UnSelect All
    function unCheckChkAll(me){
      var thisForm=me.form;
      var totalCount =thisForm.chkFolderDocIds.length;
      var checkValue = (me.checked)?true:false;
      var isAllSelected=false;
      if(checkValue){        
        if (typeof totalCount != "undefined") {
          for (var count=0;count<totalCount;count++) {
            if(thisForm.chkFolderDocIds[count].checked){
              isAllSelected=true;           
            }else{
              isAllSelected=false;
              break;
            }
          }      
        }else{
          if(thisForm.chkFolderDocIds.checked){
            isAllSelected=true;           
          }else{
            isAllSelected=false;
          }
        }        
      }
      thisForm.chkAll.checked=isAllSelected;
    }
    
    //for addressbar
    function enter(thisField,e){
      var i;
      i=handleEnter(thisField,e);
      if (i==1) {
        return gotoFolder();
      }
    }
    
 //Called when new drawer button is clicked
    
    function drawerNew() { 
    
        openWindow("","newDrawer",150,530,0,0,true);
    
        document.forms[0].target = "newDrawer";
    
        document.forms[0].action = "b4FolderNewAction.do";
    
        document.forms[0].submit();
    
    }

    //Called when go button is clicked
    
    function gotoFolder(){
      if (document.forms[0].txtAddress.value != "" ){
        document.forms[0].hdnActionType.value=<%=FolderOperation.GOTO%>;
        document.forms[0].target = "_self";
        document.forms[0].action = "folderDocGoToFolderAction.do";
        document.forms[0].submit();
      }
    }
    
    //Called when rename button is clicked    
    function folderDocRename(){
        var itemSelected = false;
        var rowsInList = <%=rowsInList%>;
        var counter

        if (rowsInList == 1){
          itemSelected = document.forms[0].chkFolderDocIds.checked
        }
    
        if (rowsInList > 1){
          for(counter=0; counter < document.forms[0].chkFolderDocIds.length; counter++){
            if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
                break;
            }
          }
        }
    
        if (itemSelected){
          openWindow("","Rename",220,530,0,0,true);
          document.forms[0].target = "Rename";
          document.forms[0].action = "folderDocB4RenameAction.do";
          document.forms[0].submit();
        }else{
          alert("<bean:message key="msg.folderdoc.notselected" />");
        }
    }
    
    //Called when delete button is clicked
    function folderDelete(){
      var itemSelected = false;
      var rowsInList = <%=rowsInList%>;
      var counter;
      var itemsToBeDeleted = 0;
      var itemName;

      if (rowsInList == 1){
        itemSelected = document.forms[0].chkFolderDocIds.checked
        itemsToBeDeleted = 1;
        itemName = document.forms[0].name.value;
        
      }else if (rowsInList > 1){
        for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
          if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
            itemsToBeDeleted++;
            itemName = document.forms[0].name[counter].value;
          }
        }
      }
  
      if (itemsToBeDeleted >= 1){
        if (itemsToBeDeleted == 1){
          if (confirm("<bean:message key="msg.delete.confirm" /> \n\"" + itemName + "\" ?" )){
            document.forms[0].target = "_self";
            document.forms[0].action = "folderDocDeleteAction.do";
            document.forms[0].submit();
          }
  
        }else if (itemsToBeDeleted > 1){
          if (confirm("<bean:message key="msg.delete.confirm" /> " + " <bean:message key="lbl.these" /> " + itemsToBeDeleted + " <bean:message key="lbl.items" />" + " ?" )){
              document.forms[0].target = "_self";
              document.forms[0].action = "folderDocDeleteAction.do";
              document.forms[0].submit();
          }
        }
      }else{
        alert("<bean:message key="msg.folder.notselected" />");
      }
    }
    
    //Called When Search / Folder Button is Clicked
    
    function folderSearchFolders(me){
        document.forms[0].target="_self";
        document.forms[0].action = "toggleFolderSearchAction.do";
        document.forms[0].submit();
    }

    function folderDocSearch(){
        document.forms[0].target = "_self";
        document.forms[0].action = "advanceSearchAction.do";
        document.forms[0].submit();    
    }

    function lookuponclick(){
      openWindow("folderDocSelectB4Action.do?heading=<bean:message key="lbl.ChoosePath" />&foldersOnly=true&openerControl=txtLookinFolderPath","searchLookUp",495,390,0,0,true);    
    }

    //Select All
    function checkAll(menu){
      var thisForm = document.forms[0];
      var checkValue =menu?(thisForm.chkAll.checked=true):((thisForm.chkAll.checked)?true:false);
      if(typeof thisForm.chkFolderDocIds != "undefined"){
        var totalRows = thisForm.chkFolderDocIds.length;
        if (typeof totalRows != "undefined") {
          var count=0;                
          for (count=0;count<totalRows;count++) {
            thisForm.chkFolderDocIds[count].checked=checkValue;
          }
        }else{
          thisForm.chkFolderDocIds.checked=checkValue;
        }
      }
    }
    
  </script>
  
  </head>
  
  <body>
    <html:form name="drawerListForm" action="/drawerPersonalListingAction" type="rts.web.actionforms.filesystem.DrawerListForm" >
      <html:hidden property="hdnActionType" value="" />
      <html:hidden property="txtPageNo" value="<%=folderDocInfo.getDrawerPageNumber()%>" />
      <table id="4outermostBorder" class="bdrColor_336633" width="1000px" height="570px" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr >
          <td valign="top">
            <!--Header Starts-->
            <%if( userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION ){%>
              <jsp:include page="/header.jsp" />
            <%}else{%>
              <jsp:include page="/headerForTreeNav.jsp" />
            <%}%>
            <!--Header Ends-->
          </td>
        </tr>
        <tr class="bgColor_e8e8e8">
          <td>          
          <table id="tabContainer" width="800px"  border="0" cellspacing="0" cellpadding="0" align="center" style="margin-top:20px">
            <tr >
              <td width="140px">
                <table id="tabPersonal" width="140px" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="5px" class="imgTabLeft"></td>
                    <td width="130px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.Personal" /></div></td>
                    <td width="5px" class="imgTabRight"></td>
                  </tr>
                </table>
              </td>
              <% if( (userInfo.isAdmin()) || (userInfo.isSystemAdmin())) {%>              
              <td width="140px">
                <table id="tabResume" width="140px" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="5px" class="imgTabLeft"></td>
                    <td width="130px" class="imgTabTile" align="center"><a class="tabTextLink" href="b4DrawerListingAction.do?type=resume"><bean:message key="lbl.Resume" /></a></td>
                    <td width="5px" class="imgTabRight"></td>
                  </tr>
                </table>
              </td>
              <td>
              <table id="tabTimesheet" width="140px" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="5px" class="imgTabLeft"></td>
                  <td width="130px" class="imgTabTile" align="center"><a class="tabTextLink" href="b4DrawerListingAction.do?type=timesheet"><bean:message key="lbl.Timesheet" /></a></td>
                  <td width="5px" class="imgTabRight"></td>
                </tr>
              </table>
            </td>
            <%}%>
            </tr>
          </table>
          <table class="bdrColor_336633" width="800px" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr class="bgColor_e8e8e8">
              <td>
                <table id="btnHolder" width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="56%" height="30px">
                      <a class="imgNewDrawer" onclick="drawerNew();" title="<bean:message key="tooltips.DrawerNew" />" style="margin-left:3px"></a>
                      <a class="imgRenameDrawer" onclick="folderDocRename();" title="<bean:message key="tooltips.DrawerRename" />" style="margin-left:2px"></a>
                      <a class="imgDeleteDrawer" onclick="folderDelete();" title="<bean:message key="tooltips.DrawerDelete" />" style="margin-left:2px"></a>
                      <% if(!folderDocInfo.isBackButtonDisabled()){ %>
                
                        <a onclick="folderBack();" class="imgBack" onmouseout="this.className='imgBack'" onmouseover="this.className='imgBack'" style="margin-left:2px;" title="<bean:message key="tooltips.Back" />" ></a>
                
                      <%}else{%>
                
                        <div class="imgBackDisable" style="margin-left:2px;" ></div>		
                
                      <%}%>
                
                      <% if(!folderDocInfo.isForwardButtonDisabled()){ %>
                
                        <a onclick="folderForward();" class="imgForward" onmouseout="this.className='imgForward'" onmouseover="this.className='imgForward'" style="margin-left:2px;" title="<bean:message key="tooltips.Forward" />" ></a>
                
                      <%}else{%>
                
                        <div class="imgForwardDisable" style="margin-left:2px;" ></div>  
                
                      <%}%>
                
                      <% if(!upButtonDisabled && !isRootFolder ){ %>
                
                        <a onclick="folderUp();" class="imgGoUp" onmouseout="this.className='imgGoUp'" onmouseover="this.className='imgGoUp'" style="margin-left:2px;" title="<bean:message key="tooltips.Up" />" ></a>
                
                      <%}else{%>
                
                        <div  class="imgGoUpDisable" style="margin-left:2px;" ></div>
                
                      <%}%>
                    <a class="imgSearch" onmouseout="this.className='imgSearch'" onmouseover="this.className='imgSearch'" title="<bean:message key="tooltips.FolderSearch" />" 
                         style="margin-left:2px;" onclick="folderSearchFolders(this);"></a>
                      
                    <a onclick="folderRefresh();" class="imgRefresh" onmouseout="this.className='imgRefresh'" onmouseover="this.className='imgRefresh'" style="margin-left:2px;" title="<bean:message key="tooltips.Refresh" />" ></a> 
                    </td>
                  </tr>
                </table>
                <!-- -->
            <%if(!folderDocInfo.isTreeVisible()){%>
            
              <bean:define id="advanceSearchForm" name="advanceSearchForm" type="rts.web.actionforms.filesystem.AdvanceSearchForm" />
              
              <script>
                function showHideSearchLayer(){
                  MM_findObj('searchShowHide').style.display=='none'?MM_showHideLayers('searchShowHide','','show'):MM_showHideLayers('searchShowHide','','hide');
                }
              </script>
              
              <table id="searchShowHide" style="display:'';" width="800px" border="0" align="center" cellpadding="0" cellspacing="0" class="bdrBottomColor_336633">
                <tr>
                  <td height="70px" class="bgColor_e8e8e8 " align="center">
                  <fieldset style="width:97%;">
                    <legend align="left" class="tabText"><bean:message key="lbl.Search" /></legend>
                      <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
                        <tr>
                          <td width="11%" align="right"><bean:message key="lbl.Name" /></td>
                          <td width="9%">
                            <html:text name="advanceSearchForm" property="txtFolderOrDocName" styleClass="bdrColor_336633" style="width:150px; height:18px" />
                          </td>
                          <td width="18%" align="right"><bean:message key="lbl.Description" /></td>
                          <td width="10%">
                            <html:text name="advanceSearchForm" property="txtDocDescription" styleClass="bdrColor_336633" style="width:150px; height:18px" />
                          </td>
                          <td width="15%" align="right"><bean:message key="lbl.LookIn" /></td>
                          <td width="30%">
                            <html:text name="advanceSearchForm" property="txtLookinFolderPath" styleClass="bdrColor_336633" style="width:83%; height:18px" readonly="true" />
                            <html:button onclick="lookuponclick();" property="btnLookUp" style="width:20px; height:18px; cursor:pointer; cursor:hand" title="Look In..." styleClass="buttons" value="..." />
                          </td>
                          <td width="7%">
                            <a onclick="folderDocSearch()" class="imgGo"  title="<bean:message key="tooltips.Go" />"></a>
                          </td>
                        </tr>
                  </table>
                    </fieldset>
                  </td>
                </tr>
              </table>
            
            <%}%>
                
                <!-- -->
              </td>
            </tr>
            <tr>
              <td align="center" valign="top" class="bgColor_e8e8e8">
              <div style="overflow:auto; width:800px; height:357px">
                <table width="100%"  border="0" cellspacing="1" cellpadding="1" class="bgColor_FFFFFF">
                  <tr>
                    <td width="2%" align="center" class="th669966">
                      <input type="checkbox" onclick="checkAll()" name="chkAll" title="SelectAll">
                    </td>
                    <td width="30%" align="center" class="th669966">Drawer Name</td>
                    <td width="68%" align="center" class="th669966">Description</td>
                  </tr>
                  
                  <logic:iterate id="drawerList" name="drawerLists" type="rts.web.beans.filesystem.DrawerList" >
                  
                    <%if (firstTableRow == true){ firstTableRow = false; %>
                      <tr class="trF7F7F7">
                    <%}else{ firstTableRow = true; %>
                      <tr class="trD7EFD7">
                    <%}%>
                      <td>
                        <html:multibox property="chkFolderDocIds" onclick="unCheckChkAll(this);" >
                          <bean:write name="drawerList"  property="id" />
                        </html:multibox>
                      </td>     

                      <td>
                        <html:hidden property="name" value="<%=drawerList.getName()%>" />
                        <a class="imgDrawer" href="folderDocFolderClickAction.do?currentFolderId=<bean:write name="drawerList"  property="id" />"></a>
                        <a class="recordLink" href="folderDocFolderClickAction.do?currentFolderId=<bean:write name="drawerList"  property="id" />" title="<bean:write name="drawerList" property="drawerPath" />" ><bean:write name="drawerList"  property="name" /></a>
                      </td>
                      <td><bean:write name="drawerList"  property="description" /></td>
                    </tr>
                  </logic:iterate>
                </table>
                <%if(drawerLists.size() == 0){%>
                  <div style="position:relative; top:175px; text-align:center;" class="tabText">
                    <bean:message key="info.no_item_found.no_item_found" />
                  </div> 
                <%}%>  
              </div>
              </td>
            </tr>
          </table>
          <table id="statusBar" class="bdrColor_336633 bgColor_e8e8e8" align="center" width="800px" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="30px"><div class="imgStatusMsg"></div></td>
                <td width="467px">
                  <logic:messagesPresent >
                    <bean:message key="errors.header"/>
                    <html:messages id="error">
                    <font color="red"><bean:write name="error"/></font>            
                    </html:messages>
                  </logic:messagesPresent>
                  <html:messages id="msg" message="true">
                    <bean:write  name="msg"/>
                  </html:messages>
                </td>
                <td width="3px">
                  <div style="float:left; width:1px;height:22px;" class="bgClrLvl_2"></div>
                  <div style="float:left; width:1px;height:22px;" class="bgClrLvl_F"></div>
                </td>
                <td width="200px" align="right">
                  <script>navBar.render();</script>
                </td>
              </tr>
            </table>  
          </td>
        </tr>
      </table>
      
      <table id="copyright" align="center" width="1000px" class="bdrColor_336633 bgColor_e8e8e8" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10px" align="center" class="bgColor_669966 textCopyRight"><bean:message key="lbl.CopyRight" /></td>
        </tr>
      </table>        
    </html:form> 
    </body>

</html:html>