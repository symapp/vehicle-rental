package me.simao.vehicle_rental_2.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import me.simao.vehicle_rental_2.RentalApplication;
import me.simao.vehicle_rental_2.db.DBUtil;
import me.simao.vehicle_rental_2.db.models.Category;
import me.simao.vehicle_rental_2.db.models.Location;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private LocalDateTime dropOffDateTime;
    private LocalDateTime pickUpDateTime;

    @FXML
    private ChoiceBox<String> dropOffChoiceBox;
    @FXML
    private ChoiceBox<String> pickUpChoiceBox;
    @FXML
    private ComboBox<Location> locationPicker;
    @FXML
    private ComboBox<Category> categoryPicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // location picker
        DBUtil.doWithTransaction(s -> {
            List<Location> locations = s.createQuery("FROM Location ", Location.class).getResultList();
            locationPicker.setItems(FXCollections.observableList(locations));

            locationPicker.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Location> call(ListView<Location> locationListView) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Location location, boolean empty) {
                            super.updateItem(location, empty);
                            if (location == null || empty) {
                                setGraphic(null);
                            } else {
                                setText(location.getCity() + ", " + location.getAddress());
                            }
                        }
                    };
                }
            });
            locationPicker.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(Location location, boolean empty) {
                    super.updateItem(location, empty);
                    if (location == null || empty) {
                        setGraphic(null);
                    } else {
                        setText(location.getCity() + ", " + location.getAddress());
                    }
                }
            });

            List<Category> categories = s.createQuery("FROM Category ", Category.class).list();
            categoryPicker.setItems(FXCollections.observableList(categories));
            categoryPicker.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Category> call(ListView<Category> categoryListView) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Category category, boolean empty) {
                            super.updateItem(category, empty);
                            if (category == null || empty) {
                                setGraphic(null);
                            } else {
                                setText(category.getName() + ", " + category.getChargeRate());
                            }
                        }
                    };
                }
            });
            categoryPicker.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(Category category, boolean empty) {
                    super.updateItem(category, empty);
                    if (category == null || empty) {
                        setGraphic(null);
                    } else {
                        setText(category.getName() + ", " + category.getChargeRate());
                    }
                }
            });

        });
    }

    @FXML
    public void onDropOffClicked() throws IOException {
        FXMLLoader dialogLoader = new FXMLLoader(RentalApplication.class.getResource("date-time-dialog.fxml"));
        DialogPane dialogPane = dialogLoader.load();
        ((DateTimeDialogController) dialogLoader.getController()).setTime(dropOffDateTime);
        dialogPane.getStylesheets().add("stylesheet.css");

        Scene scene = new Scene(dialogPane);
        Stage stage = new Stage();
        stage.setTitle("Date / Time picker");


        dialogPane.lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ANY, actionEvent -> {
            LocalDateTime ldt = ((DateTimeDialogController) dialogLoader.getController()).getDateTime();
            if (ldt.isBefore(LocalDateTime.now())) {
                RentalApplication.alertError("date", "date cannot be in the past");
                return;
            }
            dropOffDateTime = LocalDateTime.from(ldt);
            dropOffChoiceBox.setValue(ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            stage.close();
        });
        dialogPane.lookupButton(ButtonType.CANCEL).addEventFilter(ActionEvent.ANY, actionEvent -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void onPickUpClicked() throws IOException {
        FXMLLoader dialogLoader = new FXMLLoader(RentalApplication.class.getResource("date-time-dialog.fxml"));
        DialogPane dialogPane = dialogLoader.load();
        ((DateTimeDialogController) dialogLoader.getController()).setTime(pickUpDateTime);
        dialogPane.getStylesheets().add("stylesheet.css");

        Scene scene = new Scene(dialogPane);
        Stage stage = new Stage();
        stage.setTitle("Date / Time picker");


        dialogPane.lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ANY, actionEvent -> {
            LocalDateTime ldt = ((DateTimeDialogController) dialogLoader.getController()).getDateTime();
            if (ldt.isBefore(LocalDateTime.now())) {
                RentalApplication.alertError("date", "date cannot be in the past");
                return;
            }
            pickUpDateTime = LocalDateTime.from(ldt);
            pickUpChoiceBox.setValue(ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            stage.close();
        });
        dialogPane.lookupButton(ButtonType.CANCEL).addEventFilter(ActionEvent.ANY, actionEvent -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
