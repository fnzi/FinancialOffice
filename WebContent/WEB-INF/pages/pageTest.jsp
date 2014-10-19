<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cn.tj.ykt.financialoffice.fw.dao.Page" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>分页测试jsp页</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/pager.css" />
</head>
<body>

<%
Page pager = (Page)request.getAttribute("pager");
for(Object os : pager.getData()) {
    for(Object o : (Object[])os) {
        out.print(o);
        out.print(", ");
    }
    out.println("<br />");
}

%>


<div class="page_and_btn">${pager.pageStr }</div>
</body>
</html>