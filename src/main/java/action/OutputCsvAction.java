package action;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Member;
import beans.Schedule;
import dao.ConnectionManager;
import dao.ScheduleDAO;
import orgex.NSCOException;

public class OutputCsvAction implements IAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws NSCOException {
		String nextPage = "error.jsp";
		Connection con = null;
		
		String scheduleCode = request.getParameter("scheduleCode");
		
		try {
			//データベース接続情報を取得
        	con = ConnectionManager.getConnection();

            // DAOクラスをインスタンス化
        	ScheduleDAO scheduleDao = new ScheduleDAO(con);
        	Schedule schedule = scheduleDao.getSchedule(Integer.parseInt(scheduleCode));
        	List<Member> memberList = scheduleDao.getOutputCsvMember(scheduleCode); 

        	if(memberList.size() > 0) {
    			//保存先のフォルダ名
    			String dir = "csv";
    			
    			//保存先フォルダの指定
    			ServletContext sc = request.getServletContext();
    			String p = sc.getRealPath("/" + dir);
            	
        		//現在日時取得用のクラス
        		Calendar calendar = Calendar.getInstance();

        		//書式指定用のクラス
        		SimpleDateFormat  sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        		//ファイル名の作成
        		String csvFileName = sdf.format(calendar.getTime()) + ".csv";
    			
                //ファイルを作成
                FileWriter file = new FileWriter(p + "/" + csvFileName);

                //ファイルに書き込む機能を持ったクラス
                PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            	// メールタイトル(レッスン名)
            	String mailTitle = schedule.getLesson().getLessonName();

            	//ファイルに書き込む
                for(Member member:memberList) {
            		// 送信先メールアドレス
                	String email = member.getEmail();
                	
            		// 宛名(ニックネーム)
                	String nickname = member.getNickname();
                	
                	pw.println(email +","+ nickname +","+ mailTitle);
                }

                //ファイルを閉じる
                pw.close();

                //ファイルのパスをrequestオブジェクトに格納
                request.setAttribute("fileName", dir + "/" + csvFileName);
                
                nextPage = "csvDownload.jsp";
        	}else {
        		request.getSession().setAttribute("outputMessage", "参加者はいませんでした");
        		nextPage = "outputCsv.jsp";
        	}
		}catch (SQLException | IOException e) {
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
