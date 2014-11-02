<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.HashMap"%>
    <%@page import="java.util.Map"%>
    <%@page import="java.util.List"%>
    <%@page import="cn.tj.ykt.financialoffice.fw.entity.Menu"%>
    <%@page import="cn.tj.ykt.financialoffice.web.service.JspResult"%>
    <%@page import="cn.tj.ykt.financialoffice.fw.entity.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>自动登录系统首页</title>
</head>
<body>
登陆成功！

<table width="300" border="0" cellspacing="0" cellpadding="0">

<%
User u = (User)session.getAttribute("__session_key_user__");
out.print(u.getUsername());
%>
              <tr> 
                <td colspan="2" align="center"><font color="red">&nbsp;</font></td>
              </tr>
              <tr>
                <td width="35%" height="30" align="right">成功</td>
                <td><input class="input" maxlength="20" type="text" name="username" /></td> 
              </tr>
            
          </table>
</body>
</html>