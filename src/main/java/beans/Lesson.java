package beans;

//レッスン
public class Lesson {
	private int lessonCode;					//レッスンコード
	private String lessonName;				//レッスン名
	private String description;				//説明文
	private LessonCategory lessonCategory;	//レッスンカテゴリー

	public Lesson() {
	}

	public Lesson(int lessonCode, String lessonName, String description, LessonCategory lessonCategory) {
		this.lessonCode = lessonCode;
		this.lessonName = lessonName;
		this.description = description;
		this.lessonCategory = lessonCategory;
	}

	public int getLessonCode() {
		return lessonCode;
	}

	public void setLessonCode(int lessonCode) {
		this.lessonCode = lessonCode;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LessonCategory getLessonCategory() {
		return lessonCategory;
	}

	public void setLessonCategory(LessonCategory lessonCategory) {
		this.lessonCategory = lessonCategory;
	}
}
