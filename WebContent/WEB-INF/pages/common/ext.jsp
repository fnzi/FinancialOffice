<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/extjs/resources/css/yourtheme.css" />
<script type="text/javascript"
	src="${ctx}/js/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/js/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/js/extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/Ext.ux.override.js"></script>
<script type="text/javascript">
var ctx="${ctx}";
Ext.BLANK_IMAGE_URL = '${ctx}/js/extjs/resources/images/default/s.gif';

/* function KeyDown(){ //屏蔽鼠标右键、Ctrl+n、shift+F10、F5刷新、退格键
 //alert("ASCII代码是："+event.keyCode);
  if ((window.event.altKey)&&
      ((window.event.keyCode==37)||   //屏蔽 Alt+ 方向键 ←
       (window.event.keyCode==39))){  //屏蔽 Alt+ 方向键 →
 //    alert("不准你使用ALT+方向键前进或后退网页！");
     event.returnValue=false;
	  return false;
     }
  //(event.keyCode==8)  ||                 //屏蔽退格删除键
  if ((event.keyCode==116)||                 //屏蔽 F5 刷新键
      (event.keyCode==112)||                 //屏蔽 F1 刷新键
      (event.ctrlKey && event.keyCode==82)){ //Ctrl + R
     event.keyCode=0;
     event.returnValue=false;
		return false;
     }
  if ((event.ctrlKey)&&(event.keyCode==78)) {  //屏蔽 Ctrl+n
     event.returnValue=false;
	 return false;
  }
  if ((event.shiftKey)&&(event.keyCode==121)){ //屏蔽 shift+F10
     event.returnValue=false;
	  return false;
  }
  if (window.event.srcElement.tagName == "A" && window.event.shiftKey){ 
      window.event.returnValue = false;  //屏蔽 shift 加鼠标左键新开一网页
	  return false;
  }
  if ((window.event.altKey)&&(window.event.keyCode==115)){ //屏蔽Alt+F4
      window.showModelessDialog("about:blank","","dialogWidth:1px;dialogheight:1px");
      return false;}
}
document.onkeydown=KeyDown */

//禁止右键弹出菜单  
//function document.oncontextmenu()   
//{   
//      return   false;   
//}
</script>
<style type="text/css">
.delete {
	background-image: url(${ctx}/images/delete.png) !important;
}

.add {
	background-image: url(${ctx}/images/add.png) !important;
}

.edit {
	background-image: url(${ctx}/images/edit.png) !important;
}

.search {
	background-image: url(${ctx}/images/search.png) !important;
}

.accept {
	background-image: url(${ctx}/images/accept.png) !important;
}

.cancel {
	background-image: url(${ctx}/images/cancel.png) !important;
}

.disk {
	background-image: url(${ctx}/images/disk.png) !important;
}

.noneIcon {
	display: none
}

.money {
	background-image: url(${ctx}/images/money.png) !important;
}

.printer {
	background-image: url(${ctx}/images/printer.png) !important;
}

.export {
	background-image: url(${ctx}/images/excel.png) !important;
}

.x-tree-node div.feeds-node {
	background: #eee url(${ctx}/images/cmp-bg.gif) repeat-x;
	margin-top: 1px;
	border-top: 1px solid #ddd;
	border-bottom: 1px solid #ccc;
	padding-top: 2px;
	padding-bottom: 1px;
}

.x-node-ctx {
	background: #eee !important;
	border: 1px solid #ccc !important;
}

.feed {
	border: 1px solid #fff;
	margin: 3px;
}
</style>