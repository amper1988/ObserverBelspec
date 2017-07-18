import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FormNotificationController implements Initializable {
    @FXML
    private Label lblManufacture;
    @FXML
    private Label lblModel;
    @FXML
    private Label lblCarId;
    @FXML
    private Label lblPoliceDepartment;
    @FXML
    private Label lblPoliceman;
    @FXML
    private Label lblEvacuationDate;
    @FXML
    private Label lblEvacuationAddress;
    @FXML
    private Label lblRoadLawPoint;
    @FXML
    private Label lblParking;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnShowDetails;

    private FormNotification link;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnClose.setOnAction(event->{
            if(link != null)
                link.close();
        });
        btnShowDetails.setOnAction(event->{
            if(link != null){
                FormCarDetails frm = new FormCarDetails(link.data.getIdentifier());
                try {
                    frm.start(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    void setLink(FormNotification link){
        this.link = link;
        lblEvacuationDate.setText(link.data.getEvacuationDate());
        lblManufacture.setText(link.data.getManufacture());
        lblModel.setText(link.data.getModel());
        lblCarId.setText(link.data.getCarId());
        lblPoliceDepartment.setText(link.data.getPoliceDepartment());
        lblPoliceman.setText(link.data.getPoliceOfficer());
        lblEvacuationAddress.setText(link.data.getEvacuationAddress());
        lblParking.setText(link.data.getParking());
        lblRoadLawPoint.setText(link.data.getRoadLawPoint());
    }

    void onClose(){
        NotificationManager.getInstance().onCloseNotification(link.data.getIdentifier());
        link.close();
    }

}
