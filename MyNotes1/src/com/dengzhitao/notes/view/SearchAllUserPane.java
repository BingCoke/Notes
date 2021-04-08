package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.Userhandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理员用来查找用户
 */
public class SearchAllUserPane {
    private static Userhandle userhandle = new Userhandle();
    private static BorderPane root = new BorderPane();
    private static HBox box = new HBox();
    private static TextField search = new TextField();
    private static Button start = new Button("搜索");
    private static List<User> userAll = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static ListView<String> listView = new ListView<>();

    private static User reader;

    public static Pane getPane(User reader){
        SearchAllUserPane.reader = reader;
        //初始化
        userAll.addAll(userhandle.getBanUser());
        userAll.addAll(userhandle.getOrdinaryUser());
        ObservableList<String> list = FXCollections.observableArrayList();
        users.addAll(userAll);
        for (User user : userAll) {
            list.add(user.getName());
        }
        listView.setItems(list);


        //设置事件
        start.setOnAction(new Search());
        search.setOnKeyReleased(new ShowAll());
        listView.setOnMouseClicked(new ViewClick());

        //控件组装
        HBox hBox = new HBox(search,start);
        HBox.setHgrow(search, Priority.ALWAYS);
        root.setTop(hBox);
        root.setCenter(listView);

        return root;
    }


    /**
     * 点击搜索按钮后显示用户的事件
     */
    private static class Search implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            String name = search.getText();
            if(name.equals("")){
                return;
            }
            users.clear();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (User user : userAll) {
                if(user.getName().equals(name)){
                    list.add(name);
                    users.add(user);
                }
            }
            listView.setItems(list);
        }
    }


    /**
     * 如果搜索框是空白的时候就显示所有
     */
    private static class ShowAll implements EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent event) {
            if(search.getText().equals("")){
                ObservableList<String> list = FXCollections.observableArrayList();
                users.clear();
                users.addAll(userAll);
                for (User user : userAll) {
                    list.add(user.getName());
                }
                listView.setItems(list);
            }
        }
    }

    /**
     * 点击view后显示用户的信息
     */
    private static class ViewClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = listView.getSelectionModel().getSelectedIndex();
            if(i == -1){
                return;
            }
            User user = users.get(i);
            Stage stage = new Stage();
            stage.setScene(new Scene(new InformationPane().getPane(reader,user),400,500));
            stage.setTitle(user.getName());
            stage.show();
        }
    }
}
