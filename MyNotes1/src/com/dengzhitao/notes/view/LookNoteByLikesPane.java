package com.dengzhitao.notes.view;

import com.dengzhitao.notes.dao.NoteDao;
import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.NoteHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.util.ArrayList;
import java.util.List;

public class LookNoteByLikesPane {
    BorderPane root = new BorderPane();
    private NoteHandle noteHandle = new NoteHandle();
    ListView<String> listView = new ListView();
    private User reader;
    List<Note> notes;


    public Pane getPane(User reader){
        this.reader = reader;

        noteFlush();

        listView.setOnMouseClicked(new ViewClick());



        root.setCenter(listView);
        return root;
    }


    //list view点击事件打开笔记
    private class ViewClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = listView.getSelectionModel().getSelectedIndex();
            if (i == -1){
                return;
            }
            Stage stage = NoteStageForImg.showNote(reader,notes.get(i),"save");
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    noteFlush();
                }
            });
        }
    }

    //笔记刷新
    public void noteFlush(){
        notes = noteHandle.getNotesByLikes(reader);
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (Note note : notes) {
            observableList.add(note.getName() + " 点赞数:" + String.valueOf(noteHandle.likeCount(note)));
        }
        listView.setItems(observableList);
    }

}
