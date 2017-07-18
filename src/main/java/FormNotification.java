import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import retrofit.model.CarDataShort;
import utils.UserManager;

import java.io.IOException;

public class FormNotification {
    FormNotificationController controller;
    CarDataShort data;
    private Stage stage;

    public void start(Stage primaryStage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_notification.fxml"));
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
        controller.setLink(this);
        stage.setOnCloseRequest(windowEvent -> {
            controller.onClose();
        });
        stage.requestFocus();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.show();
    }

    public void setData(CarDataShort data){
        this.data = data;
    }

    void close(){
        stage.close();
    }
}
