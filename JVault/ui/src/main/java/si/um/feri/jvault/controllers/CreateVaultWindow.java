package si.um.feri.jvault.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import si.um.feri.jvault.controllers.helpers.UIHelper;

import java.io.File;

public class CreateVaultWindow {

    @FXML
    private TextField vaultNameField;

    @FXML
    private TextField vaultPathField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleBrowse() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Vault Location");
        File selectedDirectory = directoryChooser.showDialog(vaultNameField.getScene().getWindow());

        if (selectedDirectory != null) {
            vaultPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void handleCreateVault() {
        String vaultName = vaultNameField.getText();
        String vaultPath = vaultPathField.getText();
        String password = passwordField.getText();

        if (vaultName.isEmpty() || vaultPath.isEmpty() || password.isEmpty()) {
            UIHelper.showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR);
            return;
        }

        // vault creation logic here !!!

        UIHelper.showAlert("Success", "Vault created successfully!", Alert.AlertType.CONFIRMATION);

        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) vaultNameField.getScene().getWindow();
        UIHelper.closeWindow(stage);
    }

}
