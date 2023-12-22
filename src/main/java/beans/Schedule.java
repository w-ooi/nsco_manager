package beans;

//スケジュール
public class Schedule {
	private int scheduleCode;		//スケジュールコード
	private Lesson lesson;			//レッスン
	private String eventDate;		//開催日
	private TimeFrame timeFrame;	//時間枠
	private Instructor instructor;	//インストラクター
	private String streamingId;		//配信ツールID
	private String streamingPass;	//配信ツールパスコード
	private int cancelFlag;			//中止フラグ

	public Schedule() {
	}

	public Schedule(int scheduleCode, Lesson lesson, String eventDate, TimeFrame timeFrame, Instructor instructor,
			String streamingId, String streamingPass, int cancelFlag) {
		this.scheduleCode = scheduleCode;
		this.lesson = lesson;
		this.eventDate = eventDate;
		this.timeFrame = timeFrame;
		this.instructor = instructor;
		this.streamingId = streamingId;
		this.streamingPass = streamingPass;
		this.cancelFlag = cancelFlag;
	}

	public int getScheduleCode() {
		return scheduleCode;
	}

	public void setScheduleCode(int scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public TimeFrame getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(TimeFrame timeFrame) {
		this.timeFrame = timeFrame;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public String getStreamingId() {
		return streamingId;
	}

	public void setStreamingId(String streamingId) {
		this.streamingId = streamingId;
	}

	public String getStreamingPass() {
		return streamingPass;
	}

	public void setStreamingPass(String streamingPass) {
		this.streamingPass = streamingPass;
	}

	public int getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(int cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	@Override
	public String toString() {
		return "Schedule [scheduleCode=" + scheduleCode + ", lesson=" + lesson + ", eventDate=" + eventDate
				+ ", timeFrame=" + timeFrame + ", instructor=" + instructor + ", streamingId=" + streamingId
				+ ", streamingPass=" + streamingPass + ", cancelFlag=" + cancelFlag + "]";
	}
}
