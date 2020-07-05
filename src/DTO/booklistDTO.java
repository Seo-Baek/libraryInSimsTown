package DTO;

public class booklistDTO {
	
	private int bno;
	private String booktitle;
	private String author;
	private String loc;
	private String con;  
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return booktitle;
	}
	public void setTitle(String booktitle) {
		this.booktitle = booktitle;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	
}