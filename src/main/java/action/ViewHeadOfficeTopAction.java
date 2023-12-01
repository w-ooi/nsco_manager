package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import orgex.NSCOException;

public class ViewHeadOfficeTopAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		HttpSession session = request.getSession(); 
		session.removeAttribute("scheduleList");
		session.removeAttribute("registrationSchedule");
		
		return "headOfficeTop.jsp";
	}

}
