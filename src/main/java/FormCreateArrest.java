import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FormCreateArrest {

    private int identifier = -1;
    private ControllerFormCarDetails controllerFormCarDetails = null;

    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_create_arrest.fxml"));
        try {
            Parent root = (Parent) loader.load();
            ControllerFormCreateArrest controllerFormCreateArrest = loader.getController();
            Stage stage;
            if (primaryStage == null) {
                stage = new Stage();
            } else {
                stage = primaryStage;
            }
            stage.setTitle("Электронный учет. Наложение ареста.");
            stage.setScene(new Scene(root));
            if(identifier != -1){
                controllerFormCreateArrest.setData(identifier, this.controllerFormCarDetails);
            }
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FormCreateArrest(int identifier, ControllerFormCarDetails controllerFormCarDetails) {
        this.identifier = identifier;
        this.controllerFormCarDetails = controllerFormCarDetails;
    }
}
