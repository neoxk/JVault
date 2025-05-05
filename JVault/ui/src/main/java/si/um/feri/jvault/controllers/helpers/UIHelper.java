package si.um.feri.jvault.controllers.helpers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;


public class UIHelper {

    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void closeWindow(Stage stage) {
        stage.close();
    }

    public static void loadWindow(ActionEvent event, String fxmlPath, String title, boolean closeCurrent) {
        try {
            FXMLLoader loader = new FXMLLoader(UIHelper.class.getResource(fxmlPath));
            Parent root = loader.load();

            if (closeCurrent) {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            }

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(title);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load window: " + title, e);
        }
    }

}
