import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.ScreenSize;

import java.io.IOException;

public class Main extends Application {
    public static final String VERSION = "1.0.0.2";
    public static final int COUNT_RETRY = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_login.fxml"));
        GridPane root = loader.load();
        root.setId("with_background");
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Электронный учет. Вход в систему");
        ScreenSize.getInstance().maximizedStage(stage);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(scene);
        controller.setStage(stage);
        stage.show();

    }
}
