package DTO;

public class memberDTO {
	
	private int mno;
	private String mname;
	private String phone;
	private String addr;
	private int bcount;
	

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public void setBcount(int bcount) {
		this.bcount = bcount;
	}
	public int getBcount() {
		return bcount;
	}

}
