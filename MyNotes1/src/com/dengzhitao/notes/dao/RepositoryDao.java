package com.dengzhitao.notes.dao;

import com.dengzhitao.notes.entity.Repository;

import java.util.List;

public class RepositoryDao extends BaseDaoImpl<Repository> {
    private static Class clazz = Repository.class;

    public List selectAll() {
        return super.selectAll(clazz);
    }


    public List selectVague(String label, Object value) {
        return super.selectVague(clazz, label, value);
    }


    public List<Repository> selectExact(String label, Object value) {
        return super.selectExact(clazz, label, value);
    }

    public Repository selectById(int id) {
        return super.selectById(clazz, id);
    }
}
