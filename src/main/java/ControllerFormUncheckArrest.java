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
import retrofit.model.create_uncheck_arrest.request.CreateUncheckArrestRequestEnvelope;
import retrofit.model.create_uncheck_arrest.response.CreateUncheckArrestResponseEnvelope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Converter;
import utils.Encode;
import utils.UserManager;
import utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerFormUncheckArrest implements Initializable {
    @FXML
    private TextField txtUncheckReason;
    @FXML
    private Button btnUncheckArrest;

    private ChangerListener listener;
    private int identifier;
    private int createUncheckArrestCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnUncheckArrest.setOnAction(actionEvent -> {
            if (confirmData()) {
                createUncheckArrest();
            } else {
                Utils.showAlertMessage("Данные не заполнены", "Проверьте введенные данные");
            }
        });
    }

    private void createUncheckArrest() {
        Api.createRetrofitService().executeCreateUncheckArrest(Encode.getBasicAuthTemplate(UserManager.getInstanse().getmLogin(), UserManager.getInstanse().getmPassword()),
                new CreateUncheckArrestRequestEnvelope(identifier, txtUncheckReason.getText())).enqueue(new Callback<CreateUncheckArrestResponseEnvelope>() {
            @Override
            public void onResponse(Call<CreateUncheckArrestResponseEnvelope> call, final Response<CreateUncheckArrestResponseEnvelope> response) {
                createUncheckArrestCount = 0;
                if (response.code() == 200) {
                    final ServerAnswer serverAnswer = response.body().getServerAnswer();
                    if (serverAnswer.getCode() == 1) {
                        Platform.runLater(() -> Utils.showAlertMessage("Арест успешно снят.", serverAnswer.getDescription()));
                        DataChangeObserver.getInstance().dataChangeNotify();
                        listener.onChangeData();
                        Platform.runLater(() -> ((Stage) btnUncheckArrest.getScene().getWindow()).close());
                    } else {
                        Platform.runLater(() -> Utils.showAlertMessage("Ошбика ответа сервера " + serverAnswer.getCode(), serverAnswer.getDescription()));
                    }
                } else {
                    Platform.runLater(() -> Utils.showAlertMessage("Ошбика сервера " + response.code(), Converter.convertResponseToSting(response.errorBody())));
                }
            }

            @Override
            public void onFailure(Call<CreateUncheckArrestResponseEnvelope> call, final Throwable t) {
                if (createUncheckArrestCount++ < Main.COUNT_RETRY) {
                    createUncheckArrest();
                } else {
                    createUncheckArrestCount = 0;
                    Platform.runLater(() -> Utils.showAlertMessage("Ошибка отравления запроса", t.getMessage()));
                }

            }
        });
    }

    void setData(int identifier, ChangerListener listener) {
        this.identifier = identifier;
        this.listener = listener;
    }

    private boolean confirmData() {
        if (txtUncheckReason.getText().isEmpty())
            return false;
        return true;
    }
}
