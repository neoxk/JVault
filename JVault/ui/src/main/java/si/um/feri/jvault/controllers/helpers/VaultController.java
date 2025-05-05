package si.um.feri.jvault.controllers.helpers;

import javafx.event.ActionEvent;

public class VaultController {

    public void handleCreateVault(ActionEvent event) {
        UIHelper.loadWindow(event, "/si/um/feri/jvault/fxml/create-new-vault.fxml", "JVault - Create New Vault", true);
    }

    public void handleEditVault(ActionEvent event) {
        UIHelper.loadWindow(event, "/si/um/feri/jvault/fxml/vault-properties.fxml", "JVault - Edit Vault", false);
    }

    public void handleOpenVault(ActionEvent event) {
        UIHelper.loadWindow(event, "/si/um/feri/jvault/fxml/open-vault.fxml", "JVault - Open Vault", true);
    }

    public void handleCloseVault(ActionEvent event) {
        UIHelper.loadWindow(event, "/si/um/feri/jvault/fxml/welcome.fxml", "JVault - Welcome", true);
    }

}
