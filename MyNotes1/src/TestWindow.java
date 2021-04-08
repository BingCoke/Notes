import com.dengzhitao.notes.view.TextWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import javax.xml.soap.Text;
import java.util.jar.JarOutputStream;

public class TestWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(DigestUtils.md5("111").toString());
    }
    @Test
    public void t(){
        System.out.println(DigestUtils.md5("c"));
    }
}
