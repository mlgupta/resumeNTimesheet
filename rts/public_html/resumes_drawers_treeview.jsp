<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="rts.web.beans.user.*" %>
<%@ page import="rts.web.beans.filesystem.*" %>
<%@ page import="rts.web.actions.filesystem.*" %>
<%@ page import="rts.web.actionforms.filesystem.*" %>
<%@ page import="org.apache.struts.action.*" %>
<%@ page import="java.util.*" %>

<bean:define id="folderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />
<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />
<bean:define id="currentFolderPath" name="FolderDocInfo" property="currentFolderPath" type="String" />
<bean:define id="currentFolderId" name="FolderDocInfo" property="currentFolderId" />
<bean:define id="jsFileLinks" name="Treeview4List" property="jsFileLinks" />
<bean:define id="jsFileName" name="FolderDocInfo" property="jsFileName" />
<bean:define id="userInfo" name="UserInfo" type="rts.web.beans.user.UserInfo" />

<% 

//request.setAttribute("topic","folder_doc_introduction_html");

int rowsInList = 0;
ArrayList folderDocLists = (ArrayList)request.getAttribute("folderDocLists");
if(folderDocLists != null){
  rowsInList = folderDocLists.size();
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
    <title><bean:message key="title.Resume.Drawer.List" /></title>
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
    
    </script>
    
    <script>
    
      var navBar=new NavigationBar("navBar");

      navBar.setPageNumber(<%=folderDocInfo.getPageNumber()%>);    
      navBar.setPageCount(<%=folderDocInfo.getPageCount()%>);
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
      
        thisForm.action = "folderDocNavigateAction.do";
      
        thisForm.submit();
      
      }
    
    
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
      
          document.forms[0].action = "folderDocTreeListAction.do";
      
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
    
    //Called when go button is clicked
    
    function gotoFolder(){
    
        if (document.forms[0].txtAddress.value != "" ){
    
            document.forms[0].hdnActionType.value=<%=FolderOperation.GOTO%>;
    
            document.forms[0].target = "_self";
    
            document.forms[0].action = "folderDocGoToFolderAction.do";
    
            document.forms[0].submit();
    
       }
    
    }
    
    //Called when Search Lookup button is clicked
    
    function lookuponclick(){
    
      openWindow("folderDocSelectB4Action.do?heading=<bean:message key="lbl.ChoosePath" />&foldersOnly=true&openerControl=txtLookinFolderPath&recreate=true","searchLookUp",495,390,0,0,true);
    
    }

    function folderDocSearch(){
    
        document.forms[0].target = "_self";
    
        document.forms[0].action = "advanceSearchAction.do";
    
        document.forms[0].submit();
    
    }
    
    //Called when new drawer button is clicked
    
    function drawerNew() { 
    
        openWindow("","newDrawer",150,530,0,0,true);
    
        document.forms[0].target = "newDrawer";
    
        document.forms[0].action = "b4FolderNewAction.do";
    
        document.forms[0].submit();
    
    }
    

    /* called for resume link generation */
    function generateLinks(){
    
        var itemSelected = false;
    
        var folderSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
    
        var itemCount=0;
        
        if (rowsInList == 1){
    
          if(document.forms[0].chkFolderDocIds.checked){
    
            if(document.forms[0].hdnClassName.value =="FOLDER"){
          
              folderSelected=true;
            
            }else{
    
              itemSelected=true;
    
            }
    
            itemCount=1;
    
          }
    
        }
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length; counter++){
    
              if(document.forms[0].chkFolderDocIds[counter].checked && (document.forms[0].hdnClassName[counter].value == "FOLDER")){
    
               folderSelected=true; 
    
               break;
    
              }
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                    itemSelected=true;
    
                    break;
    
                }
    
            itemCount=itemCount+1;
    
           }
    
        }
    
        if (itemSelected && !(folderSelected)){
    
            openWindow("","GenerateLink",230,700,0,0,true);
    
            document.forms[0].target = "GenerateLink";
    
            document.forms[0].action = "docGenerateLinkAction.do";
    
            document.forms[0].submit();
    
        }
    
        if(folderSelected){
    
            alert("<bean:message key="msg.folderdoc.select_resume_only" />");
    
        }
    
        
        if(!itemSelected && !folderSelected){
            
            alert("<bean:message key="msg.folderdoc.no_resume4link" />");
    
        }
    
    
    }

    //Called when upload button is clicked
    
    function docUpload(){
      document.forms[0].target = "_self";
      document.forms[0].action = "b4DocUploadAction.do";
      document.forms[0].submit();
    }

    // function to popup window to select target folder for link.
    function linkPopUp(){
      var itemSelected = false;
      var counter
      var folderSelected = false;
      
      if (typeof document.forms[0].chkFolderDocIds!="undefined"){
        if (typeof document.forms[0].chkFolderDocIds.length!="undefined"){
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
                    if(document.forms[0].hdnClassName[counter].value =="FOLDER"){
                      folderSelected = true;
                    }
                    break;
                }    
            }
        }else{
          itemSelected = document.forms[0].chkFolderDocIds.checked;
        }
      }    
  
      if ( itemSelected && !folderSelected ){
        document.forms[0].hdnActionType.value = <%=FolderOperation.LINK%>;
        openWindow('folderDocLinkB4Action.do?heading=<bean:message key="lbl.SharedLink" />&foldersOnly=true&openerControl=hdnTargetFolderPath&recreate=true',"copyToLookup",495,390,0,0,true);
      }
      if(folderSelected){
        alert("<bean:message key="msg.folderdoc.select_resume_only" />");
      }
      if(!itemSelected && !folderSelected){
        alert("<bean:message key="msg.resume.not.selected" />");
      }
    }
    
    // function to link document in target folder
    function linkForPOs(){
        var itemSelected = false;
        var counter
        if (typeof document.forms[0].chkFolderDocIds!="undefined"){
          if (typeof document.forms[0].chkFolderDocIds.length!="undefined"){
              for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
                  if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
                      break;
                  }    
              }
          }else{
            itemSelected = document.forms[0].chkFolderDocIds.checked
          }
        }
    
        if (itemSelected){
            document.forms[0].target = "_self";
            document.forms[0].action = "folderDocLinkAction.do";
            document.forms[0].submit();
        }else{
            alert("<bean:message key="msg.resume.not.selected" />");
        }
    }
    
    //Called when rename button is clicked
    
    function folderDocRename(){
        var itemSelected = false;
        var folderSelected = false;
        var rowsInList = <%=rowsInList%>;
        var itemCount = 0;
        var counter
    
        if (rowsInList == 1){
          if(document.forms[0].chkFolderDocIds.checked){
            if(document.forms[0].hdnClassName.value =="FOLDER"){
              folderSelected = true;
            }else{
              itemSelected = true; 
            }
            itemCount = 1;
          }
        }        
        
        if (rowsInList > 1){
          for(counter=0; counter < document.forms[0].chkFolderDocIds.length; counter++){
             if(document.forms[0].chkFolderDocIds[counter].checked){
               if(document.forms[0].hdnClassName[counter].value == "FOLDER"){
                folderSelected = true;
               }else{
                itemSelected=true;
                 break;
               }
             itemCount=itemCount+1;
             }
          }
        }
        if (folderSelected && !(itemSelected)){
            openWindow("","Rename",220,530,0,0,true);
            document.forms[0].target = "Rename";
            document.forms[0].action = "folderDocB4RenameAction.do";
            document.forms[0].submit();
        }
        if(itemSelected){
            alert("Select Drawers Only");
        }
        if(!itemSelected && !folderSelected){
            alert("Select Drawers To Rename");
        }
    }
    

    //Called when copyTo or moveTo button is clicked
    
    function b4CopyMoveFolderDocTo(actionType){
    
        var itemSelected = false;
    
        var counter
    
        if (typeof document.forms[0].chkFolderDocIds!="undefined"){
    
          if (typeof document.forms[0].chkFolderDocIds.length!="undefined"){
    
              for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                  if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                      break;
    
                  }    
    
              }
    
          }else{
    
            itemSelected = document.forms[0].chkFolderDocIds.checked
    
          }
    
        }    
    
        if (itemSelected){
    
            if(actionType == 'COPY'){
    
                document.forms[0].hdnActionType.value = <%=FolderOperation.COPY%>;
    
                openWindow('folderDocSelectB4Action.do?heading=<bean:message key="lbl.CopyFolderDoc" />&foldersOnly=true&openerControl=hdnTargetFolderPath&recreate=true',"copyToLookup",495,390,0,0,true);
    
            }else{
    
                document.forms[0].hdnActionType.value = <%=FolderOperation.MOVE%>;
    
                openWindow('folderDocSelectB4Action.do?heading=<bean:message key="lbl.MoveFolderDoc" />&foldersOnly=true&openerControl=hdnTargetFolderPath&recreate=true',"moveToLookup",495,390,0,0,true);
    
            }
    
        }else{
    
            alert("<bean:message key="msg.drawerResume.not.selected" />");
    
        }
    
    }
    
    
    
    //Called when copyTo or moveTo ok button is clicked
    
    function copyMoveFolderDocTo(){
    
        var itemSelected = false;
    
        var counter
    
        if (typeof document.forms[0].chkFolderDocIds!="undefined"){
    
          if (typeof document.forms[0].chkFolderDocIds.length!="undefined"){
    
              for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                  if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                      break;
    
                  }    
    
              }
    
          }else{
    
            itemSelected = document.forms[0].chkFolderDocIds.checked
    
          }
    
        }
    
        if (itemSelected){
    
            document.forms[0].target = "_self";
    
            var conf = confirm("<bean:message key="msg.overwrite.confirm" />");
    
            document.forms[0].hdnOverWrite.value=conf;
    
            document.forms[0].action = "folderDocCopyMoveAction.do";
    
            document.forms[0].submit();
    
        }else{
    
            alert("<bean:message key="msg.drawerResume.not.selected" />");
    
        }
    
    }
    
    
    
    //Called when copy button is clicked
    
    function folderDocCopy(){
    
        var itemSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter
    
        if (rowsInList == 1){
    
            itemSelected = document.forms[0].chkFolderDocIds.checked
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                    break;
    
                }    
    
           }
    
        }
    
        
    
        if (itemSelected){
    
            document.forms[0].target="_self";
    
            document.forms[0].action = "folderDocCopyAction.do" ;
    
            document.forms[0].submit();
    
        }else{
    
            alert("<bean:message key="msg.drawerResume.not.selected" />");
    
        }
    
    }
    
    
    
    
    
    
    
    //Called when copy button is clicked
    
    function folderDocCut(){
    
        var itemSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter
    
        if (rowsInList == 1){
    
            itemSelected = document.forms[0].chkFolderDocIds.checked
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                    break;
    
                }    
    
           }
    
        }
    
        
    
        if (itemSelected){
    
            document.forms[0].target="_self";
    
            document.forms[0].action = "folderDocCutAction.do" ;
    
            document.forms[0].submit();
    
        }else{
    
            alert("<bean:message key="msg.drawerResume.not.selected" />");
    
        }
    
    }
    
    
    
    
    
    
    
    //Called when paste button is clicked
    
    function folderDocPaste(){
    
        document.forms[0].target="_self";
    
        document.forms[0].action = "folderDocB4PasteAction.do" ;
    
        document.forms[0].submit();
    
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
    
            itemName = document.forms[0].name1.value;
    
        }else if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                    itemsToBeDeleted++;
    
                    itemName = document.forms[0].name1[counter].value;
    
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
    
            alert("<bean:message key="msg.drawerResume.not.selected" />");
    
        }
    
    }
    
    
    //Called when Make Versionable  button is clicked
    
    function docMakeVersionable(){
    
        var itemSelected = false;
        var rowsInList = <%=rowsInList%>;
        var counter
    
        if (rowsInList == 1){
          if( document.forms[0].hdnClassName.value == "FOLDER" ){
            alert("<bean:message key="msg.folderdoc.select_resume_only" />");
            return false;
          }else if(document.forms[0].hdnClassName.value == "FAMILY"){
            alert("<bean:message key="msg.folderdoc.select.nonversioned.resume" />");
            return false;
          }
          itemSelected = document.forms[0].chkFolderDocIds.checked
        }        
    
        if (rowsInList > 1){
          for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
           if(document.forms[0].chkFolderDocIds[counter].checked){
             if(document.forms[0].hdnClassName[counter].value == "FOLDER"){
                alert("<bean:message key="msg.folderdoc.select_resume_only" />");
                return false;
              }else if(document.forms[0].hdnClassName[counter].value == "FAMILY"){
                alert("<bean:message key="msg.folderdoc.select.nonversioned.resume" />");
                return false;
              }
            }
            if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
              selectedItemIndex = counter;
              break;
            }    
          }
        }
    
        if (itemSelected){
          if (confirm("<bean:message key="msg.resume.makeversionable" />")){
            document.forms[0].target = "_self"
            document.forms[0].action = "folderDocMakeVersionableAction.do";
            document.forms[0].submit();
          }
        }else{
          alert("<bean:message key="msg.resume.not.selected" />");
        }
    }
    
    
    
    //Called when Checkout button is clicked
    
    function folderDocCheckOut(){
    
        var itemSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
        
        var itemCount = 0;
        
        thisForm = document.forms[0];
    
        if (rowsInList == 1){
    
          if(document.forms[0].chkFolderDocIds.checked){
    
            if(thisForm.hdnClassName.value == "FAMILY"){
    
              itemSelected = true;
            
            }
        
          itemCount += 1;
            
          }
        
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(document.forms[0].chkFolderDocIds[counter].checked){
                
                  if(thisForm.hdnClassName[counter].value == "FAMILY"){                
    
                    itemSelected = true; 
                   
                    itemCount += 1;
                  
                  }else{
                  
                    itemCount += 1;
                  
                    itemSelected = false;
                    
                    break;
                  
                  }
                  
                }    
    
           }
    
        }
    
        if (itemSelected  && itemCount >= 1){
    
            openWindow("","CheckOut",200,525,0,0,true);
    
            document.forms[0].target = "CheckOut"
    
            document.forms[0].action = "folderDocB4CheckOutAction.do";
    
            document.forms[0].submit();
    
        }else{
    
            if( itemCount == 0){
            
              alert("<bean:message key="msg.folderdoc.notselected" />");
              
            }
            
            if( itemCount >= 1){
            
              alert("<bean:message key="msg.select.versioned.document.only" />");
            
            }
    
        }
    
    }
    
    
    
    //Called when CheckIn button is clicked
    
    function folderDocCheckIn(){
    
        var itemSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
        
        var itemCount = 0;
        
        thisForm = document.forms[0];
    
        if (rowsInList == 1){
    
          if(document.forms[0].chkFolderDocIds.checked){
    
            if(thisForm.hdnClassName.value == "FAMILY"){
    
              itemSelected = true;
            
            }
        
          itemCount += 1;
            
          }
        
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(document.forms[0].chkFolderDocIds[counter].checked){
                
                  if(thisForm.hdnClassName[counter].value == "FAMILY"){                
    
                    itemSelected = true; 
                   
                    itemCount += 1;
                  
                  }else{
                  
                    itemCount += 1;
                  
                    itemSelected = false;
                    
                    break;
                  
                  }
                  
                }    
    
           }
    
        }
    
        if (itemSelected  && itemCount >= 1){
    
            openWindow("","CheckIn",200,525,0,0,true);
    
            document.forms[0].target = "CheckIn"
    
            document.forms[0].action = "folderDocB4CheckInAction.do";
    
            document.forms[0].submit();
    
        }else{
    
            if( itemCount == 0){
            
              alert("<bean:message key="msg.folderdoc.notselected" />");
              
            }
            
            if( itemCount >= 1){
            
              alert("<bean:message key="msg.select.versioned.document.only" />");
            
            }
    
        }
       
    
    }
    
    
    
    //Called when Cancle CheckOut button is clicked
    
    function folderDocCancleCheckout(){
    
        var itemSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
        
        var itemCount = 0;
        
        thisForm = document.forms[0];
    
        if (rowsInList == 1){
    
          if(document.forms[0].chkFolderDocIds.checked){
    
            if(thisForm.hdnClassName.value == "FAMILY"){
    
              itemSelected = true;
            
            }
        
          itemCount += 1;
            
          }
        
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(document.forms[0].chkFolderDocIds[counter].checked){
                
                  if(thisForm.hdnClassName[counter].value == "FAMILY"){                
    
                    itemSelected = true; 
                   
                    itemCount += 1;
                  
                  }else{
                  
                    itemCount += 1;
                  
                    itemSelected = false;
                    
                    break;
                  
                  }
                  
                }    
    
           }
    
        }
    
        if (itemSelected  && itemCount >= 1){
    
            if (confirm("<bean:message key="msg.cancelcheckout.confirm" />")){
    
                document.forms[0].target = "_self"
    
                document.forms[0].action = "folderDocCancleCheckoutAction.do";
    
                document.forms[0].submit();
    
            }
    
        }else{
    
            if( itemCount == 0){
            
              alert("<bean:message key="msg.folderdoc.notselected" />");
              
            }
            
            if( itemCount >= 1){
            
              alert("<bean:message key="msg.select.versioned.document.only" />");
            
            }
    
        }
    
    
    
    }
    
    
    
    //Called when History button is clicked
    
    function docHistory(){
        var itemSelected = false;
        var rowsInList = <%=rowsInList%>;
        var counter;
        var itemCount = 0;
        thisForm = document.forms[0];
        if (rowsInList == 1){
            if(document.forms[0].chkFolderDocIds.checked){
                if(thisForm.hdnClassName.value == "FAMILY"){
                    itemSelected = true;
                }
                itemCount = 1;
            }
        }        
        if (rowsInList > 1){
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
                if(document.forms[0].chkFolderDocIds[counter].checked){
                    if(thisForm.hdnClassName[counter].value == "FAMILY"){                
                        itemSelected = true;
                    }
                    itemCount = itemCount + 1;
                }
           }
        }
        if (itemSelected && itemCount == 1){
            openWindow("","History",450,525,0,0,true);
            document.forms[0].target = "History"
            document.forms[0].action = "docHistoryListAction.do";
            document.forms[0].submit();
        }else{
            if(itemCount > 1){
                alert("<bean:message key="msg.folderdoc.select.one.versioned.resume" />");
            }else if(itemCount == 1){
                alert("<bean:message key="msg.SelectResumeNotVersioned" />");
            }else if(itemCount == 0){
                alert("<bean:message key="msg.resume.not.selected" />");
            }
        }
    }
    

    //Called when mail button is clicked
    
    function docMailAction(){    
    
        var itemSelected = false;
    
        var folderSelected =false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
    
        var itemCount = 0;
    
        thisForm = document.forms[0];
    
        
    
        if (rowsInList == 1){
    
            if(document.forms[0].chkFolderDocIds.checked){
    
                if(thisForm.hdnClassName.value != "FOLDER"){
    
                    itemSelected = true;
    
                }else{
    
                    folderSelected=true;
    
                }
    
                itemCount = 1;
    
            }
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(document.forms[0].chkFolderDocIds[counter].checked){
    
                    if(thisForm.hdnClassName[counter].value != "FOLDER"){                
    
                        itemSelected = true;
    
                    }else{
    
                        folderSelected=true;
    
                    }
    
                    itemCount = itemCount + 1;
    
                }
    
           }
    
        }
    
            
    
        if (itemSelected && (itemCount > 0) ){
    
            if (!folderSelected){
    
                document.forms[0].target = "_self"
    
                document.forms[0].action = "mailB4Action.do";
    
                document.forms[0].submit();
    
            }else{
    
                alert("<bean:message key="msg.resume.not.selected" />");
    
            }
    
        }else{        
    
                alert("<bean:message key="msg.resume.not.selected" />");
    
           
    
        }
    
    }
    
    
    
    //Called when fax button is clicked
    
    function docFaxAction(){
    
        var itemSelected = false;
    
        var folderSelected =false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
    
    
    
        var itemCount = 0;
    
        thisForm = document.forms[0];
    
        
    
        if (rowsInList == 1){
    
            if(document.forms[0].chkFolderDocIds.checked){
    
                if(thisForm.hdnClassName.value != "FOLDER"){
    
                    itemSelected = true;
    
                }else{
    
                    folderSelected=true;
    
                }
    
                itemCount = 1;
    
            }
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(document.forms[0].chkFolderDocIds[counter].checked){
    
                    if(thisForm.hdnClassName[counter].value != "FOLDER"){                
    
                        itemSelected = true;
    
                    }else{
    
                        folderSelected=true;
    
                    }
    
                    itemCount = itemCount + 1;
    
                }
    
           }
    
        }
    
            
    
        if (itemSelected && (itemCount > 0) ){
    
            if (!folderSelected){
    
                document.forms[0].target = "_self"
    
                document.forms[0].action = "faxB4Action.do";
    
                document.forms[0].submit();
    
            }else{
    
                alert("<bean:message key="msg.resume.not.selected" />");
    
            }
    
        }else{        
    
                alert("<bean:message key="msg.resume.not.selected" />");
    
           
    
        }
    
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
    
    //Called When Search / Folder Button is Clicked
    
    function folderSearchFolders(me){
    
        document.forms[0].target="_self";
    
        document.forms[0].action = "toggleFolderSearchAction.do";
    
        document.forms[0].submit();
    
    }
    
    
    
function docEdit(){
    var itemSelected = false;
    var rowsInList = <%=rowsInList%>;
    var counter;
    var itemCount = 0;
    thisForm = document.forms[0];

    if (rowsInList == 1){
        if(document.forms[0].chkFolderDocIds.checked){
            if(thisForm.hdnClassName.value != "FOLDER"){
                itemSelected = true;
            }
            itemCount = 1;
        }
    }        

    if (rowsInList > 1){
        for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
            if(document.forms[0].chkFolderDocIds[counter].checked){
                if(thisForm.hdnClassName[counter].value != "FOLDER"){                
                    itemSelected = true;
                }
                itemCount = itemCount + 1;
            }
       }

    }

    if (itemSelected && itemCount == 1){        
      document.forms[0].target = "_self";
      document.forms[0].action = "docB4EditAction.do?link=false";
      document.forms[0].submit();        
    }else{
        if(itemCount > 1){
            alert("<bean:message key="msg.folderdoc.select.one.resume" />");
        }else{
            alert("<bean:message key="msg.resume.not.selected" />");
        }
    }
}
  
  </script>
  
  </head>
  
  
  
  
  <body>
    <html:form name="folderDocListForm" action="/folderDocTreeListAction" type="rts.web.actionforms.filesystem.FolderDocListForm" >
    
      <html:hidden property="hdnActionType" value="" />
      <!-- hidden values to be passed for following operation
          1. UP_FOLDER
          2. REFRESH
      -->
      <html:hidden property="currentFolderId" value="<%=currentFolderId %>" />
  
      <!-- hidden values to be passed to the copy or move operation -->
      <html:hidden property="hdnTargetFolderPath" value="<%=currentFolderPath%>" />
      <html:hidden property="hdnPropertyType" value="" />
  
      <!-- hidden values to be passed to the folder copyTo moveTo and paste operation -->
      <html:hidden property="hdnOverWrite" value="true" />
      
      <!-- hidden values to be passed to encrypt operation -->
      <html:hidden property="hdnEncryptionPassword" value="" />
      <html:hidden property="txtPageNo" value="<%=folderDocInfo.getPageNumber()%>" /> 

      <table id="4outermostBorder" class="bdrColor_336633"  width="1000px" height="555px" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" height="155px">
          
            <jsp:include flush="true" page="/headerForTreeNav.jsp" />
          
            <table id="toolBar" width="1000px" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:0px" class="bgColor_e8e8e8 bdrBottomColor_336633 bdrTopColor_FFFFFF">
            
              <tr>
              
                <td height="30px" colspan="2">
                
                  <logic:equal name="folderDocInfo" property="listingType" value="<%=FolderDocInfo.SIMPLE_LISTING%>" >
                  
                    <a onclick="drawerNew();" class="imgNewDrawer" onmouseout="this.className='imgNewDrawer'" onmouseover="this.className='imgNewDrawer'" title="<bean:message key="tooltips.DrawerNew" /> " style="margin-left:2px"></a>
                    
                    <%if( !((Boolean)request.getAttribute("isRootFolder")).booleanValue() ){ %>
                    
                      <a onclick="docUpload();" class="imgNewResume" onmouseout="this.className='imgNewResume'" onmouseover="this.className='imgNewResume'" title="<bean:message key="tooltips.ResumeNew" /> " style="margin-left:2px"></a>

                      <a onclick="docEdit();" class="imgEditResume" onmouseout="this.className='imgEditResume'" onmouseover="this.className='imgEditResume'" title="<bean:message key="tooltips.ResumeEdit" /> " style="margin-left:2px"></a>                    

                      <a class="imgRenameDrawer" onclick="folderDocRename();" title="<bean:message key="tooltips.DrawerRename" />" style="margin-left:2px"></a>                      
                      
                      <a onclick="generateLinks();" class="imgGenerateLink" onmouseout="this.className='imgGenerateLink'" onmouseover="this.className='imgGenerateLink'" title="<bean:message key="tooltips.DocumentGenerateLink" />" style="margin-left:2px"></a>

                      <a  onclick="folderDocCut()" class="imgCut" onmouseout="this.className='imgCut'" onmouseover="this.className='imgCut'" title="<bean:message key="tooltips.DrawerResumeCut" />" style="margin-left:2px"></a>
    
                      <a  onclick="folderDocCopy()" class="imgCopy" onmouseout="this.className='imgCopy'" onmouseover="this.className='imgCopy'" title="<bean:message key="tooltips.DrawerResumeCopy" />" style="margin-left:2px"></a>

                      <a  onclick="folderDocPaste()" class="imgPaste" onmouseout="this.className='imgPaste'" onmouseover="this.className='imgPaste'" title="<bean:message key="tooltips.DrawerResumePaste" />" style="margin-left:2px"></a>
            
                      <a  onclick="folderDelete();" class="imgDeleteFolder" onmouseout="this.className='imgDeleteFolder'" onmouseover="this.className='imgDeleteFolder'" title="<bean:message key="tooltips.DrawerResumeDelete" />" style="margin-left:2px"></a>
            
                      <a  onclick="b4CopyMoveFolderDocTo('COPY')" class="imgCopyTo" onmouseout="this.className='imgCopyTo'" onmouseover="this.className='imgCopyTo'" title="<bean:message key="tooltips.DrawerResumeCopyTo" />" style="margin-left:2px"></a>
            
                      <a  onclick="b4CopyMoveFolderDocTo('MOVE')" class="imgMoveTo" onmouseout="this.className='imgMoveTo'" onmouseover="this.className='imgMoveTo'" title="<bean:message key="tooltips.DrawerResumeMoveTo" />" style="margin-left:2px"></a>

                      <a onclick="linkPopUp();" class="imgShareDoc" onmouseout="this.className='imgShareDoc'" onmouseover="this.className='imgShareDoc'" title="<bean:message key="tooltips.ShareResume" />" style="margin-left:2px"></a>

                      <a  onclick="docMakeVersionable();" class="imgVersionable" onmouseout="this.className='imgVersionable'" onmouseover="this.className='imgVersionable'" title="<bean:message key="tooltips.ResumeMakeVersionable" />" style="margin-left:2px"></a>
                      
                      <a  onclick="docHistory();" class="imgHistory" onmouseout="this.className='imgHistory'" onmouseover="this.className='imgHistory'" title="<bean:message key="tooltips.ResumeHistory" />" style="margin-left:2px"></a>
          
                      <a  onclick="docMailAction();" class="imgMailTo" onmouseout="this.className='imgMailTo'" onmouseover="this.className='imgMailTo'" title="<bean:message key="tooltips.MailResume" />" style="margin-left:2px"></a>
            
                      <a  onclick="docFaxAction();" class="imgFaxTo" onmouseout="this.className='imgFaxTo'" onmouseover="this.className='imgFaxTo'" title="<bean:message key="tooltips.FaxResume" />" style="margin-left:2px"></a>          
                    
                    <% }else{ %>
                    
                      <div class="imgNewResumeDisable" style="margin-left:2px"></div>  

                      <div class="imgEditResumeDisable" style="margin-left:2px"></div>  

                      <div class="imgRenameDrawerDisable" style="margin-left:2px"></div>
                      
                      <div class="imgGenerateLinkDisable" style="margin-left:2px"></div>  

                      <div class="imgCutDisable" style="margin-left:2px"></div>  

                      <div class="imgCopyDisable" style="margin-left:2px"></div>  

                      <div class="imgPasteDisable" style="margin-left:2px"></div>  

                      <a  onclick="folderDelete();" class="imgDeleteFolder" onmouseout="this.className='imgDeleteFolder'" onmouseover="this.className='imgDeleteFolder'" title="<bean:message key="tooltips.DrawerResumeDelete" />" style="margin-left:2px"></a>  

                      <div class="imgCopyToDisable" style="margin-left:2px"></div>  

                      <div class="imgMoveToDisable" style="margin-left:2px"></div>  

                      <div class="imgShareDocDisable" style="margin-left:2px"></div>
                      
                      <div class="imgVersionableDisable" style="margin-left:2px"></div>  

                      <div class="imgHistoryDisable" style="margin-left:2px"></div>  
                    
                      <div class="imgMailToDiasble" style="margin-left:2px"></div>  

                      <div class="imgFaxToDiasble" style="margin-left:2px"></div>  
                    
                    <%}%>
                      
                  </logic:equal>
                  
                  <logic:equal name="folderDocInfo" property="listingType" value="<%=FolderDocInfo.SEARCH_LISTING%>" >

                    <div class="imgNewDrawerDisable" style="margin-left:2px"></div>  
          
                    <div class="imgNewResumeDisable" style="margin-left:2px"></div>  
                    
                    <a onclick="docEdit();" class="imgEditResume" onmouseout="this.className='imgEditResume'" onmouseover="this.className='imgEditResume'" title="<bean:message key="tooltips.ResumeEdit" /> " style="margin-left:2px"></a>                    
                    
                    <a class="imgRenameDrawer" onclick="folderDocRename();" title="<bean:message key="tooltips.DrawerRename" />" style="margin-left:2px"></a>
                    
                    <a onclick="generateLinks();" class="imgGenerateLink" onmouseout="this.className='imgGenerateLink'" onmouseover="this.className='imgGenerateLink'" title="<bean:message key="tooltips.DocumentGenerateLink" />" style="margin-left:2px"></a>
  
                    <a  onclick="folderDocCut()" class="imgCut" onmouseout="this.className='imgCut'" onmouseover="this.className='imgCut'" title="<bean:message key="tooltips.DrawerResumeCut" />" style="margin-left:2px"></a>
  
                    <a  onclick="folderDocCopy()" class="imgCopy" onmouseout="this.className='imgCopy'" onmouseover="this.className='imgCopy'" title="<bean:message key="tooltips.DrawerResumeCopy" />" style="margin-left:2px"></a>
          
                    <div class="imgPasteDisable" style="margin-left:2px"></div>  
          
                    <a  onclick="folderDelete();" class="imgDeleteFolder" onmouseout="this.className='imgDeleteFolder'" onmouseover="this.className='imgDeleteFolder'" title="<bean:message key="tooltips.DrawerResumeDelete" />" style="margin-left:2px"></a>
          
                    <a  onclick="b4CopyMoveFolderDocTo('COPY')" class="imgCopyTo" onmouseout="this.className='imgCopyTo'" onmouseover="this.className='imgCopyTo'" title="<bean:message key="tooltips.DrawerResumeCopyTo" />" style="margin-left:2px"></a>
          
                    <a  onclick="b4CopyMoveFolderDocTo('MOVE')" class="imgMoveTo" onmouseout="this.className='imgMoveTo'" onmouseover="this.className='imgMoveTo'" title="<bean:message key="tooltips.DrawerResumeMoveTo" />" style="margin-left:2px"></a>

                    <a onclick="linkPopUp();" class="imgShareDoc" onmouseout="this.className='imgShareDoc'" onmouseover="this.className='imgShareDoc'" title="<bean:message key="tooltips.ShareResume" />" style="margin-left:2px"></a>
          
                    <a  onclick="docMakeVersionable();" class="imgVersionable" onmouseout="this.className='imgVersionable'" onmouseover="this.className='imgVersionable'" title="<bean:message key="tooltips.ResumeMakeVersionable" />" style="margin-left:2px"></a>
          
                    <a  onclick="docHistory();" class="imgHistory" onmouseout="this.className='imgHistory'" onmouseover="this.className='imgHistory'" title="<bean:message key="tooltips.ResumeHistory" />" style="margin-left:2px"></a>
          
                    <a  onclick="docMailAction();" class="imgMailTo" onmouseout="this.className='imgMailTo'" onmouseover="this.className='imgMailTo'" title="<bean:message key="tooltips.MailResume" />" style="margin-left:2px"></a>
          
                    <a  onclick="docFaxAction();" class="imgFaxTo" onmouseout="this.className='imgFaxTo'" onmouseover="this.className='imgFaxTo'" title="<bean:message key="tooltips.FaxResume" />" style="margin-left:2px"></a>          
                                    
                  </logic:equal>
                  
                </td>
                
              </tr>
              
            </table>
  
            <table id="addressBar" width="1000px" border="0" align="center" cellpadding="0" cellspacing="0" class="bdrBottomColor_336633">
              <tr>
                <td width="180px" height="30px" class="bgColor_e8e8e8 bdrTopColor_FFFFFF">
                
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
                
                  <a class="imgSearch" onmouseout="this.className='imgSearch'" onmouseover="this.className='imgSearch'" title="<bean:message key="tooltips.FolderDocSearch" />" 
                     style="margin-left:2px;" onclick="folderSearchFolders(this);"></a>
                  
                  <a onclick="folderRefresh();" class="imgRefresh" onmouseout="this.className='imgRefresh'" onmouseover="this.className='imgRefresh'" style="margin-left:2px;" title="<bean:message key="tooltips.Refresh" />" ></a>
                </td>
                <td class="bgColor_e8e8e8">
                  <div style="float:left; width:1px;height:100%;" class="bgClrLvl_2"></div>
                  <div style="float:left; width:1px;height:100%;" class="bgClrLvl_F"></div>
                  <div style="float:left; width:5px;height:100%;" ></div>
                  <table id="addressBarTab" border="0" width="800px" cellpadding="1" cellspacing="1">
                    <tr>
                      <td align="right" width="35px" class="bgColor_e8e8e8">
                        <bean:message key="lbl.Address" />:
                      </td>
                      <td class="bgColor_e8e8e8">
                        <html:text property="txtAddress" value="<%=folderDocInfo.getCurrentFolderPath()%>" styleClass="bdrColor_336633 componentStyle" style="width:700px;float:left;margin-top:3px;" onkeypress="return enter(this,event);" />
                        <a onclick="gotoFolder();" class="imgGo" style="margin-top:3px;" title="<bean:message key="btn.Go" /> "></a>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table> <!-- end addressBar -->
          </td>
        </tr>
        
        <!-- Drawer/Document Listing -->
        <tr>
          <td valign="top">
          <table id="treeNList" border="0">
            <tr>
              
              <td id="treeOrSearch" width="245px" valign="top" rowspan="2">
              <%if( folderDocInfo.isTreeVisible() ){%>
                <div id="treeDiv" class="bdrColor_336633 bgColor_e8e8e8">
                  <iframe name="ifDrawerList" src="treeview_for_resume.jsp" class="bgColor_e8e8e8"  height="385px" width="245px" frameborder="0" scrolling="yes"></iframe>
                </div>
              <%}%>
              <%if( !folderDocInfo.isTreeVisible() ){%>
                <script>
                  function showHideSearchLayer(){
                    MM_findObj('searchShowHide').style.display=='none'?MM_showHideLayers('searchShowHide','','show'):MM_showHideLayers('searchShowHide','','hide');
                  }
                </script>
                <div id="searchShowHide" class="bdrColor_336633 bgColor_e8e8e8">
                  <iframe name="iframeSearch" src="search.jsp" class="bgColor_e8e8e8" style="display:'';"  height="385px" width="245px" frameborder="0" scrolling="no"></iframe>
                </div>
              <%}%>
              </td>
            
              <td align="center" valign="top" class="bgColor_e8e8e8 bdrColor_336633" width="755px">
                <div id="listingDiv" style="overflow:auto; width:100%; height:353px">
                <table width="100%"  border="0" cellspacing="1" cellpadding="1" class="bgColor_FFFFFF">
                  <tr>
                    <th width="2%" align="center" class="th669966">
                      <input type="checkbox" onclick="checkAll()" name="chkAll" title="SelectAll">
                    </th>
                    <th width="25%" align="center" class="th669966"><bean:message key="tbl.head.Name" /></th>
                    <th width="25%" align="center" class="th669966"><bean:message key="tbl.head.E-mailId" /></th>
                    <th width="48%" align="center" class="th669966"><bean:message key="tbl.head.address" /></th>
                  </tr>             
                  
                  <%if(folderDocLists.size() > 0 ){%>
                  
                    <logic:iterate id="folderDocList" name="folderDocLists" type="rts.web.beans.filesystem.FolderDocList" >
                    
                      <%if (firstTableRow == true){ firstTableRow = false; %>
                        <tr class="trF7F7F7">
                      <%}else{ firstTableRow = true; %>
                        <tr class="trD7EFD7">
                      <%}%>
                      
                        <td>
                          <div align="center">
                            <html:multibox property="chkFolderDocIds" onclick="unCheckChkAll(this);" >
                              <bean:write name="folderDocList"  property="id" />
                            </html:multibox>
                          </div>
                          <bean:define id="className" property="className" name="folderDocList" />
                          <html:hidden property="hdnClassName" value="<%=className%>" />
                        </td>
                        
                        <td>

                          <%if(folderDocList.getClassName().equalsIgnoreCase("Resume1") ||(folderDocList.getClassName().equalsIgnoreCase("FAMILY"))){%>
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="26px">
                                  <html:hidden property="name1" value="<%=folderDocList.getName1() %>" />
                                  <a class="imgDocIcon" href="docB4EditAction.do?link=true&documentId=<bean:write name="folderDocList" property="id" />" title="<bean:write name="folderDocList" property="name" />" />
                                </td>
                                <td>
                                  <a href="docB4EditAction.do?link=true&documentId=<bean:write name="folderDocList" property="id" />" class="recordLink" title="<bean:write name="folderDocList" property="name" />">
                                  <bean:write name="folderDocList" property="name1" />
                                  <logic:equal name="folderDocList" property="className" value="FAMILY" >
                                  (v)
                                  </logic:equal>
                                  </a>
                                </td>
                                <td width="26px">
                                  <a target=newwin href="b4DocDownloadAction.do?documentId=<bean:write name="folderDocList" property="id" />" title="DownLoad Resume" class="dataLink" ></a>
                                </td>
                              </tr>
                            </table>
                           <%}else{%>
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="26px">
                                  <html:hidden property="name1" value="<%=folderDocList.getName1()%>" />
                                  <a class="imgDrawer" href="folderDocFolderClickAction.do?currentFolderId=<bean:write name="folderDocList" property="id" />" class="recordLink" title="<bean:write name="folderDocList" property="name" />" />
                                </td>
                                <td>
                                  <a href="folderDocFolderClickAction.do?currentFolderId=<bean:write name="folderDocList" property="id" />" class="recordLink" title="<bean:write name="folderDocList" property="name" />">
                                  <bean:write name="folderDocList" property="name1" />
                                  <% if(folderDocInfo.getListingType()!=folderDocInfo.SEARCH_LISTING){
                                    int item=folderDocList.getItem();
                                    if(folderDocList.getItem()<=1){%>
                                    (<b><%=item%></b>)
                                    <%}else{%>
                                    (<b><%=item%></b>)
                                    <%}%>
                                    </a>
                                  <%}%>
                                </td>
                              </tr>
                            </table>
                          <%}%>
                        </td>
                        <td>
                          <table id="emailTrim" align="center" width="100%" cellpadding="1" cellspacing="1" >
                            <tr align="left" >
                              <td width="98%" title="<bean:write name="folderDocList" property="email" />">
                                <bean:write name="folderDocList" property="trimEmail" />
                              </td>
                            </tr>
                          </table>
                        </td>
                        <td>
                          <table id="addressTrim" align="center" width="100%" cellpadding="1" cellspacing="1" >
                            <tr align="left" >
                              <td width="98%" title="<bean:write name="folderDocList" property="address" />">
                                <bean:write name="folderDocList" property="trimAddress" />
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      
                      </logic:iterate>
                      </table>
                    <%}%>
                    <%if(folderDocLists.size() == 0){%>
                      <table>
                      <tr>
                        <td colspan="4" class="bgColor_e8e8e8">
                        <div style="position:relative; top:175px; text-align:center;" class="tabText">
                          <bean:message key="info.no_item_found.no_item_found" />
                        </div>
                        </td>
                      </tr>
                      </table>
                    <%}%>
                </div>
              </td>
            </tr>
            <tr>
                <td>
                  <table class="bdrColor_336633 bgColor_e8e8e8" align="right" width="744px" border="0" cellpadding="0" cellspacing="0" id="statusBar">
                    <tr>
                      <td width="15px"><div class="imgStatusMsg"></div></td>
                      <td width="527px">
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
                  </table><!-- end statusBar -->
                </td>
              </tr>
          </table> <!-- end treeNList -->
        </td>
       </tr>
       </table>
       
      <table align="center" width="1000px" class="bdrColor_336633 bgColor_e8e8e8" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10px" align="center" class="bgColor_669966 textCopyRight"><bean:message key="lbl.CopyRight" /></td>
        </tr>
      </table>
    </html:form>       
  </body>
</html:html>