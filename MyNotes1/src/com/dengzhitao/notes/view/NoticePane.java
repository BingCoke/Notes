package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.Notice;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.NoticeHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * 公告界面
 */
public class NoticePane {
    private TextArea noitceText = new TextArea();
    NoticeHandle noticeHandle = new NoticeHandle();
    ListView<String> list = new ListView<>();
    BorderPane root = new BorderPane();
    List<Notice> notices;
    public Pane getPane(User user){
        //hbox的设置
        HBox hBox = new HBox();
        Button update = new Button("修改");
        Button save = new Button("保存");
        Button remove = new Button("删除");
        hBox.getChildren().addAll(update,save,remove);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        //listview的设置
        list.setPrefWidth(120);
        //noticetext的设置
        noitceText.setEditable(false);


        //设置初始页面
        listViewFlush();
        Notice notice = notices.get(0);
        noitceText.setText(notice.getDetail());

        //事件设置
        list.setOnMouseClicked(new ListClick());
        update.setOnAction(new UpdateClick());
        save.setOnAction(new SaveClick());
        remove.setOnAction(new RemoveClick());
        //pane布局
        root.setCenter(noitceText);
        root.setRight(list);
        if(user.getPower() == 3){
            root.setBottom(hBox);
        }

        return root;
    }


    /**
     * 列表点击事件，查看对应的公告
     */
    private class ListClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = list.getSelectionModel().getSelectedIndex();
            if(i == -1){
                return;
            }
            Notice notice = notices.get(i);
            noitceText.setText(notice.getDetail());
            root.setCenter(noitceText);
        }
    }


    /**
     * 修改公告的事件
     */
    private class UpdateClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            noitceText.setEditable(true);
        }
    }


    /**
     * 公告信息的保存事件
     */
    private class SaveClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            int i = list.getSelectionModel().getSelectedIndex();
            int id = notices.get(i).getId();
            if(i == -1){
                TextWindow.textWindow("请先选择一项！");
                return;
            }
            Notice notice = noticeHandle.getById(id);
            notice.setDetail(noitceText.getText());
            noticeHandle.save(notice);
            listViewFlush();
        }
    }


    /**
     * 公告删除事件
     */
    private class RemoveClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            int i = list.getSelectionModel().getSelectedIndex();
            int id = notices.get(i).getId();
            Notice notice = noticeHandle.getById(id);
            noticeHandle.remove(notice);
            listViewFlush();
            notice = notices.get(0);
            noitceText.setText(notice.getDetail());
        }
    }

    /**
     * 把公告列表的删除事件
     */
    public void listViewFlush(){
        //刷新读取公告，并加入到listview中
        ObservableList<String> items = FXCollections.observableArrayList();
        notices = noticeHandle.getAll();
        for (Notice notice : notices) {
            items.add(notice.getTitle());
        }
        list.setItems(items);
    }
}
