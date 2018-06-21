package com.neuedu.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.neuedu.pojo.Sorder;

public interface SorderMapper {
	
	public int addOrder(Sorder s);
	
	public void updatePayStatus(int oid, String status) throws ClassNotFoundException, SQLException;

	public void updatePayTransactionID(int oid, String transactionid) throws ClassNotFoundException, SQLException;

	public List<Map> getAllOrders(String openid) throws ClassNotFoundException, SQLException;

    public Sorder getSorderById(int oid);
}
