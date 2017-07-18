import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.FileManager;
import retrofit.Api;
import retrofit.RetrofitService;
import retrofit.model.ServerAnswer;
import retrofit.model.check_update_observer.request.CheckUpdateObserverRequestEnvelope;
import retrofit.model.check_update_observer.response.CheckUpdateObserverResponseEnvelope;
import retrofit.model.test_connection.request.TestRequestEnvelope;
import retrofit.model.test_connection.response.TestResponseEnvelope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Converter;
import utils.Encode;
import utils.UserManager;
import utils.Utils;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private Button btnSend;
    @FXML
    private TextField txfName;
    @FXML
    private PasswordField pwfPwd;

    @FXML
    private Text txtResult;

    private Stage primaryStage = null;

    private FormLoading formLoading = null;

    private int loginCount = 0;
    private int checkUpdateCount = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txfName.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER)
                pwfPwd.requestFocus();

        });

        pwfPwd.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER)
                login();
        });
        btnSend.setOnAction(event -> login());
    }

    private void login() {
        try {
            blockUI(true, "Проверяются введенные данные");
            RetrofitService retrofitService = Api.createRetrofitService();
            try {
                retrofitService
                        .executeTestOperation(
                                Encode.getBasicAuthTemplate(
                                        txfName.getText(),
                                        pwfPwd.getText()
                                ),
                                new TestRequestEnvelope()
                        )
                        .enqueue(new Callback<TestResponseEnvelope>() {
                            @Override
                            public void onResponse(Call<TestResponseEnvelope> call, Response<TestResponseEnvelope> response) {
                                loginCount = 0;
                                if (response.code() == 200) {
                                    TestResponseEnvelope responseEnvelope = response.body();
                                    txtResult.setText(responseEnvelope.getTestData().getFullName());
                                    UserManager user = UserManager.getInstanse();
                                    user.setUserData(
                                            txfName.getText(),
                                            pwfPwd.getText(),
                                            responseEnvelope.getTestData().getFullName(),
                                            true,
                                            responseEnvelope.getTestData().getOrganization()
                                    );
                                    checkUpdate();
                                } else {
                                    blockUI(false, "Получен ответ.");
                                    txtResult.setText(Converter.convertResponseToSting(response.errorBody()));
                                }
                            }

                            @Override
                            public void onFailure(Call<TestResponseEnvelope> call, Throwable t) {
                                if (loginCount++ < Main.COUNT_RETRY) {
                                    login();
                                } else {
                                    blockUI(false, "Получен ответ.");
                                    txtResult.setText(t.getLocalizedMessage());
                                    Platform.runLater(() -> Utils.showAlertMessage("Ошбика отправления запроса ", t.getMessage()));
                                    loginCount = 0;
                                }

                            }
                        });
            } catch (Throwable t) {
                blockUI(false, "Получен ответ.");
                txtResult.setText(t.getMessage());
            }

        } catch (Throwable e) {
            blockUI(false, "Получен ответ.");
            txtResult.setText(e.getMessage());
        }
    }

    /**
     * Block or unblock UI components depends on "block" argument
     *
     * @param block boolean (set "true" to block, set "false" to unblock)
     */
    private void blockUI(boolean block, String info) {
        if (block) {
            Platform.runLater(() -> {
                if (formLoading == null) {
                    formLoading = new FormLoading();

                }
                try {
                    formLoading.start(primaryStage, info);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            Platform.runLater(() -> {
                if (formLoading != null) {
                    try {
                        formLoading.stop(info);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    formLoading = null;
                }
            });
        }

        btnSend.setDisable(block);
        txfName.setDisable(block);
        pwfPwd.setDisable(block);

    }

    private void checkUpdate() {
        blockUI(true, "Проверка наличия обновления.");
        Api.createRetrofitService()
                .executeCheckUpdateObserver(
                        Encode.getBasicAuthTemplate(
                                UserManager.getInstanse().getmLogin(),
                                UserManager.getInstanse().getmPassword()
                        ),
                        new CheckUpdateObserverRequestEnvelope(Main.VERSION)
                )
                .enqueue(new Callback<CheckUpdateObserverResponseEnvelope>() {
                    @Override
                    public void onResponse(Call<CheckUpdateObserverResponseEnvelope> call, Response<CheckUpdateObserverResponseEnvelope> response) {
                        checkUpdateCount = 0;
                        if (response.code() == 200) {
                            ServerAnswer serverAnswer = response.body().getServerAnswer();
                            if (serverAnswer.getCode() == 5) {
                                Platform.runLater(() -> {
                                    try {
                                        FormObserver frm = new FormObserver();
                                        frm.start(primaryStage);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                            } else if (serverAnswer.getCode() == 1) {
                                try {
                                    blockUI(false, "Обновления найдены.");
                                    txfName.setDisable(true);
                                    pwfPwd.setDisable(true);
                                    btnSend.setDisable(true);
                                    txtResult.setText("Идет обновление компонентов. Дождитесь окончания. \n Если программа попросит внести изменения в систему, нажмите 'Yes' ('Да')");
                                    File exeFile = FileManager.createExeFile();
                                    FileOutputStream fos = new FileOutputStream(exeFile);
                                    byte[] fileContent = Converter.convertBase64StringToByteArray(serverAnswer.getDescription());
                                    fos.write(fileContent);
                                    fos.close();
                                    Desktop.getDesktop().open(exeFile);
                                    Platform.exit();
                                    System.exit(0);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                blockUI(false, "Ошбика сервера.");
                                Platform.runLater(() -> Utils.showAlertMessage("Ошбика ответа сервера " + serverAnswer.getCode(), serverAnswer.getDescription()));
                            }
                        } else {
                            blockUI(false, "Ошибка сервера.");
                            Platform.runLater(() -> Utils.showAlertMessage("Ошибка сервера " + response.code(), Converter.convertResponseToSting(response.errorBody())));
                        }

                    }

                    @Override
                    public void onFailure(Call<CheckUpdateObserverResponseEnvelope> call, Throwable t) {
                        if (checkUpdateCount++ < Main.COUNT_RETRY) {
                            checkUpdate();
                        } else {
                            blockUI(false, "Ошибка отправления запроса.");
                            Platform.runLater(() -> Utils.showAlertMessage("Ошбика отправления запроса", "Произведено " + checkUpdateCount + " попыток. При попытке обнолвения:  " + t.getMessage()));
                            checkUpdateCount = 0;
                        }

                    }
                });
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }
}
