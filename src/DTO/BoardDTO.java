package DTO;

import java.util.Date;

public class BoardDTO {
	private int board_seq;
	private String writer;
	private String title;
	private String contents;
	private Date writeDate;
	private int viewCount;
	private String ipAddr;
	
	public BoardDTO() {};
	public BoardDTO(int board_seq,String writer, String title, String contents, Date writeDate, int viewCount, String ipAddr) {
		super();
		this.board_seq = board_seq;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.writeDate = writeDate;
		this.viewCount = viewCount;
		this.ipAddr = ipAddr;
	}
	
	public int getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	
	
	
	

}
