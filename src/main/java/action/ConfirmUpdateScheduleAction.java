package action;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Schedule;
import dao.ConnectionManager;
import dao.ScheduleDAO;
import orgex.NSCOException;

public class ConfirmUpdateScheduleAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		String nextPage = "error.jsp";
		Connection con = null;
		
		try {
        	//データベース接続情報を取得
        	con = ConnectionManager.getConnection();

        	HttpSession session = request.getSession(); 

        	// DAOクラスをインスタンス化
        	ScheduleDAO scheduleDao = new ScheduleDAO(con);
        	Schedule schedule = (Schedule)session.getAttribute("updateSchedule");
    		int result = scheduleDao.updateSchedule(schedule);
			
    		if(result == 1) {
				session.setAttribute("updateMessage", "スケジュールを更新しました");
    		}else {
    			session.setAttribute("updateMessage", "スケジュールを更新できませんでした");
    		}
			
    		session.setAttribute("updateSchedule", null);
    		session.setAttribute("scheduleList", null);
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
