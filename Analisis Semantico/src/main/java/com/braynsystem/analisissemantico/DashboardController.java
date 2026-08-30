package com.braynsystem.analisissemantico;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DashboardController {

    @FXML
    private StackPane panelContenido;


    @FXML
    public void initialize() {

        mostrarInicio();
    }


    private void cargarVista(String archivo) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            AnalisadorSApplication.class
                                    .getResource(archivo)
                    );

            Parent vista =
                    loader.load();

            panelContenido
                    .getChildren()
                    .clear();

            panelContenido
                    .getChildren()
                    .add(vista);

        } catch (IOException e) {

            mostrarError(
                    "No se pudo cargar la pantalla:\n"
                            + archivo
                            + "\n\n"
                            + e.getMessage()
            );
        }
    }


    @FXML
    private void onMenu() {

        mostrarInicio();
    }


    private void mostrarInicio() {

        panelContenido
                .getChildren()
                .clear();

        javafx.scene.control.Label bienvenida =
                new javafx.scene.control.Label(
                        "ANÁLISIS SEMÁNTICO\n\n"
                                + "Selecciona un tema del menú lateral."
                );

        bienvenida.setStyle(
                "-fx-font-size: 22px;"
                        + "-fx-font-weight: bold;"
        );

        panelContenido
                .getChildren()
                .add(bienvenida);
    }


    // 1.1
    @FXML
    private void onAE() {

        cargarVista(
                "ArbolExp.fxml"
        );
    }


    // 1.2
    @FXML
    private void onAS() {

        cargarVista(
                "AccionesS.fxml"
        );
    }


    // 1.3
    @FXML
    private void onCDT() {

        cargarVista(
                "CDT.fxml"
        );
    }


    // 1.4
    @FXML
    private void onPS() {

        cargarVista(
                "PS.fxml"
        );
    }


    // 1.5
    @FXML
    private void onEDT() {

        cargarVista(
                "EDT.fxml"
        );
    }


    // 1.6
    @FXML
    private void onTS() {

        cargarVista(
                "TS.fxml"
        );
    }


    // 1.7
    @FXML
    private void onES() {

        cargarVista(
                "ES.fxml"
        );
    }


    private void mostrarError(String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alerta.setTitle(
                "Error"
        );

        alerta.setHeaderText(
                "No se pudo cargar la vista"
        );

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }
}