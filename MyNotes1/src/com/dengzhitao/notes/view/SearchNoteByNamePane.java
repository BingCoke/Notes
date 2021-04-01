package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.NoteHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过笔记名字来搜索
 */
public class SearchNoteByNamePane {

    private BorderPane root = new BorderPane();
    private TextField search = new TextField();
    private Button start = new Button("搜索");
    private ListView<String> listView = new ListView<>();
    private Button date = new Button("时间排序");
    private Button like = new Button("点赞排序");

    //放入该用户能查的笔记
    private List<Note> notes = new ArrayList<>();
    //放入用户检索的笔记
    private List<Note> noteList = new ArrayList<>();
    private NoteHandle noteHandle = new NoteHandle();
    private User reader;

    public Pane getPane(User reader) {
        this.reader = reader;

        if (reader.getPower() == 3) {
            notes = noteHandle.getAll();
        } else {
            notes = noteHandle.getOpenNotes(reader);
        }


        HBox box1 = new HBox(search, start);
        HBox box2 = new HBox(date,like);

        start.setOnAction(new SearchStart());
        listView.setOnMouseClicked(new ViewClick());
        date.setOnAction(new DateClick());
        like.setOnAction(new LikeClick());

        VBox box = new VBox(box1,box2);
        root.setTop(box);
        root.setTop(box);
        root.setCenter(listView);
        box.setAlignment(Pos.BASELINE_CENTER);
        HBox.setHgrow(search, Priority.ALWAYS);

        return root;
    }

    private class SearchStart implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            noteList.clear();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            if(notes == null){
                return;
            }

            for (Note note : notes) {
                System.out.println(search.getText());
                System.out.println(note.getName());
                if(note.getName().equals(search.getText())){
                    noteList.add(note);
                    observableList.add(note.getName());
                }
            }
            listView.setItems(observableList);
        }
    }


    private class ViewClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = listView.getSelectionModel().getSelectedIndex();
            if (i == -1){
                return;
            }
            NoteStage.showNote(reader,noteList.get(i),0).show();
        }
    }

    private class DateClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if (noteList.size() == 0 || noteList == null ){
                return;
            }
            noteList = noteHandle.orderByDate(noteList);
            ObservableList<String> observableList = FXCollections.observableArrayList();
            for (Note note : noteList) {
                observableList.add(note.getName());
            }
            listView.setItems(observableList);
        }
    }


    private  class LikeClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if (noteList.size() == 0 || noteList == null ){
                return;
            }
            noteList = noteHandle.orderByLikes(noteList);
            ObservableList<String> observableList = FXCollections.observableArrayList();
            for (Note note : noteList) {
                observableList.add(note.getName());
            }
            listView.setItems(observableList);
        }
    }
}
