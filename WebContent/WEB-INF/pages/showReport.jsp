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
	<div class="main">
		<div class="row">
			<div class="rightContent fl">
				<div class="xwdtBox">
					<div class="rightCont">
						<ul>
							<%
								List<Menu> menus = (List<Menu>) request.getAttribute("menu");
								for (Menu me : menus) {
									String mename = me.getMname();
							%>
							<li><a href="${ctx}/copysuccess.jsp" target="mainFrame"><span><%=mename%>
								</span></a> <!-- <a href="doJsp/loginService.action" target="mainFrame"><span>${mename} </span></a> -->
							</li>
							<%
								}
							%>
						</ul>
					</div>
				</div>
			</div>
			<div class="leftContent fl">
				<div class="articleContent">
					<ul class="listStyle2">
						<table cellpadding="0" cellspacing="0" style="margin-bottom: 7px;"
							width="100%" height="400px;">
							<tr>
								<td style="width: 8px;" valign="top"><iframe
										src="${ctx}/showRepRiW.jsp" name="mainFrame" frameborder="0"
										marginheight="0" marginwidth="0" height="700" width="100%"></iframe>
								</td>
							</tr>
						</table>
					</ul>
				</div>

			</div>
		</div>

</body>
</html>
