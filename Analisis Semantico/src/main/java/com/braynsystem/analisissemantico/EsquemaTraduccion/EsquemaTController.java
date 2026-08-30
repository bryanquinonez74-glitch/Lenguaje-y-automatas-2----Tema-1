package com.braynsystem.analisissemantico.EsquemaTraduccion;

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

public class EsquemaTController {

    @FXML
    private TextField txtExpresion;

    @FXML
    private TextArea txaPT;

    @FXML
    private TextArea txaResultado;


    private final AnalizadorT analizador =
            new AnalizadorT();


    // TRADUCIR

    @FXML
    private void onIniciar() {

        txaPT.clear();
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

            AnalizadorT.ResultadoTraduccion resultado =
                    analizador.traducir(
                            expresion
                    );


            txaPT.setText(
                    resultado.getProceso()
            );


            StringBuilder salida =
                    new StringBuilder();

            salida.append(
                    "ESQUEMA DE TRADUCCIÓN\n\n"
            );

            salida.append(
                    "Expresión infija:\n"
            );

            salida.append(
                    resultado.getInfija()
            );

            salida.append(
                    "\n\n"
            );

            salida.append(
                    "Expresión postfija:\n"
            );

            salida.append(
                    resultado.getPostfija()
            );

            salida.append(
                    "\n\n"
            );

            salida.append(
                    "Traducción realizada correctamente ✓"
            );


            txaResultado.setText(
                    salida.toString()
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

        txtExpresion.clear();

        txaPT.clear();

        txaResultado.clear();

        txtExpresion.requestFocus();
    }



    // boton de menu

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

    // ERROR

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
                "Error en el esquema de traducción"
        );

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }
}