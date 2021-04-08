package com.dengzhitao.notes.dao;

import com.dengzhitao.notes.util.JdbcUtil;
import com.dengzhitao.notes.util.StringUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDaoImpl<T> implements BaseDao<T> {

    /**
     * 向数据库增加数据
     * @param t
     */
    @Override
    public void add(T t) {
        int i = 0;
        Class clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Connection connection = JdbcUtil.connection;
        PreparedStatement preparedStatement =null;
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ")
                .append(StringUtil.toUnderScoreCase(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1))).append("(");
        for ( i = 0; i < fields.length - 1; i++) {
            sql.append(StringUtil.toUnderScoreCase(fields[i].getName())).append(",");
        }
        sql.append(StringUtil.toUnderScoreCase(fields[i].getName())).append(")").append(" values (");
        for ( i = 0; i < fields.length - 1; i++) {
            sql.append("?,");
        }
        sql.append("?)");
        try {
               preparedStatement = connection.prepareStatement(String.valueOf(sql));
            for (int j = 0; j < fields.length ; j++){
                fields[j].setAccessible(true);
                preparedStatement.setObject(j + 1,fields[j].get(t));
            }
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
             JdbcUtil.close(preparedStatement,null);
        }
    }


    /**
     * 把修改后的对象保存到数据库中
     * @param t
     * @return
     */
    @Override
    public boolean save(T t) {
        PreparedStatement preparedStatement = null;
        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        Class clazz = t.getClass();
        int id = 0;
        StringBuilder stringBuilder = new StringBuilder();
        //循环遍历找到对象的id值并保存
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.getName().equals("id")){
                try {
                     id = Integer.parseInt(field.get(t).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //如果找得到id对应的行就更改数据并返回true
        if (selectById(t.getClass(), id) != null) {
            try {
                stringBuilder.append("update ")
                        .append(StringUtil.toUnderScoreCase(clazz.getName()
                                .substring(clazz.getName().lastIndexOf("." ) + 1)))
                        .append(" set ");
                //拼串建立SQL语句
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    String valueName = StringUtil.toUnderScoreCase(field.getName());
                    Object value = field.get(t);
                    stringBuilder.append(valueName).append("=").append("?").append(",");
                }
                stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
                stringBuilder.append(" where id=").append(id);
                preparedStatement = connection.prepareStatement(stringBuilder.toString());
                //循环设置值
                for (int i = 0; i < declaredFields.length; i++) {
                    declaredFields[i].setAccessible(true);
                    preparedStatement.setObject(i+1,declaredFields[i].get(t));
                }

                preparedStatement.executeUpdate();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                JdbcUtil.close(preparedStatement,resultSet);
            }
            return true;
        }

        return false;
    }

    /**
     * 删除对象对应在数据库的数据
     * @param t
     * @return
     */
    @Override
    public boolean remove(T t) {
        PreparedStatement preparedStatement = null;
        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        Class clazz = t.getClass();
        int id = 0;
        boolean result = false;
        StringBuilder stringBuilder = new StringBuilder();

        //循环遍历找到对象的id值并保存
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.getName().equals("id")){
                try {
                    id = Integer.parseInt(field.get(t).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        //如果找得到id对应的行就删除并返回true
        if (selectById(t.getClass(), id) != null){
            stringBuilder
                    .append("delete from ")
                    .append(StringUtil.toUnderScoreCase(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1)))
                    .append(" where id=").append(id);
        } else {
            return false;
        }
        try {
            preparedStatement = connection.prepareStatement(stringBuilder.toString());
            result = preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }
        return result;
    }


    @Override
    public List<T> selectAll(Class clazz) {
        List<T> list = new ArrayList<>();
        Connection connection = JdbcUtil.connection;
        StringBuilder sql = new StringBuilder();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        sql.append("select * from ").append(StringUtil.toUnderScoreCase(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1)));
        try {
            preparedStatement = connection.prepareStatement(sql.toString());
            resultSet = preparedStatement.executeQuery();
            Field[] fields = clazz.getDeclaredFields();
            while(resultSet.next()){
                Object obj = clazz.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object object = resultSet.getObject(StringUtil.toUnderScoreCase(field.getName()));
                    if(object == null){continue;}
                        field.set(obj,object);
                }
                list.add((T)obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }
        return list;
    }


    /**
     * 模糊查询
     * @param clazz 传入类
     * @param label 传入标签的名字（驼峰命名）
     * @param value 需要查询的标签的值
     * @return
     */
    @Override
    public List<T> selectVague(Class clazz, String label, Object value) {
        StringBuilder sql = new StringBuilder();
        Connection connection = JdbcUtil.connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();
        sql.append("select * from ").append(StringUtil.toUnderScoreCase(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1)))
                .append(" where ")
                .append(StringUtil.toUnderScoreCase(label)).append(" LIKE ")
                .append("'%")
                .append(value)
                .append("%'");
        try {
            preparedStatement = connection.prepareStatement(sql.toString());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Field[] fields = clazz.getDeclaredFields();
                Object obj = clazz.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object object = resultSet.getObject(StringUtil.toUnderScoreCase(field.getName()));
                        field.set(obj,object);
                }
                list.add((T)obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }
        return list;
    }

    /**
     * 准确查询
     * @param clazz
     * @param label 驼峰命名的标签
     * @param value
     * @return
     */
    @Override
    public List<T> selectExact(Class clazz, String label, Object value) {
        StringBuilder sql = new StringBuilder();
        Connection connection = JdbcUtil.connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();
        sql.append("select * from ").append(StringUtil.toUnderScoreCase(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1)))
                .append(" where ")
                .append(StringUtil.toUnderScoreCase(label))
                .append("=").append("?");

        try {
            preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setObject(1,value);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Field[] fields = clazz.getDeclaredFields();
                Object obj = clazz.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object object = resultSet.getObject(StringUtil.toUnderScoreCase(field.getName()));
                        field.set(obj,object);
                }
                list.add((T)obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }
        return list;
    }

    /**
     * 根据id查找 并返回对象
     * @param clazz
     * @param id
     * @return
     */
    @Override
    public T selectById(Class clazz, int id) {
        T t = null;
        ResultSet resultSet = null;
        Connection connection = JdbcUtil.connection;
        PreparedStatement preparedStatement = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from ")
                .append(StringUtil.toUnderScoreCase(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1)))
                .append(" where id = ")
                .append(id);

        try {
            preparedStatement = connection.prepareStatement(stringBuilder.toString());
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                return null;
            }
            t = (T)clazz.newInstance();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object object = resultSet.getObject(StringUtil.toUnderScoreCase(field.getName()));
                field.set(t,object);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }
        return (T)t;
    }


}
