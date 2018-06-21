package com.neuedu.model.mapper;

import java.util.List;

import com.neuedu.pojo.FreeListen;

public interface FreeListenMapper {
	
	public List<FreeListen> getFreeListensTop(int qid);
	
	public List<FreeListen> getFreeListens(int qid);
	
	public FreeListen getFreeListen(int fid);

}
