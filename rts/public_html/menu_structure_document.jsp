<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="rts.web.beans.filesystem.*" %>
<script language="Javascript1.1" >
    //Folder Document Menu 
		var folderDoc=gMenu("folderDoc",94,0,13,150);
		folderDoc.menuDelay=1000;
    folderDoc.className="dropMenu bgClrLvl_4 borderClrLvl_2";

    //File Main Menu		
		var menu1=insMainMenu(folderDoc,gMainMenu("menu1",folderDoc,"<bean:message key="mnu.File" />"));
		menu1.align="center";
    menu1.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu1.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //New Folder - File
		var menu11=insSubMenu(menu1, gSubMenu("menu11", menu1,"<bean:message key="mnu.File.NewFolder" />"));
		menu11.align="left"
		menu11.onclick="folderNew()";
    menu11.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu11.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Apply ACL - File
		var menu12=insSubMenu(menu1, gSubMenu("menu12",menu1,"<bean:message key="mnu.File.ApplyACL" />"));
		menu12.align="left"
		menu12.onclick="folderDocApplyAcl()";
    menu12.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu12.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Upload File - File
		var menu13=insSubMenu(menu1, gSubMenu("menu13",menu1,"<bean:message key="mnu.File.UploadFile" />"));
		menu13.align="left"
		menu13.onclick="docUpload()";
    menu13.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu13.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Download File - File
		var menu14=insSubMenu(menu1, gSubMenu("menu14",menu1,"<bean:message key="mnu.File.DownloadFile" />"));
		menu14.align="left"
		menu14.onclick="docDownload()";
    menu14.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu14.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Property - File
		var menu15=insSubMenu(menu1, gSubMenu("menu15",menu1,"<bean:message key="mnu.File.Property" />"));
		menu15.align="left"
    menu15.onclick="folderDocProperty()";
    menu15.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu15.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    
    //Encrypt - File
    var menu16=insSubMenu(menu1, gSubMenu("menu16",menu1,"<bean:message key="mnu.File.Encrypt" />"));
		menu16.align="left"
    menu16.onclick="docEncryptAction()";
    menu16.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu16.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Decrypt - File
    var menu17=insSubMenu(menu1, gSubMenu("menu17",menu1,"<bean:message key="mnu.File.Decrypt" />"));
		menu17.align="left"
    menu17.onclick="docDecryptAction()";
    menu17.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu17.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Zip - File
    var menu18=insSubMenu(menu1, gSubMenu("menu18",menu1,"<bean:message key="mnu.File.Zip" />"));
		menu18.align="left"
    menu18.onclick="docZipAction()";
    menu18.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu18.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Unzip - File
    var menu19=insSubMenu(menu1, gSubMenu("menu19",menu1,"<bean:message key="mnu.File.Unzip" />"));
		menu19.align="left"
    menu19.onclick="docUnZipAction()";
    menu19.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu19.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Mail - File
    var menu110=insSubMenu(menu1, gSubMenu("menu110",menu1,"<bean:message key="mnu.File.Mail" />"));
		menu110.align="left"
    menu110.onclick="docMailAction()";
    menu110.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu110.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Fax - File
    var menu111=insSubMenu(menu1, gSubMenu("menu111",menu1,"<bean:message key="mnu.File.Fax" />"));
		menu111.align="left"
    menu111.onclick="docFaxAction()";
    menu111.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu111.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";    

    //Generate file link -File
    var menu112=insSubMenu(menu1, gSubMenu("menu112",menu1,"<bean:message key="mnu.File.GenerateLinks(s)" />"));
		menu112.align="left"
    menu112.onclick="generateLinks()";
    menu112.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu112.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";        
  
    //View Document Log - File
    var menu113=insSubMenu(menu1, gSubMenu("menu113",menu1,"<bean:message key="mnu.File.ViewDocLog" />"));
    menu113.align="left";
    menu113.onclick="auditLog()";
		menu113.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu113.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";


    //Submit Document To WorkFlow
    var menu114=insSubMenu(menu1, gSubMenu("menu114",menu1,"<bean:message key="mnu.File.SubmitDoc" />"));
    menu114.align="left";
    menu114.onclick="submitDocToWf()";
		menu114.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu114.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    
    //Edit Main Menu		
		var menu2=insMainMenu(folderDoc,gMainMenu("menu2",folderDoc,"<bean:message key="mnu.Edit" />"));
		menu2.align="center";
    menu2.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu2.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Rename - Edit
		var menu21=insSubMenu(menu2, gSubMenu("menu21",menu2,"<bean:message key="mnu.Edit.Rename" />"));
		menu21.align="left"
    menu21.onclick="folderDocRename()";
    menu21.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu21.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Cut - Edit 
		var menu22=insSubMenu(menu2, gSubMenu("menu22",menu2,"<bean:message key="mnu.Edit.Cut" />"));
		menu22.align="left"
    menu22.onclick="folderDocCut()";    
    menu22.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu22.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Copy - Edit
		var menu23=insSubMenu(menu2, gSubMenu("menu23",menu2,"<bean:message key="mnu.Edit.Copy" />"));
		menu23.align="left"
    menu23.onclick="folderDocCopy()";    
    menu23.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu23.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";    

    //Paste - Edit
		var menu24=insSubMenu(menu2, gSubMenu("menu24",menu2,"<bean:message key="mnu.Edit.Paste" />"));
		menu24.align="left"
    menu24.onclick="folderDocPaste()";
    menu24.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu24.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";    

    //Delete - Edit
		var menu25=insSubMenu(menu2, gSubMenu("menu25",menu2,"<bean:message key="mnu.Edit.Delete" />"));
		menu25.align="left"
    menu25.onclick="folderDelete()";
    menu25.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu25.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";    

    // Copy To Folder - Edit
		var menu26=insSubMenu(menu2, gSubMenu("menu26",menu2,"<bean:message key="mnu.Edit.CopyToFolder" />"));
		menu26.align="left"
    menu26.onclick="b4CopyMoveFolderDocTo('<%=FolderOperation.COPY%>')";
    menu26.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu26.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Move To Folder - Edit
		var menu27=insSubMenu(menu2, gSubMenu("menu27",menu2,"<bean:message key="mnu.Edit.MoveToFolder" />"));
		menu27.align="left"
    menu27.onclick="b4CopyMoveFolderDocTo('<%=FolderOperation.MOVE%>')";
    menu27.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu27.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";    

    //Select All  - Edit
		var menu28=insSubMenu(menu2, gSubMenu("menu28",menu2,"<bean:message key="mnu.Edit.SelectAll" />"));
		menu28.align="left"
    menu28.onclick="checkAll(true)";    
    menu28.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu28.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";    

    //Invert Selection - Edit 
		var menu29=insSubMenu(menu2, gSubMenu("menu29",menu2,"<bean:message key="mnu.Edit.InvertSelection" />"));
		menu29.align="left"
    menu29.onclick="invertSelection()";    
		menu29.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu29.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";


    //Version Main Menu
		var menu3=insMainMenu(folderDoc,gMainMenu("menu3",folderDoc,"<bean:message key="mnu.Version" />"));
		menu3.align="center";
    menu3.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu3.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";

    //Make Versionable - Version
		var menu31=insSubMenu(menu3, gSubMenu("menu31",menu3,"<bean:message key="mnu.Version.MakeVersionable" />"));
		menu31.align="left"
    menu31.onclick="docMakeVersionable()";
    menu31.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu31.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";    

    //Checkout - Version
		var menu32=insSubMenu(menu3, gSubMenu("menu32",menu3,"<bean:message key="mnu.Version.CheckOut" />"));
		menu32.align="left"
    menu32.onclick="folderDocCheckOut()";
    menu32.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu32.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";        

    //Checkin - Version
		var menu33=insSubMenu(menu3, gSubMenu("menu33",menu3,"<bean:message key="mnu.Version.CheckIn" />"));
		menu33.align="left"
    menu33.onclick="folderDocCheckIn()";
    menu33.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu33.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";    

    //Undo Checkout - Version
		var menu34=insSubMenu(menu3, gSubMenu("menu34",menu3,"<bean:message key="mnu.Version.UndoCheckOut" />"));
		menu34.align="left"
    menu34.onclick="folderDocCancleCheckout()";
    menu34.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu34.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";        

    //History - Version
		var menu35=insSubMenu(menu3, gSubMenu("menu35",menu3,"<bean:message key="mnu.Version.History" />"));
		menu35.align="left"
    menu35.onclick="docHistory()";
    menu35.mouseoutClassName="dropMenu bgClrLvl_4 borderClrLvl_2";
    menu35.mouseoverClassName="dropMenuover bgClrLvl_2 borderClrLvl_2";        

</script>