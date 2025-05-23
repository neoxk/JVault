package ui.controllers;

import core.encryption.ICipher;
import core.io.EncryptedVaultIO;
import core.io.fs.FileProxy;
import core.vault.FileVault;
import core.vault.Vault;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;
import ui.controllers.helpers.UIHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AddFileWindow {

    @FXML private TextField filePathField;

    private Vault vault;

    public void setVault(Vault vault) {
        this.vault = vault;
    }

    @FXML
    void handleBrowse(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        java.io.File chosen = chooser.showOpenDialog(
                filePathField.getScene().getWindow());
        if (chosen != null) {
            filePathField.setText(chosen.getAbsolutePath());
        }
    }

    @FXML
    void handleAddFile(ActionEvent event) {
        String sourcePath   = filePathField.getText().trim();
        Path sysPath        = Paths.get(sourcePath);

        String internalPath = sysPath.getFileName().toString();

        try {
            vault.addFile(sysPath, internalPath);
            vault.save();
            UIHelper.showAlert("Success",
                    "File added successfully as “" + internalPath + "”",
                    Alert.AlertType.CONFIRMATION);
            closeWindow();
        } catch (Exception e) {
            UIHelper.showAlert("Error",
                    "Failed to add file: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }


    @FXML
    void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) filePathField.getScene().getWindow();
        UIHelper.closeWindow(stage);
    }
}
