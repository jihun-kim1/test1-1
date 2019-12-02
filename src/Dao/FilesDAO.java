package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import DTO.FilesDTO;


public class FilesDAO {
	private static FilesDAO instance;
	private BasicDataSource bds = new BasicDataSource();

	private FilesDAO() {
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("manager");
		bds.setPassword("manager");
		bds.setInitialSize(30);
	}

	public synchronized static FilesDAO getInstance() {
		if(instance == null) {
			instance = new FilesDAO();	
		}
		return instance;
	}
	public Connection getConnection() throws Exception{

		return bds.getConnection();
	}

	public int insert(FilesDTO dto) throws Exception {
		String sql = "insert into files values(files_seq.nextval,?,?,?)";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareCall(sql);
				){
			pstat.setInt(1, dto.getParent_seq());
			pstat.setString(2, dto.getFile_name());
			pstat.setString(3, dto.getOriginal_file_name());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	public List<FilesDTO> seqFiles(int sequence) throws Exception{
		String sql = "select * from files where seq = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				
				){
			pstat.setInt(1, sequence);
			ResultSet rs = pstat.executeQuery();
			List<FilesDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq = rs.getInt("seq");
				int parent_seq = rs.getInt("parent_seq");
				String file_name = rs.getString("file_name");
				String original_file_name = rs.getString("original_file_name");
				FilesDTO dto = new FilesDTO(seq,parent_seq,file_name,original_file_name);
				list.add(dto);
			}
			return list;
		}
	}
}
