package com.neuedu.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.neu.edu.util.DBUtil;
import com.neuedu.model.mapper.CouponMapper;
import com.neuedu.pojo.Coupon;

public class CouponService {
	
	public static List<Coupon> getCoupons(int qid)
	{
		SqlSession session = DBUtil.getSession();
		CouponMapper couponMapper = session.getMapper(CouponMapper.class);
		return couponMapper.getCounpons(qid);
	}

}
