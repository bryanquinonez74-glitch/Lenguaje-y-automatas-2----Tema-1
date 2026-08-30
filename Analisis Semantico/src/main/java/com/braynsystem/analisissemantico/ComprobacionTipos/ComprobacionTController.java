package com.braynsystem.analisissemantico.ComprobacionTipos;

import com.braynsystem.analisissemantico.AnalisadorSApplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;

public class ComprobacionTController {

    @FXML
    private TextField txtExpresion;

    @FXML
    private TextArea txaResultado;


    private final AnalizadorT analizador =
            new AnalizadorT();

    // ANALIZAR
    @FXML
    private void onAnalizar() {

        txaResultado.clear();

        String expresion =
                txtExpresion.getText();

        if (expresion == null
                || expresion.isBlank()) {

            mostrarError(
                    "Escribe una expresión."
            );

            return;
        }

        try {

            String resultado =
                    analizador.analizar(
                            expresion
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

    // Boton de limpiar

    @FXML
    private void onLimpiar() {

        txtExpresion.clear();

        txaResultado.clear();

        txtExpresion.requestFocus();
    }


    // Boton de menu

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

    // MOSTRAR ERROR

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
                "Error de tipos"
        );

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }
}