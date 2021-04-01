package com.dengzhitao.notes.entity;

import com.dengzhitao.notes.service.Id;

public class NoteGroup {
    private Integer id;
    private String name;
    private Integer repositoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public NoteGroup(String name, Integer repositoryId) {
        this.id = 0;
        this.name = name;
        this.repositoryId = repositoryId;
    }

    public NoteGroup() {
    }
}
