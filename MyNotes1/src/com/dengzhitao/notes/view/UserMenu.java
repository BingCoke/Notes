package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.entity.UserComment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 *用户界面
 *
 */
public class UserMenu {
    private static BorderPane root = new BorderPane();
    private static User user = null;
    public static Stage showMenu(User u){
        Stage stage = new Stage();
        stage.setTitle("欢迎" + u.getName() + u.getGender() + "士的到来！");
        user = u;

        Button lookNotice = new Button("公告");
        Button myRepository = new Button("我的知识库");
        Button personalInformation = new Button("个人信息");
        Button searchNote = new Button("查询");
        Button updatePassword = new Button("修改密码");
        Button userComment = new Button("用户讨论");
        Button exit = new Button("退出");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(lookNotice,myRepository,personalInformation,searchNote,updatePassword,userComment,exit);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        root.setTop(hBox);

        //起始页面
        root.setCenter(new NoticePane().getPane(user));

        //事件设置
        lookNotice.setOnAction(new NoticeClick());
        myRepository.setOnAction(new MyRepositoryCilck());
        personalInformation.setOnAction(new PersonalInformationClick());
        searchNote.setOnAction(new SearchNoteClick());
        updatePassword.setOnAction(new UpdatePasswordClick());
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

    private static class NoticeClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new NoticePane().getPane(user));
        }
    }

    private static class MyRepositoryCilck implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new MyRepositoryPane(user,user).getPane());
        }
    }

    private static class PersonalInformationClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new InformationPane().getPane(user,user));
        }
    }

    private static class  SearchNoteClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new SearchPane().getPane(user));
        }
    }

    private static class UpdatePasswordClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new UpdatePasswordPane(user).getPane());
        }
    }

    private static class UserCommentClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new CommentPane().getPane(user));
        }
    }
}
