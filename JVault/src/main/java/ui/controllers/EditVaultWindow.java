package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.controllers.helpers.UIHelper;

public class EditVaultWindow {

    @FXML
    private TextField vaultNameField;

    @FXML
    private TextField vaultPathField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    void handleBrowse(ActionEvent event) {

    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeWindow();
    }

    @FXML
    void handleSaveChanges(ActionEvent event) {
        String vaultName = vaultNameField.getText();
        String vaultPath = vaultPathField.getText();
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();

        if (vaultName.isEmpty() || vaultPath.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty()) {
            UIHelper.showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR);
            return;
        }

        // vault update logic here !!!
        UIHelper.showAlert("Success", "Vault updated successfully!", Alert.AlertType.CONFIRMATION);

        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) vaultNameField.getScene().getWindow();
        UIHelper.closeWindow(stage);
    }

}
