package si.um.feri.jvault.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

import si.um.feri.jvault.controllers.helpers.FileController;
import si.um.feri.jvault.controllers.helpers.VaultController;
import si.um.feri.jvault.managers.FileManager;

import si.um.feri.jvault.controllers.helpers.UIHelper;

public class MainWindow {

    @FXML
    private Label successStatus;

    @FXML
    private Label errorStatus;

    @FXML
    private Font x3;

    @FXML
    private TableView<FileManager> tableView;

    private final VaultController vaultController = new VaultController();
    private final FileController fileController = new FileController();
    private final ObservableList<FileManager> data = FXCollections.observableArrayList();

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

        // sample data
        addFileEntry(new FileManager("MyFile.txt", "C:/Users/User/Documents", "C:", "15 KB"));
        addFileEntry(new FileManager("Project.zip", "D:/Projects", "D:", "150 MB"));
    }

    public void addFileEntry(FileManager file) {
        data.add(file);
    }

    @FXML
    void handleAddFile(ActionEvent event) {
        fileController.handleAddFile(event);
    }

    @FXML
    void handleRemoveFile(ActionEvent event) {
        FileManager selectedFile = tableView.getSelectionModel().getSelectedItem();

        if (selectedFile != null) {
            data.remove(selectedFile);
        } else {
            UIHelper.showAlert("No File Selected", "Please select a file to remove.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void handleEditFile(ActionEvent event) {
        fileController.handleEditFile(event);
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