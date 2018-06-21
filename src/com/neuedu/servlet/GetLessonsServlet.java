package com.neuedu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.neuedu.pojo.Lesson;
import com.neuedu.service.LessonService;


@WebServlet("/GetLessonsServlet")
public class GetLessonsServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag = request.getParameter("flag");
		String qid = request.getParameter("qid");
		
		List<Lesson> lessons = LessonService.getLessons(Integer.parseInt(qid),flag);
		
		Gson g = new Gson();
		String jsonstr = g.toJson(lessons);		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.write(jsonstr);
		out.flush();
		out.close();
	}

}
