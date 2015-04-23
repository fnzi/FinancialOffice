<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%="<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE8\" />" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% response.setHeader("Pragma","no-cache");response.setHeader("Cache-Control","no-store");response.setDateHeader("Expires",-1);%>
<title>${title}</title>
<%@ include file="/WEB-INF/pages/common/ext.jsp"%>
<script type="text/javascript">
	var ctx = '${ctx}';
	var userName = '${sessionScope.__session_key_user__.username }';
	var userRid = '${sessionScope.__session_key_user__.role.rid }';
</script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<style type="text/css">
a {
	text-decoration: none;
}

a:link {
	text-decoration: none;
	color: black
}

　　a:hover {
	color: blue
}

　　a:visited {
	text-decoration: none;
	color: black
}

.html .body {
	margin: 0 0 0 0;
	padding: 0 0 0 0;
	border: none
}
</style>
</head>
<body>
</body>
</html>