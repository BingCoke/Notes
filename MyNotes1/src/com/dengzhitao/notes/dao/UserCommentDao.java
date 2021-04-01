package com.dengzhitao.notes.dao;

import com.dengzhitao.notes.entity.UserComment;

import java.util.List;

public class UserCommentDao extends BaseDaoImpl<UserComment>{

    private Class clazz = UserComment.class;


    public List<UserComment> selectVague(String label, Object value) {
        return super.selectVague(clazz, label, value);
    }


    public List<UserComment> selectAll() {
        return super.selectAll(clazz);
    }


    public UserComment selectById(int id) {
        return super.selectById(clazz, id);
    }


    public List<UserComment> selectExact(String label, Object value) {
        return super.selectExact(clazz, label, value);
    }
}
