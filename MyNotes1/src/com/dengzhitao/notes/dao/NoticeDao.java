package com.dengzhitao.notes.dao;

import com.dengzhitao.notes.entity.Notice;

import java.util.List;

public class NoticeDao extends BaseDaoImpl<Notice>{
    private static Class clazz = Notice.class;

    public List<Notice> selectAll() {
        return super.selectAll(clazz);
    }

    public List<Notice> selectVague(String label, Object value) {
        return super.selectVague(clazz, label, value);
    }

    public Notice selectById(int id) {
        return super.selectById(clazz, id);
    }
}
