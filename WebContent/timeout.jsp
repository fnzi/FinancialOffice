<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>错误提示</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var ctx = '${ctx}';
var logout = 'login.jsp';

function relogin(){
	parent.location.href = logout;
}
</script>
</head>

<body>
<div class="success">
	<div class="wrap-bg">
		<div class="failure-content">
			<div class="success-text">
				<h3>登录超时，请重新登录</h3>
			</div>
            <p>
                <a href="javascript:void(0)" onclick="relogin()" title="重新登录">重新登录</a>
            </p>
		</div>
	</div>
</div>
</body>
</html>
