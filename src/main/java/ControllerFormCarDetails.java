import interfaces.ChangerListener;
import interfaces.ImageContextMenuListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.FileManager;
import retrofit.Api;
import retrofit.RetrofitService;
import retrofit.model.CarDataFull;
import retrofit.model.ServerAnswer;
import retrofit.model.get_car_details.request.GetCarDetailsRequestEnvelope;
import retrofit.model.get_car_details.response.GetCarDetailsResponseEnvelope;
import retrofit.model.get_photos.request.GetPhotosRequestEnvelope;
import retrofit.model.get_photos.response.GetPhotosResponseEnvelope;
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

public class ControllerFormCarDetails implements Initializable, ImageContextMenuListener, ChangerListener {
    @FXML
    private Label lblManufTitle;
    @FXML
    private Label lblEvacuatedBool;
    @FXML
    private Label lblPoliceman;
    @FXML
    private Label lblParkingDate;
    @FXML
    private Label lblEvacuationDate;
    @FXML
    private Label lblColor;
    @FXML
    private HBox hbWrecker;
    @FXML
    private WebView wvMaps;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblManufacture;
    @FXML
    private Label lblCarId;
    @FXML
    private Label lblEvacuationOrganization;
    @FXML
    private Label lblModel;
    @FXML
    private Label lblWrecker;
    @FXML
    private Label lblParkingAddress;
    @FXML
    private Label lblPoliceDepartment;
    @FXML
    private Label lblAddressFrom;
    @FXML
    private Label lblRoadLawPoint;
    @FXML
    private HBox hbEvacuationDate;
    @FXML
    private Label lblClause;
    @FXML
    private Label lblProtocolNumber;
    @FXML
    private HBox hbEvacuationOrganization;
    @FXML
    private Label lblStorageOrganization;
    @FXML
    private Label lblWhoTook;
    @FXML
    private ImageView imvImage1;
    @FXML
    private ImageView imvImage2;
    @FXML
    private ImageView imvImage3;
    @FXML
    private ImageView imvImage4;
    @FXML
    private HBox hbEvacuationAddress;
    @FXML
    private VBox vbTop;
    @FXML
    private VBox vbLeft;
    @FXML
    private VBox vbCenter;
    @FXML
    private VBox vbRight;
    @FXML
    private BorderPane brdpFull;
    @FXML
    private Button btnTakePhotos;
    @FXML
    private Button btnBlock;
    @FXML
    private Button btnBlockFree;
    private int takePhotosCount = 0;
    private int getActualDataCount = 0;

    private int identifier = -1;
    private Stage primaryStage = null;
    private FormLoading formLoading = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageContextMenu imageContextMenu = new ImageContextMenu(imvImage1);
        imageContextMenu.setListener(this);
        ImageContextMenu imageContextMenu2 = new ImageContextMenu(imvImage2);
        imageContextMenu2.setListener(this);
        ImageContextMenu imageContextMenu3 = new ImageContextMenu(imvImage3);
        imageContextMenu3.setListener(this);
        ImageContextMenu imageContextMenu4 = new ImageContextMenu(imvImage4);
        imageContextMenu4.setListener(this);
        wvMaps.setContextMenuEnabled(false);
        initializeButtonsEvent();
    }

    private void initializeButtonsEvent() {


        btnTakePhotos.setOnAction(actionEvent -> {
            blockUI(true, "Получаем фотографии.");
            Api.createRetrofitService().executeGetPhotos(Encode.getBasicAuthTemplate(UserManager.getInstanse().getmLogin(), UserManager.getInstanse().getmPassword()),
                    new GetPhotosRequestEnvelope(identifier)).enqueue(new Callback<GetPhotosResponseEnvelope>() {
                @Override
                public void onResponse(Call<GetPhotosResponseEnvelope> call, final Response<GetPhotosResponseEnvelope> response) {
                    blockUI(false, "Данные успешно получены  с сервера.");
                    takePhotosCount = 0;
                    if (response.code() == 200) {
                        final ServerAnswer serverAnswer = response.body().getServerAnswer();
                        if (serverAnswer.getCode() == 1) {
                            try {
                                final File pdfFile = FileManager.createPdfFile();
                                FileOutputStream fos = new FileOutputStream(pdfFile);
                                byte[] fileContent = Converter.convertBase64StringToByteArray(response.body().getServerAnswer().getDescription());
                                fos.write(fileContent);
                                fos.close();
                                Desktop.getDesktop().open(pdfFile);
                                Platform.runLater(() -> Utils.showAlertMessage("Запрос выполнен успешно", "Если документ не открылся автоматически то его можно найти по пути " + pdfFile.getAbsolutePath()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Platform.runLater(() -> Utils.showAlertMessage("Ошбика ответа сервера " + serverAnswer.getCode(), serverAnswer.getDescription()));
                        }
                    } else {
                        Platform.runLater(() -> Utils.showAlertMessage("Ошбика сервера " + response.code(), Converter.convertResponseToSting(response.errorBody())));
                    }
                }

                @Override
                public void onFailure(Call<GetPhotosResponseEnvelope> call, final Throwable t) {
                    if (takePhotosCount++ < Main.COUNT_RETRY) {
                        btnTakePhotos.fire();
                    } else {
                        takePhotosCount = 0;
                        blockUI(false, "Ошибка отправления запроса.");
                        Platform.runLater(() -> Utils.showAlertMessage("Ошбика отправления зарпоса", t.getMessage()));
                    }


                }
            });
        });

        btnBlock.setOnAction(actionEvent -> {
            FormCreateArrest formCreateArrest = new FormCreateArrest(identifier, ControllerFormCarDetails.this);
            formCreateArrest.start(null);
        });

        btnBlockFree.setOnAction(actionEvent -> {
            FormUncheckArrest frm = new FormUncheckArrest(identifier, ControllerFormCarDetails.this);
            frm.start(null);
        });
    }

    void setIdentifier(int identifier, Stage primaryStage) {
        this.identifier = identifier;
        this.primaryStage = primaryStage;
        if (this.identifier != -1) {
            blockUI(true, "Получение данных о транспортном средстве.");
            getActualData();
        }
    }

    void getActualData() {
        blockUI(true, "Получение данных о транспортном средстве.");
        RetrofitService retrofitService = Api.createRetrofitService();
        retrofitService.executeGetCarDetails(Encode.getBasicAuthTemplate(UserManager.getInstanse().getmLogin(), UserManager.getInstanse().getmPassword()),
                new GetCarDetailsRequestEnvelope(this.identifier)).enqueue(new Callback<GetCarDetailsResponseEnvelope>() {
            @Override
            public void onResponse(Call<GetCarDetailsResponseEnvelope> call, final Response<GetCarDetailsResponseEnvelope> response) {
                getActualDataCount = 0;
                blockUI(false, "Данные успешно получены с сервера.");
                if (response.code() == 200) {
                    Platform.runLater(() -> setData(response.body().getCarDataFull()));
                } else {
                    Platform.runLater(() -> Utils.showAlertMessage("Ошибка сервера " + response.code(), Converter.convertResponseToSting(response.errorBody())));
                }
            }

            @Override
            public void onFailure(Call<GetCarDetailsResponseEnvelope> call, final Throwable t) {
                if (getActualDataCount++ < Main.COUNT_RETRY) {
                    getActualData();
                } else {
                    getActualDataCount = 0;
                    blockUI(false, "Ошбика при отправлении запроса.");
                    Platform.runLater(() -> Utils.showAlertMessage("Ошибка при отправлении запроса.", t.getMessage()));
                }

            }
        });
    }

    public void setData(CarDataFull carDataFull) {
        if (carDataFull != null) {
            lblManufTitle.setText(carDataFull.getManufacture());
            lblStatus.setText(carDataFull.getStatus());
            lblManufacture.setText(carDataFull.getManufacture());
            lblModel.setText(carDataFull.getModel());
            lblColor.setText(carDataFull.getColor());
            lblCarId.setText(carDataFull.getCarId());
            lblPoliceDepartment.setText(carDataFull.getPoliceDepartment());
            lblPoliceman.setText(carDataFull.getPoliceman());
            lblProtocolNumber.setText(carDataFull.getProtocolNumber());
            lblClause.setText(carDataFull.getClause());
            lblEvacuatedBool.setText(carDataFull.isEvacuatedBool() ? "Да" : "Нет");
            lblEvacuationOrganization.setText(carDataFull.getEvacuationOrganization());
            lblWrecker.setText(carDataFull.getWrecker());
            lblEvacuationDate.setText(carDataFull.getEvacuationDate());
            lblAddressFrom.setText(carDataFull.getEvacuationAddress());
            lblWhoTook.setText(carDataFull.getWhoTook());
            lblParkingAddress.setText(carDataFull.getParkingAddress());
            lblParkingDate.setText(carDataFull.getParkingDate());
            lblStorageOrganization.setText(carDataFull.getStoringOrganization());
            lblRoadLawPoint.setText(carDataFull.getRoadLawPoint());
            final WebEngine webEngine = wvMaps.getEngine();
            webEngine.loadContent(carDataFull.getMaps());
            imvImage1.setImage(Converter.convertBase64StringToImage(carDataFull.getImage1()));
            imvImage2.setImage(Converter.convertBase64StringToImage(carDataFull.getImage2()));
            imvImage3.setImage(Converter.convertBase64StringToImage(carDataFull.getImage3()));
            imvImage4.setImage(Converter.convertBase64StringToImage(carDataFull.getImage4()));
            applyView(carDataFull.isEvacuatedBool(), carDataFull.isBlock());
        }
    }

    private void applyView(boolean evacuated, boolean block) {
        btnBlock.setDisable(block);
        btnBlockFree.setDisable(!block);

        hbWrecker.setVisible(evacuated);
        hbEvacuationDate.setVisible(evacuated);
        hbEvacuationOrganization.setVisible(evacuated);
        hbEvacuationAddress.setVisible(evacuated);


    }

    void blockUI(boolean block, String info) {
        if (block) {
            Platform.runLater(() -> {
                try {
                    if (formLoading == null) {
                        formLoading = new FormLoading();
                    }
                    formLoading.start(primaryStage, info);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            Platform.runLater(() -> {
                try {
                    if (formLoading != null) {
                        formLoading.stop(info);
                        formLoading = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });


        }
        brdpFull.setVisible(!block);
        vbTop.setVisible(!block);
        vbLeft.setVisible(!block);
        vbCenter.setVisible(!block);
        vbRight.setVisible(!block);

    }

    @Override
    public void showPicture(ImageView imv) {
        if (imv.equals(imvImage1)) {
            FormImageFull frm = new FormImageFull(this.identifier, 1);
            frm.start(null);
        }
        if (imv.equals(imvImage2)) {
            FormImageFull frm = new FormImageFull(this.identifier, 2);
            frm.start(null);
        }
        if (imv.equals(imvImage3)) {
            FormImageFull frm = new FormImageFull(this.identifier, 3);
            frm.start(null);
        }
        if (imv.equals(imvImage4)) {
            FormImageFull frm = new FormImageFull(this.identifier, 4);
            frm.start(null);
        }
    }

    @Override
    public void onChangeData() {
        getActualData();
    }
}
