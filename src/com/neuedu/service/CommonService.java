package com.neuedu.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.neu.edu.util.DBUtil;
import com.neuedu.model.mapper.AddressMapper;
import com.neuedu.model.mapper.EnterpriseMapper;
import com.neuedu.model.mapper.FreeListenBookMapper;
import com.neuedu.model.mapper.LessonMapper;
import com.neuedu.model.mapper.MessageMapper;
import com.neuedu.model.mapper.TeacherMapper;
import com.neuedu.pojo.Branch;
import com.neuedu.pojo.Enterprise;
import com.neuedu.pojo.FreeListenBook;
import com.neuedu.pojo.Message;
import com.neuedu.pojo.Messagelike;
import com.neuedu.pojo.Teacher;

public class CommonService {
	
	public static Enterprise getEnterprise(int qid)
	{
		SqlSession session = DBUtil.getSession();
		EnterpriseMapper enterpriseMapper = session.getMapper(EnterpriseMapper.class);
		return enterpriseMapper.getEnterpriseById(qid);
	}
	
	public static List<Teacher> getTeachers(int qid)
	{
		SqlSession session = DBUtil.getSession();
		TeacherMapper teacherMapper = session.getMapper(TeacherMapper.class);
		return teacherMapper.getTeachers(qid);
	}
	
	public static List<Branch> getBranchs(int qid)
	{
		SqlSession session = DBUtil.getSession();
		AddressMapper addressMapper = session.getMapper(AddressMapper.class);
		return addressMapper.getAllAdress(qid);
	}
	
	public static List<FreeListenBook> getFreeListenBook(String openid, int qid)
	{
		SqlSession session = DBUtil.getSession();
		FreeListenBookMapper freeListenBookMapper = session.getMapper(FreeListenBookMapper.class);
		return freeListenBookMapper.getFreeListenBook(openid, qid);
	}
	
	public static List<String> getSwiper(int qid)
	{
		SqlSession session = DBUtil.getSession();
		EnterpriseMapper enterpriseMapper = session.getMapper(EnterpriseMapper.class);
		return enterpriseMapper.getSwiper(qid); 
	}
	
	public static List<Message> getMessage(int qid)
	{
		SqlSession session = DBUtil.getSession();
		MessageMapper messageMapper = session.getMapper(MessageMapper.class);
		List<Message> m1 = messageMapper.getMessageImgs(qid);
		List<Message> m2 = messageMapper.getMessageLikes(qid);
		List<Message> m3 = messageMapper.getMessageReplies(qid);
		
		//设置点赞
		for(Message item1 :m1)
		{
			for(Message item2 :m2)
			{
				if(item1.getMid() == item2.getMid())
				{
					item1.setMessagelikeList(item2.getMessagelikeList());
				}
			}
		}
		
		//设置评论
		for(Message item1 :m1)
		{
			for(Message item3 :m3)
			{
				if(item1.getMid() == item3.getMid())
				{
					item1.setMessagereplyList(item3.getMessagereplyList());
				}
			}
		}
		
		return m1;
	}
	
	public static void addLike(int mid, String nickName)
	{
		SqlSession session = DBUtil.getSession();
		MessageMapper messageMapper = session.getMapper(MessageMapper.class);
		messageMapper.addLike(mid, nickName);
		
		session.commit();
	}
	
	public static void addReply(int mid, String nickName,String comment)
	{
		SqlSession session = DBUtil.getSession();
		MessageMapper messageMapper = session.getMapper(MessageMapper.class);
		messageMapper.addComment(mid, nickName, comment);
		
		session.commit();
	}

}
