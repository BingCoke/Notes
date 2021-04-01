package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.GroupDao;
import com.dengzhitao.notes.entity.NoteGroup;
import com.dengzhitao.notes.entity.Repository;

import java.util.List;

public class GroupHandle implements Handle<NoteGroup> {
    RepositoryHandle repositoryHandle = new RepositoryHandle();
    GroupDao groupDao = new GroupDao();
    @Override
    public List<NoteGroup> getAll() {
        return groupDao.selectAll();
    }

    @Override
    public NoteGroup getById(int id) {
        return groupDao.selectById(id);
    }

    @Override
    public void save(NoteGroup noteGroup) {
        groupDao.save(noteGroup);
    }

    @Override
    public void remove(NoteGroup noteGroup) {
        groupDao.remove(noteGroup);
    }

    @Override
    public void add(NoteGroup noteGroup) {
        groupDao.add(noteGroup);
    }


    public List<NoteGroup> getByRepository(Repository repository){
        return groupDao.selectExact("repositoryId",repository.getId());
    }


    public void removeByRepository(Repository repository){
        List<NoteGroup> noteGroups = groupDao.selectExact("repositoryId",repository.getId());

        for (NoteGroup noteGroup : noteGroups) {
            remove(noteGroup);
        }
    }


}
