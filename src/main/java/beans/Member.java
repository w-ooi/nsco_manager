package beans;

//オンライン会員
public class Member {
	private String memberNo;	//会員番号
	private String nameSei;		//氏名（姓）
	private String nameMei;		//氏名（名）
	private String kanaSei;		//ふりがな（姓）
	private String kanaMei;		//ふりがな（名）
	private String email;		//メールアドレス
	private String nickname;	//ニックネーム
	private String password;	//パスワード
	private Creca creca;		//クレジットカード
	private String crecaNo;		//クレカ番号
	private String creacaExpiration;	//クレカ期限

	public Member() {
	}

	public Member(String memberNo, String nameSei, String nameMei, String kanaSei, String kanaMei, String email,
			String nickname, String password, Creca creca, String crecaNo, String creacaExpiration) {
		this.memberNo = memberNo;
		this.nameSei = nameSei;
		this.nameMei = nameMei;
		this.kanaSei = kanaSei;
		this.kanaMei = kanaMei;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.creca = creca;
		this.crecaNo = crecaNo;
		this.creacaExpiration = creacaExpiration;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getNameSei() {
		return nameSei;
	}

	public void setNameSei(String nameSei) {
		this.nameSei = nameSei;
	}

	public String getNameMei() {
		return nameMei;
	}

	public void setNameMei(String nameMei) {
		this.nameMei = nameMei;
	}

	public String getKanaSei() {
		return kanaSei;
	}

	public void setKanaSei(String kanaSei) {
		this.kanaSei = kanaSei;
	}

	public String getKanaMei() {
		return kanaMei;
	}

	public void setKanaMei(String kanaMei) {
		this.kanaMei = kanaMei;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Creca getCreca() {
		return creca;
	}

	public void setCreca(Creca creca) {
		this.creca = creca;
	}

	public String getCrecaNo() {
		return crecaNo;
	}

	public void setCrecaNo(String crecaNo) {
		this.crecaNo = crecaNo;
	}

	public String getCreacaExpiration() {
		return creacaExpiration;
	}

	public void setCreacaExpiration(String creacaExpiration) {
		this.creacaExpiration = creacaExpiration;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", nameSei=" + nameSei + ", nameMei=" + nameMei + ", kanaSei=" + kanaSei
				+ ", kanaMei=" + kanaMei + ", email=" + email + ", nickname=" + nickname + ", password=" + password
				+ ", creca=" + creca + ", crecaNo=" + crecaNo + ", creacaExpiration=" + creacaExpiration + "]";
	}
}
