package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.Judge;
import com.dengzhitao.notes.service.Userhandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 注册界面
 */
public class Register {
    private static TextField username1 = new TextField();
    private static PasswordField password1 = new PasswordField();
    private static PasswordField passwordAgain1 = new PasswordField();
    private static TextField name1 = new TextField();
    private static ChoiceBox gender1 = new ChoiceBox() ;
    private static ChoiceBox age1 = new ChoiceBox();
    private static TextField phoneNumber1 = new TextField();
    private static Text usernameJudge = new Text("");
    private static Text passwordJudge = new Text("");
    private static Text phoneNumberJudge = new Text("");

    //初始化choicebox的值
    static {
        gender1.setItems(FXCollections.observableArrayList("男","女"));
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int i = 1; i < 151; i++) {
            observableList.add(String.valueOf(i));
        }
        age1.setItems(observableList);
        age1.getSelectionModel().selectFirst();
        gender1.getSelectionModel().selectFirst();

        age1.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        gender1.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    }

    public static Stage getRegister(){
        Stage stage = new Stage();
        GridPane root = new GridPane();
        Text username = new Text("输入用户名");
        Text password = new Text("输入密码");
        Text passwordAgain = new Text("再次输入密码");
        Text name = new Text("输入姓名");
        Text gender = new Text("选择性别");
        Text age = new Text("选择年龄");
        Text phoneNumber = new Text("输入手机号");
        Button ensure = new Button("确认");

        //加入控件
        root.add(username,0,0);
        root.add(username1,1,0);
        root.add(password,0,1);
        root.add(password1,1,1);
        root.add(passwordAgain,0,2);
        root.add(passwordAgain1,1,2);
        root.add(name,0,3);
        root.add(name1,1,3);
        root.add(gender,0,4);
        root.add(gender1,1,4);
        root.add(age,0,5);
        root.add(age1,1,5);
        root.add(phoneNumber,0,6);
        root.add(phoneNumber1,1,6);
        root.add(ensure,1,7);


        //负责判断的控件加入
        root.add(usernameJudge,2,0);
        root.add(passwordJudge,2,2);
        root.add(phoneNumberJudge,2,6);

        //设置控件的事件，对文本输入是否合法判断
        username1.setOnKeyReleased(new UsernameJudge());
        password1.setOnKeyReleased(new PasswordJudge());
        passwordAgain1.setOnKeyReleased(new PasswordJudge());
        phoneNumber1.setOnKeyReleased(new PhoneNumberJudge());

        //注册确认
        ensure.setOnAction(new EnsureClick());


        root.setAlignment(Pos.BASELINE_CENTER);

        stage.setTitle("注册");
        stage.setScene(new Scene(root,350,300));
        stage.setResizable(false);
        return stage;
    }

    //内部类设置事件之后的判断操作

    //用户名合法判断
    private static class UsernameJudge implements EventHandler<Event>{
        @Override
        public void handle(Event event) {
            if(Judge.haveSameUsername(username1.getText())){
                usernameJudge.setText("用户名重复！");
            } else if (!Judge.notHaveChinese(username1.getText())){
                usernameJudge.setText("用户名不能有中文！");
            } else {
                usernameJudge.setText("");
            }
        }
    }

    //密码是否相同判断
    private static class PasswordJudge implements EventHandler<Event>{
        @Override
        public void handle(Event event) {
            if(!(password1.getText().equals(passwordAgain1.getText()))){
                passwordJudge.setText("输入的密码不一致");
            } else {
                passwordJudge.setText("");
            }
        }
    }

    //手机号是否合法判断
    private static class PhoneNumberJudge implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            if( !Judge.onlyHaveNumber(phoneNumber1.getText()) || phoneNumber1.getText().length() != 11){
                phoneNumberJudge.setText("请输入正确的手机号");
            } else {
                phoneNumberJudge.setText("");
            }
        }
    }
    //注册确认
    private static class EnsureClick implements EventHandler{

        @Override
        public void handle(Event event) {
            if(!Judge.onlyHaveNumber(phoneNumber1.getText()) ||
                    phoneNumber1.getText().length() != 11 ||
                    !(password1.getText().equals(passwordAgain1.getText())) ||
                    Judge.haveSameUsername(username1.getText()) ||
                    !Judge.notHaveChinese(username1.getText())){
                TextWindow.textWindow("请输入正确的信息！");
                return;
            }
            User user = new User(username1.getText(),
                    password1.getText(),
                    name1.getText(),
                    gender1.getSelectionModel().getSelectedItem().toString(),
                    Integer.parseInt(age1.getSelectionModel().getSelectedItem().toString()),
                    phoneNumber1.getText()
                    );

            Userhandle userhandle = new Userhandle();
            userhandle.register(user);
            TextWindow.textWindow("注册成功！");
        }
    }

}
