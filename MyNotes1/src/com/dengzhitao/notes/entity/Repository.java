package com.dengzhitao.notes.entity;

import com.dengzhitao.notes.service.Id;

public class Repository {
    private Integer id;
    private String name;
    private Integer userId;
    private Integer open;

    public Repository(String name, Integer userId, Integer open) {
        this.id = 0;
        this.name = name;
        this.userId = userId;
        this.open = open;
    }

    public Repository() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", open=" + open +
                '}';
    }
}
