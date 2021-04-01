package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.Userhandle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * 密码修改界面
 */
public class UpdatePasswordPane {
    private PasswordField pastPassword = new PasswordField();
    private PasswordField password = new PasswordField();
    private PasswordField passwordAgain = new PasswordField();
    private Text pastPasswordJudge = new Text("");
    private Text passwordJudge = new Text("");
    private Button sure = new Button("确认修改");
    private User user;

    public UpdatePasswordPane(User user) {
        this.user = user;
    }

    public Pane getPane (){
        Text pastPassword = new Text("输入旧密码");
        Text password = new Text("输入密码");
        Text passwordAgain = new Text("再次输入密码");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_CENTER);

        //普通控件增加
        gridPane.add(pastPassword,0,0);
        gridPane.add(this.pastPassword,1,0);
        gridPane.add(password,0,1);
        gridPane.add(this.password,1,1);
        gridPane.add(passwordAgain,0,2);
        gridPane.add(this.passwordAgain,1,2);

        //事件控件增加
        gridPane.add(pastPasswordJudge,2,0 );
        gridPane.add(passwordJudge,2,2);
        gridPane.add(sure,0,3);


        //事件控件设置事件
        this.pastPassword.setOnKeyReleased(new PastPasswordJudge());
        this.password.setOnKeyReleased(new PasswordJudge());
        this.passwordAgain.setOnKeyReleased(new PasswordJudge());
        this.sure.setOnAction(new SuerClick());



        return gridPane;
    }


    //事件具体实现

    //旧密码验证
    private class PastPasswordJudge implements EventHandler<Event>{
        @Override
        public void handle(Event event) {
            if(!user.getPassword().equals(pastPassword.getText())){
                pastPasswordJudge.setText("并不是旧密码哦！");
            } else {
                pastPasswordJudge.setText("");
            }

        }
    }

    //两次密码验证
    private class PasswordJudge implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            if(!(password.getText().equals(passwordAgain.getText()))){
                passwordJudge.setText("输入的密码不一致");
            } else {
                passwordJudge.setText("");
            }
        }
    }

    //确认验证并将密码修改
    private class SuerClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(!(password.getText().equals(passwordAgain.getText())) || !user.getPassword().equals(password.getText())){
                TextWindow.textWindow("请检查信息正确性o");
            } else {
                TextWindow.textWindow("更改密码成功！");
                new Userhandle().update(user);
                pastPassword.setText("");
                password.setText("");
                passwordAgain.setText("");
            }
        }
    }
}
