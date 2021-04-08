package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.Reply;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.entity.UserComment;
import com.dengzhitao.notes.service.ReplyHandle;
import com.dengzhitao.notes.service.UserCommentHandle;
import com.dengzhitao.notes.service.Userhandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.List;

/**
 * 评论回复界面
 * @author Z
 */
public class ReplyStage {
    private static Stage stage = new Stage();
    private static Userhandle userhandle = new Userhandle();
    private static ReplyHandle replyHandle = new ReplyHandle();
    private static UserCommentHandle userCommentHandle = new UserCommentHandle();
    private static List<Reply> replies ;

    private static User reader;
    private static UserComment userComment;


    private static Button commentRemove = new Button("评论删除");
    private static Button replyRemove = new Button("回复删除");
    private static Button send = new Button("发送");
    private static TextField comment = new TextField();
    private static ListView<String> listView = new ListView<>();
    private static TextField reply = new TextField();
    private static HBox hBoxBottom ;

    public static Stage getStage(User reader, UserComment userComment){
        ReplyStage.reader = reader;
        ReplyStage.userComment = userComment;
        replyFlush();

        BorderPane root = new BorderPane();
        HBox hBoxTop = new HBox();
        HBox hBoxBottom = new HBox();
        ReplyStage.hBoxBottom = hBoxBottom;
        comment.setText(userComment.getUserComment());
        comment.setEditable(false);

        if(reader.getPower() == 2){
            hBoxBottom.getChildren().addAll(reply,send);
        }
        hBoxTop.getChildren().add(comment);
        if(reader.getId() == userComment.getUserId()){
            hBoxTop.getChildren().add(commentRemove);
        }

        //事件
        send.setOnAction(new SentClick());
        replyRemove.setOnAction(new ReplyRemoveClick());
        commentRemove.setOnAction(new UserCommentRemoveClick());
        listView.setOnMouseClicked(new ListViewClick());

        HBox.setHgrow(comment,Priority.ALWAYS);
        HBox.setHgrow(reply, Priority.ALWAYS);
        root.setTop(hBoxTop);
        root.setCenter(listView);
        root.setBottom(hBoxBottom);
        stage.setScene(new Scene(root,400,500));
        return stage;
    }

    /**
     * 回复发送按钮的事件
     */
    private static class SentClick implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            replyHandle.add(new Reply(reply.getText(),reader.getId(),userComment.getId()));
            replyFlush();
            reply.setText("");
        }
    }

    /**
     * 评论列表的刷新事件
     */
    private static class ListViewClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = listView.getSelectionModel().getSelectedIndex();
            if(i == -1){
                return;
            }
            Reply reply = replies.get(i);

            if(reply.getUserId() == reader.getId()){
                hBoxBottom.getChildren().remove(replyRemove);
                hBoxBottom.getChildren().add(replyRemove);

            }else {
                hBoxBottom.getChildren().remove(replyRemove);
            }

        }
    }

    /**
     * 用户评论的删除事件
     */
    private static class UserCommentRemoveClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            userCommentHandle.remove(userComment);
            replyHandle.removeByUserComment(userComment);
            stage.close();
        }
    }

    /**
     * 评论回复的删除事件
     */
    private static class ReplyRemoveClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            int i = listView.getSelectionModel().getSelectedIndex();
            if (i == -1){
                return;
            }
            replyHandle.remove(replies.get(i));
            replyFlush();
        }
    }


    /**
     * 回复刷新
     */
    private static void replyFlush(){
        replies = replyHandle.getByUserComment(userComment);
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (Reply reply : replies) {
            User user = userhandle.getById(reply.getUserId());
            observableList.add(user.getName() + " : " + reply.getReply());
        }
        listView.setItems(observableList);
    }



}
