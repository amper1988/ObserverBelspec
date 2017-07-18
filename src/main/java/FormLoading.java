import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class FormLoading {
    private Stage stage;
    private boolean parentWindow;
    private Scene oldScene;
    private boolean started = false;
    ControllerFormLoading controllerFormLoading;

    public void start(Stage primaryStage, String info) throws IOException {
        if (!started) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form_loading.fxml"));
            AnchorPane root = loader.load();
            controllerFormLoading = loader.getController();
            if (primaryStage == null) {
                stage = new Stage(StageStyle.UNDECORATED);
                parentWindow = false;
            } else {
                stage = primaryStage;
                parentWindow = true;
                if (!started) {
                    oldScene = primaryStage.getScene();
                }
            }
            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(getClass().getResource("style.css").toExternalForm());
            root.getChildren().get(0).setId("with_background");
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            stage.show();
            started = true;
        }
        controllerFormLoading.setInfo(info);

    }


    public void stop(String info) throws Exception {
        started = false;
        controllerFormLoading.setInfo(info);
        if (parentWindow) {
            stage.setScene(oldScene);
        } else {
            stage.close();
        }

    }
}
