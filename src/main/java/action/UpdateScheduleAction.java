package action;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Instructor;
import beans.Lesson;
import beans.Schedule;
import beans.TimeFrame;
import dao.ConnectionManager;
import dao.InstructorDAO;
import dao.LessonDAO;
import dao.ScheduleDAO;
import dao.TimeFrameDAO;
import orgex.NSCOException;

public class UpdateScheduleAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		String nextPage = "error.jsp";
		Connection con = null;
		
		int lessonCode = Integer.parseInt(request.getParameter("lessonCode"));
		String eventDate = request.getParameter("eventDate");
		int timeFrameCode = Integer.parseInt(request.getParameter("timeFrameCode"));
		int instructorCode = Integer.parseInt(request.getParameter("instructorCode"));
		String streamingId = request.getParameter("streamingId");
		String streamingPass = request.getParameter("streamingPass");
		int cancelFlag = Integer.parseInt(request.getParameter("cancelFlag"));
		
		try {
			//データベース接続情報を取得
        	con = ConnectionManager.getConnection();

            // DAOクラスをインスタンス化
        	ScheduleDAO scheduleDao = new ScheduleDAO(con);
        	LessonDAO lessonDao = new LessonDAO(con);
        	TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
        	InstructorDAO instructorDao = new InstructorDAO(con);

			Lesson lesson = lessonDao.getLesson(lessonCode);
			TimeFrame timeFrame = timeFrameDao.getTimeFrame(timeFrameCode);
			Instructor instructor = instructorDao.getInstructor(instructorCode);
        	
			HttpSession session = request.getSession();
			Schedule tmpSchedule = (Schedule)session.getAttribute("schedule");
			int scheduleCode = tmpSchedule.getScheduleCode();
			
			nextPage = "confirmUpdateSchedule.jsp";
			
			if(tmpSchedule.getInstructor().getInstructorCode() != instructorCode && cancelFlag != 1) {
				// インストラクター重複チェック
				boolean resultInstructor = scheduleDao.checkDuplicateInstructor(eventDate,timeFrameCode,instructorCode);
			
				if(!resultInstructor) {
					nextPage = "updateSchedule.jsp";
					session.setAttribute("updateMessage", "同じ日時にインストラクターが担当しています");
					instructor.setInstructorName("");
				}
			}
			
			Schedule schedule = new Schedule(scheduleCode,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,cancelFlag);
			session.setAttribute("updateSchedule", schedule);
		}catch (SQLException e) {
			throw new NSCOException(e.getMessage());
        }finally {
        	if(con != null) {
        		try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
		
		return nextPage;
	}

}
