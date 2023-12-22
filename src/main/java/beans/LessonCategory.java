package beans;

//レッスンカテゴリー
public class LessonCategory {
	private int lessonCategoryCode;		//レッスンカテゴリーコード
	private String lessonCategoryName;	//レッスンカテゴリー名

	public LessonCategory() {
	}

	public LessonCategory(int lessonCategoryCode, String lessonCategoryName) {
		this.lessonCategoryCode = lessonCategoryCode;
		this.lessonCategoryName = lessonCategoryName;
	}

	public int getLessonCategoryCode() {
		return lessonCategoryCode;
	}

	public void setLessonCategoryCode(int lessonCategoryCode) {
		this.lessonCategoryCode = lessonCategoryCode;
	}

	public String getLessonCategoryName() {
		return lessonCategoryName;
	}

	public void setLessonCategoryName(String lessonCategoryName) {
		this.lessonCategoryName = lessonCategoryName;
	}

	@Override
	public String toString() {
		return "LessonCategory [lessonCategoryCode=" + lessonCategoryCode + ", lessonCategoryName=" + lessonCategoryName
				+ "]";
	}
}
