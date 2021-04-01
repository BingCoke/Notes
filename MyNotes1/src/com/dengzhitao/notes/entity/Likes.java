package com.dengzhitao.notes.entity;

public class Likes {
    private Integer id;
    private Integer noteId;
    private Integer userId;

    public Likes() {
    }

    public Likes(Integer noteId, Integer userId) {
        this.id = 0;
        this.noteId = noteId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
