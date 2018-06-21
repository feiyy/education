package com.neuedu.model.mapper;

import java.util.List;

import com.neuedu.pojo.Message;

/**
 * Created by Administrator on 2018/6/14.
 */
public interface MessageMapper
{
    public List<Message> getMessageImgs(int qid);

    public List<Message> getMessageLikes(int qid);

    public List<Message> getMessageReplies(int qid);
    
    public void addLike(int mid, String nickName);
    
    public void addComment(int mid, String nickName, String comment);
}
