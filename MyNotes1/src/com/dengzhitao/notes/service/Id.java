package com.dengzhitao.notes.service;

import com.dengzhitao.notes.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Id {
    private String tbaleName;
    private int id;
    public Id(String tbaleName) {
        this.tbaleName = tbaleName;
        Connection connection = JdbcUtil.connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select max(id) id from " + tbaleName);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            this.id = resultSet.getInt("id") + 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }
    }

    public int getId() {
        return id;
    }
}

