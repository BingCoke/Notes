package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.Judge;
import com.dengzhitao.notes.service.Userhandle;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import org.apache.commons.codec.digest.DigestUtils;

public class AdminRegisterStage extends Application {
    private static TextField username1 = new TextField();
    private static PasswordField password1 = new PasswordField();
    private static PasswordField passwordAgain1 = new PasswordField();

    private static Text usernameJudge = new Text("");
    private static Text passwordJudge = new Text("");



    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        GridPane root = new GridPane();
        Text username = new Text("输入用户名");
        Text password = new Text("输入密码");
        Text passwordAgain = new Text("再次输入密码");

        Button ensure = new Button("确认");

        //加入控件
        root.add(username,0,0);
        root.add(username1,1,0);
        root.add(password,0,1);
        root.add(password1,1,1);
        root.add(passwordAgain,0,2);
        root.add(passwordAgain1,1,2);
        root.add(ensure,1,7);


        //负责判断的控件加入
        root.add(usernameJudge,2,0);
        root.add(passwordJudge,2,2);

        //设置控件的事件，对文本输入是否合法判断
        username1.focusedProperty().addListener(new UsernameJudge());
        password1.setOnKeyReleased(new PasswordJudge());
        passwordAgain1.setOnKeyReleased(new PasswordJudge());

        //注册确认
        ensure.setOnAction(new EnsureClick());


        root.setAlignment(Pos.BASELINE_CENTER);

        stage.setTitle("管理员注册");
        stage.setScene(new Scene(root,350,300));
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }



    //内部类设置事件之后的判断操作
    /**
     * 输入用户名的文本框失去焦点的时候判断用户名是否合法
     */
    private static class UsernameJudge implements InvalidationListener {
        @Override
        public void invalidated(Observable observable) {
            if(!username1.isFocused() && !"".equals(username1)){
                if (!Judge.notHaveChinese(username1.getText())){
                    usernameJudge.setText("用户名不能有中文！");
                }else if(Judge.haveSameUsername(username1.getText())){
                    usernameJudge.setText("用户名重复！");
                } else {
                    usernameJudge.setText("");
                }
            } else if (username1.isFocused()){
                usernameJudge.setText("");
            }
        }
    }

    /**
     * 密码是否相同判断
     */
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


    /**
     * 注册确认
     */
    private static class EnsureClick implements EventHandler{

        @Override
        public void handle(Event event) {
            if(!(password1.getText().equals(passwordAgain1.getText())) ||
                    Judge.haveSameUsername(username1.getText()) ||
                    !Judge.notHaveChinese(username1.getText())){
                TextWindow.textWindow("请输入正确的信息！");
                return;
            }
            User user = new User(username1.getText(),
                    DigestUtils.md5Hex(password1.getText()).toString(),
                    "1",
                    "1",
                    1,
                    "11111111111",
                    3);

            Userhandle userhandle = new Userhandle();
            userhandle.register(user);
            TextWindow.textWindow("注册成功！");
        }
    }

}
