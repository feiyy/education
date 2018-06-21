package com.neuedu.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.neuedu.pojo.Enterprise;
import com.neuedu.pojo.FreeListen;
import com.neuedu.service.CommonService;
import com.neuedu.service.FreeListenService;


@WebServlet("/GetFreeListenDetailsServlet")
public class GetFreeListenDetailsServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fid = request.getParameter("fid");
		
		FreeListen item = FreeListenService.getFreeListenDetails(Integer.parseInt(fid));
		
		Gson g = new Gson();
		String jsonstr = g.toJson(item);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.write(jsonstr);
		out.flush();
		out.close();
	}

}
