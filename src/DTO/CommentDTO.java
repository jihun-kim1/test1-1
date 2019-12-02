package DTO;

import java.util.Date;

public class CommentDTO {
	private int seq;
	private String writer;
	private String contents;
	private Date writedate;
	
	public CommentDTO() {}

	public CommentDTO(int seq, String writer, String contents, Date writedate) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.contents = contents;
		this.writedate = writedate;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	};

	
	
	
	

}
