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

    private NoteHandle noteHandle = new NoteHandle();
    private User reader;
    /**
     *     放入用户检索的笔记
     */
    private List<Note> noteList = new ArrayList<>();

    public Pane getPane(User reader) {
        this.reader = reader;



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

    /**
     * 搜索按钮点击后显示搜索结果
     */
    private class SearchStart implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            noteList.clear();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            noteList = noteHandle.getOpenNotesByName(reader,search.getText());
            for (Note note : noteList) {
                observableList.addAll(note.getName());
            }
            listView.setItems(observableList);
        }
    }

    /**
     * listview的点击事件打开笔记
     */
    private class ViewClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = listView.getSelectionModel().getSelectedIndex();
            if (i == -1){
                return;
            }
            //先确认笔记有没有被删除
            Note note =noteHandle.getById(noteList.get(i).getId());
            if(note == null){
                TextWindow.textWindow("笔记被删除！");
                return;
            }
            NoteStageForImg.showNote(reader,note,"save").show();
        }
    }


    /**
     * 点击事件后把笔记按照时间顺序排序
     */
    private class DateClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if (noteList.size() == 0){
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

    /**
     * 把搜素到的笔记按照点赞顺序排序
     */
    private  class LikeClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if (noteList.size() == 0){
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
