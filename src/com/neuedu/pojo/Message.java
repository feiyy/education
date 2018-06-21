package com.neuedu.pojo;

import java.util.List;

public class Message {
  private Integer mid;
  private String mtitle;
  private java.util.Date mtime;
  private Integer qid;
  private List<Messageimg> messageimgList;
  private List<String> messagelikeList;
  private List<Messagereply> messagereplyList;

  public List<Messageimg> getMessageimgList()
  {
    return messageimgList;
  }

  public void setMessageimgList(List<Messageimg> messageimgList)
  {
    this.messageimgList = messageimgList;
  }

  public List<String> getMessagelikeList() {
	return messagelikeList;
}

public void setMessagelikeList(List<String> messagelikeList) {
	this.messagelikeList = messagelikeList;
}

public List<Messagereply> getMessagereplyList()
  {
    return messagereplyList;
  }

  public void setMessagereplyList(List<Messagereply> messagereplyList)
  {
    this.messagereplyList = messagereplyList;
  }

  public Integer getMid() {
    return mid;
  }

  public void setMid(Integer mid) {
    this.mid = mid;
  }

  public String getMtitle() {
    return mtitle;
  }

  public void setMtitle(String mtitle) {
    this.mtitle = mtitle;
  }

  public java.util.Date getMtime() {
    return mtime;
  }

  public void setMtime(java.util.Date mtime) {
    this.mtime = mtime;
  }

  public Integer getQid() {
    return qid;
  }

  public void setQid(Integer qid) {
    this.qid = qid;
  }
}
