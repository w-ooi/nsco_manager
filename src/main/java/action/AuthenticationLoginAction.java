package action;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Instructor;
import dao.ConnectionManager;
import dao.InstructorDAO;
import orgex.NSCOException;

public class AuthenticationLoginAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		String nextPage = "error.jsp";
		Connection con = null;
		Instructor instructor = null;
		
		try {
        	//データベース接続情報を取得
        	con = ConnectionManager.getConnection();

            // DAOクラスをインスタンス化
        	InstructorDAO instructorDao = new InstructorDAO(con);
	
        	String loginId = request.getParameter("loginId");
        	String password = request.getParameter("password");
        	instructor = instructorDao.getInstructorByLoginId(loginId, password);

        	HttpSession session = request.getSession(); 
			session.setAttribute("instructor", instructor);
			
			if(instructor != null) {
				if(instructor.getFacility().getFacilityCode() == 0) {
					nextPage = "headOfficeTop.jsp";
				}else {
					nextPage = "branchOfficeTop.jsp";
				}
			}
			
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
