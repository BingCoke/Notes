package com.dengzhitao.notes.entity;

public class UserComment {
    private Integer id;
    private String userComment;
    private Integer userId;

    public UserComment() {
    }

    public UserComment(String userComment, Integer userId) {
        this.id = 0;
        this.userComment = userComment;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
