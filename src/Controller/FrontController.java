package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.MemberDTO;
import Dao.MemberDAO;
import Utils.Encrypt;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.mem")
public class FrontController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmd = requestURI.substring(contextPath.length());
		request.setCharacterEncoding("utf8");
		
		Encrypt en = new Encrypt();
		System.out.println(cmd);

		MemberDAO dao = MemberDAO.getInstance();
		if(cmd.contentEquals("/member/DupleCheck.mem")) {
			String id = request.getParameter("inputId");
			System.out.println(id);
			try {
				boolean result = dao.isIdExist(id);
				System.out.println(result);
				if(!result) {
					PrintWriter pw = response.getWriter();
					pw.append("You can USE");
				}else {
					PrintWriter pw = response.getWriter();
					pw.append("You can not use");
				}

			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(cmd.contentEquals("/member/signup.mem")) {
			String id = request.getParameter("id");
			String pw = en.encrypt((request.getParameter("pw")));
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String zipcode = request.getParameter("zipcode");
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");

			MemberDTO dto = new MemberDTO(id,pw,name,phone,email,zipcode,address1,address2);
			try {
				int result = dao.insert(dto);
				request.setAttribute("result", result);
				request.getRequestDispatcher("signupCheck.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(cmd.contentEquals("/FrontController.mem")) {
			String id = request.getParameter("id");
			String pw = en.encrypt(request.getParameter("pw"));

			try {
				boolean result = dao.login(id, pw);
				System.out.println(result);
				request.setAttribute("result", result);
				if(result) {
					request.getSession().setAttribute("loginInfo",id);
				}
				request.getRequestDispatcher("member/loginCheck.jsp").forward(request, response);

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(cmd.contentEquals("/logout.mem")) {
			String id= (String) request.getSession().getAttribute("loginInfo");
			request.getSession().invalidate();
			response.sendRedirect("index.jsp");
		}
		else if(cmd.contentEquals("/memberOut.board")) {
			String id = (String)request.getSession().getAttribute("loginInfo");
			request.getSession().invalidate();
			try {
				int result = dao.memberOut(id);
				request.setAttribute("result",result);
				request.getRequestDispatcher("member/memberoutCheck.jsp");


			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(cmd.contentEquals("/memberout.mem")) {
			String id = (String)request.getSession().getAttribute("loginInfo");
			try {
				int result = dao.memberOut(id);
				if(result>0) {
					request.getSession().invalidate();
					request.setAttribute("result", result);
					request.getRequestDispatcher("member/memberoutCheck.jsp").forward(request, response);
				}


			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(cmd.contentEquals("/modify.mem")) {
			String id = (String)request.getSession().getAttribute("loginInfo");
			try {
				MemberDTO result = dao.myInfo(id);
				request.setAttribute("result", result);
				request.getRequestDispatcher("member/modifyForm.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(cmd.contentEquals("/modified.mem")) {
			String id = request.getParameter("id");
			String pw = en.encrypt(request.getParameter("pw"));
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String zipcode = request.getParameter("zipcode");
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");
			
			MemberDTO dto = new MemberDTO(id,pw,name,phone,email,zipcode,address1,address2);
			try {
				request.getSession().invalidate();
				int result = dao.modify(dto);
				request.setAttribute("result", result);
				request.getRequestDispatcher("member/modified.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(cmd.contentEquals("/myInfo.mem")) {
			String id = (String)request.getSession().getAttribute("loginInfo");
			
			try {
				MemberDTO dto = dao.myInfo(id);
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("member/myInfo.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
