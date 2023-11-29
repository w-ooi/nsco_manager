package action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Schedule;
import dao.ConnectionManager;
import dao.ScheduleDAO;
import orgex.NSCOException;

public class ScheduleSearchByLessonCategoryAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		String nextPage = "error.jsp";
		Connection con = null;
		List<Schedule> scheduleList = null;
		
		try {
        	//データベース接続情報を取得
        	con = ConnectionManager.getConnection();

            // DAOクラスをインスタンス化
        	ScheduleDAO scheduleDao = new ScheduleDAO(con);
	
			//検索項目用
        	String code = request.getParameter("code");
        	scheduleList = scheduleDao.getScheduleByLessonCategory(code, "headOffice");
        	
        	HttpSession session = request.getSession(); 
			session.setAttribute("scheduleList", scheduleList);
			
			nextPage = "outputCsv.jsp";
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
