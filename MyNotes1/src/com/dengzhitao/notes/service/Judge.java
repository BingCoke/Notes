package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.UserDao;
import com.dengzhitao.notes.entity.User;

import java.util.List;

public class Judge {




    //根据用户名寻找用户
    public static boolean haveSameUsername(String username){

        UserDao userDao = new UserDao();
        List<User> users = userDao.selectExact("username",username);
        if(users.size() == 0){
            return false;
        }
        return true;
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
