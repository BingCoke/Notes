package com.dengzhitao.notes.dao;

import com.dengzhitao.notes.entity.NoteGroup;
import com.dengzhitao.notes.entity.Repository;

import java.util.List;

public class GroupDao extends BaseDaoImpl<NoteGroup> {
    Class clazz = NoteGroup.class;

    public List<NoteGroup> selectAll() {
        return super.selectAll(clazz);
    }


    public NoteGroup selectById(int id) {
        return super.selectById(clazz, id);
    }

    public List<NoteGroup> selectExact(String label, Object value) {
        return super.selectExact(clazz, label, value);
    }

    public List<NoteGroup> selectVague(String label, Object value) {
        return super.selectVague(clazz, label, value);
    }


}
