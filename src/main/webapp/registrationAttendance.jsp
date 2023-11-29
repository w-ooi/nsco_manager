<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList,beans.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NatureSportsClub管理システム</title>
<link rel="stylesheet" href="css/resultSearch.css">
<%
	List<Reserve> reserveList = (ArrayList<Reserve>)session.getAttribute("reserveList");
	
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
<div style="text-align:center;"><strong>出欠登録</strong></div>
<%
	if(reserveList.size() > 0){
%>
	<table style="margin:auto;border:1px solid;">
		<tr><td style="width:180px;text-align:right;"><strong>レッスン名</strong></td><td style="width:600px"><%= reserveList.get(0).getSchedule().getLesson().getLessonName() %></td></tr>
		<tr><td style="width:180px;text-align:right;"><strong>開催日時</strong></td><td><%= reserveList.get(0).getSchedule().getEventDate() %>&nbsp;<%= reserveList.get(0).getSchedule().getTimeFrame().getStartTime() %>&nbsp;～&nbsp;<%= reserveList.get(0).getSchedule().getTimeFrame().getEndTime() %></td></tr>
		<tr><td style="width:180px;text-align:right;"><strong>インストラクター名</strong></td><td><%= reserveList.get(0).getSchedule().getInstructor().getInstructorName() %></td></tr>
	</table>
	<br>
	<form action="mfc" method="post">
	<table style="margin:auto;border:1px solid;">
		<tr><th style="width:180px;"><strong>参加者名</strong></th><th style="width:600px">出欠</th></tr>
<%		
		int cnt = 0;
		for(Reserve reserve:reserveList){
%>
		<tr><td style="width:180px;text-align:right;"><strong><%= reserve.getMember().getNickname() %></strong></td><td style="width:600px"><input type="radio" name="data<%= cnt %>" value="1" <% if(reserve.getAttendanceFlag()==1){ %>checked<% } %>>出席&nbsp;<input type="radio" name="data<%= cnt %>" value="2" <% if(reserve.getAttendanceFlag()==2){ %>checked<% } %>>欠席</td></tr>
<%
			cnt++;
		}
%>
		<tr><th colspan="2"><input type="submit" value="出欠を登録する"></th></tr>
		</table>
		<input type="hidden" name="visit" value="registrationAttendance">
		</form>
<%
	}else{
%>
		<div style="text-align:center;">参加者はいません</div>
<%		
	}
%>
<br>
<div style="text-align:center;">
<form action="mfc" method="post">
<input type="submit" value="トップページへ戻る">
<input type="hidden" name="visit" value="branchOfficeTop">41
</form>
</div>
</body>
</html>