package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.PageDao;
import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.Page;

import java.util.List;

public class PageHandle implements Handle<Page> {
    PageDao pageDao = new PageDao();

    @Override
    public List<Page> getAll() {
        return pageDao.selectAll();
    }

    @Override
    public Page getById(int id) {
        return pageDao.selectById(id);
    }

    @Override
    public void save(Page page) {
        pageDao.save(page);
    }

    @Override
    public void remove(Page page) {
        pageDao.remove(page);
    }

    @Override
    public void add(Page page) {
        pageDao.add(page);
    }


    public List<Page> getNotePage(Note note){
        return pageDao.getNotePage(note);
    }


    //把笔记中的pages都保存
    public void saveAll(List<Page> pages,int initpage,String type){
        switch (type){
            case "add":
                for (Page page : pages) {
                    add(page);
                }
                break;
            case "save":
                int i ;
                for (i = 0; i < initpage; i++) {
                    save(pages.get(i));
                }
                for (;i<pages.size();i++){
                        add(pages.get(i));
                }
                break;
        }
    }


    public void removeAll(List<Page> pages){
        for (Page page : pages) {
            remove(page);
        }
    }
}
