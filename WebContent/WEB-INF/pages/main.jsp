<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" " http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.tj.ykt.financialoffice.fw.entity.Menu"%>
<%@page import="cn.tj.ykt.financialoffice.web.service.JspResult"%>
<html>
<head>
<title>报表显示</title>
</head>

<frameset rows="25%,75%">
	<frame src="${ctx}/doJsp/mainTopService.do">
	<frameset cols="25%,75%">
		<frame src="${ctx}/doJsp/mainLeftService.do">
		<frame src="${ctx}/doJsp/mainRightService.do" name="mainFrame">
	</frameset>
</frameset>
<body>
</body>
</html>
