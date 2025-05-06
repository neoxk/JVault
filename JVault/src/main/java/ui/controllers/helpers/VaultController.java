package ui.controllers.helpers;

import javafx.event.ActionEvent;

public class VaultController {

    public void handleCreateVault(ActionEvent event) {
        UIHelper.loadWindow(event, "/fxml/create-new-vault.fxml", "JVault - Create New Vault", true);
    }

    public void handleOpenVault(ActionEvent event) {
        UIHelper.loadWindow(event, "/fxml/open-vault.fxml", "JVault - Open Vault", true);
    }

    public void handleCloseVault(ActionEvent event) {
        UIHelper.loadWindow(event, "/fxml/welcome.fxml", "JVault - Welcome", true);
    }

}
