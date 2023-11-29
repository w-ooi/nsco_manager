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
	List<LessonCategory> lessonCategoryList = (ArrayList<LessonCategory>)session.getAttribute("lessonCategoryList");
	List<TimeFrame> timeFrameList = (ArrayList<TimeFrame>)session.getAttribute("timeFrameList");
	List<Instructor> instructorList = (ArrayList<Instructor>)session.getAttribute("instructorList");
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
<div style="text-align:center;"><strong>スケジュール検索</strong></div>
<div style="text-align:center;">次のいずれかの条件で検索ができます</div>
<form action="fc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><td style="width:180px;text-align:right;"><strong>カテゴリ</strong></td><td colspan="2" style="width:300px;"><select name="code">
<option value="all">すべて</option>
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
<input type="hidden" name="visit" value="lessonCategorySearchReserve">
</form>
<br>
<form action="fc" method="post">
<table style="margin:auto;border:1px solid;">
<tr><td style="width:180px;text-align:right;"><strong>日時</strong></td><td style="width:150px;"><input type="date" name="date" required></td><td style="width:150px;"><select name="code">
<option value="all">すべて</option>
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
<input type="hidden" name="visit" value="timeFrameSearchReserve">
</form>
<br>
<form action="fc" method="post">
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
<input type="hidden" name="visit" value="instructorSearchReserve">
</form>
<hr>
<div style="text-align:center;"><strong>検索結果</strong></div>
<%
	if(reserveList.size() > 0){
%>
<div style="text-align:center;">(<%= reserveList.size() %>件)</div>
<%
		for(Reserve reserve:reserveList){
			if(reserve.getSchedule().getCancelFlag() == 0){
%>
		<div class="result">
		<form action="mfc" method="post">
		<table style="margin:auto;border:1px solid;">
			<tr><td style="width:180px;text-align:right;"><strong>レッスン名</strong></td><td style="width:600px"><%= reserve.getSchedule().getLesson().getLessonName() %></td></tr>
			<tr><td style="width:180px;text-align:right;"><strong>開催日時</strong></td><td><%= reserve.getSchedule().getEventDate() %>&nbsp;<%= reserve.getSchedule().getTimeFrame().getStartTime() %>&nbsp;～&nbsp;<%= reserve.getSchedule().getTimeFrame().getEndTime() %></td></tr>
			<tr><td style="width:180px;text-align:right;"><strong>インストラクター名</strong></td><td><%= reserve.getSchedule().getInstructor().getInstructorName() %></td></tr>
			<tr><th colspan="2"><input type="submit" value="アンケート結果を確認する"></th></tr>
		</table>
		<input type="hidden" name="scheduleCode" value=<%= reserve.getReserveCode() %>>
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