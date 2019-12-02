package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import DTO.MemberDTO;

public class MemberDAO {
	// 2. 내부에서 NEW를 한다.
	private static MemberDAO instance;

	private BasicDataSource bds = new BasicDataSource();
	// static 사용시 메모리에 하나만 생성하고 사용하기에, 사용자가 많아져도 과부하가 걸리지 않음.

	// 1. MemberDAO를 NEW하지 못하도록 막음
	private MemberDAO() {
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("manager");
		bds.setPassword("manager");
		bds.setInitialSize(30);
	}

	// 3.single turn pattern
	public synchronized static MemberDAO getInstance() {
		if(instance == null) {
			instance = new MemberDAO();	
		}

		return instance;
	}
	public Connection getConnection() throws Exception{

		return bds.getConnection();

	}

	public boolean isIdExist(String id) throws Exception{
		String sql = "select * from members where id=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			ResultSet rs = pstat.executeQuery();

			boolean result =rs.next();
			return result;
		}
	}
	public int insert(MemberDTO dto) throws Exception{
		String sql = "insert into members values(?,?,?,?,?,?,?,?)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getPw());
			pstat.setString(3, dto.getName());
			pstat.setString(4, dto.getPhone());
			pstat.setString(5, dto.getEmail());
			pstat.setString(6, dto.getZipcode());
			pstat.setString(7, dto.getAddress1());
			pstat.setString(8, dto.getAddress2());

			int result = pstat.executeUpdate();
			return result;
		}
	}
	public boolean login(String id, String pw) throws Exception{
		String sql = "select * from members where id=? and pw=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			pstat.setString(2, pw);
			try(
					ResultSet rs = pstat.executeQuery()){
				boolean result = rs.next();
				return result;	
			}	
		}	
	}
	public int memberOut(String id) throws Exception{
		String sql = "delete from members where id=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);

			int result = pstat.executeUpdate();
			return result;

		}
	}
	public MemberDTO myInfo(String id) throws Exception{
		String sql = "select * from members where id=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, id);
			MemberDTO m = new MemberDTO();		
			try(
					ResultSet rs = pstat.executeQuery()){		
				if(rs.next()) {
					m.setId(rs.getString(1));
					m.setPw(rs.getString(2));
					m.setName(rs.getString(3));
					m.setPhone(rs.getString(4));
					m.setEmail(rs.getString(5));
					m.setZipcode(rs.getString(6));
					m.setAddress1(rs.getString(7));
					m.setAddress2(rs.getString(8));

				}
				return m;
			}
		}
	}
	
	public int modify(MemberDTO dto) throws Exception{
		String sql = "update members set id=?, pw=?, name=?, phone=?, email=?, zipcode=?, address1=?, address2=? where id=? ";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getPw());
			pstat.setString(3, dto.getName());
			pstat.setString(4, dto.getPhone());
			pstat.setString(5, dto.getEmail());
			pstat.setString(6, dto.getZipcode());
			pstat.setString(7, dto.getAddress1());
			pstat.setString(8, dto.getAddress2());		
			pstat.setString(9, dto.getId());

			int result = pstat.executeUpdate();
			return result;
		}
	}

}
