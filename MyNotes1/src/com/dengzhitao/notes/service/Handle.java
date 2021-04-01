package com.dengzhitao.notes.service;

import java.util.List;

public interface Handle<T> {
    public List<T> getAll();

    public T getById(int id);

    public void save(T t);

    public void remove(T t);

    public void add(T t);
}
