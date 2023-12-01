<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList,beans.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NatureSportsClub管理システム</title>
<%
	List<Lesson> lessonList = (ArrayList<Lesson>)session.getAttribute("lessonList");
	List<TimeFrame> timeFrameList = (ArrayList<TimeFrame>)session.getAttribute("timeFrameList");
	List<Instructor> instructorList = (ArrayList<Instructor>)session.getAttribute("instructorList");
	Instructor instructor = (Instructor)session.getAttribute("instructor");
	Schedule schedule = (Schedule)session.getAttribute("registrationSchedule");
	String registrationMessage = (String)session.getAttribute("registrationMessage");

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
<div style="text-align:center;"><strong>スケジュール登録</strong></div>
<div style="text-align:center;">すべての項目を入力してください</div>
<div style="text-align:center;">同じ日時の複数レッスンに同じインストラクターは担当できません</div>
<%
	if(registrationMessage != null && !registrationMessage.equals("")){
%>
		<br>
		<div style="text-align:center;color:#ff0000;"><strong><%= registrationMessage %></strong></div>
		<br>
<%
		session.removeAttribute("registrationMessage");
	}
%>

<form action="mfc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><td style="width:180px;text-align:right;"><strong>レッスン名</strong></td><td><select name="lessonCode">
<%
	for(Lesson lesson:lessonList){
%>
		<option value="<%= lesson.getLessonCode() %>" <% if(schedule!=null && (schedule.getLesson().getLessonCode() == lesson.getLessonCode())){ %>selected<% } %>><%= lesson.getLessonName() %></option>
<%
	}
%>
</select></td>
</tr>
<tr><td style="width:180px;text-align:right;"><strong>開催日</strong></td><td><input type="date" name="eventDate" required value="<% if(schedule!=null){ %><%= schedule.getEventDate() %><% } %>"></td></tr>
<tr><td style="width:180px;text-align:right;"><strong>時間枠</strong></td><td><select name="timeFrameCode">
<%
	for(TimeFrame timeFrame:timeFrameList){
%>
		<option value="<%= timeFrame.getTimeFrameCode() %>" <% if(schedule!=null && (schedule.getTimeFrame().getTimeFrameCode() == timeFrame.getTimeFrameCode())){ %>selected<% } %>><%= timeFrame.getStartTime() %>～<%= timeFrame.getEndTime() %></option>
<%
	}
%>
</select></td>
</tr>
<tr><td style="width:180px;text-align:right;"><strong>インストラクター</strong></td><td><select name="instructorCode">
<%
	for(Instructor inst:instructorList){
%>
		<option value="<%= inst.getInstructorCode() %>" <% if(schedule!=null && (schedule.getInstructor().getInstructorCode() == inst.getInstructorCode())){ %>selected<% } %>><%= inst.getInstructorName() %></option>
<%
	}
%>
</select></td>
</tr>
<tr><td style="width:180px;text-align:right;"><strong>配信ツールID</strong></td><td><input type="text" name="streamingId" required value="<% if(schedule!=null){ %><%= schedule.getStreamingId() %><% } %>"></td></tr>
<tr><td style="width:180px;text-align:right;"><strong>配信パスコード</strong></td><td><input type="text" name="streamingPass" required value="<% if(schedule!=null){ %><%= schedule.getStreamingPass() %><% } %>"></td></tr>
<tr><th colspan="2"><input type="submit" value="スケジュール登録"></th></tr>
</table>
<input type="hidden" name="visit" value="registrationSchedule">
</form>
<br>
<div style="text-align:center;">
<form action="mfc" method="post">
<input type="submit" value="トップページへ戻る">
<input type="hidden" name="visit" value="headOfficeTop">
</form>
</div>
</body>
</html>