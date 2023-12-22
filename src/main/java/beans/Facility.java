package beans;

//施設
public class Facility {
	private int facilityCode;		//施設コード(本社:0 各店舗:1以降)
	private String facilityName;	//施設名

	public Facility() {
	}

	public Facility(int facilityCode, String facilityName) {
		this.facilityCode = facilityCode;
		this.facilityName = facilityName;
	}

	public int getFacilityCode() {
		return facilityCode;
	}

	public void setFacilityCode(int facilityCode) {
		this.facilityCode = facilityCode;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	@Override
	public String toString() {
		return "Facility [facilityCode=" + facilityCode + ", facilityName=" + facilityName + "]";
	}
}
