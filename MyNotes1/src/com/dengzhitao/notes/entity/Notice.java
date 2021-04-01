package com.dengzhitao.notes.entity;

import com.dengzhitao.notes.service.Id;

public class Notice {
    private Integer id;
    private String title;
    private String detail;

    public Notice(String title, String detail) {
        this.id = 0;
        this.title = title;
        this.detail = detail;
    }

    public Notice() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
