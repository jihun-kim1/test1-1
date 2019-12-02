package DTO;

public class FilesDTO {
	private int seq;
	private int parent_seq;
	private String file_name;
	private String original_file_name;

	public FilesDTO() {};
	public FilesDTO(int seq, int parent_seq, String file_name, String original_file_name) {
		super();
		this.seq = seq;
		this.parent_seq = parent_seq;
		this.file_name = file_name;
		this.original_file_name = original_file_name;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getParent_seq() {
		return parent_seq;
	}
	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getOriginal_file_name() {
		return original_file_name;
	}
	public void setOriginal_file_name(String original_file_name) {
		this.original_file_name = original_file_name;
	}
}
