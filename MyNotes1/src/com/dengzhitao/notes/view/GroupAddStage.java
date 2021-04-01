package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.NoteGroup;
import com.dengzhitao.notes.entity.Repository;
import com.dengzhitao.notes.service.GroupHandle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

/**
 * 笔记组的增加界面
 */
public class GroupAddStage {

    private static TextField name = new TextField();
    private static Text judge = new Text("");
    private static Repository master;
    private static GroupHandle groupHandle = new GroupHandle();
    public static Stage show(Repository repository){
        master = repository;
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        Text input = new Text("输入组名称");
        HBox hBox = new HBox();
        Button sure = new Button("确认建立");
        HBox box = new HBox();

        //控件增加
        box.getChildren().addAll(sure);
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
        stage.setTitle("建立知识库组");
        return stage;
    }

    private static class Judge implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            List<NoteGroup> noteGroups = groupHandle.getByRepository(master);
            if(noteGroups == null){
                return;
            }
            for (NoteGroup noteGroup : noteGroups) {
                if(noteGroup.getName().equals(name.getText())){
                    judge.setText("组名已经存在");
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

            groupHandle.add(new NoteGroup(name.getText(),master.getId()));
            name.setText("");
            TextWindow.textWindow("成功添加！");

        }
    }
}
