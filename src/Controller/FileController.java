package Controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import Dao.FilesDAO;
import DTO.FilesDTO;

@WebServlet("/FileController")
public class FileController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String cmd = requestURI.substring(ctxPath.length());
		
		System.out.println(cmd);
		try {
			if(cmd.contentEquals("/upload.file")) {
				String uploadPath = request.getServletContext().getRealPath("/files");
				System.out.println(uploadPath);

				File uploadFilePath = new File(uploadPath);
				if(!uploadFilePath.exists()) {
					uploadFilePath.mkdir();
				}

				int maxSize = 1024 * 1024 * 10;   //10MB 까지 용량제한

				MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize,"UTF8",new DefaultFileRenamePolicy());

				String fileName = multi.getFilesystemName("file1");    //업로드된 파일 이름
				String oriFileName = multi.getOriginalFileName("file1");     //업로드할때 당시에 파일 이름

				
				System.out.println(fileName);
				System.out.println(oriFileName);
				
				FilesDTO dto = new FilesDTO(0,1,fileName,oriFileName);
				int result = FilesDAO.getInstance().insert(dto);
				
				//썸모노트 코드
				String respPath = "files/";
				respPath += fileName;
				JsonObject obj = new JsonObject();
				
				obj.addProperty("url",respPath);
				System.out.println(obj.toString());
				Gson g = new Gson();
				response.getWriter().append(obj.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
