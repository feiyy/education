package com.neuedu.model.mapper;

import java.util.List;

import com.neuedu.pojo.Enterprise;

public interface EnterpriseMapper {
	
	public Enterprise getEnterpriseById(int qid);
	
	public List<String> getSwiper(int qid);

}
