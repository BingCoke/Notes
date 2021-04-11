package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.Judge;
import com.dengzhitao.notes.service.Userhandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class InformationPane {
    private  User reader;
    private  User master;
    private  Userhandle userhandle = new Userhandle();


    private  BorderPane root = new BorderPane();
    private  TextField username1 = new TextField();
    private  TextField name1 = new TextField();
    private  ChoiceBox gender2 = new ChoiceBox() ;
    private  TextField gender1 = new TextField();
    private  ChoiceBox age2 = new ChoiceBox();
    private  TextField age1 = new TextField();
    private  TextField phoneNumber1 = new TextField();
    private  Text phoneNumberJudge = new Text("");

    private  RadioButton ban = new RadioButton("拉黑");

    /**
     * 用户个人信息展示界面
     */

    {
        gender2.setItems(FXCollections.observableArrayList("男","女"));
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int i = 1; i < 151; i++) {
            observableList.add(String.valueOf(i));
        }
        age2.setItems(observableList);
        age2.getSelectionModel().selectFirst();
        gender2.getSelectionModel().selectFirst();

        age2.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        gender2.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    }


    public Pane getPane(User reader,User master){
        this.reader = reader;
        this.master = master;
        Button person = new Button("用户信息");
        Button repository = new Button("知识库");
        HBox box = new HBox();

        //信息初始化
        username1.setText(master.getUsername());
        name1.setText(master.getName());
        gender1.setText(master.getGender());
        age1.setText(String.valueOf(master.getAge()));
        phoneNumber1.setText(master.getPhoneNumber());
        gender2.getSelectionModel().select(master.getGender());
        age2.getSelectionModel().select(master.getAge() - 1);

        //事件设置
        person.setOnAction(new PersonClick());
        repository.setOnAction(new RepositoryClick());

        if(reader.getPower() == 3) {
            box.getChildren().addAll(person,repository);
        }
        box.setAlignment(Pos.BASELINE_CENTER);
        root.setTop(box);
        root.setCenter(PersonInformation());
        return root;
    }

    //整个界面的事件的实现
    private class PersonClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(PersonInformation());
        }
    }


    private class RepositoryClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            root.setCenter(new MyRepositoryPane(reader,master).getPane());
        }
    }

    //用户信息界面
    private Pane PersonInformation(){
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        Text username = new Text("用户名");
        Text name = new Text("姓名");
        Text gender = new Text("性别");
        Text age = new Text("年龄");
        Text phoneNumber = new Text("手机号");

        HBox hBox = new HBox();
        //master专有
        Button save = new Button("保存");
        Text isBan = new Text("");


        //加入控件
        if(reader.getPower() == 3){
            hBox.getChildren().addAll(ban);

            gridPane.add(gender1,1,2);
            gridPane.add(age1,1,3);
            age1.setEditable(false);
            gender1.setEditable(false);
            name1.setEditable(false);
            phoneNumber1.setEditable(false);
        }else if(reader.getId() == master.getId()){
            hBox.getChildren().addAll(save,isBan);

            gridPane.add(gender2,1,2);
            gridPane.add(age2,1,3);
        }

        gridPane.add(username,0,0);
        gridPane.add(username1,1,0);
        gridPane.add(name,0,1);
        gridPane.add(name1,1,1);
        gridPane.add(age,0,3);
        gridPane.add(gender,0,2);
        gridPane.add(phoneNumber,0,4);
        gridPane.add(phoneNumber1,1,4);
        gridPane.add(phoneNumberJudge,2,4);



        //设置
        if(master.getPower() == 1){
            isBan.setText("黑名单");
            ban.setSelected(true);
        }else {
            isBan.setText("正常");
            ban.setSelected(false);
        }
        root.setCenter(gridPane);
        root.setBottom(hBox);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        username1.setEditable(false);
        phoneNumber1.setOnKeyReleased(new PhoneNumberJudge());


        //设置事件
        save.setOnAction(new Save());
        ban.setOnMouseClicked(new Ban());

        return root;
    }


    //用户信息界面的事件实现

    /**
     * 电话号码判断事件
     */
    private class PhoneNumberJudge implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            if( !Judge.onlyHaveNumber(phoneNumber1.getText()) || phoneNumber1.getText().length() != 11){
                phoneNumberJudge.setText("请输入正确的手机号");
            } else {
                phoneNumberJudge.setText("");
            }
        }
    }

    /**
     * 个人信息 保存事件
     */
    private class Save implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(!phoneNumberJudge.getText().equals("") || name1.equals("")){
                TextWindow.textWindow("检查信息正确性！");
                return;
            }
            master.setName(name1.getText());
            master.setGender(gender2.getSelectionModel().getSelectedItem().toString());
            master.setAge(age2.getSelectionModel().getSelectedIndex() + 1);
            master.setPhoneNumber(phoneNumber1.getText());
            userhandle.update(master);
            TextWindow.textWindow("信息修改成功！");
        }
    }

    /**
     * 设置用户黑名单事件
     */
    private  class Ban implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            if(ban.isSelected()){
                master.setPower(1);
            } else {
                master.setPower(2);
            }
            userhandle.update(master);
        }
    }
}
