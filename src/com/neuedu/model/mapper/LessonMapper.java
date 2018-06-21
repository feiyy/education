package com.neuedu.model.mapper;

import java.util.List;
import java.util.Map;

import com.neuedu.pojo.Branch;
import com.neuedu.pojo.Lesson;

public interface LessonMapper {
	
	public List<Lesson> getAllLessons(int qid);
	
	public List<Lesson> getLessonsTop(int qid);
	
	public Lesson getLessonDetailById(int lid);
	
	public List<Branch> getBranches(int qid);

}
