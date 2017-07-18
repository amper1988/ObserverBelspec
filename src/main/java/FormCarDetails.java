import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.ScreenSize;

import java.io.IOException;

public class FormCarDetails {
    private int identifier;

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_car_details.fxml"));
        AnchorPane root = loader.load();
        ControllerFormCarDetails controllerFormCarDetails = loader.getController();
        root.setId("with_background");
        Stage stage;
        if (primaryStage != null)
            stage = primaryStage;
        else
            stage = new Stage();
        stage.setTitle("Электронный учет. Детали авто");
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        ScreenSize.getInstance().maximizedStage(stage);
        controllerFormCarDetails.setIdentifier(identifier, stage);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.show();
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    FormCarDetails(int identifier) {
        this.identifier = identifier;

    }
}
