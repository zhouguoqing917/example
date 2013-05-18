package org.example.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
 
/**
 * Servlet implementation class Hello
 */
@WebServlet("/helloWorld")
public class HelloServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		writeToResponse(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	//响应输出
     protected void writeToResponse(HttpServletRequest request,HttpServletResponse response )throws ServletException, IOException{
	        String vidOrNid = request.getParameter("nid");
	        String again = request.getParameter("again");
	       
	        String ip = request.getParameter("ip");
	        if (ip == null || ip.equals("")) {
	            ip = request.getHeader("X-REAL-IP");// 取客户端真实IP,由nginx添加在HEADER
	        }
	        if (ip == null || "".equals(ip)) {
	            ip = request.getRemoteAddr();
	        }
	     
	       //  VideoInfoObject object = mobilePlayService.getVideoInfo(vidOrNid, type);
	  
	        request.setAttribute("cid", 1);
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        request.getRequestDispatcher("/hello.jsp").forward(request, response);
	    }


}
