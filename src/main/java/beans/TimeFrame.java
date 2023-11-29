package beans;

//時間枠
public class TimeFrame {
	private int timeFrameCode;	//時間枠コード
	private String startTime;	//開始時刻
	private String endTime;		//終了時刻

	public TimeFrame() {
	}

	public TimeFrame(int timeFrameCode, String startTime, String endTime) {
		this.timeFrameCode = timeFrameCode;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getTimeFrameCode() {
		return timeFrameCode;
	}

	public void setTimeFrameCode(int timeFrameCode) {
		this.timeFrameCode = timeFrameCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
