package si.um.feri.jvault.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import si.um.feri.jvault.controllers.helpers.VaultController;

public class WelcomeWindow {

    private final VaultController vaultController = new VaultController();

    @FXML
    public void handleCreateVault(ActionEvent event) {
        vaultController.handleCreateVault(event);
    }

    @FXML
    public void handleOpenVault(ActionEvent event) {
        vaultController.handleOpenVault(event);
    }

}
