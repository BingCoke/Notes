package com.dengzhitao.notes.view;

import com.dengzhitao.notes.entity.*;
import com.dengzhitao.notes.service.GroupHandle;
import com.dengzhitao.notes.service.NoteHandle;
import com.dengzhitao.notes.service.RepositoryHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;


/**
 * 展示用户的知识库
 */
public class MyRepositoryPane {
    private ListView<String> repositoryView = new ListView<>();
    private ListView<String> groupView = new ListView<>();
    private ListView<String> noteView = new ListView<>();
    private RadioButton open = new RadioButton("开放");

    private User reader;
    private User master;

    private List<Repository> repositories;
    private List<NoteGroup> noteGroups;
    private List<Note> notes;

    private RepositoryHandle repositoryHandle = new RepositoryHandle();
    private GroupHandle groupHandle = new GroupHandle();
    private NoteHandle noteHandle = new NoteHandle();

    public MyRepositoryPane(User reader, User master) {
        this.reader = reader;
        this.master = master;
    }

    public Pane getPane(){
        BorderPane root = new BorderPane();
        Button repositoryAdd = new Button("增加");
        Button groupAdd = new Button("增加");
        Button noteAdd = new Button("增加");

        Button repositoryRemove = new Button("删除");
        Button groupRemove = new Button("删除");

        Button repositoryUpdate = new Button("修改");
        Button groupUpdate = new Button("修改");

        HBox repositoryHBox = new HBox();
        HBox groupHBox = new HBox();



        VBox repositoryBox = new VBox();
        VBox groupBox = new VBox();
        VBox noteBox = new VBox();
        repositoryBox.setPrefWidth(133);
        groupBox.setPrefWidth(100);
        noteBox.setPrefWidth(133);
        open.setSelected(false);

        repositoryFlush();

        //控件添加
        repositoryBox.getChildren().add(repositoryView);
        groupBox.getChildren().add(groupView);
        noteBox.getChildren().add(noteView);
        VBox.setVgrow(repositoryView, Priority.ALWAYS);
        VBox.setVgrow(groupView, Priority.ALWAYS);
        VBox.setVgrow(noteView, Priority.ALWAYS);
        //判断reader权限并添加控件
        if(reader.getId() == master.getId()){
            if(reader.getPower() == 1){
                TextWindow.textWindow("无法增加笔记！");
            }else{
                repositoryHBox.getChildren().addAll(repositoryAdd,repositoryRemove,repositoryUpdate);
                repositoryBox.getChildren().addAll(repositoryHBox,open);
                groupHBox.getChildren().addAll(groupAdd,groupRemove,groupUpdate );
                groupBox.getChildren().addAll(groupHBox);
                noteBox.getChildren().addAll(noteAdd);
            }
        }


        //事件设置
        repositoryView.setOnMouseClicked(new RepositoryClick());
        groupView.setOnMouseClicked(new GroupClick());
        noteView.setOnMouseClicked(new NoteClick());

        repositoryAdd.setOnAction(new RepositoryAdd());
        groupAdd.setOnAction(new GroupAdd());
        noteAdd.setOnAction(new NoteAdd());

        repositoryRemove.setOnAction(new RepositoryRemove());
        groupRemove.setOnAction(new GroupRemove());

        repositoryUpdate.setOnAction(new RepositoryUpdate());
        groupUpdate.setOnAction(new GroupUPdate());
        open.setOnMouseClicked(new OpenClick());



        root.setLeft(repositoryBox);
        root.setCenter(groupBox);
        root.setRight(noteBox);
        return root;
    }


    //事件实现

    //listview鼠标点击后相关界面的刷新
    private class RepositoryClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = repositoryView.getSelectionModel().getSelectedIndex();
            if(i == -1){
                return;
            }
            Repository repository = repositories.get(i);
            if(repository.getOpen() == 1){
                open.setSelected(true);
            } else {
                open.setSelected(false);
            }
            noteView.setItems(null);
            groupFlush();
        }
    }

    private class GroupClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            noteFlush();
        }
    }


    private class NoteClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = noteView.getSelectionModel().getSelectedIndex();
            if(i == -1){return;}
            Stage stage = NoteStage.showNote(reader,notes.get(i),0);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    noteFlush();
                }
            });
        }
    }




    //增加按钮的事件实现

    //增加事件
    private class RepositoryAdd implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            Stage stage = RepositoryAddStage.show(master);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    repositoryFlush();
                }
            });
            stage.show();
        }
    }


    private class GroupAdd implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            int i = repositoryView.getSelectionModel().getSelectedIndex();
            if(i == -1){ return; }
            Stage stage = GroupAddStage.show(repositories.get(i));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    groupFlush();
                }
            });
            stage.show();
        }
    }

    private class NoteAdd implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            int i = groupView.getSelectionModel().getSelectedIndex();
            if(i == -1){ return; }

            int j = repositoryView.getSelectionModel().getSelectedIndex();
            if(j == -1){ return; }

            Stage stage = NoteStage.showNote(master,new Note(noteHandle.getId(),"","",1,repositories.get(j).getId(),noteGroups.get(i).getId()),1);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    noteFlush();
                }
            });
        }
    }


    //删除事件
    //删除后将对应的子数据都删除掉，并且刷新一下页面
    private class RepositoryRemove implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            int i = repositoryView.getSelectionModel().getSelectedIndex();
            if(i == -1){ return; }
            noteHandle.removeByRepository(repositories.get(i));
            groupHandle.removeByRepository(repositories.get(i));
            repositoryHandle.remove(repositories.get(i));
            repositoryFlush();
            ObservableList observableList = FXCollections.observableArrayList();
            noteView.setItems(observableList);
            groupView.setItems(observableList);
        }
    }

    private class GroupRemove implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            int i = groupView.getSelectionModel().getSelectedIndex();
            if(i == -1){ return; }
            noteHandle.getByGroup(noteGroups.get(i));
            groupHandle.remove(noteGroups.get(i));
            groupFlush();
            ObservableList observableList = FXCollections.observableArrayList();
            noteView.setItems(observableList);
        }
    }


    //修改事件
    private class RepositoryUpdate implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            int i = repositoryView.getSelectionModel().getSelectedIndex();
            if(i == -1){ return; }
            Stage stage = UpdateStage.getStack(repositories.get(i),null,master);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    repositoryFlush();
                }
            });
        }
    }


    private class GroupUPdate implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            int i = groupView.getSelectionModel().getSelectedIndex();
            if(i == -1){ return; }
            int j = repositoryView.getSelectionModel().getSelectedIndex();
            if(j == -1){ return; }
            Stage stage = UpdateStage.getStack(noteGroups.get(i),repositories.get(j),master);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    groupFlush();
                }
            });
        }
    }

    //知识库是否隐藏设置
    private class OpenClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            int i = repositoryView.getSelectionModel().getSelectedIndex();
            if(i == -1){ return; }
            Repository repository = repositories.get(i);
            if(open.isSelected()){
                repository.setOpen(1);
            }else {
                repository.setOpen(0);
            }
            repositoryHandle.save(repository);
        }
    }


    //listview的刷新
    public void repositoryFlush(){
        ObservableList<String> repositoryList = FXCollections.observableArrayList();
        repositories = repositoryHandle.getByMaster(master);
        if(repositories == null){
            return;
        }
        for (Repository repository : repositories) {
            repositoryList.add(repository.getName());
        }
        repositoryView.setItems(repositoryList);
    }

    public void groupFlush(){
        int i = repositoryView.getSelectionModel().getSelectedIndex();
        if(i == -1){
            return;
        }
        Repository repository = repositories.get(i);
        ObservableList<String> groupList = FXCollections.observableArrayList();
        noteGroups = groupHandle.getByRepository(repository);
        if(noteGroups == null){
            return;
        }
        for (NoteGroup noteGroup : noteGroups) {
            groupList.add(noteGroup.getName());
        }
        groupView.setItems(groupList);
    }

    public void noteFlush(){
        int i = groupView.getSelectionModel().getSelectedIndex();
        if(i == -1){
            return;
        }
        NoteGroup noteGroup = noteGroups.get(i);
        ObservableList<String> noteList = FXCollections.observableArrayList();
        notes = noteHandle.getByGroup(noteGroup);
        if(notes == null){
            return;
        }
        for (Note note : notes) {
            noteList.add(note.getName());
        }
        noteView.setItems(noteList);
    }





}
