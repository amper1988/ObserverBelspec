import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFormLoading implements Initializable {
    @FXML
    private Label lblInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblInfo.setMaxWidth(Double.MAX_VALUE);
        lblInfo.setAlignment(Pos.CENTER);
    }

    public void setInfo(String info) {
        lblInfo.setText(info);
    }
}
