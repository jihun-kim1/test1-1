package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import DTO.CommentDTO;

public class CommentDAO {
	private static CommentDAO instance;

	private BasicDataSource bds = new BasicDataSource();

	private CommentDAO() {
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("manager");
		bds.setPassword("manager");
		bds.setInitialSize(30);
	}

	public synchronized static CommentDAO getInstance() {
		if(instance == null) {
			instance = new CommentDAO();	
		}
		return instance;
	}
	public Connection getConnection() throws Exception{

		return bds.getConnection();
	}
	
	public int insertComment(CommentDTO dto) throws Exception{
		String sql = "insert into recomment values(?,?,?,sysdate)";
		
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareCall(sql);
				){
			
			pstat.setInt(1, dto.getSeq());
			pstat.setString(2, dto.getWriter());
			pstat.setString(3, dto.getContents());
			
			int result = pstat.executeUpdate();
			
			return result;
		}
	}
	public List<CommentDTO> selectAll(int seq) throws Exception{
		String sql = "select * from recomment where seq = ?";
		
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);
			ResultSet rs = pstat.executeQuery();
	
			List<CommentDTO> list = new ArrayList<>();
			while(rs.next()) {
				CommentDTO dto = new CommentDTO();
				dto.setSeq(rs.getInt(1));
				dto.setWriter(rs.getString(2));
				dto.setContents((rs.getString(3)));
				dto.setWritedate(rs.getDate(4));
				list.add(dto);
			}
			return list;
		}
	}
}
