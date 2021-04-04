package com.dengzhitao.notes.dao;

import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.Page;
import com.dengzhitao.notes.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PageDao extends BaseDaoImpl<Page> {

    Class clazz = Page.class;


    public Page selectById(int id) {
        return super.selectById(clazz, id);
    }



    public List<Page> selectAll() {
        return super.selectAll(clazz);
    }


    public List<Page> selectVague(String label, Object value) {
        return super.selectVague(clazz, label, value);
    }


    public List<Page> selectExact(String label, Object value) {
        return super.selectExact(clazz, label, value);
    }

    public List<Page> getNotePage(Note note){
        List<Page> list = new ArrayList<>();
        Connection connection = JdbcUtil.connection;
        StringBuilder sql = new StringBuilder();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("select * from page where note_id =" + note.getId() +" order by page");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(new Page(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }
        return list;
    }

}
