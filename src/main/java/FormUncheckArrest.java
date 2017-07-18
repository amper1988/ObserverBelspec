/**
 * Created by amper1988 on 07.03.2017.
 */

import interfaces.ChangerListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FormUncheckArrest implements ChangerListener {
    private int identifier;
    private ChangerListener listener;

    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_uncheck_arrest.fxml"));
        try {
            Parent root = (Parent) loader.load();
            ControllerFormUncheckArrest controllerFormUncheckArrest = loader.getController();
            Stage stage;
            if (primaryStage == null) {
                stage = new Stage();
            } else {
                stage = primaryStage;
            }
            stage.setTitle("Электронный учет. Снятие ареста.");
            stage.setScene(new Scene(root));
            controllerFormUncheckArrest.setData(this.identifier, this.listener);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public FormUncheckArrest(int identifier, ChangerListener listener) {
        this.identifier = identifier;
        this.listener = listener;
    }

    @Override
    public void onChangeData() {
        if (listener != null) {
            listener.onChangeData();
        }
    }
}
