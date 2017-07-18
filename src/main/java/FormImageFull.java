import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.ScreenSize;

import java.io.IOException;

public class FormImageFull {
    private int identifier = -1;
    private int index = -1;


    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_image_full.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ControllerFormImageFull controllerFormImageFull = loader.getController();
        Stage stage;
        if (primaryStage == null)
            stage = new Stage();
        else
            stage = primaryStage;
        stage.setTitle("Электронный учет. Детали авто. Показать фото.");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        ScreenSize.getInstance().maximizedStage(stage);
        controllerFormImageFull.setData(identifier, index, stage);
        stage.show();
    }

    public FormImageFull(int identifier, int index) {
        this.identifier = identifier;
        this.index = index;
    }
}
