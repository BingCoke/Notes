package com.dengzhitao.notes.dao;

import java.util.List;

public interface BaseDao<T> {

    public void add(T t);

    public boolean save(T t);

    public boolean remove(T t);

    public List<T> selectAll(Class clazz);

    public List<T> selectVague(Class clazz,String label,Object value);

    public List<T> selectExact(Class clazz,String label,Object value);

    public T selectById(Class clazz,int id);
}
