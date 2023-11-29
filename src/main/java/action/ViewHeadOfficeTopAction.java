package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import orgex.NSCOException;

public class ViewHeadOfficeTopAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		request.getSession().removeAttribute("scheduleList");
		return "headOfficeTop.jsp";
	}

}
