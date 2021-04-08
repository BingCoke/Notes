package com.dengzhitao.notes.dao;

import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDao extends BaseDaoImpl<Note> {
    private Class clazz = Note.class;


    public List<Note> selectVague(String label, Object value) {
        return super.selectVague(clazz, label, value);
    }


    public List<Note> selectExact(String label, Object value) {
        return super.selectExact(clazz, label, value);
    }


    public Note selectById(int id) {
        return super.selectById(clazz, id);
    }


    public List<Note> selectAll() {
        return super.selectAll(clazz);
    }


    /**
     * 得到某个笔记的点赞数
     * @param note
     * @return
     */
    public Integer getLikeCount(Note note){
        Integer count = 0;
        Connection connection = JdbcUtil.connection;
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;
        String sql = "SELECT count(id) count from likes group by note_id  having note_id=" + note.getId();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                count = resultSet.getInt("count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }
        return count;
    }


    /**
     * 得到new出新的note的id
     * @return
     */
    public int getId(){
        int id = 0;
        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select max(id) id from note");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id") + 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(preparedStatement,resultSet);
        }
        return id;
    }


    /**
     * 根据日期排序所有的公开的笔记（搜索用户
     * @param reader
     * @return
     */
    public List<Note> selectOpenByDate(User reader){
        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Note> notes = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM note where repository_id in (SELECT id from repository WHERE open=1) and open=1"+
                    " union "+
                    "SELECT * from note where repository_id in (SELECT id from repository WHERE repository.user_id = " + String.valueOf(reader.getId()) +")"+
                    " ORDER BY date desc"
            );
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Note note = new Note(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getTimestamp(8)
                );
                notes.add(note);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notes;
    }

    /**
     * 根据日期排序的所有的笔记（管理专用
     * @return
     */
    public List<Note> selectAllByDate(){
        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Note> notes = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM note  ORDER BY date");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Note note = new Note(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getTimestamp(8)
                );
                notes.add(note);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notes;
    }


    /**
     * 根据点赞数排序公开笔记
     * @param reader
     * @return
     */
    public List<Note> selectByLikes(User reader){
        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Note> notes = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT *,count(t.id) count from" +
                    "(SELECT * FROM note where repository_id in (SELECT id from repository WHERE open=1  and open=1) union" +
                    " SELECT * from note where repository_id in (SELECT id from repository WHERE repository.user_id = " + String.valueOf(reader.getId()) +")) " +
                    " t " +
                    "left JOIN" +
                    " likes " +
                    "on t.id=likes.note_id" +
                    " GROUP BY t.id " +
                    "order by count DESC");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Note note = new Note(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getTimestamp(8)
                );
                notes.add(note);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notes;
    }



    /**
     * 普通用户搜索
     * @param reader
     * @param name
     * @return
     */
    public List<Note> selectOpenByName(User reader,String name){
        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Note> notes = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * from " +
                    "(SELECT * FROM note where repository_id in (SELECT id from repository WHERE open=1) and open=1 " +
                    "union " +
                    "SELECT * from note where repository_id in (SELECT id from repository WHERE repository.user_id = "+ reader.getId() +") " +
                    ") " +
                    "note where name = ? ");
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Note note = new Note(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getTimestamp(8)
                );
                notes.add(note);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notes;
    }

    /**
     * 为管理员搜索笔记
     * @param name
     * @return
     */
    public List<Note> selectByName(String name){
        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Note> notes = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("select  * from note where name = ?");
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Note note = new Note(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getTimestamp(8)
                );
                notes.add(note);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notes;
    }



    /**
     * 把某个用户的所有公开的note查找出来
     * @param user
     * @return
     */
    public List<Note> selectOpenInUser(User user){
        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Note> notes = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(" SELECT * FROM note where repository_id in (SELECT id from repository WHERE open=1 and user_id = "+ String.valueOf(user.getId()) +" ) and open=1");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Note note = new Note(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getTimestamp(8)
                );
                notes.add(note);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notes;
    }


    /**
     * 传入list把笔记根据时间排序
     * @param noteList
     * @return
     */
    public List<Note> orderByDate(List<Note> noteList){

        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Note> notes = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * from note where id in (");
        for (Note note : noteList) {
            stringBuilder.append(note.getId()).append(",");
        }

        stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),")");
        stringBuilder.append(" order by date desc");

        try {
            preparedStatement = connection.prepareStatement(stringBuilder.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Note note = new Note(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getTimestamp(8)
                );
                notes.add(note);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notes;
    }


    /**
     * 传入list把笔记根据点赞排序
     * @param noteList
     * @return
     */
    public List<Note> orderByLikes(List<Note> noteList){

        Connection connection = JdbcUtil.connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Note> notes = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT *,count(t.id) count from (SELECT * FROM note where id in (");
        for (Note note : noteList) {
            stringBuilder.append(note.getId()).append(",");
        }
        stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),")");
        stringBuilder.append(") t  left JOIN likes on t.id=likes.note_id GROUP BY t.id order by count DESC");
        try {
            preparedStatement = connection.prepareStatement(stringBuilder.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Note note = new Note(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getTimestamp(8)
                );
                notes.add(note);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return notes;
    }

}
