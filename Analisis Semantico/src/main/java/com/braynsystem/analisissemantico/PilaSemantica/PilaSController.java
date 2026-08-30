package com.braynsystem.analisissemantico.PilaSemantica;

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

public class PilaSController {

    @FXML
    private TextField txtExprecion;

    @FXML
    private TextArea txaPila;

    @FXML
    private TextArea txaResultado;


    private final AnalizadorP analizador =
            new AnalizadorP();

    // Proceso de pila

    @FXML
    private void onProsesar() {

        txaPila.clear();
        txaResultado.clear();

        String expresion =
                txtExprecion.getText();

        if (expresion == null
                || expresion.isBlank()) {

            mostrarError(
                    "Escribe una expresión."
            );

            return;
        }

        try {

            String proceso =
                    analizador.procesar(
                            expresion
                    );

            txaPila.setText(
                    proceso
            );

            txaResultado.setText(
                    obtenerResultadoFinal(
                            proceso
                    )
            );

        } catch (Exception e) {

            mostrarError(
                    e.getMessage()
            );
        }
    }

    // Da el resultado

    private String obtenerResultadoFinal(
            String proceso) {

        int posicion =
                proceso.lastIndexOf(
                        "Expresión final:"
                );

        if (posicion == -1) {

            return "No se pudo obtener el resultado.";
        }

        String resultado =
                proceso.substring(
                        posicion
                );

        return ""
                + "PILA SEMÁNTICA\n\n"
                + "Expresión procesada correctamente ✓\n\n"
                + resultado;
    }


    // =========================
    // Limpiar tex area

    @FXML
    private void onLimpiar() {

        txtExprecion.clear();

        txaPila.clear();

        txaResultado.clear();

        txtExprecion.requestFocus();
    }

    // Boton de regresar al menu

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
                "Error en la pila semántica"
        );

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }
}