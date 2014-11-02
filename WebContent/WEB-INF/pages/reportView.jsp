<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.tj.ykt.financialoffice.fw.dao.Page" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%  
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
	<base href="<%=basePath%>">
	<title>报表系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/pager.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/test.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery-ui-1.8.1.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery-ui-1.8.1.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/site.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/language.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {     
	    $('.datepicker').datepicker({
	    	changeMonth: true,
	    	changeYear: true,
	    	dateFormat: "yy-mm-dd",
	    	showOn: "button",
	    	buttonImage: '<%=basePath%>images/calendar.gif',
			buttonImageOnly: true
	    });
	});
	
	function checkSearchForm(){
		var bool = $("#searchForm input").hasClass("datepicker");
		if(bool){
			var start = end = "";
			$("#searchForm .datepicker").each(function(){
				var id = $(this).attr("id");
				var arr = new Array();
				arr = id.split("_");
				var len = arr.length;
			    var flag = arr[len - 1];
			    switch(flag){
					case 's': start = id; break;
					case 'e': end = id; break;
					default: break;
			    }
		  	});
		      
			var s = $("#searchForm #"+start).val();
			var e = $("#searchForm #"+end).val();
			if(s != '' && e != ''){
				var k = checkDate(s, e);
				if(!k){
					alert("起始时间应小于结束时间");
					return false;
				}
			}else{
				alert("请设置起始时间与结束时间");
				return false;
			}
		}
		$("#searchForm").submit();
	}
	</script>
</head>
<body>
	<div class="warp">
		<h3>${title}</h3>
		<div>
			<!-- form -->
			<form id="searchForm" action="<%=basePath%>doJsp/reportViewService.action" method="get">
				<input type="hidden" name="report" value="${report}" />
				${options}
				<!-- btn -->
				<c:if test="${options != ''}">
					<input type="button" value="查询" onclick="checkSearchForm();" />
				</c:if>
				<input type="button" value="导出" />
				<input type="button" value="审核" />
			</form>
			<!-- excel data -->
			<div style="margin-top: 20px;">
				<table>
					<thead>${thead}</thead>
					<c:if test="${pager.data != null}">
						<tbody>
							<%
							Page pager = (Page)request.getAttribute("pager");
							for(Object os : pager.getData()) {
								out.print("<tr>");
							    for(Object o : (Object[])os) {
							    	out.print("<td>"+ o +"</td>");
							    }
							    out.print("</tr>");
							}
							%>
						</tbody>
					</c:if>
				</table>
				<div class="page_and_btn">${pager.pageStr}</div>
			</div>
		</div>
	</div>
</body>
</html>