package com.neuedu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.pojo.Sorder;
import com.neuedu.service.OrderService;

@WebServlet("/PutOrderServlet")
public class PutOrderServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String openid = request.getParameter("openid");
		String nickname = request.getParameter("nickname");
		String tel = request.getParameter("tel");
		int qid = Integer.parseInt(request.getParameter("qid"));
		int lid = Integer.parseInt(request.getParameter("lid"));
		double money = Double.parseDouble(request.getParameter("money"));
		
		Sorder s = new Sorder();
		s.setLid(lid);
		s.setActual(money);
		s.setOpenid(openid);
		s.setOrdertime(new Timestamp(System.currentTimeMillis()));
		s.setQid(qid);
		s.setStatus("´ý¸¶¿î");
		s.setTotal(money);
		s.setNickname(nickname);
		s.setTel(tel);
		
		int oid = OrderService.addOrder(s);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.write("{\"result\":"+oid+"}");
		out.flush();
		out.close();
					
	}

}
