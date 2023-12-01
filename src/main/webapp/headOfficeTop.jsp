<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NatureSportsClub管理システム</title>
</head>
<body>
<div>
<table style="margin:auto;border-collapse:separate;border-spacing:20px;">
<tr>
<td><a href="index.jsp"><img src="images/logo1.png" width="30%" height="30%"></a></td>
<td><form action="mfc" method="post"><input type="submit" value="ログアウト"><input type="hidden" name="visit" value="logout"></form></td>
</tr>
</table>
</div>
<div style="text-align:center;"><strong>管理システム　メニュー</strong></div>
<form action="mfc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><th style="width:200px;"><input type="submit" value="スケジュール登録"></th></tr>
<tr><th style="width:200px;"><input type="hidden" name="visit" value="registrationSchedulePage"></th></tr>
</table>
</form>
<form action="mfc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><th style="width:200px;"><input type="submit" value="CSV出力"></th></tr>
<tr><th style="width:200px;"><input type="hidden" name="visit" value="outputCsvPage"></th></tr>
</table>
</form>
<form action="mfc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><th style="width:200px;"><input type="submit" value="アンケート確認"></th></tr>
<tr><th style="width:200px;"><input type="hidden" name="visit" value="checkQuestionPage"></th></tr>
</table>
</form>
</body>
</html>