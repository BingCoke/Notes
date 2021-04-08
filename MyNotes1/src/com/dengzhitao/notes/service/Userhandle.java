package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.UserDao;
import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class Userhandle {
    private UserDao userDao = new UserDao();

    public User getById(int id){
        return userDao.selectById(id);
    }

    /**
     * 注册
     * @param user
     */
    public void register(User user){
        userDao.add(user);
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    public User login(String username,String password){
        User user = null;
        //通过用户名搜索
        List<User> list = userDao.selectExact("username",username);
        if(list.size() == 0){
            return null;
        } else {
            //再比对用户密码
            user = list.get(0);
            if(!user.getPassword().equals(DigestUtils.md5Hex(password).toString())){
                System.out.println(password);
                System.out.println(DigestUtils.md5Hex("x"));
                return null;
            }
        }
        return user;
    }

    /**
     * 用户信息更新操作
     * @param user
     */
    public void update(User user){
        userDao.save(user);
    }

    /**
     * 检查用户是否把某个笔记点赞
     * @param note
     * @param user
     * @return
     */
    public Boolean isLike(Note note, User user){return userDao.isLike(note,user);}

    /**
     * 得到所有普通用户
     * @return
     */
    public List<User> getOrdinaryUser(){
        List<User> users = userDao.selectExact("power",2);
        return users;
    }

    /**
     * 得到所有的黑名单用户
     * @return
     */
    public List<User> getBanUser(){
        List<User> users = userDao.selectExact("power",1);
        return users;
    }
}
