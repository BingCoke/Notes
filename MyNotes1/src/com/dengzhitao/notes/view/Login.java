package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.Userhandle;
import com.dengzhitao.notes.util.JdbcUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;

/**
 * 登录界面
 */
public class Login extends Application {
    private TextField usernameType = new TextField();
    private PasswordField passwordType = new PasswordField();
    Stage stage = null;
    Button login = new Button("登录");
    Button register = new Button("注册");
    Button clear = new Button("清除输入");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();

        Text username = new Text("用户名");
        Text password = new Text("密码");

        gridPane.add(username,0,0);
        gridPane.add(password,0,1);
        gridPane.add(usernameType,1,0);
        gridPane.add(passwordType,1,1);
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        root.setCenter(gridPane);

        HBox hBox = new HBox();


        //设置事件
        register.setOnAction(new RegisterCilck());
        clear.setOnAction(new ClearCilck());
        login.setOnAction(new LoginCilck());


        hBox.getChildren().addAll(login,register,clear);
        hBox.setAlignment(Pos.BASELINE_CENTER);

        root.setBottom(hBox);
        primaryStage.setResizable(false);
        primaryStage.setTitle("MyNotes");
        primaryStage.setScene(new Scene(root,250,120));
        stage = primaryStage;
        stage.show();
        stage.setOnCloseRequest(new StageClose());
    }



    //事件实现

    /**
     * 登录事件
     */
    private class LoginCilck implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            Userhandle userhandle = new Userhandle();
            User user = userhandle.login(usernameType.getText(),passwordType.getText());
            if(user == null){
                TextWindow.textWindow("用户名或密码错误！");
                return;
            } else if(user.getPower() == 3){
                stage.hide();
                AdminMenu.showMenu(user).show();
            } else {
                stage.hide();
                UserMenu.showMenu(user).show();
            }
        }
    }

    /**
     * 注册界面打开
     */
    private class RegisterCilck implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            Register.getRegister().show();
        }
    }

    /**
     * 清除输入框
     */
    private class ClearCilck implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            usernameType.setText("");
            passwordType.setText("");
        }
    }


    /**
     * 程序退出后把connect关闭
     */
    private class StageClose implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent event) {
            try {
                JdbcUtil.connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }




}
