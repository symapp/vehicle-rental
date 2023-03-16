package me.simao.vehicle_rental_2.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.simao.vehicle_rental_2.RentalApplication;
import me.simao.vehicle_rental_2.Session;
import me.simao.vehicle_rental_2.db.DBUtil;
import me.simao.vehicle_rental_2.db.models.User;
import org.hibernate.query.Query;

import java.util.Objects;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    protected void onLoginClicked() {
        String email = emailField.getText();
        String password = passwordField.getText();

        DBUtil.doWithTransaction(s -> {
            Query query = s.createQuery("from User where email=:email");
            query.setParameter("email", email);
            User user = (User) query.uniqueResult();

            if (user == null) {
                passwordField.clear();
                errorLabel.setText("Email not found");
            } else if (Objects.equals(user.password, password)) {
                RentalApplication.showMain();
                Session.setLoggedInUser(user);
            } else {
                passwordField.clear();
                errorLabel.setText("Password incorrect");
            }
        });
    }

    @FXML
    protected void onCancelClicked() {
        Platform.exit();
    }

    @FXML
    protected void onNewClicked() {
        RentalApplication.showRegistration();
    }
}
