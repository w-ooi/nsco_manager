package beans;

//予約
public class Reserve {
	private int reserveCode;	//予約コード
	private Member member;		//会員
	private Schedule schedule;	//スケジュール
	private int attendanceFlag;	//出席フラグ
	private int cancelFlag;		//キャンセルフラグ
	private int lessonEvaluation;		//レッスン評価
	private int instructorEvaluation;	//インストラクター評価

	public Reserve() {
	}

	public Reserve(int reserveCode, Member member, Schedule schedule, int attendanceFlag, int cancelFlag,
			int lessonEvaluation, int instructorEvaluation) {
		this.reserveCode = reserveCode;
		this.member = member;
		this.schedule = schedule;
		this.attendanceFlag = attendanceFlag;
		this.cancelFlag = cancelFlag;
		this.lessonEvaluation = lessonEvaluation;
		this.instructorEvaluation = instructorEvaluation;
	}

	public int getReserveCode() {
		return reserveCode;
	}

	public void setReserveCode(int reserveCode) {
		this.reserveCode = reserveCode;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public int getAttendanceFlag() {
		return attendanceFlag;
	}

	public void setAttendanceFlag(int attendanceFlag) {
		this.attendanceFlag = attendanceFlag;
	}

	public int getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(int cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public int getLessonEvaluation() {
		return lessonEvaluation;
	}

	public void setLessonEvaluation(int lessonEvaluation) {
		this.lessonEvaluation = lessonEvaluation;
	}

	public int getInstructorEvaluation() {
		return instructorEvaluation;
	}

	public void setInstructorEvaluation(int instructorEvaluation) {
		this.instructorEvaluation = instructorEvaluation;
	}
}
