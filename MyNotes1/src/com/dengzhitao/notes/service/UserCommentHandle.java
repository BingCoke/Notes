package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.UserCommentDao;
import com.dengzhitao.notes.entity.UserComment;

import java.util.List;

public class UserCommentHandle implements Handle<UserComment> {

    private UserCommentDao userCommentDao = new UserCommentDao();

    @Override
    public List<UserComment> getAll() {
        return userCommentDao.selectAll();
    }

    @Override
    public UserComment getById(int id) {
        return userCommentDao.selectById(id);
    }

    @Override
    public void save(UserComment userComment) {
        userCommentDao.save(userComment);
    }

    @Override
    public void remove(UserComment userComment) {
        userCommentDao.remove(userComment);
    }

    @Override
    public void add(UserComment userComment) {
        userCommentDao.add(userComment);
    }
}
