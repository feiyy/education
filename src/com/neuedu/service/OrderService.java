package com.neuedu.service;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.ibatis.session.SqlSession;
import org.eclipse.jdt.internal.compiler.batch.Main;

import com.neu.edu.util.DBUtil;
import com.neuedu.model.mapper.RefundMapper;
import com.neuedu.model.mapper.SorderMapper;
import com.neuedu.pojo.Sorder;

public class OrderService {
	
	public static int addOrder(Sorder s)
	{
		SqlSession session = DBUtil.getSession();
		SorderMapper sorderMapper = (SorderMapper)session.getMapper(SorderMapper.class);
		
		sorderMapper.addOrder(s);
		
		session.commit();
		session.close();
		
		return s.getOid();
			    		
	}
	
	public static void askforrefund(int oid, String reason) throws Exception
	{
		SqlSession session = DBUtil.getSession();
		RefundMapper refundMapper = (RefundMapper)session.getMapper(RefundMapper.class);
		SorderMapper sorderMapper = (SorderMapper)session.getMapper(SorderMapper.class);
		try {
			sorderMapper.updatePayStatus(oid, "退款中");
			refundMapper.addRefund(oid, reason);
			session.commit();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			
			session.rollback();
			
			throw e;
		}
		finally
		{
			session.close();
		}
		
		
	}
	
	public static void updatePayStatus(int oid, String transactionid, String status) throws ClassNotFoundException, SQLException
	{
		SqlSession session = DBUtil.getSession();
		SorderMapper sorderMapper = (SorderMapper)session.getMapper(SorderMapper.class);
		RefundMapper refundMapper = (RefundMapper)session.getMapper(RefundMapper.class);
		try
		{
			if(transactionid!=null)
			{
				sorderMapper.updatePayTransactionID(oid, transactionid);
			}
			sorderMapper.updatePayStatus(oid, status);
			
			if(status.equals("已退款"))
			{
				refundMapper.updateRefundStatus(oid);
			}

			session.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			session.rollback();
			
			throw e;
		}
		finally
		{
			session.close();
		}
	}
	
	public static List<Map> getAllOrders(String openid) throws ClassNotFoundException, SQLException
	{
		SqlSession session = DBUtil.getSession();
		SorderMapper sorderMapper = (SorderMapper)session.getMapper(SorderMapper.class);		
		return sorderMapper.getAllOrders(openid);
	}
	
	public static Sorder getOrderById(int oid)
	{
		SqlSession session = DBUtil.getSession();
		SorderMapper sorderMapper = (SorderMapper)session.getMapper(SorderMapper.class);		
		return sorderMapper.getSorderById(oid);
	}
	
	public static void main(String[] args) {
		
		try {
			List<Map> list = getAllOrders("xxx");
			for(Map m :list)
			{
				System.out.println(m.get("lid"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
