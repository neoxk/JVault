package si.um.feri.jvault.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import si.um.feri.jvault.controllers.helpers.UIHelper;

import java.io.File;
import java.io.IOException;

public class OpenVaultWindow {

    @FXML
    private TextField vaultPathField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void handleBrowse(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Vault Location");
        File selectedDirectory = directoryChooser.showDialog(vaultPathField.getScene().getWindow());

        if (selectedDirectory != null) {
            vaultPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    void handleOpenVault(ActionEvent event) {
        String vaultName = vaultPathField.getText();
        String password = passwordField.getText();

        if (vaultName.isEmpty() || password.isEmpty()) {
            UIHelper.showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR);
            return;
        }

        // vault open logic here !!!
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/si/um/feri/jvault/fxml/main-view.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("JVault - Vault");
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        closeWindow();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) vaultPathField.getScene().getWindow();
        UIHelper.closeWindow(stage);
    }


}
