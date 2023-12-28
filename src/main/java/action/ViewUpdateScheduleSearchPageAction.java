package action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Instructor;
import beans.LessonCategory;
import beans.TimeFrame;
import dao.ConnectionManager;
import dao.InstructorDAO;
import dao.LessonCategoryDAO;
import dao.TimeFrameDAO;
import orgex.NSCOException;

public class ViewUpdateScheduleSearchPageAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		String nextPage = "error.jsp";
		Connection con = null;
		List<LessonCategory> lessonCategoryList = null;
		List<Instructor> instructorList = null;
		List<TimeFrame> timeFrameList = null;
		
		try {
        	//データベース接続情報を取得
        	con = ConnectionManager.getConnection();

            // DAOクラスをインスタンス化
            LessonCategoryDAO lessonCategoryDao = new LessonCategoryDAO(con);
            InstructorDAO instructorDao = new InstructorDAO(con);
            TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
	
			//検索項目用
			lessonCategoryList = lessonCategoryDao.getAllLessonCategories();
			instructorList = instructorDao.getAllInstructors();
			timeFrameList = timeFrameDao.getAllTimeFrames();
			
			HttpSession session = request.getSession();
			session.setAttribute("lessonCategoryList", lessonCategoryList);
			session.setAttribute("instructorList", instructorList);
			session.setAttribute("timeFrameList", timeFrameList);
			
			nextPage = "updateScheduleSearch.jsp";
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
