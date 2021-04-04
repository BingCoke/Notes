package com.dengzhitao.notes.entity;

public class Page {
    private Integer id;
    private Integer noteId;
    private Integer page;
    private String content;
    private Integer count;

    public Page() {
    }

    public Page(Integer noteId, Integer page, String content,Integer count) {
        this.id = 0;
        this.noteId = noteId;
        this.page = page;
        this.content = content;
        this.count = count;
    }

    public Page(Integer id, Integer noteId, Integer page, String content, Integer count) {
        this.id = id;
        this.noteId = noteId;
        this.page = page;
        this.content = content;
        this.count = count;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
