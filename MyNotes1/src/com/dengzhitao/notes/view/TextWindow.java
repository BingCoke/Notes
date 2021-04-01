package com.dengzhitao.notes.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * 小窗口类 自定义小窗口文字
 * @author Z
 */
public class TextWindow  extends Application{


    private static Stage stage ;
    public static Stage textWindow(String text){
        TextWindow.stage = new Stage();
        Button button = new Button("确认");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        BorderPane borderPane = new BorderPane();

        javafx.scene.text.Text text1 = new Text();
        text1.setText(text);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().addAll(text1,button);
        vBox.setPadding(new Insets(10,10,10,10));
        borderPane.setCenter(vBox);

        stage.setResizable(false);
        stage.setScene(new Scene(borderPane,100,60));
        stage.show();
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextWindow.textWindow("ff").show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
