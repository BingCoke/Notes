package com.dengzhitao.notes.entity;

public class Reply {
    private Integer id;
    private String reply;
    private Integer userId;
    private Integer userCommentId;

    public Reply() {
    }

    public Reply(String reply, Integer userId, Integer userCommentId) {
        this.id = 0;
        this.reply = reply;
        this.userId = userId;
        this.userCommentId = userCommentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserCommentId() {
        return userCommentId;
    }

    public void setUserCommentId(int userCommentId) {
        this.userCommentId = userCommentId;
    }
}
