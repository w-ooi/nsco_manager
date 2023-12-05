<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList,beans.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NatureSportsClub管理システム</title>
<%
	List<LessonCategory> lessonCategoryList = (ArrayList<LessonCategory>)session.getAttribute("lessonCategoryList");
	List<TimeFrame> timeFrameList = (ArrayList<TimeFrame>)session.getAttribute("timeFrameList");
	List<Instructor> instructorList = (ArrayList<Instructor>)session.getAttribute("instructorList");
	List<Schedule> scheduleList = (ArrayList<Schedule>)session.getAttribute("scheduleList");
	String checkMessage = (String)session.getAttribute("checkMessage");
	
%>
</head>
<body>
<div>
<table style="margin:auto;border-collapse:separate;border-spacing:20px;">
<tr>
<td><a href="${sessionScope.linkPage}"><img src="images/logo1.png" width="30%" height="30%"></a></td>
<td><form action="mfc" method="post"><input type="submit" value="ログアウト"><input type="hidden" name="visit" value="logout"></form></td>
</tr>
</table>
</div>
<div style="text-align:center;"><strong>アンケート結果確認</strong></div>
<div style="text-align:center;">検索をして結果を確認するスケジュールを選択してください</div>
<%
	if(checkMessage != null && !checkMessage.equals("")){
%>
		<br>
		<div style="text-align:center;color:#ff0000;"><strong><%= checkMessage %></strong></div>
		<br>
<%
		session.removeAttribute("checkMessage");
	}
%>
<hr>
<div style="text-align:center;"><strong>スケジュール検索</strong></div>
<div style="text-align:center;">次のいずれかの条件で検索ができます</div>
<form action="mfc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><td style="width:180px;text-align:right;"><strong>カテゴリ</strong></td><td colspan="2" style="width:300px;"><select name="code">
<%
	for(LessonCategory category:lessonCategoryList){
%>
		<option value=<%= category.getLessonCategoryCode() %>><%= category.getLessonCategoryName() %></option>
<%
	}
%>
</select></td>
<td style="width:50px;"><input type="submit" value="検索"></td></tr>
</table>
<input type="hidden" name="visit" value="lessonCategorySearch">
<input type="hidden" name="page" value="checkQuestion.jsp">
</form>
<br>
<form action="mfc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><td style="width:180px;text-align:right;"><strong>日時</strong></td><td style="width:150px;"><input type="date" name="date" required></td><td style="width:150px;"><select name="code">
<%
	for(TimeFrame timeFrame:timeFrameList){
%>
		<option value=<%= timeFrame.getTimeFrameCode() %>><%= timeFrame.getStartTime() %>～<%= timeFrame.getEndTime() %></option>
<%
	}
%>
</select></td>
<td style="width:50px;"><input type="submit" value="検索"></td></tr>
</table>
<input type="hidden" name="visit" value="timeFrameSearch">
<input type="hidden" name="page" value="checkQuestion.jsp">
</form>
<br>
<form action="mfc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><td style="width:180px;text-align:right;"><strong>インストラクター</strong></td><td colspan="2" style="width:300px;"><select name="code">
<%
	for(Instructor instructor:instructorList){
%>
		<option value=<%= instructor.getInstructorCode() %>><%= instructor.getInstructorName() %></option>
<%
	}
%>
</select></td>
<td style="width:50px;"><input type="submit" value="検索"></td></tr>
</table>
<input type="hidden" name="visit" value="instructorSearch">
<input type="hidden" name="page" value="checkQuestion.jsp">
</form>
<hr>
<div style="text-align:center;"><strong>検索結果</strong></div>
<%
	if(scheduleList != null && scheduleList.size() > 0){
%>
<div style="text-align:center;">(<%= scheduleList.size() %>件)</div>
<%
		for(Schedule schedule:scheduleList){
			if(schedule.getCancelFlag() == 0){
%>
		<div>
		<form action="mfc" method="post">
		<table style="margin:auto;border:1px solid;">
			<tr><td style="width:180px;text-align:right;"><strong>レッスン名</strong></td><td style="width:600px"><%= schedule.getLesson().getLessonName() %></td></tr>
			<tr><td style="width:180px;text-align:right;"><strong>開催日時</strong></td><td><%= schedule.getEventDate() %>&nbsp;<%= schedule.getTimeFrame().getStartTime() %>&nbsp;～&nbsp;<%= schedule.getTimeFrame().getEndTime() %></td></tr>
			<tr><td style="width:180px;text-align:right;"><strong>インストラクター名</strong></td><td><%= schedule.getInstructor().getInstructorName() %></td></tr>
			<tr><th colspan="2"><input type="submit" value="結果を確認する"></th></tr>
		</table>
		<input type="hidden" name="scheduleCode" value=<%= schedule.getScheduleCode() %>>
		<input type="hidden" name="visit" value="checkQuestion">
		</form>
		</div>
		<br>
<%
			}
		}
%>
<%
	}else{
%>
		<div style="text-align:center;">検索結果はありません</div>
<%		
	}
%>
<br>
<div style="text-align:center;">
<form action="mfc" method="post">
<input type="submit" value="トップページへ戻る">
<input type="hidden" name="visit" value="headOfficeTop">
</form>
</div>
</body>
</html>