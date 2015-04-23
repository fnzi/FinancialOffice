<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" " http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.tj.ykt.financialoffice.fw.entity.Menu"%>
<%@page import="cn.tj.ykt.financialoffice.web.service.JspResult"%>
<html>
<head>
<title>报表显示</title>
</head>
<body>
	<table border="0">
		<c:forEach items="${parentmenus}" var="pmenudata"
			varStatus="pmenudatac">
			<tr>
				<td colspan="2" align="center">${pmenudata.mname}</td>
			</tr>
			<table border="0">
			<c:set var="pmid" value="${pmenudata.mid}" />
			<c:forEach items="${childmenus}" var="cmenudata"
				varStatus="cmenudatac">
				<c:if test="${cmenudata.mmodule==pmid}">
					<tr>
						<td colspan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
							href="${ctx}/doJsp/${cmenudata.mlink}?meth=list"
							target="mainFrame">${cmenudata.mname}</a></td>
					</tr>
				</c:if>
			</c:forEach>
			</table>
		</c:forEach>
	</table>
</body>
</html>
