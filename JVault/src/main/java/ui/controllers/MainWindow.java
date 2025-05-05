package ui.controllers;

import core.vault.Vault;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

import javafx.stage.Stage;
import lombok.Setter;
import ui.controllers.helpers.FileController;
import ui.controllers.helpers.VaultController;
import ui.managers.FileManager;

import ui.controllers.helpers.UIHelper;

import java.io.IOException;
import java.nio.file.Paths;

public class MainWindow {

    @FXML
    private Label successStatus;

    @FXML
    private Label errorStatus;

    @FXML
    private Font x3;

    @FXML
    private TableView<FileManager> tableView;

    private Vault vault;

    private final VaultController vaultController = new VaultController();
    private final FileController fileController = new FileController();
    private final ObservableList<FileManager> data = FXCollections.observableArrayList();

    public void setVault(Vault vault) {
        this.vault = vault;
        loadFileList();
    }

    private void loadFileList() {
        data.clear();
        for (String path : vault.getPaths()) {
            data.add(new FileManager(path, /*location=*/"", /*size=*/""));
        }
    }

    @FXML
    private void initialize() {
        @SuppressWarnings("unchecked")
        TableColumn<FileManager, String> nameCol = (TableColumn<FileManager, String>) tableView.getColumns().get(0);
        @SuppressWarnings("unchecked")
        TableColumn<FileManager, String> locationCol = (TableColumn<FileManager, String>) tableView.getColumns().get(1);
        @SuppressWarnings("unchecked")
        TableColumn<FileManager, String> driveCol = (TableColumn<FileManager, String>) tableView.getColumns().get(2);
        @SuppressWarnings("unchecked")
        TableColumn<FileManager, String> sizeCol = (TableColumn<FileManager, String>) tableView.getColumns().get(3);


        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        driveCol.setCellValueFactory(new PropertyValueFactory<>("drive"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));

        tableView.setItems(data);
    }

    public void addFileEntry(FileManager file) {
        data.add(file);
    }

    @FXML
    void handleAddFile(ActionEvent event) {
        if (vault == null) {
            UIHelper.showAlert("Error", "No vault open.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/add-file.fxml"));
            Parent root = loader.load();

            AddFileWindow ctrl = loader.getController();
            ctrl.setVault(vault);

            Stage dialog = new Stage();
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.setTitle("JVault – Add File");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

            loadFileList();

        } catch (IOException e) {
            UIHelper.showAlert("Error",
                    "Cannot open Add File dialog:\n" + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }


    @FXML
    void handleRemoveFile(ActionEvent event) {
        FileManager sel = tableView.getSelectionModel().getSelectedItem();
        if (sel == null) {
            UIHelper.showAlert("No File Selected",
                    "Please select a file to remove.", Alert.AlertType.WARNING);
            return;
        }
        vault.removeFile(Paths.get(sel.getLocation()));
        vault.save();
        loadFileList();
    }

    @FXML
    void handleEditFile(ActionEvent event) {
        fileController.handleDecryptFile(event);
    }

    @FXML
    void handleCreateVault(ActionEvent event) {
        vaultController.handleCreateVault(event);
    }

    @FXML
    void handleEditVault(ActionEvent event) {
        vaultController.handleEditVault(event);
    }

    @FXML
    void handleCloseVault(ActionEvent event) {
        vaultController.handleCloseVault(event);
    }

    @FXML
    void handleQuit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

}