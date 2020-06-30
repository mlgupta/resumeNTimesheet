<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="folderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />
<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />
<bean:define id="currentFolderPath" name="FolderDocInfo" property="currentFolderPath" type="String" />
<bean:define id="currentFolderId" name="FolderDocInfo" property="currentFolderId" />
<bean:define id="jsFileLinks" name="Treeview4TimesheetList" property="jsFileLinks" />
<bean:define id="jsFileName" name="FolderDocInfo" property="jsFileName" />
<bean:define id="userInfo" name="UserInfo" type="rts.web.beans.user.UserInfo" />

<html:html>
  <head>
    <title><bean:message key="title.Timesheet.Drawer.List" /></title>
    <link href="themes/main.css" rel="stylesheet" type="text/css">
    <script src="navigationbar.js"></script>
    <script src="general.js"></script>
    <!-- Begin (Tree View Related js Files) -->
    <script src="useragent.js"></script>
    <script src="treeview.js"></script>
    <script src="<%=jsFileName%>"></script>
    <%=jsFileLinks%>
    <!-- End (Tree View Related js Files)  -->
    
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
    function userDefinedClickOnFolder(folderId){
      window.top.location.replace("folderDocFolderClickAction.do?currentFolderId="+ folderId);
    }
    
    </script>
  </head>
  <body style="margin-left:5px;"  class="bgColor_e8e8e8">
  <table id="tabParentSearch" width="220px"  border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td>
        <div id="treeview" align="center" style="position:relative;height:385px;width:218px;left:0px;top:0px;z-index:0">
          <!-- Begin Tree View Generation-->    
          <div style="width:100%;" class="bgColor_e8e8e8" >
            <script>
              SetCookie("clickedFolder4List", "<%=currentFolderId%>");
              SetCookie("highlightedTreeviewLink", "<%=currentFolderId%>");
              initializeDocument();  
            </script>                  
          </div>
          <!-- End Tree View Generation-->
        </div>
      </td>
    </tr>
  </table>
  </body>
</html:html>