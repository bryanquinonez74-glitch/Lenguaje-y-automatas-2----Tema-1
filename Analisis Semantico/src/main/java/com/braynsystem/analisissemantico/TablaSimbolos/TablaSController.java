package com.braynsystem.analisissemantico.TablaSimbolos;

import com.braynsystem.analisissemantico.AnalisadorSApplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TablaSController {

    @FXML
    private TextArea txaCodigo;

    @FXML
    private TableView<Simbolo> tablaSimbolos;

    @FXML
    private TableColumn<Simbolo, String>
            colVariable;

    @FXML
    private TableColumn<Simbolo, String>
            colTipo;

    @FXML
    private TableColumn<Simbolo, String>
            colValor;

    @FXML
    private TableColumn<Simbolo, Integer>
            colDireccion;

    @FXML
    private TextArea txaResultado;


    private final AnalizadorS analizador =
            new AnalizadorS();


    @FXML
    public void initialize() {

        colVariable.setCellValueFactory(
                new PropertyValueFactory<>(
                        "variable"
                )
        );

        colTipo.setCellValueFactory(
                new PropertyValueFactory<>(
                        "tipo"
                )
        );

        colValor.setCellValueFactory(
                new PropertyValueFactory<>(
                        "valor"
                )
        );

        colDireccion.setCellValueFactory(
                new PropertyValueFactory<>(
                        "direccion"
                )
        );

        txaResultado.setEditable(false);
    }


    @FXML
    private void onIniciar() {

        tablaSimbolos
                .getItems()
                .clear();

        txaResultado.clear();

        String codigo =
                txaCodigo.getText();

        if (codigo == null
                || codigo.isBlank()) {

            mostrarError(
                    "Escribe una o más declaraciones."
            );

            return;
        }

        try {

            List<Simbolo> lista =
                    analizador.analizar(
                            codigo
                    );

            ObservableList<Simbolo>
                    datos =
                    FXCollections.observableArrayList(
                            lista
                    );

            tablaSimbolos.setItems(
                    datos
            );

            StringBuilder resultado =
                    new StringBuilder();

            resultado.append(
                    "TABLA GENERADA CORRECTAMENTE ✓\n\n"
            );

            resultado.append(
                    "Símbolos encontrados: "
            );

            resultado.append(
                    lista.size()
            );

            resultado.append(
                    "\n\n"
            );

            resultado.append(
                    "Direcciones asignadas:\n"
            );

            for (Simbolo simbolo : lista) {

                resultado.append(
                        simbolo.getVariable()
                );

                resultado.append(
                        " → "
                );

                resultado.append(
                        simbolo.getDireccion()
                );

                resultado.append(
                        "\n"
                );
            }

            txaResultado.setText(
                    resultado.toString()
            );

        } catch (Exception e) {

            mostrarError(
                    e.getMessage()
            );
        }
    }


    @FXML
    private void onLimpiar() {

        txaCodigo.clear();

        txaResultado.clear();

        tablaSimbolos
                .getItems()
                .clear();

        txaCodigo.requestFocus();
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
                "Error en la tabla de símbolos"
        );

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }
}