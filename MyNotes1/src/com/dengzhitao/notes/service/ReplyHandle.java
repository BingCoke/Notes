package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.ReplyDao;
import com.dengzhitao.notes.entity.Reply;
import com.dengzhitao.notes.entity.UserComment;

import java.util.List;

public class ReplyHandle implements Handle<Reply> {
    ReplyDao replyDao = new ReplyDao();


    @Override
    public List getAll() {
        return replyDao.selectAll();
    }

    @Override
    public Reply getById(int id) {
        return replyDao.selectById(id);
    }

    @Override
    public void save(Reply reply) {
        replyDao.save(reply);
    }

    @Override
    public void remove(Reply reply) {
        replyDao.remove(reply);
    }

    @Override
    public void add(Reply reply) {
        replyDao.add(reply);
    }

    public List<Reply> getByUserComment(UserComment userComment){
       return replyDao.selectExact("userCommentId",userComment.getId());
    }


    public void removeByUserComment(UserComment userComment) {
        List<Reply> replies = getByUserComment(userComment);
        for (Reply reply : replies) {
            remove(reply);
        }
    }
}
