<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无纸化财务办公系统</title>
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
							<form name="loginForm" method="post"
								action="${ctx}/doJsp/loginService.do" >
							<table width="300" border="0" cellspacing="0" cellpadding="0">
								<tr> 
                                    <td colspan="2" align="center"><font color="red">&nbsp;${msg}</font></td>
                                </tr>
								<tr>
									<td height="30" align="right"><strong>用户名：</strong></td>
									<td><input class="input" maxlength="20" type="text"name="username" /></td>
								</tr>
								<tr>
									<td height="30" align="right"><strong>密 码：</strong></td>
									<td><input class="input" maxlength="20" type="password"
										name="password" /></td>
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
</body>
</html>
