package com.braynsystem.analisissemantico.ErroresSemanticos;

import com.braynsystem.analisissemantico.AnalisadorSApplication;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import javafx.stage.Stage;

import java.io.IOException;

public class ErroresSController {

    @FXML
    private TextArea txaCodigo;

    @FXML
    private TextArea txaResultado;


    private final AnalizadorE analizador =
            new AnalizadorE();



    // Boton de analizar

    @FXML
    private void onAnalizar() {

        txaResultado.clear();


        String codigo =
                txaCodigo.getText();


        if (codigo == null
                || codigo.isBlank()) {

            mostrarError(
                    "Escribe el código que deseas analizar."
            );

            return;
        }


        try {

            String resultado =
                    analizador.analizar(
                            codigo
                    );


            txaResultado.setText(
                    resultado
            );

        } catch (Exception e) {

            mostrarError(
                    e.getMessage()
            );
        }
    }

    // boton limpiar

    @FXML
    private void onLimpiar() {

        txaCodigo.clear();

        txaResultado.clear();

        txaCodigo.requestFocus();
    }

    // Boton regresar al menu

    @FXML
    private void onMenu(
            ActionEvent event)
            throws IOException {


        FXMLLoader loader =
                new FXMLLoader(
                        AnalisadorSApplication.class
                                .getResource(
                                        "Analisis sematico.fxml"
                                )
                );


        Parent root =
                loader.load();


        Stage ventana =
                (Stage)
                        ((Node)
                                event.getSource())
                                .getScene()
                                .getWindow();


        ventana.setScene(
                new Scene(root)
        );


        ventana.setTitle(
                "Análisis Semántico"
        );


        ventana.centerOnScreen();
    }


    // ==============================
    // ERROR
    // ==============================
    private void mostrarError(
            String mensaje) {


        Alert alerta =
                new Alert(
                        Alert.AlertType.ERROR
                );


        alerta.setTitle(
                "Error"
        );


        alerta.setHeaderText(
                "Error durante el análisis semántico"
        );


        alerta.setContentText(
                mensaje
        );


        alerta.showAndWait();
    }
}