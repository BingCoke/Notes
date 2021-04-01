package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.RepositoryDao;
import com.dengzhitao.notes.dao.UserDao;
import com.dengzhitao.notes.entity.Repository;
import com.dengzhitao.notes.entity.User;

import java.util.List;

public class RepositoryHandle implements Handle<Repository> {
    private UserDao userDao = new UserDao();
    private RepositoryDao repositoryDao = new RepositoryDao();

    @Override
    public List<Repository> getAll() {
        return repositoryDao.selectAll();
    }

    @Override
    public Repository getById(int id) {
        return (Repository) repositoryDao.selectById(id);
    }

    @Override
    public void save(Repository repository) {
        repositoryDao.save(repository);
    }

    @Override
    public void remove(Repository repository) {
        repositoryDao.remove(repository);
    }

    @Override
    public void add(Repository repository) {
        repositoryDao.add(repository);
    }


    public List<Repository> getByMaster(User master){
        int userId = master.getId();

        List<Repository> list = repositoryDao.selectExact("userId",userId);

        return list;
    }
}
