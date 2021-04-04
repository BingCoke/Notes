package com.dengzhitao.notes.view;

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

import java.util.List;
/**
 * 找到所有笔记，根据笔记的发布时间来排序
 */

public class LookNoteByDatePane {
    private BorderPane root = new BorderPane();
    private NoteHandle noteHandle = new NoteHandle();
    private ListView<String> listView = new ListView();
    private User reader;
    private List<Note> notes;
    public Pane getPane(User reader){
        this.reader = reader;
        ObservableList<String> observableList = FXCollections.observableArrayList();

        if(reader.getPower() == 3){
            notes = noteHandle.getAllNotesByDate();
        } else {
           notes = noteHandle.getOpenNotesByDate(reader);
        }

        for (Note note : notes) {
            observableList.add(note.getName());
        }


        listView.setOnMouseClicked(new ViewClick());


        listView.setItems(observableList);
        root.setCenter(listView);
        return root;
    }

    private class ViewClick implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            int i = listView.getSelectionModel().getSelectedIndex();
            if (i == -1){
                return;
            }
            NoteStageForImg.showNote(reader,notes.get(i),"add").show();
        }
    }
}
