package action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Instructor;
import beans.Lesson;
import beans.TimeFrame;
import dao.ConnectionManager;
import dao.InstructorDAO;
import dao.LessonDAO;
import dao.TimeFrameDAO;
import orgex.NSCOException;

public class ViewRegistrationSchedulePageAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		String nextPage = "error.jsp";
		Connection con = null;
		List<Lesson> lessonList = null;
		List<TimeFrame> timeFrameList = null;
		List<Instructor> instructorList = null;
		
		try {
        	//データベース接続情報を取得
        	con = ConnectionManager.getConnection();

            // DAOクラスをインスタンス化
        	LessonDAO lessonDao = new LessonDAO(con);
        	TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
        	InstructorDAO instructorDao = new InstructorDAO(con);
	
        	lessonList = lessonDao.getAllLessons();
        	timeFrameList = timeFrameDao.getAllTimeFrames();
        	instructorList = instructorDao.getAllInstructors();
			
			HttpSession session = request.getSession();
			session.setAttribute("lessonList", lessonList);
			session.setAttribute("timeFrameList", timeFrameList);
			session.setAttribute("instructorList", instructorList);
			
			nextPage = "registrationSchedule.jsp";
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
