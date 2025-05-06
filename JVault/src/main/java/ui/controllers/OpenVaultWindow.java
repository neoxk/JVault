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
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.controllers.helpers.UIHelper;
import ui.controllers.MainWindow;  // import your main controller

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class OpenVaultWindow {

    @FXML private TextField vaultPathField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleBrowse(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File chosen = chooser.showOpenDialog(vaultPathField.getScene().getWindow());
        if (chosen != null) {
            vaultPathField.setText(chosen.getAbsolutePath());
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

        Vault vault = null;

        // 1) open the existing vault
        try {
            vault = new VaultFactory()
                    .open(Paths.get(vaultPath), new Password(pw));
        } catch (IllegalArgumentException e) {
            UIHelper.showAlert("Error", "Incorrect vault path or password.\nFailed to open the vault: " + e.getMessage(), Alert.AlertType.ERROR);
        }

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
            mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/fxml/img/icon.png"))));
            mainStage.setTitle("JVault – Vault");
            mainStage.show();

        } catch (IOException | IllegalArgumentException e) {
            UIHelper.showAlert("Error", "Failed to open the vault:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage currentStage = (Stage) vaultPathField.getScene().getWindow();
        currentStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Parent root = loader.load();

            Stage welcomeStage = new Stage();
            welcomeStage.setScene(new Scene(root));
            welcomeStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/fxml/img/icon.png"))));
            welcomeStage.setTitle("JVault - Welcome");
            welcomeStage.show();

        } catch (IOException e) {
            UIHelper.showAlert("Error", "Failed to load Welcome Window:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
