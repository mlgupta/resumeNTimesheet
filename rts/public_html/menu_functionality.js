/*
Purpose: To Render Menu 
*/

//Global Constants
var VP_GAP=1;
var HP_GAP=1;

var VN_GAP=-1;
var HN_GAP=-1;

var INIT_GAP=2;

var DEF_WIDTH=120;
var DEF_HEIGHT=13;
var DEF_TOP=0;
var DEF_LEFT=0;

/*
Contructor to Construct the Menu control object
@param id - Acts as Id as well as control object(instance) - i.e. both should be same
@param menuTop - Top of the Menu Control
@param menuLeft - Left of the Menu Control 
@param menuWidth - Width of the each Menu Item 
@param menuHeight - Height of the each Menu Item 
*/

function Menu(id,menuTop, menuLeft, menuHeight,menuWidth){
	this.id=id;
	this.menuTop=menuTop;
	this.menuLeft=menuLeft;
	this.menuHeight=menuHeight;
	this.menuWidth=menuWidth;
	this.menus=new Array;
	this.nMenus=0;
	this.renderMenu=drawMenu;
	this.add=addMainMenu;
	this.className="";
	this.indices=0;
	this.menuTimer=null;
	this.menuDelay=500;
	this.lastClicked=null;
}


/*
Contructor to Construct the Main Menu object
@param id - Acts as Id as well as Main Menu object(instance) - i.e. both should be same
@param menu - menu control object
@param description - description of the Main menu
*/
function  MainMenu(id,menu,description){
	this.id=id;
	this.menu=menu;
	this.description=description;
	this.subMenus=new Array;
	this.nSubMenus=0;
	this.add=addSubMenu;
	this.align="left";
	this.click=showMenu;
	this.hide=hideMenu;
	this.timeout=menuTimeout;
	this.mouseout=onMainMenuMouseOut;
	this.mouseover=onMainMenuMouseOver;
  this.mouseoutClassName="";
  this.mouseoverClassName="";
}

/*
Contructor to Construct the Sub Menu object
@param id - Acts as Id as well as Sub Menu object(instance) - i.e. both should be same
@param mainMenu - Main Menu object
@param description - description of the Sub menu
*/
function SubMenu(id,mainMenu,description){
	this.id=id;
	this.mainMenu=mainMenu;
	this.description="&nbsp;" + description;
	this.align="left";
	this.mouseout=onSubMenuMouseOut;
	this.mouseover=onSubMenuMouseOver;
	this.click=dummy;
	this.onclick=null;
  this.mouseoutClassName="";
  this.mouseoverClassName="";
}

//Renders the Menu
function drawMenu(){
	var menuTop=this.menuTop;
	var menuLeft=this.menuLeft;
	var menuHeight=this.menuHeight;
	var menuWidth=this.menuWidth;
	var className=this.className;
	var menus=this.menus;
	var nMenus=this.nMenus;
	var subMenus=null;
	var nSubMenus=null;
	var counterMenus=0;
	var counterSubMenus=0;
	var mainMenu=null;
	var subMenu=null;
	var align=null;
	var clickMethod=null;
	var mouseOverMethod=null;
	var mouseOutMethod=null;
	menuHeight=((menuHeight>DEF_HEIGHT)?menuHeight:DEF_HEIGHT);
	menuWidth=((menuWidth>DEF_WIDTH)?menuWidth:DEF_WIDTH);
	document.write("<div class='" + className + "' style='z-index:3;position:absolute; top:" + menuTop + "px;left:" + menuLeft + "px;height:" + menuHeight + "px ;width:100%' ></div>");
  for(counterMenus=0;counterMenus<nMenus;counterMenus++){
		menuTop=this.menuTop;
		mainMenu=menus[counterMenus];
		className=mainMenu.mouseoutClassName;
		align=mainMenu.align;
		clickMethod=mainMenu.id +'.click()';
		mouseOverMethod=mainMenu.id +'.mouseover()';
		mouseOutMethod=mainMenu.id +'.mouseout()';
		document.write("<div id='"+ mainMenu.id +"' onclick='" + clickMethod + "' onmouseover='" + mouseOverMethod + "' onmouseout='" + mouseOutMethod + "' align='"+ align + "' class='" + className + "' style='z-index:3;position:absolute; top:" + menuTop + "px;left:" + menuLeft + "px;height:" + menuHeight + "px ;width:" + menuWidth + "'>" + mainMenu.description + "</div>");
  	subMenus=mainMenu.subMenus;
		nSubMenus=mainMenu.nSubMenus;
		for(counterSubMenus=0;counterSubMenus<nSubMenus;counterSubMenus++){
			subMenu=subMenus[counterSubMenus];
			className=subMenu.mouseoutClassName;
			align=subMenu.align;
			menuTop=menuTop+menuHeight+VP_GAP;
			menuTop=((counterSubMenus==0)?(menuTop+INIT_GAP):menuTop);
			clickMethod=subMenu.id +'.click()';
			mouseOverMethod=subMenu.id +'.mouseover()';
			mouseOutMethod=subMenu.id +'.mouseout()';
			document.write("<div id='"+ subMenu.id +"' onclick='" + clickMethod + "' onmouseover='" + mouseOverMethod + "' onmouseout='" + mouseOutMethod + "' align='"+ align + "' class='" + className + "' style='z-index:3;display:none;position:absolute; top:" + menuTop + "px;left:" + menuLeft + "px;height:" + menuHeight + "px ;width:" + menuWidth + "'> " + subMenu.description + "</div>");			
    
		}
		menuLeft=menuLeft+menuWidth+HP_GAP;
	}
}

//Pop downs the menu
function showMenu(){
	var counterSubMenu=0;
	var nSubMenus=this.nSubMenus;
	var subMenus=this.subMenus;
	var subMenu=null;
	for(counterSubMenu=0;counterSubMenu<nSubMenus;counterSubMenu++){
			subMenu=subMenus[counterSubMenu];
			MM_findObj(subMenu.id).style.display="";			
	}
	if (this.menu.lastClicked!=null && this.menu.lastClicked!=this){
		this.menu.lastClicked.hide();
	}
	this.menu.lastClicked=this;
}

//Mouse over of each sub menu
function onSubMenuMouseOver(){
	MM_findObj(this.id).className=this.mouseoverClassName;
	clearTimeout(this.mainMenu.menu.menuTimer);		
}

//Mouse Out of each sub menu
function onSubMenuMouseOut(){
	MM_findObj(this.id).className=this.mouseoutClassName;
	this.mainMenu.timeout();
}

//Hides the menu on the time out
function menuTimeout(){
	if(this.menu.lastClicked!=null  ){
		this.menu.menuTimer=setTimeout(this.id + ".menu.lastClicked.hide()",this.menu.menuDelay);
	}
}

//Mouse out of each Main Menu
function onMainMenuMouseOut(){
  MM_findObj(this.id).className=this.mouseoutClassName;
	this.timeout();
}

//Mouse over of each Main Menu
function onMainMenuMouseOver(){
  MM_findObj(this.id).className=this.mouseoverClassName;
	clearTimeout(this.menu.menuTimer);		
}

//Dummy method
function dummy(){
	if (this.onclick==null){
		window.status="Undefined Method";
	}else{
		eval(this.onclick);
	}
}
//Hidding a menu
function hideMenu(){
	var counterSubMenu=0;
	var nSubMenus=this.nSubMenus;
	var subMenus=this.subMenus;
	var subMenu=null;
	for(counterSubMenu=0;counterSubMenu<nSubMenus;counterSubMenu++){
			subMenu=subMenus[counterSubMenu];
			MM_findObj(subMenu.id).style.display="none";			
	}	
}

/*Adding MainMenu to a Menu control
@param mainMenu object
*/
function addMainMenu(mainMenu){
	this.menus[this.nMenus++]=mainMenu;
	return mainMenu;
}

/*Adding subMenu to a mainMenu object
@param subMenu object
*/
function addSubMenu(subMenu){
	this.subMenus[this.nSubMenus++]=subMenu;
	return subMenu;
}

/*Inserting MainMenu to in a Menu control
@param menu object
@param mainMenu object
*/
function insMainMenu(menu,mainMenu){ 
  return menu.add(mainMenu) 
} 

/*Inserting subMenu to in a mainMenu object
@param mainMenu object
@param subMenu object
*/
function insSubMenu(mainMenu, subMenu){ 
  return mainMenu.add(subMenu) 
} 

/*global Menu creation function
@param id - Acts as Id as well as control object(instance) - i.e. both should be same
@param menuTop - Top of the Menu Control
@param menuLeft - Left of the Menu Control 
@param menuWidth - Width of the each Menu Item 
@param menuHeight - Height of the each Menu Item 
*/
function gMenu(id,menuTop, menuLeft, menuHeight,menuWidth){ 
  return  new Menu(id,menuTop, menuLeft, menuHeight,menuWidth); 
} 

/*global mainMenu creation function
@param id - Acts as Id as well as Main Menu object(instance) - i.e. both should be same
@param menu - menu control object
@param description - description of the Main menu
*/
function gMainMenu(id,menu,description){ 
  return  new MainMenu(id,menu,description); 
}

/*global subMenu creation function
@param id - Acts as Id as well as Sub Menu object(instance) - i.e. both should be same
@param mainMenu - Main Menu object
@param description - description of the Sub menu
*/
function gSubMenu( id, mainMenu,description){ 
  return  new SubMenu(id, mainMenu,description); 
}