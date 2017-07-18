import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import retrofit.Api;
import retrofit.RetrofitService;
import retrofit.model.get_image_full.request.GetImageFullRequestEnvelope;
import retrofit.model.get_image_full.response.GetImageFullResponseEnvelope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Converter;
import utils.Encode;
import utils.UserManager;
import utils.Utils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFormImageFull implements Initializable {
    @FXML
    private ImageView imvImage;
    @FXML
    private Button btnIncrease;
    @FXML
    private Button btnDecrease;
    @FXML
    private Button btnTrueSize;
    @FXML
    private Button btnSave;
    @FXML
    private VBox vbFull;
    private Image image;

    private int identifier = -1;
    private int index = -1;
    private FormLoading formLoading = null;
    private Stage primaryStage;

    private int getImageCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnIncrease.setOnAction(actionEvent -> {
            imvImage.setFitWidth(imvImage.getFitWidth() + 30.0);
            imvImage.setFitHeight(imvImage.getFitHeight() + 30.0);
        });

        btnDecrease.setOnAction(actionEvent -> {
            imvImage.setFitWidth(imvImage.getFitWidth() - 30.0);
            imvImage.setFitHeight(imvImage.getFitHeight() - 30.0);
        });

        btnTrueSize.setOnAction(actionEvent -> {
            imvImage.setFitWidth(image.getWidth());
            imvImage.setFitHeight(image.getHeight());
        });

        btnSave.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
            File file = fileChooser.showSaveDialog(btnSave.getScene().getWindow());
            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image,
                            null), "jpg", file);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

    }

    public void setData(int identifier, int index, Stage primaryStage) {
        this.identifier = identifier;
        this.index = index;
        this.primaryStage = primaryStage;
        if (identifier != -1 || index != -1) {
            blockUI(true, "Получение данных с сервера.");
            RetrofitService retrofitService = Api.createRetrofitService();
            retrofitService.executeGetImageFull(Encode.getBasicAuthTemplate(UserManager.getInstanse().getmLogin(), UserManager.getInstanse().getmPassword()),
                    new GetImageFullRequestEnvelope(this.identifier, this.index)).enqueue(new Callback<GetImageFullResponseEnvelope>() {
                @Override
                public void onResponse(Call<GetImageFullResponseEnvelope> call, final Response<GetImageFullResponseEnvelope> response) {
                    blockUI(false, "Данные успешно получены.");
                    getImageCount = 0;
                    if (response.code() == 200) {
                        final int serverCode = response.body().getAnswer().getCode();
                        if (serverCode == 1) {
                            Platform.runLater(() -> setImage(Converter.convertBase64StringToImage(response.body().getAnswer().getDescription())));
                        } else {
                            Platform.runLater(() -> Utils.showAlertMessage("Ответ сервера содержит ошибку " + serverCode, response.body().getAnswer().getDescription()));
                        }

                    } else {
                        Platform.runLater(() -> Utils.showAlertMessage("Ошибка сервера " + response.code(), Converter.convertResponseToSting(response.errorBody())));
                    }

                }

                @Override
                public void onFailure(Call<GetImageFullResponseEnvelope> call, final Throwable t) {
                    if (getImageCount++ < Main.COUNT_RETRY) {
                        setData(identifier, index, primaryStage);
                    } else {
                        getImageCount = 0;
                        blockUI(false, "Ошибка при отправлении запроса.");
                        Platform.runLater(() -> Utils.showAlertMessage("Ошибка при отправлении запроса.", t.getMessage()));
                    }
                }
            });
        }
    }

    private void blockUI(boolean block, String info) {
        if (block) {
            Platform.runLater(() -> {
                if (formLoading == null) {
                    formLoading = new FormLoading();
                    try {
                        formLoading.start(primaryStage, info);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        vbFull.setVisible(!block);
    }

    private void setImage(Image imv) {
        imvImage.setFitWidth(imv.getWidth());
        imvImage.setFitHeight(imv.getHeight());
        imvImage.setImage(imv);
        image = imv;
    }
}
