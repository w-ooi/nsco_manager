<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NatureSportsClub管理システム</title>
<%
	String authenticationMessage = (String)session.getAttribute("authenticationMessage");
%>
</head>
<body>
<div>
<table style="margin:auto;border-collapse:separate;border-spacing:20px;">
<tr>
<td><a href="index.jsp"><img src="images/logo1.png" width="30%" height="30%"></a></td>
</tr>
</table>
</div>
<div style="text-align:center;"><strong>管理システム　ログイン</strong></div>
<form action="mfc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><td style="width:180px;text-align:right;"><strong>ログインID</strong></td><td><input type="text" name="logonId" required></td></tr>
<tr><td style="width:180px;text-align:right;"><strong>パスワード</strong></td><td><input type="password" name="password" required></td></tr>
<tr><th colspan="2"><input type="submit" value="ログイン"></th></tr>
</table>
<input type="hidden" name="visit" value="authenticationLogin">
</form>
<br>
<%
	if(authenticationMessage != null && !authenticationMessage.equals("")){
%>
		<br>
		<div style="text-align:center;color:#ff0000;"><strong><%= authenticationMessage %></strong></div>
		<br>
<%
		session.removeAttribute("authenticationMessage");
	}
%>
</body>
</html>