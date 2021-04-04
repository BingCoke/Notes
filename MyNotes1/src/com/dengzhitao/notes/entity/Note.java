package com.dengzhitao.notes.entity;

import com.dengzhitao.notes.service.Id;
import com.dengzhitao.notes.util.JdbcUtil;
import sun.applet.AppletResourceLoader;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class Note {
    private Integer id;
    private String name;
    private Integer page;
    private Integer imgCount;
    private Integer open;
    private Integer repositoryId;
    private Integer groupId;
    private Timestamp date;

    public Note() {

    }

    public Note(Integer id, String name, Integer page, Integer imgCount, Integer open, Integer repositoryId, Integer groupId) {
        this.id = id;
        this.name = name;
        this.page = page;
        this.imgCount = imgCount;
        this.open = open;
        this.repositoryId = repositoryId;
        this.groupId = groupId;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = new Timestamp(date.getTime());
    }

    public Note(Integer id, String name, Integer page, Integer imgCount, Integer open, Integer repositoryId, Integer groupId, Timestamp date) {
        this.id = id;
        this.name = name;
        this.page = page;
        this.imgCount = imgCount;
        this.open = open;
        this.repositoryId = repositoryId;
        this.groupId = groupId;
        this.date = date;
    }

    public Note(String name, Integer page, Integer imgCount, Integer open, Integer repositoryId, Integer groupId) {
        this.id = 0;
        this.name = name;
        this.page = page;
        this.imgCount = imgCount;
        this.open = open;
        this.repositoryId = repositoryId;
        this.groupId = groupId;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = new Timestamp(date.getTime());
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getImgCount() {
        return imgCount;
    }

    public void setImgCount(Integer imgCount) {
        this.imgCount = imgCount;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) &&
                Objects.equals(name, note.name) &&
                Objects.equals(page, note.page) &&
                Objects.equals(imgCount, note.imgCount) &&
                Objects.equals(open, note.open) &&
                Objects.equals(repositoryId, note.repositoryId) &&
                Objects.equals(groupId, note.groupId) &&
                Objects.equals(date, note.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, page, imgCount, open, repositoryId, groupId, date);
    }
}
