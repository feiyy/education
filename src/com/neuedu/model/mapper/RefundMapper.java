package com.neuedu.model.mapper;

public interface RefundMapper {
	
	public void addRefund(int oid, String reason);
	
	public void updateRefundStatus(int oid);
	

}
