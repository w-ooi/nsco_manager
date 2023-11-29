package beans;

//スタッフ
public class Instructor {
	private int instructorCode;		//インストラクタコード
	private String instructorName;	//インストラクタ名
	private Facility facility;	//所属施設
	private String imageFile;	//イメージファイル
	private String loginId;		//ログインID
	private String password;	//パスワード
	
	public Instructor() {
	}

	public Instructor(int instructorCode, String instructorName, Facility facility, String imageFile, String loginId,
			String password) {
		this.instructorCode = instructorCode;
		this.instructorName = instructorName;
		this.facility = facility;
		this.imageFile = imageFile;
		this.loginId = loginId;
		this.password = password;
	}

	public int getInstructorCode() {
		return instructorCode;
	}

	public void setInstructorCode(int instructorCode) {
		this.instructorCode = instructorCode;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
