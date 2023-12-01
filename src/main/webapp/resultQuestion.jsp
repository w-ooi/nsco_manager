<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList,beans.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NatureSportsClub管理システム</title>
<%
	List<Reserve> reserveList = (ArrayList<Reserve>)session.getAttribute("reserveList");
	int cntLesson[] = new int[6];
	int cntInstructor[] = new int[6];
	
	if(reserveList != null && reserveList.size() > 0){
		for(Reserve reserve:reserveList){
			cntLesson[reserve.getLessonEvaluation()]++;
			cntInstructor[reserve.getInstructorEvaluation()]++;
		}
	}
%>
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
<div style="text-align:center;"><strong>アンケート結果</strong></div>
<div>
<table style="margin:auto;border:1px solid;">
	<tr><td style="width:180px;text-align:right;"><strong>レッスン名</strong></td><td style="width:600px"><%= reserveList.get(0).getSchedule().getLesson().getLessonName() %></td></tr>
	<tr><td style="width:180px;text-align:right;"><strong>開催日時</strong></td><td><%= reserveList.get(0).getSchedule().getEventDate() %>&nbsp;<%= reserveList.get(0).getSchedule().getTimeFrame().getStartTime() %>&nbsp;～&nbsp;<%= reserveList.get(0).getSchedule().getTimeFrame().getEndTime() %></td></tr>
	<tr><td style="width:180px;text-align:right;"><strong>インストラクター名</strong></td><td><%= reserveList.get(0).getSchedule().getInstructor().getInstructorName() %></td></tr>
	<tr><td style="width:180px;text-align:right;"><strong>レッスン評価</strong></td><td style="border:1px solid;">
<%
	for(int i = 5; i > 0; i--){
%>
	評価<%= i %>&nbsp;:&nbsp;<%= cntLesson[i] %><br>
<%
	}
%>
	</td></tr>
	<tr><td style="width:180px;text-align:right;"><strong>インストラクター評価</strong></td><td style="border:1px solid;">
<%
	for(int i = 5; i > 0; i--){
%>
	評価<%= i %>&nbsp;:&nbsp;<%= cntInstructor[i] %><br>
<%
	}
%>
	</td></tr>
</table>
</div>
<br>
<div style="text-align:center;">
<form action="mfc" method="post">
<input type="submit" value="トップページへ戻る">
<input type="hidden" name="visit" value="headOfficeTop">
</form>
</div>
</body>
</html>