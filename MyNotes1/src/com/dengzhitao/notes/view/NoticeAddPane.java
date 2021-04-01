package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.Notice;
import com.dengzhitao.notes.service.NoticeHandle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

/**
 * 公告增加界面
 */
public class NoticeAddPane {
    private TextField title = new TextField();
    private TextArea detail = new TextArea();

    public Pane getPane(){
        BorderPane root = new BorderPane();
        Button add = new Button("增加");
        Text name = new Text("输入标题");

        HBox box = new HBox();
        box.getChildren().addAll(name,title);
        HBox.setHgrow(title, Priority.ALWAYS);

        HBox hBox = new HBox();
        hBox.getChildren().add(add);
        hBox.setAlignment(Pos.BASELINE_CENTER);

        add.setOnAction(new AddClick());

        root.setTop(box);
        root.setBottom(hBox);
        root.setCenter(detail);
        return root;

    }


    public class AddClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {

            if(title.getText().equals("") || detail.getText().equals("")){
                TextWindow.textWindow("标题或者文本不能为空！");
                return;
            }
            Notice notice = new Notice(title.getText(),detail.getText());
            new NoticeHandle().add(notice);
            title.setText("");
            detail.setText("");
            TextWindow.textWindow("增加成功！");
        }
    }
}
