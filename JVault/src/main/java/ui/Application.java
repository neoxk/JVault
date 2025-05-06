package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/fxml/welcome.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 580);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/fxml/img/icon.png"))));
        stage.setTitle("JVault - Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}