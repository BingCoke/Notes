import com.dengzhitao.notes.view.TextWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.xml.soap.Text;

public class TestWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        TextWindow.textWindow("ff").show();
    }
}
