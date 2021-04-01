package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.UserDao;
import com.dengzhitao.notes.entity.User;

import java.util.List;

public class Judge {

    public static boolean haveSameUsername(String username){
        UserDao userDao = new UserDao();
        List<User> list = userDao.selectAll();
        for (User user : list) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean notHaveChinese(String s){
        return (s.length()==s.getBytes().length);
    }

    public static boolean onlyHaveNumber(String s){

        for (int i = 0; i < s.toCharArray().length; i++) {
            if( !(s.toCharArray()[i] <= '9' && s.toCharArray()[i] >= '0') ){
                return false;
            }
        }
        return true;
    }


}
