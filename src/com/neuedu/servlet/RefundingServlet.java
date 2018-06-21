package com.neuedu.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.service.OrderService;

/**
 * Servlet implementation class RefundingServlet
 */
@WebServlet("/RefundingServlet")
public class RefundingServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String reason = request.getParameter("reason");
		
		try {
			OrderService.askforrefund(Integer.parseInt(oid), reason);
			
			response.setCharacterEncoding("utf-8");
	    	response.setContentType("text/json");
	    	PrintWriter out = response.getWriter();
	    	out.write("{\"result\":\"申请成功，等待商家处理\"}");
	    	out.close();
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			response.setCharacterEncoding("utf-8");
	    	response.setContentType("text/json");
	    	PrintWriter out = response.getWriter();
	    	out.write("{\"result\":\"申请失败\"}");
	    	out.close();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
