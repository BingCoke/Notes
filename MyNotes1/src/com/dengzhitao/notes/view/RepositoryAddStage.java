package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.Repository;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.RepositoryHandle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

/**
 * 知识库增加窗口
 */
public class RepositoryAddStage {

    private static TextField  name = new TextField();
    private static Text judge = new Text("");
    private static RadioButton hide = new RadioButton("隐藏");
    private static User master;
    private static RepositoryHandle repositoryHandle = new RepositoryHandle();
    public static Stage show(User user){
        master = user;
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        Text input = new Text("输入知识库名称");
        HBox hBox = new HBox();
        Button sure = new Button("确认建立");
        HBox box = new HBox();

        //控件增加
        box.getChildren().addAll(sure,hide);
        hBox.getChildren().addAll(input,name,judge);

        //控件事件
        name.setOnKeyReleased(new Judge());
        sure.setOnAction(new Sure());

        //布局组合
        root.setCenter(hBox);
        root.setBottom(box);

        //布局设置
        box.setAlignment(Pos.BASELINE_CENTER);
        hBox.setPrefWidth(300);
        root.setPrefSize(300,200);
        stage.setResizable(false);
        stage.setScene(new Scene(root,300,100));
        stage.setTitle("建立知识库");
        return stage;
    }

    private static class Judge implements EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent event) {
            List<Repository> repositories = repositoryHandle.getByMaster(master);
            if (repositories == null){
                return;
            }
            for (Repository repository : repositories) {
                if(repository.getName().equals(name.getText())){
                    judge.setText("知识库名已经存在");
                } else {
                    judge.setText("");
                }
            }
        }
    }


    private static class Sure implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if( name.getText().equals("") || !judge.getText().equals("")){
                TextWindow.textWindow("请填入正确信息");
                return;
            }
            int open = 1;
            if (hide.isSelected()) {
                open = 0;
            }

            repositoryHandle.add(new Repository(name.getText(),master.getId(),open));
            name.setText("");
            TextWindow.textWindow("成功添加！");

        }
    }
}
