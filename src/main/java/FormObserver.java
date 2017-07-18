import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.UserManager;

import java.io.IOException;

public class FormObserver {
    private ControllerFormObserver controller;

    public void start(Stage primaryStage) throws IOException {
        Stage stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_observer.fxml"));
        Parent parent = loader.load();
        parent.setId("with_background");
        controller = loader.getController();
        if (primaryStage != null) {
            stage = primaryStage;
        } else {
            stage = new Stage();
        }
        stage.setTitle("Электронный учет. Текущий пользователь: " + UserManager.getInstanse().getmFullName());
        Scene scene = new Scene(parent);
        scene.getStylesheets().addAll(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.show();

    }

    public void stop() throws Exception {
//        if(controller != null)
//        {
//            controller.setWindowClosed();
//        }

        Platform.exit();
        System.exit(0);
    }


}
