package me.simao.vehicle_rental_2.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import me.simao.vehicle_rental_2.RentalApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ParentController {
    @FXML
    public Label titleLabel;

    @FXML
    public BorderPane borderPane;

    @FXML
    public void onExitClicked() {
        Platform.exit();
    }

    @FXML
    public void onAboutClicked() {
        Dialog<String> dialog = new Dialog<>();
        StringBuilder txt = new StringBuilder();
        try {
            File file = new File(RentalApplication.class.getResource("about.txt").toURI());
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) txt.append(sc.nextLine());

        } catch (FileNotFoundException | NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            txt = new StringBuilder("could not find about text");
        }
        dialog.setContentText(txt.toString());
        dialog.setTitle("About");

        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);

        dialog.getDialogPane().getStylesheets().add("stylesheet.css");

        dialog.show();
    }

    public void setContent(Pane pane, String title) {
        borderPane.setCenter(pane);
        titleLabel.setText(title);
    }
}
