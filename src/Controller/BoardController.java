package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DTO.BoardDTO;
import DTO.CommentDTO;
import DTO.FilesDTO;
import Dao.BoardDAO;
import Dao.FilesDAO;
import member.board.configuration.Configuration;

@WebServlet("*.board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		String contextPath = request.getContextPath();
		System.out.println(contextPath);
		String cmd = requestURI.substring(contextPath.length());
		System.out.println(cmd);

		BoardDTO dto = new BoardDTO();
		BoardDAO dao = BoardDAO.getInstance();

		request.setCharacterEncoding("utf8");

		if(cmd.contentEquals("/board.board")) {
			try {

				int cpage = 1;
				String page = request.getParameter("cpage");

				if(page != null) {
					cpage = Integer.parseInt(page);
				}
				int start = cpage *(Configuration.recordCountPerPage) - (Configuration.recordCountPerPage -1);
				int end = cpage * Configuration.recordCountPerPage;

				List<BoardDTO> list = dao.selectByPage(start, end);

				String sb = dao.getPageNavi(cpage);

				request.setAttribute("list", list);
				request.setAttribute("sb", sb);
				request.getRequestDispatcher("board/boardlist.jsp").forward(request, response);

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(cmd.contentEquals("/board/writeBoard.board")) {
			request.setCharacterEncoding("UTF-8");
			String uploadPath = request.getServletContext().getRealPath("/files");

			File uploadFilePath = new File(uploadPath);
			if(!uploadFilePath.exists()) {
				uploadFilePath.mkdir();
			}

			int maxSize = 1024 * 1024 *10;

			MultipartRequest Multi = new MultipartRequest(request,uploadPath,maxSize,"UTF8",new DefaultFileRenamePolicy());

			String id = (String)request.getSession().getAttribute("loginInfo");
			String title = Multi.getParameter("title");
			String contents = Multi.getParameter("contents");
			String addr = request.getRemoteAddr();

			int result = 0;
			try {

				int nextVal = dao.nextVal();
				result = dao.insert(nextVal,id, title, contents, addr);
				request.setAttribute("result", result);

				String fileName = Multi.getFilesystemName("file1");    //업로드된 파일 이름
				String oriFileName = Multi.getOriginalFileName("file1");     //업로드할때 당시에 파일 이름
				FilesDTO fdto = new FilesDTO(0,nextVal,fileName,oriFileName);

				if((fileName != null) && (oriFileName != null)) {
					int uploadResult = FilesDAO.getInstance().insert(fdto);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("insertCheck.jsp").forward(request, response);
		}
		else if(cmd.contentEquals("/contentsView.board")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			try {
				BoardDTO result	= dao.print(seq);
				try{
					dao.viewCount(seq);			
				}catch(Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("result", result);
				request.getRequestDispatcher("board/contentsView.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(cmd.contentEquals("/delete.board")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			try {
				int result = dao.deleteContents(seq);
				request.setAttribute("result", result);
				request.getRequestDispatcher("board/deleteCheck.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(cmd.contentEquals("/comment.board")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			String writer = request.getParameter("writer");
			String contents = request.getParameter("contents");
			System.out.println(seq);
			System.out.println(writer);
			System.out.println(contents);
			try {
				CommentDTO cdto = new CommentDTO(seq,writer,contents,null);
				int result = dao.insertComment(cdto);

				if(result>0) {
					PrintWriter pw = response.getWriter();
					pw.append("{\"seq\" : \"" + seq + "\", \"writer\" : \"" + writer + "\", \"contents\" :  \"" + contents + "\"}");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(cmd.contentEquals("/attachedFile.board")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			try {
				List<FilesDTO> list = FilesDAO.getInstance().seqFiles(seq);
				Gson gson = new Gson();
				
				PrintWriter pw = response.getWriter();
				pw.append(gson.toJson(list));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
