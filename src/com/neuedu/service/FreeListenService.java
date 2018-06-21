package com.neuedu.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.neu.edu.util.DBUtil;
import com.neuedu.model.mapper.FreeListenBookMapper;
import com.neuedu.model.mapper.FreeListenMapper;
import com.neuedu.pojo.FreeListen;

public class FreeListenService {
	
	public static List<FreeListen> getFreeListen(int qid, String flag)
	{
		SqlSession session = DBUtil.getSession();
		FreeListenMapper freeListenMapper = (FreeListenMapper)session.getMapper(FreeListenMapper.class);
		List<FreeListen> list = new ArrayList<>();
		if(flag!=null)
		{
			//get top
			list = freeListenMapper.getFreeListensTop(qid);
			
		}
		else
		{
			list = freeListenMapper.getFreeListens(qid);
		}
		
		return list;
	}
	
	public static void addFreeListenBook(int fid, String username, String tel, String comment,String openid)
	{
		SqlSession session = DBUtil.getSession();
		FreeListenBookMapper freeListenBookMapper = (FreeListenBookMapper)session.getMapper(FreeListenBookMapper.class);
		freeListenBookMapper.addFreeListen(fid, username, tel, comment,openid);
		session.commit();
	}
	
	public static FreeListen getFreeListenDetails(int fid)
	{
		SqlSession session = DBUtil.getSession();
		FreeListenMapper freeListenMapper = (FreeListenMapper)session.getMapper(FreeListenMapper.class);
		return freeListenMapper.getFreeListen(fid);
	}

}
