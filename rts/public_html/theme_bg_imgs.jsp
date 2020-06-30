<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
boolean firstRecord=true;
String defaultBg="";
%>
<html> 
<head>
<script src="general.js"></script>
</head>
<body style="margin:0" onload="return window_onload();">
  <div align="center">
    <div id="image" style="width:400px; display:'';" title="<bean:message key="tooltips.Click2SelectImage" />" >
      <table  width="100%" border="0" cellspacing="2">
      <tr>
      <td>
      <logic:iterate id="background" name="backgrounds">
<%      
      if (firstRecord){
        defaultBg=background.toString().substring(0,background.toString().lastIndexOf("."));
        firstRecord=false;
      }
%>      
      <div class="borderClrLvl_2 bgClrLvl_2" id="<%=background.toString().substring(0,background.toString().lastIndexOf("."))%>" style="float:left;width:40px;height:38px;cursor:default;background-image:url(themes/images/backgrounds/<%=background%>);" onClick="clickImages(this); " ></div>
      </logic:iterate>
      </td>
        </tr>
      </table>
  </div>
</div>      
<script language="JavaScript" type="text/JavaScript">
<!--
var serverUrl="<%=request.getContextPath()%>"
var objSelectedImage=null;
var selectedImageUrl=null;
var blinkFlag=false;
var imageTimer=null;
var delay=500;
var hdnBackgroundImage=null;
function blinkImgae(obj){
  if (blinkFlag){
   obj.style.backgroundImage="";
   blinkFlag=false;
   delay=250;
  }else{
    obj.style.backgroundImage=selectedImageUrl;
    blinkFlag=true;
    delay=1000;
  }

 	if(imageTimer)clearTimeout(imageTimer);
	imageTimer=setTimeout('blinkImgae(objSelectedImage)',delay);

}
  
function clickImages(obj) {
  if (objSelectedImage){
    objSelectedImage.style.backgroundImage=selectedImageUrl;
  }
  
  objSelectedImage=obj;
  selectedImageUrl=obj.style.backgroundImage;
  hdnBackgroundImage.value=fullUrl2HalfUrl(obj.style.backgroundImage);
  blinkImgae(objSelectedImage);
}

function window_onload(){
  hdnBackgroundImage=window.parent.document.forms[0].hdnBackgroundImage;
  if (hdnBackgroundImage.value==""){
    clickImages(MM_findObj("<%=defaultBg%>"));
  }else{
      clickImages(MM_findObj(halfUrl2Id(hdnBackgroundImage.value)));
  }
}

function fullUrl2HalfUrl(url){
  indexOfSUrl=url.indexOf(serverUrl);
  lengthOfsUrl=serverUrl.length;
  if(indexOfSUrl>-1){
    url=(url.substring(0,4) + url.substring(indexOfSUrl+lengthOfsUrl+1));
  }
  url=url.substring(0,4)+url.substring(url.indexOf("/images")+1)
  return url;

}

function halfUrl2Id(url){
  return (url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".")));
}


//-->
</script>
</body>
</html>