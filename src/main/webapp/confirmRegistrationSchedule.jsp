<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList,beans.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NatureSportsClub管理システム</title>
<%
	Schedule schedule = (Schedule)session.getAttribute("registrationSchedule");

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
<div style="text-align:center;"><strong>スケジュール登録の確認</strong></div>
<div style="text-align:center;">次のスケジュールを登録します</div>
<form action="fc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><td style="width:180px;text-align:right;"><strong>レッスン名</strong></td><td><%= schedule.getLesson().getLessonName() %></td></tr>
<tr><td style="width:180px;text-align:right;"><strong>開催日</strong></td><td><%= schedule.getEventDate() %></td></tr>
<tr><td style="width:180px;text-align:right;"><strong>時間枠</strong></td><td><%= schedule.getTimeFrame().getStartTime() %>～<%= schedule.getTimeFrame().getEndTime() %></td></tr>
<tr><td style="width:180px;text-align:right;"><strong>インストラクター</strong></td><td><%= schedule.getInstructor().getInstructorName() %></td></tr>
<tr><td style="width:180px;text-align:right;"><strong>配信ツールID</strong></td><td><%= schedule.getStreamingId() %></td></tr>
<tr><td style="width:180px;text-align:right;"><strong>配信パスコード</strong></td><td><%= schedule.getStreamingPass() %></td></tr>
<tr><th colspan="2"><input type="submit" value="スケジュール登録の確定"></th></tr>
</table>
<input type="hidden" name="visit" value="confirmRegistrationSchedule">
</form>
<br>
<div style="text-align:center;">
<form action="fc" method="post">
<input type="submit" value="スケジュール登録ページへ戻る">
<input type="hidden" name="visit" value="registrationSchedulePage">
</form>
</div>
</body>
</html>