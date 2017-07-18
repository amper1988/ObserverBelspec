import interfaces.ChangerListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DataChangeObserver;
import retrofit.Api;
import retrofit.model.ServerAnswer;
import retrofit.model.create_arrest.request.CreateArrestRequestEnvelope;
import retrofit.model.create_arrest.response.CreateArrestResponseEnvelope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Converter;
import utils.Encode;
import utils.UserManager;
import utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFormCreateArrest implements Initializable, ChangerListener {
    @FXML
    private TextField txtWhoArrested;
    @FXML
    private TextField txtArrestReason;
    @FXML
    private Button btnArrest;

    private int identifier;
    private ControllerFormCarDetails controllerFormCarDetails;
    private int createArrestCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnArrest.setOnAction(actionEvent -> {
            if(confirmData()){
                createArrest();
            }else{
                controllerFormCarDetails.blockUI(false, "");
                Utils.showAlertMessage("Данные не заполнены", "Проверьте введенные данные.");
            }
        });
    }

    private void createArrest() {
        controllerFormCarDetails.blockUI(true, "");
        Api.createRetrofitService().executeCreateArrest(Encode.getBasicAuthTemplate(UserManager.getInstanse().getmLogin(), UserManager.getInstanse().getmPassword()),
                new CreateArrestRequestEnvelope(identifier, txtWhoArrested.getText(), txtArrestReason.getText())).enqueue(new Callback<CreateArrestResponseEnvelope>() {
            @Override
            public void onResponse(Call<CreateArrestResponseEnvelope> call, final Response<CreateArrestResponseEnvelope> response) {
                createArrestCount = 0;
                controllerFormCarDetails.blockUI(false, "");
                if(response.code() == 200){
                    final ServerAnswer serverAnswer = response.body().getServerAnswer();
                    if(serverAnswer.getCode() == 1){
                        Platform.runLater(() -> {
                            Utils.showAlertMessage("Успешно наложен арест", serverAnswer.getDescription());
                            DataChangeObserver.getInstance().dataChangeNotify();
                            controllerFormCarDetails.getActualData();
                            ((Stage)btnArrest.getScene().getWindow()).close();
                        });
                    }else{
                        Platform.runLater(() -> Utils.showAlertMessage("Ошбика ответа сервера " + serverAnswer.getCode(), serverAnswer.getDescription()));
                    }
                }else {
                    Platform.runLater(() -> Utils.showAlertMessage("Ошбика сервера " + response.code(), Converter.convertResponseToSting(response.errorBody())));
                }
            }

            @Override
            public void onFailure(Call<CreateArrestResponseEnvelope> call, Throwable t) {
                if(createArrestCount++ < Main.COUNT_RETRY){
                    createArrest();
                }else{
                    createArrestCount = 0;
                    Platform.runLater(()-> Utils.showAlertMessage("Ошбика при отправлении запроса", t.getMessage()));
                }
            }
        });
    }

    void setData(int identifier, ControllerFormCarDetails controllerFormCarDetails){
        this.identifier = identifier;
        this.controllerFormCarDetails = controllerFormCarDetails;
    }

    private boolean confirmData(){
        if(txtArrestReason.getText().isEmpty())
            return false;
        if(txtWhoArrested.getText().isEmpty())
            return false;

        return true;
    }

    @Override
    public void onChangeData() {
        controllerFormCarDetails.getActualData();
        ((Stage)btnArrest.getScene().getWindow()).close();
    }
}
