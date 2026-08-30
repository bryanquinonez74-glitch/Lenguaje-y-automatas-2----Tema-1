package com.braynsystem.analisissemantico.AccionesSemanticas;

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

public class AccionesController {

    @FXML
    private TextField txtInstruccion;

    @FXML
    private TextArea txtResultado;


    private final AnalizadorSemantico analizador =
            new AnalizadorSemantico();


    @FXML
    private void onAnalizar() {

        txtResultado.clear();

        String instruccion =
                txtInstruccion.getText();


        if (instruccion == null
                || instruccion.isBlank()) {

            mostrarError(
                    "Escribe una instrucción."
            );

            return;
        }


        try {

            String resultado =
                    analizador.analizar(
                            instruccion
                    );

            txtResultado.setText(
                    resultado
            );

        } catch (Exception e) {

            mostrarError(
                    e.getMessage()
            );
        }
    }


    @FXML
    private void onLimpiar() {

        txtInstruccion.clear();

        txtResultado.clear();

        txtInstruccion.requestFocus();
    }


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
                "Error en la instrucción"
        );

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }
}