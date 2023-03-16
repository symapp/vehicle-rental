package me.simao.vehicle_rental_2.controllers;

import jakarta.persistence.PersistenceException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.simao.vehicle_rental_2.RentalApplication;
import me.simao.vehicle_rental_2.db.DBUtil;
import me.simao.vehicle_rental_2.db.models.User;
import org.hibernate.query.Query;

public class RegistrationController {
    @FXML
    private TextField firstNameEdit;

    @FXML
    private TextField lastNameEdit;

    @FXML
    private TextField address1Edit;

    @FXML
    private TextField address2Edit;

    @FXML
    private TextField zipEdit;

    @FXML
    private TextField cityEdit;

    @FXML
    private TextField telephoneEdit;

    @FXML
    private TextField emailEdit;

    @FXML
    private TextField creditCardNumberEdit;

    @FXML
    private PasswordField passwordEdit;

    @FXML
    private PasswordField confirmPasswordEdit;

    @FXML
    public void onCancelClicked() {
        Platform.exit();
    }

    @FXML
    public void onRegisterClicked() {
        validate();
    }

    private void validate() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        User u = new User(firstNameEdit.getText(),
                lastNameEdit.getText(),
                address1Edit.getText(),
                address2Edit.getText(),
                0,
                cityEdit.getText(),
                telephoneEdit.getText(),
                emailEdit.getText(),
                creditCardNumberEdit.getText(),
                passwordEdit.getText());


        if (u.getEmail().isEmpty()) {
            alert.setContentText("email is required");
            alert.show();
            return;
        }
        if (u.getFirstName().isEmpty()) {
            alert.setContentText("first name is required");
            alert.show();
            return;
        }
        if (u.getLastName().isEmpty()) {
            alert.setContentText("last name is required");
            alert.show();
            return;
        }
        if (u.getCreditCardNumber().isEmpty()) {
            alert.setContentText("credit card number is required");
            alert.show();
            return;
        }
        if (u.getPassword().isEmpty()) {
            alert.setContentText("password is required");
            alert.show();
            return;
        }
        if (!passwordEdit.getText().equals(confirmPasswordEdit.getText())) {
            alert.setContentText("passwords dont match");
            alert.show();
            return;
        }

        int zip;
        try {
            zip = Integer.parseInt(zipEdit.getText());
        } catch (NumberFormatException e) {
            alert.setContentText("Zip code (" + zipEdit.getText() + ") is not valid");
            alert.show();
            return;
        }
        u.setZip(zip);


        DBUtil.doWithTransaction(s -> {
            Query query = s.createQuery("from User where email=:email");
            query.setParameter("email", u.getEmail());
            User user = (User) query.uniqueResult();
            if (user != null) {
                alert.setContentText("This email is already registered");
                alert.show();
                return;
            }

            Query query2 = s.createQuery("from User where firstName=:firstName and lastName=:lastName");
            query2.setParameter("firstName", u.getFirstName());
            query2.setParameter("lastName", u.getLastName());
            User user2 = (User) query2.uniqueResult();
            if (user2 != null) {
                alert.setContentText("This person is already registered");
                alert.show();
                return;
            }

            try {
                s.persist(u);
            } catch (PersistenceException e) {
                alert.setContentText("a validation error occurred");
                alert.show();
            }
            RentalApplication.showLogin();
        });
    }
}
