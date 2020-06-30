/*
Purpose: To Render a Web-safe  Color Palette
*/

//Global Constants
var	WEB_SAFE_COLORS=new Array('00','33','66','99','cc','ff');
var	ALL_COLORS=new Array('00','11','22','33','44','55','66','77','88','99','aa','bb','cc','dd','ee','ff');

/*
Contructor to Construct the Color Palette control object
@param paletteId - Acts as Id as well as control object(instance) - i.e. both should be same
@param height - Height of the Color Palette control object
@param width - Width of the Color Palette control object
*/
function ColorPalette(paletteId,height,width){
	this.colorTimer=null;	
  this.websafeOnly=true;
	this.paletteId=paletteId;
	this.height=height;
	this.width=width;
  this.selectedPalette="#ffffff";
	this.previousPalette=this.paletteId + this.selectedPalette;
	this.click=clickPalette;
	this.mouseover=mouseoverPalette;
	this.renderPalette=drawPalette;
  this.onclick=null;
	
}

//To render the websafe color palette
function drawPalette(){
	  var height=this.height;
		var width=this.width;
    if (eval(this.websafeOnly)){
      var	clr=WEB_SAFE_COLORS;
    }else{
      var	clr=ALL_COLORS;
    }
    var paletteWidth;
    var paletteHeight;
    var clickMethod;
    var mouseOverMethod;
    
    paletteWidth=(width)/36;
    paletteHeight=(height-34)/clr.length;
       
 		document.write('<div id="colorPallet" style="margin:0px;height:' + height + 'px;width:' + width +'px; ">');
    document.write('<table width="100%" border="0" cellpadding="0" cellspacing="1" class="borderClrLvl_2" >');
		document.write("<tr>");
    document.write('<td align="center">');
		document.write('<table width="100%" border="0" cellpadding="0" cellspacing="1"> ');
		for (i=0;i<clr.length;i++) {
			document.write("<tr>");
			for (j=0;j<clr.length;j++) {
				for (k=0;k<clr.length;k++) {
					clickMethod=this.paletteId +'.click(\'#'+clr[i]+clr[j]+clr[k]+ '\')';
					mouseOverMethod=this.paletteId +'.mouseover(\'#'+clr[i]+clr[j]+clr[k]+ '\')';
					document.write('<td id="'+ this.paletteId + '#'+clr[i]+clr[j]+clr[k]+'"  width="' + paletteWidth + 'px" height="' + paletteHeight + 'px"  bgcolor="#'+clr[i]+clr[j]+clr[k]+'" onclick="'+ clickMethod +'"  style="cursor:pointer;cursor:hand" onmouseover="'+ mouseOverMethod +'"></td>');                              
				}
			}		
			document.write("</tr>");
		 }
    document.write("</table>"); 
    document.write('</td>');
    document.write("</tr>");
    document.write("<tr>");
    document.write('<td>');
    document.write('<table width="100%" border="0" cellpadding="0" cellspacing="1"  >');
    document.write("<tr>");
    document.write("<td>");
    document.write('<table>'); 
    document.write('<tr>'); 
    document.write('<td id="'+ this.paletteId + 'hexovercolor" name="'+ this.paletteId + 'hexovercolor" width="60px" class="borderClrLvl_2">&nbsp;</td>');
    document.write("</tr>"); 
    document.write("</table>"); 
    document.write("</td>");
    document.write('<td width="80%" align="right">');
    document.write('<table>'); 
    document.write('<tr>'); 
    document.write('<td id="'+ this.paletteId + 'hexvalue" name="hexval" width="60px" align="center" class="borderClrLvl_2">#ffffff</td>');
    document.write('<td id="'+ this.paletteId + 'hexcolor" name="hexval" width="60px" class="borderClrLvl_2">&nbsp;</td>');
    document.write("</tr>"); 
    document.write("</table>"); 
    document.write("</tr>");
    document.write("</table>");
    document.write('</td>');
    document.write("</tr>");
    document.write("</table>");
    document.write('</div>');
    this.click(this.selectedPalette);
}    

//blinks the selected paletted
function blinkPalette(paletteId,obj){
  var delay=null;
  obj=MM_findObj(obj);
  if(eval(paletteId).selectedPalette=="#ffffff"){
    if(obj.bgColor=="#ffffff"){
      obj.bgColor="#000000";
      delay=300;
    }else{
      obj.bgColor="#ffffff";
      delay=1000;
    }
  }else{
    if(obj.style.visibility=='visible'){
      obj.style.visibility='hidden';
      delay=300;
    }else{
      obj.style.visibility='visible';
      delay=1000;
    }
  }
  
    
	if(eval(paletteId).colorTimer)clearTimeout(eval(paletteId).colorTimer);
	eval(paletteId).colorTimer=setTimeout('blinkPalette('+ eval(paletteId).paletteId+ ','+ eval(paletteId).paletteId + '.previousPalette)',delay);
}

//click on the palette will displays the selected color 
function clickPalette(value) {
  MM_findObj(this.paletteId + 'hexvalue').innerHTML = value;
  MM_findObj(this.paletteId + 'hexcolor').bgColor = value;
  
  this.selectedPalette=value;
  var objCurrent=MM_findObj(this.paletteId + this.selectedPalette);
  var objPrevious=MM_findObj(this.previousPalette);
  objPrevious.style.visibility='visible';
  objPrevious=objCurrent;
  this.previousPalette=this.paletteId + this.selectedPalette;
  eval(this.onclick);
  MM_findObj(this.paletteId+"#ffffff").bgColor="#ffffff"; // To change the bgcolor of white pallete from black to white
  blinkPalette(this.paletteId, this.previousPalette);
}

//Mouseover on the palette will displays the color 
function mouseoverPalette(value) {
	MM_findObj(this.paletteId + 'hexovercolor').bgColor = value;
}

