package com.neuedu.pojo;

public class WeiXin {
	
	private int qid;
	private String appid;
	private String appsecret;
	private String partner;
	private String partnerkey;
	private String weixinpaycallback;
		
	public String getWeixinpaycallback() {
		return weixinpaycallback;
	}
	public void setWeixinpaycallback(String weixinpaycallback) {
		this.weixinpaycallback = weixinpaycallback;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getPartnerkey() {
		return partnerkey;
	}
	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}
}
