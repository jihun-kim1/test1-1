package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import DTO.BoardDTO;
import DTO.CommentDTO;
import member.board.configuration.Configuration;

public class BoardDAO {
	private static BoardDAO instance;

	private BasicDataSource bds = new BasicDataSource();

	private BoardDAO() {
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("manager");
		bds.setPassword("manager");
		bds.setInitialSize(30);
	}

	public synchronized static BoardDAO getInstance() {
		if(instance == null) {
			instance = new BoardDAO();	
		}
		return instance;
	}
	public Connection getConnection() throws Exception{

		return bds.getConnection();
	}

	public List<BoardDTO> getList() throws Exception{
		String sql = "select * from board";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			List<BoardDTO> list = new ArrayList<>();
			while(rs.next()){
				BoardDTO dto = new BoardDTO();
				dto.setBoard_seq(rs.getInt(1));
				dto.setWriter(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContents(rs.getString(4));
				dto.setWriteDate(rs.getDate(5));
				dto.setViewCount(rs.getInt(6));
				dto.setIpAddr(rs.getString(7));
				list.add(dto);
			}
			return list;
		}	
	}
	public int insert(int nextVal,String id, String title, String contents, String addr) throws Exception{
		String sql = "insert into board values(?,?,?,?,default,default,?)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, nextVal);
			pstat.setString(2, id);
			pstat.setString(3, title);
			pstat.setString(4, contents);
			pstat.setString(5, addr);

			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	public BoardDTO print(int seq) throws Exception{
		String sql = "select * from board where seq=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);

			ResultSet rs = pstat.executeQuery();
			BoardDTO dto = new BoardDTO();
			if(rs.next()) {
				dto.setBoard_seq(rs.getInt(1));
				dto.setWriter(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContents(rs.getString(4));
				dto.setWriteDate(rs.getDate(5));
				dto.setViewCount(rs.getInt(6));
				dto.setIpAddr(rs.getString(7));
			}
			return dto;

		}
	}
	public int deleteContents(int seq) throws Exception{
		String sql = "delete from board where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			return result;
		}
	}

	public void viewCount(int seq) throws Exception{
		String sql = "update board set viewcount = viewcount+1 where seq = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);
			pstat.executeUpdate();
		}
	}

	public int totalContents() throws Exception {
		String sql = "select count(*) from board";
		try( 
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			rs.next();
			return rs.getInt(1);
		}
	}

	public String getPageNavi(int currentPage) throws Exception  {
		int recordTotalCount = this.totalContents();     // 글의 개수

		int pageTotalCount = 0;         // 총 몇개의 페이지

		if(recordTotalCount % Configuration.recordCountPerPage >0) {
			// 총 글의 개수를 페이지당 보여줄 개수로 나누었을 때, 나머지가 생기면 총 페이지 개수+1
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage + 1;
		}else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		int startNavi = 0;

		// 현재 페이지가 비정상적일때 조정하는 보안 코드
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		// 현재 내가 위치하고 있는 페이지에 따라 네비게이터 시작 페이지 값을 구하는 공식
		startNavi = ((currentPage-1) / Configuration.naviCountPerPage * Configuration.naviCountPerPage) +1;
		int endNavi = startNavi + Configuration.naviCountPerPage -1;

		// 페이지 끝 값이 비정상 값일 때 조정하는 보안 코드
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		System.out.println("현재 페이지 번호" + currentPage);
		System.out.println("네비게이터 페이지 시작번호" + startNavi);
		System.out.println("네비게이터 끝 페이지" + endNavi);	

		boolean needPrev = true;
		boolean needNext = true;

		if(startNavi == 1) {
			needPrev = false;
		}
		if(endNavi == pageTotalCount) {
			needNext = false;
		}

		StringBuilder sb = new StringBuilder();

		if(needPrev)sb.append("<a href='board.board?cpage=" + (startNavi-1) + "'><</a>");
		for(int i= startNavi; i <= endNavi; i++) {
			sb.append("<a href='board.board?cpage=" + i  + "'> ");
			sb.append(i + " ");
			sb.append("</a>");
		}
		if(needNext)sb.append("<a href = 'board.board?cpage=" + (endNavi + 1) + "'>></a>");
		return sb.toString();
	}

	public List<BoardDTO> selectByPage(int start, int end) throws Exception{
		String sql = "select * from " + 
				"(select board.* , row_number() over (order by seq desc) as rown from board) " + 
				"where rown between ? and ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				List<BoardDTO> list = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt(1);
					String writer = rs.getString(2);
					String title = rs.getString(3);
					String contents = rs.getString(4);
					Timestamp writedate = rs.getTimestamp(5);
					int viewcount = rs.getInt(6);
					String ipAddr = rs.getString(7);
					BoardDTO dto = new BoardDTO(seq,writer,title,contents,writedate,viewcount,ipAddr);
					list.add(dto);
				}
				return list;
			}
		}
	}

	public int insertComment(CommentDTO dto) throws Exception{
		String sql = "insert into board_comment values(?,?,?,default)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, dto.getSeq());
			pstat.setString(2, dto.getWriter());
			pstat.setString(3, dto.getContents());

			int result = pstat.executeUpdate();
			return result;
		}
	}
	public int nextVal() throws Exception{
		String sql = "select board_seq.nextval from dual";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			int result = 0;
			if(rs.next()) {
				result = rs.getInt(1);
			}
			return result;
		}
	}
}
