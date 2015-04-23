<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%="<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE8\" />" %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% response.setHeader("Pragma","no-cache");response.setHeader("Cache-Control","no-store");response.setDateHeader("Expires",-1);%>
	<title>A3凭证导入</title>
	<%@ include file="/WEB-INF/pages/common/ext.jsp"%>
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
	<script type="text/javascript" src="${ctx}/js/a3ForDiff.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	${status}
</body>
</html>