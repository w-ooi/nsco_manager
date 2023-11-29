package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.AuthenticationLoginAction;
import action.CheckQuestionAction;
import action.ConfirmRegistrationScheduleAction;
import action.IAction;
import action.LogoutAction;
import action.OutputCsvAction;
import action.RegistrationScheduleAction;
import action.ReserveSearchByInstructorAction;
import action.ReserveSearchByLessonCategoryAction;
import action.ReserveSearchByTimeFrameAction;
import action.ScheduleSearchByInstructorAction;
import action.ScheduleSearchByLessonCategoryAction;
import action.ScheduleSearchByTimeFrameAction;
import action.ViewCheckQuestionPageAction;
import action.ViewHeadOfficeTopAction;
import action.ViewOutputCsvPageAction;
import action.ViewRegistrationSchedulePageAction;
import orgex.NSCOException;

@WebServlet("/mfc")
public class FrontController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "index.jsp";

        // フォワード設定
        RequestDispatcher rd = request.getRequestDispatcher(nextPage);

        // フォワード
        rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		IAction action = null;
		String visit = request.getParameter("visit");
		
		switch(visit) {
		case "authenticationLogin":
			action = new AuthenticationLoginAction();
			break;
		case "logout":
			action = new LogoutAction();
			break;
		case "registrationSchedulePage":
			action = new ViewRegistrationSchedulePageAction();
			break;
		case "registrationSchedule":
			action = new RegistrationScheduleAction();
			break;
		case "confirmRegistrationSchedule":
			action = new ConfirmRegistrationScheduleAction();
			break;
		case "outputCsvPage":
			action = new ViewOutputCsvPageAction();
			break;
		case "lessonCategorySearch":
			action = new ScheduleSearchByLessonCategoryAction();
			break;
		case "timeFrameSearch":
			action = new ScheduleSearchByTimeFrameAction();
			break;
		case "instructorSearch":
			action = new ScheduleSearchByInstructorAction();
			break;
		case "outputCsv":
			action = new OutputCsvAction();
			break;
		case "headOfficeTop":
			action = new ViewHeadOfficeTopAction();
			break;
		case "checkQuestionPage":
			action = new ViewCheckQuestionPageAction();
			break;
		case "checkQuestion":
			action = new CheckQuestionAction();
			break;
		case "lessonCategorysearchReserve":
			action = new ReserveSearchByLessonCategoryAction();
			break;
		case "timeFrameSearchReserve":
			action = new ReserveSearchByTimeFrameAction();
			break;
		case "instructorSearchReserve":
			action = new ReserveSearchByInstructorAction();
			break;
		}

        String nextPage = null;
        
        try {
			nextPage = action.execute(request, response);
		} catch (NSCOException e) {
			request.setAttribute("errorMessage", e.getMessage());
			nextPage = "error.jsp";
		}

        // フォワード設定
        RequestDispatcher rd = request.getRequestDispatcher(nextPage);

        // フォワード
        rd.forward(request, response);
	}
}
