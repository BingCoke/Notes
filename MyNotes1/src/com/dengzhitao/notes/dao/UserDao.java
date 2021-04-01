package com.dengzhitao.notes.dao;

import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.Repository;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao extends BaseDaoImpl<User> {
    private static Class clazz = User.class;

    public List<User> selectAll() {
        return super.selectAll(clazz);
    }

    public User selectById(int id) {
        return super.selectById(clazz, id);
    }


    public List<User> selectExact(String label, Object value) {
        return super.selectExact(clazz, label, value);
    }

    public List<User> selectVague(String label, Object value) {
        return super.selectVague(clazz, label, value);
    }

    public Boolean isLike(Note note,User user){
        Connection connection = JdbcUtil.connection;
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;
        String sql = "SELECT count(id) count from likes WHERE note_id="+ user.getId() +" and note_id=" + note.getId();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if(resultSet.getInt("count") == 0){
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }

        return true;
    }
}
