package com.dengzhitao.notes.view;

import com.dengzhitao.notes.dao.NoteDao;
import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.NoteHandle;
import com.dengzhitao.notes.service.NoticeHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


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
        notes = noteHandle.getNotesByLikes(reader);
        ObservableList<String> observableList = FXCollections.observableArrayList();


        for (Note note : notes) {
            observableList.add(note.getName() + " 点赞数:" + String.valueOf(noteHandle.likeCount(note)));
        }


        listView.setOnMouseClicked(new ViewClick());


        listView.setItems(observableList);
        root.setCenter(listView);
        return root;
    }

    private class ViewClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = listView.getSelectionModel().getSelectedIndex();
            if (i == -1){
                return;
            }
            NoteStage.showNote(reader,notes.get(i),0).show();
        }
    }

}
