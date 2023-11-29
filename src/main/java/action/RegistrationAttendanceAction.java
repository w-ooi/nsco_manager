package action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Reserve;
import dao.ConnectionManager;
import dao.ReserveDAO;
import orgex.NSCOException;

public class RegistrationAttendanceAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		String nextPage = "error.jsp";
		Connection con = null;
		
		HttpSession session = request.getSession();
		List<Reserve> reserveList = (ArrayList<Reserve>)session.getAttribute("reserveList");
		
		int cnt = 0;
		for(Reserve reserve:reserveList) {
			String radioValue = request.getParameter("data"+cnt);
			if(radioValue == null || radioValue.equals("")) {
				reserve.setAttendanceFlag(0);
			}else {
				reserve.setAttendanceFlag(Integer.parseInt(radioValue));
			}
		}
		
		try {
			//データベース接続情報を取得
        	con = ConnectionManager.getConnection();

            // DAOクラスをインスタンス化
        	ReserveDAO reserveDao = new ReserveDAO(con);
			int intReseult = reserveDao.updateAttendance(reserveList);
			
			session.setAttribute("registrationSchedule", reserveList);
			nextPage = "confirmRegistrationSchedule.jsp";
			
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
