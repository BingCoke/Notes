package com.dengzhitao.notes.view;

import com.dengzhitao.notes.dao.LikesDao;
import com.dengzhitao.notes.entity.Likes;
import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.NoteHandle;
import com.dengzhitao.notes.service.Userhandle;
import com.dengzhitao.notes.util.StringUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class NoteStage {
    private static Stage stage = new Stage();
    private static TextField title = new TextField();
    private static TextArea body = new TextArea();
    private static Pagination pagination = null;

    private static Text writerForReader = new Text();
    private static Button writerForAdimn = new Button("作者");
    private static RadioButton like = new RadioButton("点赞");
    private static Text likeCount = new Text();
    private static Button save = new Button("保存");
    private static Button remove = new Button("删除");
    private static RadioButton open = new RadioButton("公开");

    private static User reader;
    private static User writer;
    private static Note note;
    private static int count;
    private static int isAdd;
    private static List<String> list;

    private static Userhandle userhandle = new Userhandle();
    private static NoteHandle noteHandle = new NoteHandle();

    /**
     * 笔记查看界面
     * @param reader
     * @param note
     * @param isAdd
     * @return
     */
    public static Stage showNote(User reader, Note note,int isAdd ){

        BorderPane root = new BorderPane();
        NoteStage.isAdd = isAdd;
        NoteStage.writer = noteHandle.getMaster(note);
        NoteStage.reader = reader;
        NoteStage.note = note;
        count = noteHandle.likeCount(note);



        //初始化
        if(note.getOpen() == 1){
            open.setSelected(true);
        }else {
            open.setSelected(false);
        }
        title.setText(note.getName());
        writerForReader.setText("作者：" + writer.getName());
        like.setSelected(userhandle.isLike(note,reader));
        likeCount.setText("点赞数：" + String.valueOf(count));
        body.setEditable(false);
        title.setEditable(false);
        HBox boxBottom = new HBox();
        HBox boxTop = new HBox();

        //分页器设置
        list =  StringUtil.pagination(note.getContent());
        pagination = new Pagination(list.size(),0);

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                VBox box = new VBox();
                body.setWrapText(true);
                body.setText(list.get(param));
                body.setPrefRowCount(111);
                box.getChildren().add(body);
                body.setOnKeyTyped(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        list.set(param,body.getText());
                    }
                });
                return box;
            }
        });

        //分类讨论设置按钮
        if(reader.getId() == (writer.getId())){
            body.setEditable(true);
            title.setEditable(true);
            boxBottom.getChildren().addAll(save,remove,open);
        }else if(reader.getPower() == 3){
            boxBottom.getChildren().addAll(writerForAdimn,like,remove);
        }else {
            boxBottom.getChildren().addAll(writerForReader,like);
        }
        boxTop.getChildren().addAll(title,likeCount);
        HBox.setHgrow(title, Priority.ALWAYS);


        //事件设置
        writerForAdimn.setOnAction(new WriterForAdminClick());
        like.setOnMousePressed(new LikeClick());
        save.setOnAction(new SaveClick());
        open.setOnMouseClicked(new OpenClick());
        remove.setOnAction(new RemoveClick());
        //设置
        boxBottom.setAlignment(Pos.BASELINE_CENTER);
        boxTop.setAlignment(Pos.BASELINE_CENTER);

        root.setTop(boxTop);
        root.setCenter(pagination);

        root.setBottom(boxBottom);
        stage.setScene(new Scene(root,400,400));
        stage.setTitle(writer.getName() + "的笔记");
        return stage;
    }

    private static class WriterForAdminClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(new InformationPane().getPane(reader,writer),400,500));
        }
    }

    private static class LikeClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            LikesDao likesDao = new LikesDao();
            if( !like.isSelected()){

                List<Likes> likesList = likesDao.selectExact(Likes.class,"userId",reader.getId());
                for (Likes likes : likesList) {
                    likesDao.remove(likes);
                }
                count++;
            }else {

                likesDao.add(new Likes(note.getId(),reader.getId()));
                count--;
            }
            likeCount.setText("点赞数：" + String.valueOf(count));
        }
    }

    private static class SaveClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(title.getText().equals("")){
                TextWindow.textWindow("标题不能为空！");
                return;
            }
            note.setName(title.getText());
            note.setContent(StringUtil.gather(list));
            if(isAdd == 1){
                noteHandle.add(note);
                isAdd = 0;
            } else {
                noteHandle.save(note);
            }

        }
    }

    private static class OpenClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            if (open.isSelected()) {
                note.setOpen(1);
            }else {
                note.setOpen(0);
            }
        }
    }


    private static class RemoveClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            title.setText("");
            body.setText("");
            noteHandle.remove(note);
            TextWindow.textWindow("删除成功！").show();

        }
    }




    }
