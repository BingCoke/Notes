package com.dengzhitao.notes.dao;

import com.dengzhitao.notes.entity.Reply;

import java.util.List;

public class ReplyDao extends BaseDaoImpl<Reply> {
    private Class clazz = Reply.class;


    public List<Reply> selectExact(String label, Object value) {
        return super.selectExact(clazz, label, value);
    }


    public Reply selectById(int id) {
        return super.selectById(clazz, id);
    }


    public List<Reply> selectAll() {
        return super.selectAll(clazz);
    }


    public List<Reply> selectVague( String label, Object value) {
        return super.selectVague(clazz, label, value);
    }


}
