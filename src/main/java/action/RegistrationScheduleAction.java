package action;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class RegistrationScheduleAction implements IAction {

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
        	
			// インストラクター重複チェック
			boolean resultInstructor = scheduleDao.checkDuplicateInstructor(eventDate,timeFrameCode,instructorCode);
			
			if(!resultInstructor) {
				nextPage = "registrationSchedule.jsp";
				request.getSession().setAttribute("registrationMessage", "同じ日時にインストラクターが担当しています");
				instructor.setInstructorName("");
			}else {
				nextPage = "confirmRegistrationSchedule.jsp";
			}

			Schedule schedule = new Schedule(0,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,0);
			
			request.getSession().setAttribute("registrationSchedule", schedule);
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
