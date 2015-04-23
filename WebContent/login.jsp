<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%="<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE8\" />"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", -1);
%>
<title>${title}</title>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/extjs/resources/css/ext-all.css" /> 
  <style type="text/css">
  #loading-mask{
        position:absolute;
        left:0;
        top:0;
        width:100%;
        height:100%;
        z-index:20000;
        background-color:white;
    }
    #loading{
        position:absolute;
        left:45%;
        top:40%;
        padding:2px;
        z-index:20001;
        height:auto;
    }
    #loading .loading-indicator{
        background:white;
        color:#444;
        font:bold 20px tahoma,arial,helvetica;
        padding:10px;
        margin:0;
        height:auto;
    }
    #loading-msg {
        font: normal 18px arial,tahoma,sans-serif;
    }
  </style>
  </head>
  <body>
<!-- 加载效果 -->
<div id='loading-mask'></div>
<div id="loading">
    <div class="loading-indicator">
      <img src="${ctx}/js/extjs/resources/images/default/shared/large-loading.gif" width="32" height="32" style="margin-right:8px;float:left;vertical-align:top;"/> 
       <br/><span id="loading-msg">Loading ...</span>
    </div>
</div>
  <!-- 加载类库 -->
  <script type="text/javascript" src="${ctx}/js/extjs/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="${ctx}/js/extjs/ext-all.js"></script>
  <script type="text/javascript" src="${ctx}/js/login.js"></script>
  <!-- 退去加载效果 -->
  <script type="text/javascript">
     Ext.get('loading').setOpacity(0.0,{duration:1.0,callback:function(){this.hide();}});
     Ext.get('loading-mask').setOpacity(0.0,{duration:1.0,callback:function(){this.hide();}});
  </script>
   <!-- 登陆界面 -->  
 <div id='wins'></div>
 </body>
</html>

