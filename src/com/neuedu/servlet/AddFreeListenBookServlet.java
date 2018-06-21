package com.neuedu.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.service.FreeListenService;

@WebServlet("/AddFreeListenBookServlet")
public class AddFreeListenBookServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fid = request.getParameter("fid");
		String username = request.getParameter("username");
		String tel = request.getParameter("tel");
		String comment = request.getParameter("comment");
		String openid = request.getParameter("openid");
		
		FreeListenService.addFreeListenBook(Integer.parseInt(fid), username, tel, comment,openid);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.write("{\"result\":true}");
		out.flush();
		out.close();
		
	}

}
