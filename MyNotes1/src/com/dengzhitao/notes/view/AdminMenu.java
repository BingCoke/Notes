package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * 管理员界面
 */
public class AdminMenu {
    private static BorderPane root = new BorderPane();
    private static User user = null;

    public static Stage showMenu(User u){
        Stage stage = new Stage();
        stage.setTitle("欢迎管理员！");
        user = u;

        //控件增加
        Button notice = new Button("公告");
        Button noticeAdd = new Button("增加公告");
        Button searchUser = new Button("查询用户");
        Button searchNote = new Button("查询");
        Button updatePassword = new Button("修改密码");
        Button userComment = new Button("用户讨论");
        Button exit = new Button("退出");

        //顶部按钮
        HBox hBox = new HBox();
        hBox.getChildren().addAll(notice,noticeAdd,searchUser,searchNote,updatePassword,exit);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        root.setTop(hBox);

        //设置起始页面
        root.setCenter(new NoticePane().getPane(user));

        //事件设置
        updatePassword.setOnAction(new UpdatePassword());
        notice.setOnAction(new Notice());
        noticeAdd.setOnAction(new NoticeAdd());
        searchUser.setOnAction(new SearchUserClick());
        searchNote.setOnAction(new SearchNoteClick());
        userComment.setOnAction(new UserCommentClick());
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });


        stage.setScene(new Scene(root,400,500));
        return stage;
    }


    /**
     * 公告按钮点击事件
     */
    private static class Notice implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new NoticePane().getPane(user));
        }
    }

    /**
     * 公告增加点击事件
     */
    private static class NoticeAdd implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new NoticeAddPane().getPane());
        }
    }

    /**
     * 查询用户点击事件
     */
    private static class SearchUserClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(SearchAllUserPane.getPane(user));
        }
    }

    /**
     * 查询笔记界面事件
     */
    private static class  SearchNoteClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new SearchPane().getPane(user));
        }
    }

    /**
     * 密码修改界面点击事件
     */
    private static class UpdatePassword implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new UpdatePasswordPane(user).getPane());
        }
    }

    /**
     * 用户评论界面点击事件
     */
    private static class UserCommentClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new CommentPane().getPane(user));
        }
    }
}
