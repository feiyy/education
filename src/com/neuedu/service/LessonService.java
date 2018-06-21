package com.neuedu.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.neu.edu.util.DBUtil;
import com.neuedu.model.mapper.LessonMapper;
import com.neuedu.pojo.Branch;
import com.neuedu.pojo.Lesson;

public class LessonService {
	
	public static List<Lesson> getLessons(int qid,String flag)
	{
		SqlSession session = DBUtil.getSession();
		LessonMapper lessonMapper = (LessonMapper)session.getMapper(LessonMapper.class);
		if(flag==null)
		{
			
			return lessonMapper.getAllLessons(qid);
		}
		else
		{
			return lessonMapper.getLessonsTop(qid);
		}
	}
	
	public static Lesson getLessonDetails(int lid)
	{
		SqlSession session = DBUtil.getSession();
		LessonMapper lessonMapper = (LessonMapper)session.getMapper(LessonMapper.class);
		return lessonMapper.getLessonDetailById(lid);
	}
	
	public static List<Branch> getBranch(int qid)
	{
		SqlSession session = DBUtil.getSession();
		LessonMapper lessonMapper = (LessonMapper)session.getMapper(LessonMapper.class);
		
		List<Branch> branches= lessonMapper.getBranches(qid);
		for(Branch b :branches)
		{
			List<String> list = new ArrayList<>();
			List<Lesson> lessons = b.getLessons();
			for(Lesson l :lessons)
			{
				if(!list.contains(l.getCategory()))
				{
					list.add(l.getCategory());
				}
			}
			
			b.setCategories(list);
			
		}
		
		return branches;
	}
	
	public static void main(String[] args) {
		
		List<Branch> list = getBranch(1);
		for(Branch b :list)
		{
			System.out.println(b.getBranch());
			
			List<Lesson> lessons = b.getLessons();
			for(Lesson l : lessons)
			{
				System.out.println(l.getLname());
			}
		}
		
	}

}
