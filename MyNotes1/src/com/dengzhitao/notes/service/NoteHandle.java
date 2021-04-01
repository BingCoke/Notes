package com.dengzhitao.notes.service;

import com.dengzhitao.notes.dao.LikesDao;
import com.dengzhitao.notes.dao.NoteDao;
import com.dengzhitao.notes.dao.RepositoryDao;
import com.dengzhitao.notes.dao.UserDao;
import com.dengzhitao.notes.entity.*;

import java.util.*;

public class NoteHandle implements Handle<Note> {
    private NoteDao noteDao = new NoteDao();
    private LikesDao likesDao = new LikesDao();
    private RepositoryDao repositoryDao = new RepositoryDao();
    private RepositoryHandle repositoryHandle = new RepositoryHandle();
    private UserDao userDao = new UserDao();

    @Override
    public List<Note> getAll() {
        return noteDao.selectAll();
    }

    @Override
    public Note getById(int id) {
        return noteDao.selectById(id);
    }

    @Override
    public void save(Note note) {
        noteDao.save(note);
    }

    @Override
    public void remove(Note note) {
        noteDao.remove(note);
    }

    @Override
    public void add(Note note) {
        noteDao.add(note);
    }


    //根据组得到组下的所有的笔记
    public List<Note> getByGroup(NoteGroup noteGroup){
        int groupId = noteGroup.getId();
        return noteDao.selectExact("groupId",groupId);
    }


    //计算note的点赞数
    public int likeCount(Note note){
        return noteDao.getLikeCount(note);
    }

    //根据传来的知识库的对象把子数据删除
    public void removeByRepository(Repository repository){
        List<Note> notes = noteDao.selectExact("repositoryId",repository.getId());
        for (Note note : notes) {
            List<Likes> likeList = likesDao.selectExact(Likes.class,"noteId",note.getId());
            for (Likes like : likeList) {
                likesDao.remove(like);
            }
            remove(note);
        }
    }

    //根据传来的组把所有的对应数据删除
    public void removeByGroup(NoteGroup noteGroup){
        List<Note> notes = noteDao.selectExact("noteGroup",noteGroup.getId());
        for (Note note : notes) {
            List<Likes> likeList = likesDao.selectExact(Likes.class,"noteId",note.getId());
            for (Likes like : likeList) {
                likesDao.remove(like);
            }
            remove(note);
        }
    }

    public int getId(){
        return noteDao.getId();
    }


    public List<Note> getOpenNotes(User reader){
        return noteDao.selectAllOpen(reader);
    }



    public List<Note> getOpenNotesByDate(User reader){
        return noteDao.selectOpenByDate(reader);
    }

    public List<Note> getAllNotesByDate(){
        return noteDao.selectAllByDate();
    }

    public List<Note> getNotesByLikes(User reader){
        return noteDao.selectByLikes(reader);
    }


    public List<Note> getOpenNotesByUserName(String name, User reader){
        List<User> users = userDao.selectExact("name",name);
        List<Repository> repositories = null;
        Set<Note> notes = new HashSet<>();
        for (User user : users) {
            if(user.getId() == reader.getId()){
              repositories = repositoryHandle.getByMaster(user);
              if(repositories == null){
                  continue;
              }
                for (Repository repository : repositories) {
                     List<Note> noteList = noteDao.selectExact("repositoryId",repository.getId());
                     notes.addAll(noteList);
                }
            } else {
                notes.addAll(noteDao.selectOpenInUser(user));
            }
        }

        return new ArrayList<Note>(notes);
    }


    //得到note的作者
    public User getMaster(Note note){
        return userDao.selectById(repositoryDao.selectById(note.getRepositoryId()).getUserId());
    }


    public List<Note> orderByDate (List<Note> notes){
        return noteDao.orderByDate(notes);
    }

    public List<Note> orderByLikes (List<Note> notes){
        return noteDao.orderByLikes(notes);
    }

}
