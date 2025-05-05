package ui.controllers;

import core.encryption.Password;
import core.vault.Vault;
import core.vault.VaultFactory;
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
import ui.controllers.helpers.UIHelper;
import ui.controllers.MainWindow;  // import your main controller

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OpenVaultWindow {

    @FXML private TextField vaultPathField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleBrowse(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Vault Location");
        File dir = chooser.showDialog(vaultPathField.getScene().getWindow());
        if (dir != null) {
            vaultPathField.setText(dir.getAbsolutePath());
        }
    }

    @FXML
    private void handleOpenVault(ActionEvent event) {
        String vaultPath = vaultPathField.getText().trim();
        String pw        = passwordField.getText();

        if (vaultPath.isEmpty() || pw.isEmpty()) {
            UIHelper.showAlert("Error",
                    "Please fill in all fields.",
                    Alert.AlertType.ERROR);
            return;
        }

        // 1) open the existing vault
        Vault vault = new VaultFactory()
                .open(Paths.get(vaultPath), new Password(pw));

        // 2) load MainWindow
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/main-view.fxml"));
            Parent root = loader.load();

            // 3) inject vault
            MainWindow mainCtrl = loader.getController();
            mainCtrl.setVault(vault);

            // 4) close this dialog & show main UI
            Stage openStage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();
            openStage.close();

            Stage mainStage = new Stage();
            mainStage.setScene(new Scene(root));
            mainStage.setTitle("JVault – Vault");
            mainStage.show();

        } catch (IOException e) {
            UIHelper.showAlert("Error",
                    "Failed to load vault view:\n" + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        ((Stage) vaultPathField.getScene().getWindow()).close();
    }
}
