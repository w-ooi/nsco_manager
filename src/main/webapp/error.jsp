<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NatureSportsClub管理システム</title>
<%
	String errorMessage = (String)session.getAttribute("errorMessage");
%>
</head>
<body>
<div>
<table style="margin:auto;border-collapse:separate;border-spacing:20px;">
<tr>
<td><a href="${sessionScope.linkPage}"><img src="images/logo1.png" width="30%" height="30%"></a></td>
</tr>
</table>
</div>
<div style="text-align:center;color:#ff0000;"><strong>エラー</strong></div>
<div style="text-align:center;">しばらく経ってからアクセスしてください</div>
<br>
<%
	if(errorMessage != null && !errorMessage.equals("")){
%>
		<br>
		<div style="text-align:center;color:#ff0000;"><strong><%= errorMessage %></strong></div>
		<br>
<%
		session.removeAttribute("registrationMessage");
	}
%>
</html>