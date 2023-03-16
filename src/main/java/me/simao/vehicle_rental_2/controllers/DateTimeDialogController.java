package me.simao.vehicle_rental_2.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.ResourceBundle;

public class DateTimeDialogController implements Initializable {
    @FXML
    private DatePicker dateEdit;
    @FXML
    private TextField hourEdit;
    @FXML
    private TextField minuteEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalTime localTime = LocalTime.now();
        hourEdit.setPromptText(String.valueOf(localTime.get(ChronoField.HOUR_OF_DAY)));
        minuteEdit.setPromptText(String.valueOf(localTime.get(ChronoField.MINUTE_OF_HOUR)));
        LocalDate localDate = LocalDate.now();
        dateEdit.setPromptText(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear());

        hourEdit.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty()) return;
                int n = Integer.parseInt(newValue);
                if (newValue.length() > 2 ||
                    n < 0 || n > 23)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                hourEdit.setText(oldValue);
            }
        });

        minuteEdit.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty()) return;
                int n = Integer.parseInt(newValue);
                if (newValue.length() > 2 ||
                        n < 0 || n > 59)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                minuteEdit.setText(oldValue);
            }
        });

        dateEdit.setDayCellFactory(datePicker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    public LocalDateTime getDateTime() {
        LocalDate ld = LocalDate.now();
        int hour = LocalTime.now().getHour();
        int minute = LocalTime.now().getMinute();

        if (!hourEdit.getText().isBlank()) hour = Integer.parseInt(hourEdit.getText());
        if (!minuteEdit.getText().isBlank()) minute = Integer.parseInt(minuteEdit.getText());
        if (dateEdit.getValue() != null) ld = dateEdit.getValue();

        return LocalDateTime.of(ld.getYear(), ld.getMonth(), ld.getDayOfMonth(), hour, minute);
    }

    public void setTime(LocalDateTime l){
        if (l == null) return;
        hourEdit.setText(String.valueOf(l.getHour()));
        minuteEdit.setText(String.valueOf(l.getMinute()));
        dateEdit.setValue(l.toLocalDate());
    }
}
