package cash.vo;

public class Cashbook {
	private int cashbookNo;
	private String member_id;
	private String category;
	private String cashbookDate;
	private int price;
	private String memo;
	private String updatedate;
	private String createdate;
	
	
	public Cashbook() {
		super();
	}

	public Cashbook(int cashbookNo, String member_id, String category, String cashbookDate, int price, String memo,
			String updatedate, String createdate) {
		super();
		this.cashbookNo = cashbookNo;
		this.member_id = member_id;
		this.category = category;
		this.cashbookDate = cashbookDate;
		this.price = price;
		this.memo = memo;
		this.updatedate = updatedate;
		this.createdate = createdate;
	}

	public int getCashbookNo() {
		return cashbookNo;
	}

	public void setCashbookNo(int cashbookNo) {
		this.cashbookNo = cashbookNo;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCashbookDate() {
		return cashbookDate;
	}

	public void setCashbookDate(String cashbookDate) {
		this.cashbookDate = cashbookDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
		return "Cashbook [cashbookNo=" + cashbookNo + ", member_id=" + member_id + ", category=" + category
				+ ", cashbookDate=" + cashbookDate + ", price=" + price + ", memo=" + memo + ", updatedate="
				+ updatedate + ", createdate=" + createdate + "]";
	}
	
}
