package model;

import interfaces.ItemWithDepends;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteBoxWithDep {
    private ComboBox<String> cmbMain;
    private ComboBox<String> cmbSecend;

    private List<? extends ItemWithDepends> itemList;
    private List<String> mainElements = new ArrayList<>();

    public AutoCompleteBoxWithDep(ComboBox<String> cmbMain, ComboBox<String> cmbSecend, List<? extends ItemWithDepends> itemList) {
        this.cmbMain = cmbMain;
        this.cmbSecend = cmbSecend;
        if (cmbSecend != null)
            this.cmbSecend.setVisible(false);
        this.itemList = itemList;
        for (ItemWithDepends item : itemList) {
            mainElements.add(item.getName());
        }
        cmbMain.setItems(FXCollections.observableArrayList(mainElements));
        cmbMain.setEditable(true);
        cmbMain.getEditor().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                cmbMain.show();
            } else if (event.getCode() == KeyCode.UP) {
                cmbMain.show();
            } else {
                String searchString = (cmbMain.getEditor().getText() + Utils.returnSymbol(event.getCharacter())).toLowerCase();
                final List<String> foundList = new ArrayList<>();
                if (this.itemList != null) {
                    for (ItemWithDepends item : this.itemList) {
                        if (item.getName().toLowerCase().contains(searchString)) {
                            foundList.add(item.getName());
                        }
                    }
                } else {
                    foundList.add("");
                }
                cmbMain.getItems().clear();
                cmbMain.setItems(FXCollections.observableArrayList(foundList));
                cmbMain.show();
                if (searchString.equals("") || searchString.equals(" ")) {
                    if (cmbSecend != null) {
                        if (cmbSecend.getItems() != null) {
                            cmbSecend.getItems().clear();
                        }
                        cmbSecend.setVisible(false);
                    }
                }
            }
        });
        cmbMain.getEditor().setOnKeyTyped(event -> {
            if (event.getCharacter().hashCode() == 13) {
                if (cmbSecend != null)
                    cmbSecend.requestFocus();
            }
        });
        cmbMain.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (cmbSecend != null)
                cmbSecend.setVisible(true);
            ItemWithDepends selectedItem = null;
            if (this.itemList != null) {
                for (ItemWithDepends item : this.itemList) {
                    if (item.getName().equals(newValue)) {
                        selectedItem = item;
                    }
                }
                if (selectedItem != null) {
                    if (cmbSecend != null)
                        cmbSecend.setItems(FXCollections.observableArrayList(selectedItem.getDependElements()));
                } else {
                    cmbMain.setItems(FXCollections.observableArrayList(this.mainElements));
                    if (cmbSecend != null) {
                        cmbSecend.getItems().clear();
                        cmbSecend.setVisible(false);
                    }
                }

            }
        });
        cmbMain.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (cmbMain.getSelectionModel().isEmpty() && !cmbMain.getEditor().getText().equals("")) {
                    if (cmbSecend != null) {
                        if (cmbSecend.getItems() != null) {
                            cmbSecend.getItems().clear();
                        }
                        cmbSecend.setVisible(false);
                    }

                    Utils.showAlertMessage("Выберите значение", "Значение не выбрано.");
                    cmbMain.requestFocus();
                }
                if (cmbMain.getEditor().getText().equals("")) {
                    if (cmbSecend != null) {
                        if (cmbSecend.getItems() != null) {
                            cmbSecend.getItems().clear();
                        }
                        cmbSecend.setVisible(false);
                    }

                }
            }
        });
        if (cmbSecend != null)
            cmbSecend.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                KeyCode code = event.getCode();
                if (code == KeyCode.UP) {
                    cmbSecend.show();
                }
                if (code == KeyCode.DOWN) {
                    cmbSecend.show();
                }
                if (code == KeyCode.ESCAPE || code == KeyCode.BACK_SPACE || code == KeyCode.DELETE) {
                    cmbSecend.getSelectionModel().clearSelection();
                    cmbSecend.hide();
                }
            });
    }


}
