package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.NoteHandle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class SearchPane {
    private Button search = new Button("名字搜索");
    private Button searchByUser = new Button("搜索用户的笔记");
    private Button lookByDate = new Button("时间排序");
    private Button lookByLikes = new Button("点赞排序");
    private BorderPane root = new BorderPane();
    private User reader;
    private NoteHandle noteHandle = new NoteHandle();


    public Pane getPane(User reader){
        this.reader = reader;
        HBox box = new HBox();

        //初始化界面
        root.setCenter(new SearchNoteByNamePane().getPane(reader));

        //设置事件
        search.setOnAction(new SearchByNameClick());
        searchByUser.setOnAction(new SearchByUserClick());
        lookByDate.setOnAction(new LookByDateClick());
        lookByLikes.setOnAction(new LookByLikesClick());

        box.getChildren().addAll(search);
        if(!(reader.getPower() == 3)){
            box.getChildren().add(searchByUser);
        }
        box.getChildren().addAll(lookByDate,lookByLikes);

        box.setAlignment(Pos.BASELINE_CENTER);
        root.setTop(box);
        return root;
    }

    /**
     * 展示 搜素用户名的搜素笔记 界面
     */
    private class SearchByNameClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new SearchNoteByNamePane().getPane(reader));
        }
    }

    /**
     * 展示 搜素笔记名的搜素笔记 界面
     */
    private class SearchByUserClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new SearchNoteByUserNamePane().getPane(reader));
        }
    }

    /**
     * 按照时间排序所有笔记界面
     */
    private class LookByDateClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new LookNoteByDatePane().getPane(reader));
        }
    }

    /**
     * 按照点赞排序笔记界面
     */
    private class LookByLikesClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new LookNoteByLikesPane().getPane(reader));
        }
    }



}
