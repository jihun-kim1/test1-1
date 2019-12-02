package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import DTO.CommentDTO;
import Dao.CommentDAO;

@WebServlet("*.com")
public class CommentController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf8");
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmd = requestURI.substring(contextPath.length());

		CommentDAO dao = CommentDAO.getInstance();
		if(cmd.contentEquals("/comment.com")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			System.out.println(seq);
			String writer = (String)request.getSession().getAttribute("loginInfo");
			System.out.println(writer);
			String contents = request.getParameter("contents");
			System.out.println(contents);
			CommentDTO dto = new CommentDTO(seq,writer,contents,null);
			try {
				int result = dao.insertComment(dto);
				System.out.println(result);
				if(result > 0) {
					List<CommentDTO> list = new ArrayList<>();
					list = dao.selectAll(seq);
						
					Gson gson = new Gson();
					
					PrintWriter pw = response.getWriter();
					pw.append(gson.toJson(list));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
