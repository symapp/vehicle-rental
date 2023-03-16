package me.simao.vehicle_rental_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.simao.vehicle_rental_2.controllers.ParentController;
import me.simao.vehicle_rental_2.db.DBUtil;

import java.io.IOException;

public class RentalApplication extends Application {

    private static ParentController parentController;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        // establish connection
        DBUtil.load();

        RentalApplication.stage = stage;

        FXMLLoader parentLoader = new FXMLLoader(this.getClass().getResource("parent-view.fxml"));

        Pane parentPane = parentLoader.load();
        parentController = parentLoader.getController();

        Scene parentScene = new Scene(parentPane);
        parentScene.getStylesheets().add("stylesheet.css");

        stage.setScene(parentScene);
//        showLogin();
        showMain();
        stage.setTitle("Vehicle Rental");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showLogin() {
        FXMLLoader loginLoader = new FXMLLoader(RentalApplication.class.getResource("login-view.fxml"));
        try {
            showPane(loginLoader.load(), "Login");
        } catch (IOException e) {
            alertError("login page", "could not load login page");
        }
    }

    public static void showRegistration() {
        FXMLLoader registrationLoader = new FXMLLoader(RentalApplication.class.getResource("registration-view.fxml"));
        try {
            showPane(registrationLoader.load(), "Register");
        } catch (IOException e) {
            alertError("registration page", "could not load registration page");
        }
    }

    public static void showMain() {
        FXMLLoader mainPageLoader = new FXMLLoader(RentalApplication.class.getResource("main-view.fxml"));
        try {
            showPane(mainPageLoader.load(), "Rental");
        } catch (IOException e) {
            e.printStackTrace();
            alertError("main page", "could not load main page");
        }
    }

    public static void showPane(Pane pane, String title) {
        parentController.setContent(pane, title);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    public static void alertError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.show();
    }

    public static Stage getStage() {
        return stage;
    }
}