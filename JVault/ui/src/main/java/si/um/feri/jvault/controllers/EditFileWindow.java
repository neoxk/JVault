package si.um.feri.jvault.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import si.um.feri.jvault.controllers.helpers.UIHelper;

public class EditFileWindow {

    @FXML
    private TextField fileNameField;

    @FXML
    void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) fileNameField.getScene().getWindow();
        UIHelper.closeWindow(stage);
    }

    @FXML
    void handleSaveChanges(ActionEvent event) {
        String fileName = fileNameField.getText();

        if (fileName.isEmpty()) {
            UIHelper.showAlert("Error", "Please fill in necessary field.", Alert.AlertType.ERROR);
            return;
        }

        // file update logic here !!!
        UIHelper.showAlert("Success", "File updated successfully!", Alert.AlertType.CONFIRMATION);

        closeWindow();
    }

}
