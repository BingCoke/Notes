package com.dengzhitao.notes.view;



import com.dengzhitao.notes.entity.NoteGroup;
import com.dengzhitao.notes.entity.Repository;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.GroupHandle;
import com.dengzhitao.notes.service.RepositoryHandle;
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
import javafx.stage.WindowEvent;



import java.util.List;


/**
 * 修改知识库，组的名字
 */
public class UpdateStage {
    private static Stage stage = new Stage();
    private static Repository repository;
    private static NoteGroup noteGroup;
    private static Text input = new Text("输入修改的名字");
    private static TextField name = new TextField();
    private static Button sure = new Button("确认");
    private static Text judge = new Text("");


    private static RepositoryHandle repositoryHandle = new RepositoryHandle();
    private static GroupHandle groupHandle = new GroupHandle();

    private static User master;
    private static Repository groupMaster;

    public static Stage getStack(Object o,Repository repository, User master){
        BorderPane root = new BorderPane();
        HBox hBoxTop = new HBox();
        HBox hBoxBottom = new HBox();
        UpdateStage.master = master;
        UpdateStage.groupMaster = repository;


        if(o.getClass() == Repository.class){
            stage.setTitle("知识库名字更改");
            UpdateStage.repository = (Repository) o;
        }else if(o.getClass() == NoteGroup.class){
            stage.setTitle("组名字更改");
            noteGroup = (NoteGroup)o;
        }


        name.setOnKeyReleased(new Judge());
        sure.setOnAction(new Sure());

        hBoxBottom.setAlignment(Pos.BASELINE_CENTER);
        hBoxTop.setAlignment(Pos.BASELINE_CENTER);
        hBoxTop.getChildren().addAll(input,name,judge);
        hBoxBottom.getChildren().addAll(sure);
        root.setTop(hBoxTop);
        root.setBottom(hBoxBottom);
        stage.setScene(new Scene(root,300,100));
        return stage;
    }

    /**
     * 两次密码判断事件
     */
    private static class Judge implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if( repository != null){
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
            } else if (noteGroup != null){
                List<NoteGroup> noteGroups = groupHandle.getByRepository(groupMaster);
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
    }


    /**
     * 点击修改密码确定事件
     */
    private static class Sure implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if( name.getText().equals("") || !judge.getText().equals("")){
                TextWindow.textWindow("请填入正确信息");
                return;
            }

            if(repository != null){
                repository.setName(name.getText());
                repositoryHandle.save(repository);
            } else if(noteGroup != null ){
                noteGroup.setName(name.getText());
                groupHandle.save(noteGroup);
            }
            name.setText("");
            Stage stage = TextWindow.textWindow("成功修改！");
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    UpdateStage.stage.close();
                }
            });

        }
    }

}
