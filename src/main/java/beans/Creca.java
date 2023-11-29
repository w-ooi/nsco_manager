package beans;

//クレジットカード
public class Creca {
	private int crecaCompId;		// クレカ会社番号
	private String crecaCompName;	// クレカ会社名

	public Creca() {
	}

	public Creca(int crecaCompId, String crecaCompName) {
		this.crecaCompId = crecaCompId;
		this.crecaCompName = crecaCompName;
	}

	public int getCrecaCompId() {
		return crecaCompId;
	}

	public void setCrecaCompId(int crecaCompId) {
		this.crecaCompId = crecaCompId;
	}

	public String getCrecaCompName() {
		return crecaCompName;
	}

	public void setCrecaCompName(String crecaCompName) {
		this.crecaCompName = crecaCompName;
	}
}
