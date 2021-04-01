package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.UserDao;
import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.User;
import com.sun.xml.internal.txw2.output.DumpSerializer;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class Userhandle {
    private UserDao userDao = new UserDao();

    public User getById(int id){
        return userDao.selectById(id);
    }


    public void register(User user){
        userDao.add(user);
    }


    public User login(String username,String password){

        User user = null;
        List<User> list = userDao.selectAll();
        for (User user1 : list) {
            if(user1.getUsername().equals(username) && user1.getPassword().equals(password)){
                user = user1;
            }
        }
        return user;
    }

    public void update(User user){
        userDao.save(user);
    }

    //检查用户是否把某个笔记点赞
    public Boolean isLike(Note note, User user){return userDao.isLike(note,user);}

    //得到所有普通用户
    public List<User> getOrdinaryUser(){
        List<User> users = userDao.selectExact("power",2);
        return users;
    }

    //得到所有的黑名单用户
    public List<User> getBanUser(){
        List<User> users = userDao.selectExact("power",1);
        return users;
    }
}
