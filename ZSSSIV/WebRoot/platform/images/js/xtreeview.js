﻿// xtreeView JScript File



var ie5=document.all&&document.getElementById
var ns6=document.getElementById&&!document.all
if (ie5||ns6)
var menuobj=document.getElementById("Panel2")

function showmenuie5(e){
var rightedge=ie5? document.body.clientWidth-event.clientX : window.innerWidth-e.clientX//Find out how close the mouse is to the corner of the window
var bottomedge=ie5? document.body.clientHeight-event.clientY : window.innerHeight-e.clientY
if (rightedge<menuobj.offsetWidth)//若水平距离不够显示菜单的话

menuobj.style.left=ie5? document.body.scrollLeft+event.clientX-menuobj.offsetWidth : window.pageXOffset+e.clientX-menuobj.offsetWidth//移动水平距离使其刚够显示菜单宽度
else
menuobj.style.left=ie5? document.body.scrollLeft+event.clientX : window.pageXOffset+e.clientX//确定鼠标点的水平位置既菜单起始点
if (bottomedge<menuobj.offsetHeight)//若垂直距离不够显示菜单的话
menuobj.style.top=ie5? document.body.scrollTop+event.clientY-menuobj.offsetHeight : window.pageYOffset+e.clientY-menuobj.offsetHeight
else
menuobj.style.top=ie5? document.body.scrollTop+event.clientY : window.pageYOffset+e.clientY
if(ie5)
    window.event.cancelBubble = true;
else if(ns6)
    e.stopPropagation();
menuobj.style.visibility="visible"
    
return false
}

function hidemenuie5(e){
menuobj.style.visibility="hidden"
}

function highlightie5(e){
var firingobj=ie5? event.srcElement : e.target
if (firingobj.className=="menuitems"||ns6&&firingobj.parentNode.className=="menuitems"){
if (ns6&&firingobj.parentNode.className=="menuitems") 
firingobj=firingobj.parentNode //up one node

firingobj.style.backgroundColor="white"

}
}

function lowlightie5(e){
var firingobj=ie5? event.srcElement : e.target
if (firingobj.className=="menuitems"||ns6&&firingobj.parentNode.className=="menuitems"){
if (ns6&&firingobj.parentNode.className=="menuitems") 
firingobj=firingobj.parentNode //up one node

firingobj.style.backgroundColor=""


window.status=''
}
}

function jumptoie5(e){
var firingobj=ie5? event.srcElement : e.target
if (firingobj.className=="menuitems"||ns6&&firingobj.parentNode.className=="menuitems"){
if (ns6&&firingobj.parentNode.className=="menuitems") 
var firingobj2=firingobj.parentNode
// the click can be controlled by firingobj
// if the click was generated by the div tag enclosing the link buttons
//then the value of firingobj=htmlDivElement el
// else if the click was generated by the linkbutton
// then the value of firingobj will be href of the link button and the value of firingobj2 will be htmlDivElement
// the code for controlling the clciks goes here

}
}

if (ie5||ns6){
if(menuobj!=null)
menuobj.style.display=''
document.onclick=hidemenuie5

}

