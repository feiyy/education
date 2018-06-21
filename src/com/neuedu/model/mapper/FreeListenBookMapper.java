package com.neuedu.model.mapper;

import java.util.List;

import com.neuedu.pojo.FreeListenBook;

public interface FreeListenBookMapper {
	
	public void addFreeListen(int fid, String username, String tel, String comment,String openid);
	
	public List<FreeListenBook> getFreeListenBook(String openid, int qid);

}
