package com.braynsystem.analisissemantico;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AnalisadorSApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                AnalisadorSApplication.class.getResource("Dashboard.fxml")
        );
        Scene scene = new Scene(
                fxmlLoader.load(),
                750,
                750
        );
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        AnalisadorSApplication.class.getResource("estilos.css")
                ).toExternalForm()
        );
        stage.setTitle("Análisis Semántico");
        stage.setScene(scene);
        stage.setMinWidth(750);
        stage.setMinHeight(750);

        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}