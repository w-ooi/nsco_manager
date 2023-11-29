package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import orgex.NSCOException;

public interface IAction {
	String execute(HttpServletRequest request,HttpServletResponse response) throws NSCOException;
}
