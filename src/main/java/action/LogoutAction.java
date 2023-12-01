package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(); 
		session.removeAttribute("scheduleList");
		session.removeAttribute("registrationSchedule");
		session.removeAttribute("instructor");
		session.removeAttribute("reserveList");
		
		return "index.jsp";
	}

}
