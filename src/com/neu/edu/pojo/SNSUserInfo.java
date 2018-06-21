package com.neu.edu.pojo;

import java.util.List;

public class SNSUserInfo {
	
		private String openId;  //用户标识
		private String nickname; //用户昵称
		private int sex; //性别（1是男性 2是女性 0是未知）
		private String country; //国家
		private String province; //省份
		private String city; //城市
		private String headImgUrl; //头像链接
		private List<String> privilegeList; //特权信息
		
		public String getOpenId() {
			return openId;
		}
		public void setOpenId(String openId) {
			this.openId = openId;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public int getSex() {
			return sex;
		}
		public void setSex(int sex) {
			this.sex = sex;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getHeadImgUrl() {
			return headImgUrl;
		}
		public void setHeadImgUrl(String headImgUrl) {
			this.headImgUrl = headImgUrl;
		}
		public List<String> getPrivilegeList() {
			return privilegeList;
		}
		public void setPrivilegeList(List<String> privilegeList) {
			this.privilegeList = privilegeList;
		}
	
		
	

}
