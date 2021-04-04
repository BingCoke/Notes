package com.dengzhitao.notes.view;

import com.dengzhitao.notes.dao.LikesDao;
import com.dengzhitao.notes.entity.Likes;
import com.dengzhitao.notes.entity.Note;
import com.dengzhitao.notes.entity.Page;
import com.dengzhitao.notes.entity.User;
import com.dengzhitao.notes.service.NoteHandle;
import com.dengzhitao.notes.service.PageHandle;
import com.dengzhitao.notes.service.Userhandle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NoteStageForImg {

    private static Stage stage;



    private static WebView webView = new WebView();
    private static WebEngine webEngine = webView.getEngine();
    private static Pagination pagination ;

    private static Button save = new Button("保存");
    private static Button pageAdd = new Button("增加页数");
    private static Button pageRemove = new Button("减少页数");
    private static Button remove = new Button("删除");
    private static RadioButton like = new RadioButton("点赞");
    private static Button lookWriter = new Button("看作者");
    private static Button img = new Button("插入图片");
    private static RadioButton open = new RadioButton("公开");

    private static TextField title = new TextField();
    private static Text littleText = new Text("点开笔记");

    private static String htmlPath;
    private static String type;
    private static String js;

    //图片存放的位置
    private static String imgPath = "file:///" +NoteStage.class.getResource("").toString().substring(6,NoteStageForImg.class.getResource("").toString().lastIndexOf("/out")) + "/src/com/dengzhitao/notes/html/img/";

    private static List<Page> pages;
    //将要删除的page存起来集中删除
    private static List<Page> pagesToRemove;
    private static NoteHandle noteHandle = new NoteHandle();
    private static Userhandle userhandle = new Userhandle();
    private static PageHandle pageHandle = new PageHandle();
    private static User writer;
    private static User reader;
    private static Note note;

    //存放开始笔记一共几页
    private static int initpage;
    //存放点赞数
    private static int likeCount;
    //存放上一次的页码
    private static int pageCount;
    //记录是否是第一次打开分页器
    private static int icount;
    /**
     * 笔记查看界面
     * @param reader
     * @param note
     * @param type
     * @return
     */
    public static Stage showNote(User reader, Note note ,String type){
        NoteStageForImg.note = note;
        NoteStageForImg.reader = reader;
        NoteStageForImg.writer = noteHandle.getMaster(note);
        NoteStageForImg.type = type;
        pageCount = 0;
        icount = 0;
        title.setText(note.getName());
        title.setEditable(false);
        stage = new Stage();
        likeCount = noteHandle.likeCount(note);
        stage.setTitle(writer.getName() +"的笔记" + " 赞：" + noteHandle.likeCount(note));
        pageCount = note.getPage();
        pagesToRemove = new ArrayList<>();

        BorderPane root = new BorderPane();
        HBox hBoxTop = new HBox();
        HBox hBoxBottom = new HBox();
        hBoxBottom.setAlignment(Pos.BASELINE_CENTER);
        pagination = new Pagination(note.getPage(),(pageCount = 0));
        //得到笔记的每页的内容
        pages = pageHandle.getNotePage(note);
        //如果是新增一个笔记得到的list是空list 所以要new一个page对象
        if( pages == null || pages.size() == 0){
            pages.add(new Page(note.getId(),1,"",0));
        }


        //根据笔记是否公开设置按钮
        if(note.getOpen() == 1){
            open.setSelected(true);
        }else {
            open.setSelected(false);
        }

        hBoxTop.getChildren().addAll(title);
        root.setTop(hBoxTop);
        root.setCenter(littleText);
        //量身定制
        if(writer.getId() == reader.getId()){
            hBoxBottom.getChildren().addAll(pageAdd,pageRemove,img,open,save,remove);
            title.setEditable(true);
            htmlPath = "../html/forWriter.html";
            js = "myDiv";
        } else {
            hBoxBottom.getChildren().addAll(like);
            htmlPath = "../html/forReader.html";
            js = "myBody";
        }
        if(reader.getPower() == 3) {
            hBoxBottom.getChildren().addAll(remove,lookWriter);
        }


        //设置分页控件的页面工厂
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                if( icount != 0){
                    //先把这页的所有的内容存起来
                    pages.get(pageCount).setContent(
                            webEngine.executeScript(js + ".innerHTML").toString()
                    );
                    //存字数
                    pages.get(pageCount).setCount(webEngine.executeScript(js + ".innerText").toString().length());
                }else {
                    icount = 1;
                }

                //如果发现pages的数目少了，就要把pages删掉一个
                if(pages.size() > pagination.getPageCount()){
                    //要删除的存入pagesToRemove中
                    pagesToRemove.add(pages.get(note.getPage()));
                    pages.remove(note.getPage());
                }


                //页数计数
                pageCount = param;
                //将页面更换到对应的页数
                webEngine.executeScript(js + ".innerHTML" + "= '" + pages.get(pageCount).getContent() + "'");
                return webView;
            }
        });
        littleText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //点击查看笔记
                root.setBottom(hBoxBottom);
                root.setCenter(pagination);
            }
        });

        pageAdd.setOnAction(new AddPage());
        pageRemove.setOnAction(new RemovePage());
        remove.setOnAction(new RemoveClick());
        open.setOnMouseClicked(new OpenClick());
        img.setOnAction(new ImgClick());
        like.setOnMousePressed(new LikeClick());
        lookWriter.setOnAction(new LookWriter());
        save.setOnAction(new SaveClick());

        System.out.println(htmlPath);
        webEngine.load(NoteStageForImg.class.getResource(htmlPath).toExternalForm());
        stage.setScene(new Scene(root,600,500));
        return stage;
    }



    //选择照片后先把照片存到工程文件里，方便日后调用
    private static void copyImg(File file, String path){
        FileOutputStream fo = null;
        FileInputStream fi =null;
        try {
            fi = new FileInputStream(file);
            fo = new FileOutputStream(path);
            byte[] buf = new byte[1024];
            int len = 0;
            while( (len = fi.read(buf)) != -1 ){
                fo.write(buf,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //页面增加事件
    private static class AddPage implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            //更改对象的信息
            pages.add(new Page(note.getId(),note.getPage() + 1,"",0));
            note.setPage(note.getPage() + 1);
            pagination.setPageCount(note.getPage());
        }
    }

    //页面移除事件
    private static class RemovePage implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
                int i = pagination.getCurrentPageIndex();
                note.setPage(note.getPage() - 1);
                pagination.setPageCount(note.getPage());

        }
    }

    private static class ImgClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择你要插入的图片");
            //过滤器
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("img","*.png","*.jpg"));
            File file = fileChooser.showOpenDialog(stage);
            if(file != null) {
                note.setImgCount(note.getImgCount() + 1);
                //要存入的文件的路径
                String path = "src/com/dengzhitao/notes/html/img/" + note.getId() + "_" + note.getImgCount() + file.getName().substring(file.getName().lastIndexOf("."));
                //先把文件存起来
                copyImg(file, path);
                webEngine.executeScript(js + ".innerHTML=" + js + ".innerHTML+\"<img src='"+ imgPath+ note.getId() +"_"+ note.getImgCount() + file.getName().substring(file.getName().lastIndexOf(".")) +"'/>\"");
            }
        }
    }


    //管理员可通过笔记界面直接查看用户的个人信息界面
    private static class LookWriter implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(new InformationPane().getPane(reader,writer),400,500));
        }
    }

    //点赞后事件
    private static class LikeClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            LikesDao likesDao = new LikesDao();
            if( !like.isSelected()){
                likesDao.add(new Likes(note.getId(),reader.getId()));
                likeCount++;
            }else {
                List<Likes> likesList = likesDao.selectExact(Likes.class,"userId",reader.getId());
                for (Likes likes : likesList) {
                    likesDao.remove(likes);
                }
                likeCount--;
            }
            stage.setTitle(writer.getName() +"的笔记" + " 赞：" + String.valueOf(likeCount));
        }
    }

    //笔记保存
    private static class SaveClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(title.getText().equals("")){
                TextWindow.textWindow("标题不能为空！");
                return;
            }
            //把存进要删除的page的list都删了
            pageHandle.removeAll(pagesToRemove);
            //清除一下
            pagesToRemove.clear();

            //页数没有改动无法将本页记录 这里要记录一次
            //页面保存
            pages.get(pageCount).setContent(
                    webEngine.executeScript(js + ".innerHTML").toString()
            );
            //存字数
            pages.get(pageCount).setCount(webEngine.executeScript(js + ".innerText").toString().length());

            note.setName(title.getText());
            if(type.equals("add")){
                noteHandle.add(note);
                pageHandle.saveAll(pages,initpage,type);
                type = "save";
                initpage = note.getPage();
            } else {
                noteHandle.save(note);
                pageHandle.saveAll(pages,initpage,type);
                initpage = note.getPage();
            }


        }
    }


    private static class RemoveClick implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            noteHandle.remove(note);
            pageHandle.removeAll(pages);
        }
    }


    private static class OpenClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            if (open.isSelected()) {
                note.setOpen(1);
            }else {
                note.setOpen(0);
            }
        }
    }

}