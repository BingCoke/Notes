package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.entity.UserComment;
import com.dengzhitao.notes.service.UserCommentHandle;
import com.dengzhitao.notes.service.Userhandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户评论区域
 * 管理员无法发送
 * 黑名单用户同样也无法发送
 */
public class CommentPane {

    private Userhandle userhandle = new Userhandle();
    private UserCommentHandle userCommentHandle = new UserCommentHandle();
    private List<UserComment> userComments = new ArrayList<>();

    private ListView<String> listView = new ListView<>();
    private TextField msg = new TextField();
    private Button send = new Button("发送");

    private User reader;

    public Pane getPane(User user){
        reader = user;

        BorderPane root = new BorderPane();
        HBox box = new HBox(msg,send);

        commentFlush();



        //事件设置
        send.setOnAction(new SentClick());
        listView.setOnMouseClicked(new ListViewClick());


        HBox.setHgrow(msg,Priority.ALWAYS);
        root.setCenter(listView);
        if(reader.getPower() == 2){
            root.setBottom(box);
        }
        return root;
    }


    /**
     * 事件的实现
     */
    private class ListViewClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = listView.getSelectionModel().getSelectedIndex();
            if(i == -1){
                return;
            }
            Stage stage = ReplyStage.getStage(reader,userComments.get(i));
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    commentFlush();
                }
            });
        }
    }

    /**
     * 评论发送按钮点击事件
     */
    private class SentClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            userCommentHandle.add(new UserComment(msg.getText(),reader.getId()));
            commentFlush();
            msg.setText("");
        }
    }

    /**
     * 评论刷新
     */
    private void commentFlush(){
        userComments = userCommentHandle.getAll();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (UserComment userComment : userComments) {
            User user = userhandle.getById(userComment.getUserId());
            observableList.add(user.getName() + " : " +userComment.getUserComment());
        }
        listView.setItems(observableList);
    }


}
