package com.dengzhitao.notes.util;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author Z
 */
public class JdbcUtil {
    public static Connection connection = null;

    /**
   连接mysql
     */
    static {
        Properties properties = new Properties();
        try {
            properties.load(JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            connection = DriverManager.getConnection(
                    properties.getProperty("mysql.url")
                    ,properties.getProperty("mysql.userName")
                    ,properties.getProperty("mysql.passWord"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     关闭资源
     */
    public static void close( Statement statement , ResultSet resultSet){

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
