package si.um.feri.jvault.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import si.um.feri.jvault.controllers.helpers.UIHelper;

import java.io.File;

public class AddFileWindow {

    @FXML
    private TextField fileNameField;

    @FXML
    private TextField filePathField;

    @FXML
    void handleAddFile(ActionEvent event) {
        String fileName = fileNameField.getText();
        String filePath = filePathField.getText();

        // Basic validation checks
        if (fileName.isEmpty() || filePath.isEmpty()) {
            UIHelper.showAlert("Error", "Please fill in both the file name and file path.", Alert.AlertType.ERROR);
            return;
        }

        // file addition logic here !!!
        UIHelper.showAlert("Success", "File added successfully!", Alert.AlertType.CONFIRMATION);

        closeWindow();
    }

    @FXML
    void handleBrowse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(fileNameField.getScene().getWindow());

        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) fileNameField.getScene().getWindow();
        UIHelper.closeWindow(stage);
    }


}
