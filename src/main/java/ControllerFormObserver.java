import com.sun.javafx.scene.control.skin.TableViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import interfaces.ChangerListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import model.CarDataForLists;
import model.DataChangeObserver;
import retrofit.Api;
import retrofit.RetrofitService;
import retrofit.model.CarDataShort;
import retrofit.model.get_cars_on_evacuation.request.GetCarsOnEvacuationRequestEnvelope;
import retrofit.model.get_cars_on_evacuation.response.GetCarsOnEvacuationResponseEnvelope;
import retrofit.model.get_cars_on_parking.request.GetCarsOnParkingRequestEnvelope;
import retrofit.model.get_cars_on_parking.response.GetCarsOnParkingResponseEnvelope;
import retrofit.model.get_last_evacuated.request.GetLastEvacuatedRequestEnvelope;
import retrofit.model.get_last_evacuated.response.GetLastEvacuatedResponseEnvelope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Converter;
import utils.Encode;
import utils.UserManager;
import utils.Utils;

import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;


public class ControllerFormObserver implements Initializable, ChangerListener {
    @FXML
    private TableView<CarDataForLists> tblOnParking;
    @FXML
    private TableColumn<CarDataForLists, String> tbcProtocolNumberOnParking;
    @FXML
    private TableColumn<CarDataForLists, String> tbcManufactureOnParking;
    @FXML
    private TableColumn<CarDataForLists, String> tbcCarIdOnParking;
    @FXML
    private TableColumn<CarDataForLists, String> tbcPoliceDepartmentOnParking;
    @FXML
    private TableColumn<CarDataForLists, String> tbcPolicemanOnParking;
    @FXML
    private TableColumn<CarDataForLists, String> tbcParkingDate;
    @FXML
    private TableColumn<CarDataForLists, String> tbcClauseOnParking;
    @FXML
    private TableColumn<CarDataForLists, String> tbcRoadLawPoint;
    @FXML
    private TableColumn<CarDataForLists, String> tbcParkingOnParking;
    @FXML
    private TableView<CarDataForLists> tblOnEvacuation;
    @FXML
    private TableColumn<CarDataForLists, String> tbcProtocolNumberOnEvacuation;
    @FXML
    private TableColumn<CarDataForLists, String> tbcManufactureOnEvacuation;
    @FXML
    private TableColumn<CarDataForLists, String> tbcCarIdOnEvacuation;
    @FXML
    private TableColumn<CarDataForLists, String> tbcPoliceDepartmentOnEvacuation;
    @FXML
    private TableColumn<CarDataForLists, String> tbcPolicemanOnEvacuation;
    @FXML
    private TableColumn<CarDataForLists, String> tbcEvacuationDate;
    @FXML
    private TableColumn<CarDataForLists, String> tbcRoadLawPointOnEvacuation;
    @FXML
    private TableColumn<CarDataForLists, String> tbcClauseOnEvacuation;
    @FXML
    private TableColumn<CarDataForLists, String> tbcParkingOnEvacuation;
    @FXML
    private TextField txtOnParkingSearch;
    @FXML
    private TextField txtOnEvacuationSearch;

    private RetrofitService retrofitService;
    private int pageOnParking = -1;
    private int pageOnEvacuation = -1;
    private boolean windowClosed = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        retrofitService = Api.createRetrofitService();
        DataChangeObserver.getInstance().setListener(this);
        refreshLists();
        initializeTableParkingEvent();
        initializeTableEvacuationEvent();
        startUpdater();
        startEvacuationListener();
    }

    private void startEvacuationListener() {
        Thread thread = new Thread(() -> {
            while (!windowClosed) {
                try {

                    Api.createRetrofitService().executeGetLastEvacuated(Encode.getBasicAuthTemplate(UserManager.getInstanse().getmLogin(), UserManager.getInstanse().getmPassword()),
                            new GetLastEvacuatedRequestEnvelope()).enqueue(new Callback<GetLastEvacuatedResponseEnvelope>() {
                        @Override
                        public void onResponse(Call<GetLastEvacuatedResponseEnvelope> call, Response<GetLastEvacuatedResponseEnvelope> response) {
                            if (response.code() == 200) {
                                Platform.runLater(()-> NotificationManager.getInstance().showNotification(response.body().getData().getCarDataShortList()));
                            }
                        }

                        @Override
                        public void onFailure(Call<GetLastEvacuatedResponseEnvelope> call, Throwable t) {

                        }
                    });
                    sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("evacuationListener worked");
            }
        });
        thread.start();
    }

    /**
     * Go to server and get information about all needed lists;
     */
    private void refreshLists() {
        loadMoreOnParkingFromServer("");
        loadMoreOnEvacuationFromServer("");
    }

    private long lastActionOnParking = 0;

    private void initializeTableParkingEvent() {
        txtOnParkingSearch.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            if (event.getCharacter().hashCode() == 13) {
                String searchString = txtOnParkingSearch.getText() + Utils.returnSymbol(event.getCharacter());
                pageOnParking = -1; //load from server from start;
                loadMoreOnParkingFromServer(searchString);
            }
        });
        //Set handler for double click event in table parking;
        tblOnParking.setOnMouseClicked(mouseEvent -> {
            lastActionOnParking = System.nanoTime();
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                if (tblOnParking.getSelectionModel().getSelectedItem() != null)
                    showCarDetails(tblOnParking.getSelectionModel().getSelectedItem().getIdentifier());
            }
        });
        //Set handler for scroll event.
        tblOnParking.addEventFilter(ScrollEvent.ANY, scrollEvent -> {
            lastActionOnParking = System.nanoTime();
            loadMoreOnParkingFromServer(txtOnParkingSearch.getText());
        });
        //Set handler for key pressed event
        tblOnParking.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            lastActionOnParking = System.nanoTime();
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (tblOnParking.getSelectionModel().getSelectedItem() != null)
                    showCarDetails(tblOnParking.getSelectionModel().getSelectedItem().getIdentifier());
            }
            if (keyEvent.getCode() == KeyCode.DOWN) {
                //load more data when list scrolled to end;
                loadMoreOnParkingFromServer(txtOnParkingSearch.getText());
            }
            if (keyEvent.getCode() == KeyCode.END) {
                //load more data from server ;
                loadMoreOnParkingFromServer(txtOnParkingSearch.getText());
            }
        });
    }

    private CarDataForLists lastSelectedOnParking;
    private CarDataForLists lastSelectedOnEvacuation;

    private void startUpdater() {
        Thread thread = new Thread(() -> {
            while (!windowClosed) {
                try {
                    sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (System.nanoTime() - lastActionOnParking > 20000) {
                    pageOnParking = -1;
                    lastSelectedOnParking = tblOnParking.getSelectionModel().getSelectedItem();
                    loadMoreOnParkingFromServer(txtOnParkingSearch.getText());
                }

                if (System.nanoTime() - lastActionOnEvacuation > 20000) {
                    pageOnEvacuation = -1;
                    lastSelectedOnEvacuation = tblOnEvacuation.getSelectionModel().getSelectedItem();
                    loadMoreOnEvacuationFromServer(txtOnEvacuationSearch.getText());
                }

                System.out.println("thread2 worked");
            }
        });
        thread.start();
    }
////
////    public boolean isWindowClosed() {
////        return windowClosed;
////    }
//
//    void setWindowClosed() {
//        this.windowClosed = true;
//        DataChangeObserver.getInstance().deleteListener(this);
//    }

    private long lastActionOnEvacuation = 0;

    private void initializeTableEvacuationEvent() {
        txtOnEvacuationSearch.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            if (event.getCharacter().hashCode() == 13) {
                String searchString = txtOnEvacuationSearch.getText() + Utils.returnSymbol(event.getCharacter());
                pageOnEvacuation = -1; //load from server from start;
                loadMoreOnEvacuationFromServer(searchString);
            }
        });
        tblOnEvacuation.setOnMousePressed(event -> {
            lastActionOnEvacuation = System.nanoTime();
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                showDetailsOnEvacuation();
            }
        });
        tblOnEvacuation.addEventFilter(ScrollEvent.ANY, scrollEvent -> {
            lastActionOnEvacuation = System.nanoTime();
            loadMoreOnEvacuationFromServer(txtOnEvacuationSearch.getText());
        });
        tblOnEvacuation.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            lastActionOnEvacuation = System.nanoTime();
            if (event.getCode() == KeyCode.ENTER) {
                showDetailsOnEvacuation();
            }
            if (event.getCode() == KeyCode.DOWN) {
                loadMoreOnEvacuationFromServer(txtOnEvacuationSearch.getText());
            }
            if (event.getCode() == KeyCode.END) {
                loadMoreOnEvacuationFromServer(txtOnEvacuationSearch.getText());
            }
        });
    }

    private void showTableOnParking(ObservableList<CarDataForLists> observableList) {
        Platform.runLater(() -> {
            tblOnParking.setItems(observableList);
            tbcProtocolNumberOnParking.setCellValueFactory(cellData -> cellData.getValue().protocolNumberProperty());
            tbcManufactureOnParking.setCellValueFactory(cellData -> cellData.getValue().manufactureProperty());
            tbcCarIdOnParking.setCellValueFactory(cellData -> cellData.getValue().carIdProperty());
            tbcPoliceDepartmentOnParking.setCellValueFactory(cellData -> cellData.getValue().policeDepartmentProperty());
            tbcClauseOnParking.setCellValueFactory(cellData -> cellData.getValue().clauseProperty());
            tbcParkingDate.setCellValueFactory(cellData -> cellData.getValue().parkingDateProperty());
            tbcPolicemanOnParking.setCellValueFactory(cellData -> cellData.getValue().policeOfficerProperty());
            tbcRoadLawPoint.setCellValueFactory(cellData -> cellData.getValue().roadLawPointProperty());
            tbcParkingOnParking.setCellValueFactory(cellData -> cellData.getValue().parkingProperty());
            tblOnParking.setRowFactory(new javafx.util.Callback<TableView<CarDataForLists>, TableRow<CarDataForLists>>() {
                @Override
                public TableRow<CarDataForLists> call(TableView<CarDataForLists> carDataForListsTableView) {
                    return new TableRow<CarDataForLists>() {
                        @Override
                        protected void updateItem(CarDataForLists item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null && item.isBlock()) {
                                setStyle("-fx-background-color: orange");
                            } else {
                                setTextFill(Color.BLACK);
                                setStyle("");
                            }

                        }
                    };
                }
            });
            if (lastSelectedOnParking != null)
                tblOnParking.getSelectionModel().select(lastSelectedOnParking.getIndexInTable());
        });

    }

    private void showTableOnEvacuation(ObservableList<CarDataForLists> observableList) {
        Platform.runLater(() -> {
            tblOnEvacuation.setItems(observableList);
            tbcProtocolNumberOnEvacuation.setCellValueFactory(cellData -> cellData.getValue().protocolNumberProperty());
            tbcManufactureOnEvacuation.setCellValueFactory(cellData -> cellData.getValue().manufactureProperty());
            tbcCarIdOnEvacuation.setCellValueFactory(cellData -> cellData.getValue().carIdProperty());
            tbcClauseOnEvacuation.setCellValueFactory(cellData -> cellData.getValue().clauseProperty());
            tbcPoliceDepartmentOnEvacuation.setCellValueFactory(cellData -> cellData.getValue().policeDepartmentProperty());
            tbcPolicemanOnEvacuation.setCellValueFactory(cellData -> cellData.getValue().policeOfficerProperty());
            tbcRoadLawPointOnEvacuation.setCellValueFactory(cellData -> cellData.getValue().roadLawPointProperty());
            tbcParkingOnEvacuation.setCellValueFactory(cellData -> cellData.getValue().parkingProperty());
            tbcEvacuationDate.setCellValueFactory(cellData -> cellData.getValue().evacuationDateProperty());
            tblOnEvacuation.getSelectionModel().select(lastSelectedOnEvacuation);
            if (lastSelectedOnEvacuation != null)
                tblOnEvacuation.getSelectionModel().select(lastSelectedOnEvacuation.getIndexInTable());
        });

    }

    /**
     * Get last visible item on UI in table.
     *
     * @param table TableView
     * @return lat visible item from table or 0 if table is null;
     */
    private int getLastVisibleItem(TableView<?> table) {
        try {
            return ((VirtualFlow) ((TableViewSkin<?>) table.getSkin()).getChildren().get(1)).getLastVisibleCell().getIndex();
        } catch (NullPointerException e) {
            return 0;
        }

    }

    /**
     * Go to server and load more information about cars on parking
     * Depends on private field 'pageOnParking'
     * 'pageOnParking' contains information about loaded pages from server
     */
    private boolean parkingLoading = false;
    private ObservableList<CarDataForLists> listOnParking = FXCollections.observableArrayList();
    private int loadOnParkingCount = 0;

    private void loadMoreOnParkingFromServer(String param) {
        if (((getLastVisibleItem(tblOnParking) + 100 > pageOnParking * 200) && !parkingLoading)) {
            //list ends soon;
            //Go to server and get more information;
            parkingLoading = true; //set flags that data is loading;
            retrofitService.executeGetCarOnParking(Encode.getBasicAuthTemplate(UserManager.getInstanse().getmLogin(), UserManager.getInstanse().getmPassword()),
                    new GetCarsOnParkingRequestEnvelope(++pageOnParking, param)).enqueue(new Callback<GetCarsOnParkingResponseEnvelope>() {
                @Override
                public void onResponse(Call<GetCarsOnParkingResponseEnvelope> call, final Response<GetCarsOnParkingResponseEnvelope> response) {
                    if (response.code() == 200) {
                        //response is good;
                        ObservableList<CarDataForLists> observableList = FXCollections.observableArrayList();
                        List<CarDataShort> carDataShortList = response.body().getData().getCarDataShortList();
                        if (carDataShortList != null) {
                            int index = 0;
                            for (CarDataShort carDataShort : carDataShortList) {
                                try {
                                    observableList.add(Converter.convertCarDataShortToCarDataForLists(carDataShort, index++));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            Platform.runLater(() -> {
                                //add information to list;
                                if (pageOnParking == 0) {
                                    listOnParking.clear();
                                }
                                listOnParking.addAll(observableList);
                                //show new information in UI;
                                showTableOnParking(listOnParking);

                            });

                        } else if (pageOnParking == 0) {
                            Platform.runLater(() -> {
                                listOnParking.clear();
                                showTableOnParking(listOnParking);

                            });

                        }
                    } else {
                        //response is bad;
                        pageOnParking--;// page isn't loaded.
                        Platform.runLater(() -> Utils.showAlertMessage("Response error.", Converter.convertResponseToSting(response.errorBody())));
                    }
                    parkingLoading = false; //free flag that data is loading;
                    loadOnParkingCount = 0;
                }

                @Override
                public void onFailure(Call<GetCarsOnParkingResponseEnvelope> call, final Throwable t) {
                    //request if fail;
                    parkingLoading = false; //free flag that data is loading;
                    pageOnParking--; // page isn't loaded;
                    if (loadOnParkingCount++ < Main.COUNT_RETRY) {
                        loadMoreOnParkingFromServer(param);
                    } else {
                        loadOnParkingCount = 0;
                        Platform.runLater(() -> Utils.showAlertMessage("Fail request.", t.getMessage()));
                    }

                }
            });
        }
    }

    //    private long lastTimeEvacuation;
    private boolean evacuationLoading = false;
    private ObservableList<CarDataForLists> listOnEvacuation = FXCollections.observableArrayList();
    private int loadOnEvacuationCount = 0;

    private void loadMoreOnEvacuationFromServer(String param) {
        if ((getLastVisibleItem(tblOnEvacuation) + 100 > pageOnEvacuation * 200) && !evacuationLoading) {
            evacuationLoading = true;
            retrofitService.executeGetCarsOnEvacuation(Encode.getBasicAuthTemplate(UserManager.getInstanse().getmLogin(), UserManager.getInstanse().getmPassword()),
                    new GetCarsOnEvacuationRequestEnvelope(++pageOnEvacuation, param)).enqueue(new Callback<GetCarsOnEvacuationResponseEnvelope>() {
                @Override
                public void onResponse(Call<GetCarsOnEvacuationResponseEnvelope> call, final Response<GetCarsOnEvacuationResponseEnvelope> response) {

                    if (response.code() == 200) {
                        ObservableList<CarDataForLists> observableList = FXCollections.observableArrayList();
                        List<CarDataShort> carDataShortList = response.body().getData().getCarDataShortList();
                        if (carDataShortList != null) {
                            int index = 0;
                            for (CarDataShort carDataShort : carDataShortList) {
                                try {
                                    observableList.add(Converter.convertCarDataShortToCarDataForLists(carDataShort, index++));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            Platform.runLater(() -> {
                                if (pageOnEvacuation == 0) {
                                    listOnEvacuation.clear();
                                }
                                listOnEvacuation.addAll(observableList);
                                showTableOnEvacuation(listOnEvacuation);
                            });

                        } else if (pageOnEvacuation == 0) {
                            Platform.runLater(() -> {
                                listOnEvacuation.clear();
                                showTableOnEvacuation(listOnEvacuation);
                            });

                        }

                    } else {
                        pageOnEvacuation--;
                        Platform.runLater(() -> Utils.showAlertMessage("Response error code: " + response.code(), Converter.convertResponseToSting(response.errorBody())));
                    }
                    evacuationLoading = false;
                    loadOnEvacuationCount = 0;
                }

                @Override
                public void onFailure(Call<GetCarsOnEvacuationResponseEnvelope> call, final Throwable t) {
                    pageOnEvacuation--;
                    evacuationLoading = false;
                    if (loadOnEvacuationCount++ < Main.COUNT_RETRY) {
                        loadMoreOnEvacuationFromServer(param);
                    } else {
                        loadOnEvacuationCount = 0;
                        Platform.runLater(() -> Utils.showAlertMessage("Fail request", t.getMessage()));
                    }
                }
            });
        }
    }

    private void showCarDetails(int identifier) {
        try {
            FormCarDetails frm = new FormCarDetails(identifier);
            frm.start(null);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void showDetailsOnEvacuation() {
        if (tblOnEvacuation.getSelectionModel().getSelectedItem() != null)
            showCarDetails(tblOnEvacuation.getSelectionModel().getSelectedItem().getIdentifier());
    }

    @Override
    public void onChangeData() {
        pageOnParking = -1;
        pageOnEvacuation = -1;
        refreshLists();
    }


}
