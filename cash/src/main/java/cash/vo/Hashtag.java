package cash.vo;

public class Hashtag {
	private int cashbookno;
	private String word;
	private String updatedate;
	private String createdate;
	
	public Hashtag() {
		super();
	}
	
	public Hashtag(int cashbookno, String word, String updatedate, String createdate) {
		super();
		this.cashbookno = cashbookno;
		this.word = word;
		this.updatedate = updatedate;
		this.createdate = createdate;
	}
	
	public int getCashbookno() {
		return cashbookno;
	}
	public void setCashbookno(int cashbookno) {
		this.cashbookno = cashbookno;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	@Override
	public String toString() {
		return "Hashtag [cashbookno=" + cashbookno + ", word=" + word + ", updatedate=" + updatedate + ", createdate="
				+ createdate + "]";
	}
}
