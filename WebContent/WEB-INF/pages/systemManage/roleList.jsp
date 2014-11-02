<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无纸化财务办公系统-角色管理</title>
</head>
<body>
	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td align="center" style="padding-bottom: 100px;">
				<table width="460" border="0" cellpadding="0" cellspacing="0"
					bgcolor="#FFFFFF">
					<tr>
						<td align="center" style="padding-top: 15px;">
							<form name="addroleForm" method="post"
								action="${ctx}/doJsp/roleService.do?meth=add">
								<table width="300" border="0" cellspacing="1" cellpadding="1">
									<tr>
										<td height="30" align="right"></td>
										<td><input class="input" maxlength="20" type="hidden"
											name="arid" value="${rolee.rid}" /></td>
									</tr>
									<tr>
										<td height="30" align="right"><strong>角色名称：</strong></td>
										<td><input class="input" maxlength="20" type="text"
											name="rname" value="${rolee.rname}" /></td>
									</tr>
									<tr>
										<td colspan="2" align="center" height="40"><input
											type="submit" value=" 提 交 " class="input" style="width: 80px" />
											&nbsp; <input type="reset" value=" 重 置 " class="input"
											style="width: 80px" /></td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
				</table>

			</td>
		</tr>
	</table>
<table width="100%" height="100%" border="0" cellpadding="0">
        <tr> 
         <td colspan="2" align="center"><font color="red">&nbsp;${msg}</font></td>
        </tr>
</table>
	<table width="100%" height="100%" border="1" cellpadding="0"
		cellspacing="0">
		
		<!-- <tr>
            <td><button type="button"
                    onclick="window.location.href='${ctx}/addroleManage.jsp'">添加新用户</button></td>
        </tr> -->
		<tr>
			<td colspan="2" align="center">角色名称</td>
			<td colspan="2" align="center">操作</td>
		</tr>
		<c:forEach items="${role}" var="roledata" varStatus="roledatac">
			<tr>
				<td colspan="2" align="center">${roledata.rname}</td>
				<td colspan="2" align="center"><a
					href="${ctx}/doJsp/roleService.do?meth=edit&rid=${roledata.rid}">修改</a>
					| <a
					href="${ctx}/doJsp/roleService.do?meth=delete&rid=${roledata.rid}"
					onclick="if(!confirm('您确定删除吗？')) {return false;}">删除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
