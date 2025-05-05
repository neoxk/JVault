package si.um.feri.jvault.controllers.helpers;

import javafx.event.ActionEvent;

public class FileController {

    public void handleAddFile(ActionEvent event) {
        UIHelper.loadWindow(event, "/si/um/feri/jvault/fxml/add-file.fxml", "JVault - Add File", false);
    }

    public void handleEditFile(ActionEvent event) {
        UIHelper.loadWindow(event, "/si/um/feri/jvault/fxml/edit-file.fxml", "JVault - Edit File", false);
    }

    public void handleOpenVault(ActionEvent event) {
        UIHelper.loadWindow(event, "/si/um/feri/jvault/fxml/open-vault.fxml", "JVault - Open Vault", false);
    }

    public void handleCloseVault(ActionEvent event) {
        UIHelper.loadWindow(event, "/si/um/feri/jvault/fxml/welcome.fxml", "JVault - Welcome", true);
    }

}
