package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.NoticeDao;
import com.dengzhitao.notes.entity.Notice;

import java.util.Collections;
import java.util.List;

public class NoticeHandle implements Handle<Notice> {
    NoticeDao noticeDao = new NoticeDao();

    @Override
    public List<Notice> getAll(){
        List<Notice> notices = noticeDao.selectAll();
        Collections.reverse(notices);
        return notices;
    }

    @Override
    public Notice getById(int i){
        return noticeDao.selectById(i);
    }


    @Override
    public void save(Notice notice){
        noticeDao.save(notice);
    }

    @Override
    public void remove(Notice notice){
        noticeDao.remove(notice);
    }

    @Override
    public void add(Notice notice){
        noticeDao.add(notice);
    }


}
