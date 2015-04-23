<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无纸化财务办公系统-用户管理</title>
</head>
  <body>
<!-- <iframe  name="usereditFrame"> </iframe> -->
    <table width="100%" height="100%" border="0" cellpadding="0"
        cellspacing="0">
        <tr>
            <td align="center" style="padding-bottom: 100px;">
                <table width="460" border="0" cellpadding="0" cellspacing="0"
                    bgcolor="#FFFFFF">
                    <tr>
                        <td align="center" style="padding-top: 15px;">
                            <form name="addUserForm" method="post"
                                action="${ctx}/doJsp/userService.do?meth=add">
                                <table width="300" border="0" cellspacing="1" cellpadding="1">
                                   <tr>
                                        <td height="30" align="right"></td>
                                        <td><input class="input" maxlength="20" type="hidden"
                                            name="auid" value="${usere.uid}"/></td>
                                    </tr>
                                    <tr>
                                        <td height="30" align="right"><strong>用户名：</strong></td>
                                        <td><input class="input" maxlength="20" type="text"
                                            name="username" value="${usere.username}"/></td>
                                    </tr>
                                    <tr>
                                        <td height="30" align="right"><strong>真实姓名：</strong></td>
                                        <td><input class="input" maxlength="20" type="text"
                                            name="realname" value="${usere.realname}"/></td>
                                    </tr>
                                    <tr>
                                        <td height="30" align="right"><strong>密 码：</strong></td>
                                        <td><input class="input" maxlength="20" type="password"
                                            name="password" value="${usere.password}" /></td>
                                    </tr>
                                    <tr>
                                        <td height="30" align="right"><strong>角色权限：</strong></td>
                                        <td >
                                        <select name="rid" style="width:140px;">
                                        <c:forEach items="${role}" var="roledata" varStatus="roledatac">
                                        <option value="${roledata.rid}">${roledata.rname}</option>
                                        </c:forEach>
                                        </select>
                                        </td>
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
  
	<table width="100%" height="100%" border="1" cellpadding="0"
		cellspacing="0">
		<!-- <tr>
			<td><button type="button"
					onclick="window.location.href='${ctx}/addUserManage.jsp'">添加新用户</button></td>
		</tr> -->
		<tr>
			<td colspan="2" align="center">用户名</td>
			<td colspan="2" align="center">真实姓名</td>
			<td colspan="2" align="center">用户角色</td>
			<td colspan="2" align="center">操作</td>
		</tr>
		<c:forEach items="${user}" var="userdata" varStatus="userdatac">
			<tr>
				<td colspan="2" align="center">${userdata.username}</td>
				<td colspan="2" align="center">${userdata.realname}</td>
				<td colspan="2" align="center">${userdata.role.rname}</td>
				<td colspan="2" align="center"><a href="${ctx}/doJsp/userService.do?meth=edit&uid=${userdata.uid}" >修改</a> | 
    <a href="${ctx}/doJsp/userService.do?meth=delete&uid=${userdata.uid}"  onclick="if(!confirm('您确定删除吗？')) {return false;}">删除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
