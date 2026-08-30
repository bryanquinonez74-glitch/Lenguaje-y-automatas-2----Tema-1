package com.braynsystem.analisissemantico.AnalizadorExpresiones;

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

import javafx.scene.layout.Pane;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.scene.text.Text;

import javafx.stage.Stage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class ArbolController {

    @FXML
    private TextField txtExpresion;

    @FXML
    private Pane panelArbol;

    @FXML
    private TextArea txtResultado;

    private final AnalizadorExpresion analizador =
            new AnalizadorExpresion();

    // GENERAR ÁRBOL
    @FXML
    private void onGenerar() {

        panelArbol.getChildren().clear();
        txtResultado.clear();

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

            Nodo raiz =
                    analizador.construirArbol(
                            expresion
                    );

            double ancho =
                    panelArbol.getWidth();

            if (ancho <= 0) {
                ancho = 700;
            }

            dibujarNodo(
                    raiz,
                    ancho / 2,
                    50,
                    ancho / 4
            );

            mostrarResultado(
                    expresion,
                    raiz
            );

        } catch (Exception e) {

            mostrarError(
                    e.getMessage()
            );
        }
    }
    // DIBUJAR NODOS
    private void dibujarNodo(
            Nodo nodo,
            double x,
            double y,
            double separacion) {

        if (nodo == null) {
            return;
        }

        double siguienteY =
                y + 85;

        // HIJO IZQUIERDO
        if (nodo.izquierdo != null) {

            double hijoX =
                    x - separacion;

            Line linea =
                    new Line(
                            x,
                            y + 22,
                            hijoX,
                            siguienteY - 22
                    );

            panelArbol
                    .getChildren()
                    .add(linea);

            dibujarNodo(
                    nodo.izquierdo,
                    hijoX,
                    siguienteY,
                    separacion / 2
            );
        }


        // HIJO DERECHO
        if (nodo.derecho != null) {

            double hijoX =
                    x + separacion;

            Line linea =
                    new Line(
                            x,
                            y + 22,
                            hijoX,
                            siguienteY - 22
                    );

            panelArbol
                    .getChildren()
                    .add(linea);

            dibujarNodo(
                    nodo.derecho,
                    hijoX,
                    siguienteY,
                    separacion / 2
            );
        }


        // CÍRCULO
        Circle circulo =
                new Circle(
                        x,
                        y,
                        22
                );

        circulo.setStyle(
                "-fx-fill: white;" +
                        "-fx-stroke: black;" +
                        "-fx-stroke-width: 2;"
        );


        // TEXTO DEL NODO
        Text texto =
                new Text(
                        nodo.valor
                );

        texto.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );

        texto.setX(
                x
                        - texto
                        .getLayoutBounds()
                        .getWidth() / 2
        );

        texto.setY(
                y + 5
        );


        panelArbol
                .getChildren()
                .addAll(
                        circulo,
                        texto
                );
    }

    // MOSTRAR RESULTADO

    private void mostrarResultado(
            String expresion,
            Nodo raiz) {

        List<String> operandos =
                new ArrayList<>();

        List<String> operadores =
                new ArrayList<>();

        recorrerArbol(
                raiz,
                operandos,
                operadores
        );


        StringBuilder resultado =
                new StringBuilder();

        resultado.append(
                "Expresión analizada: "
        );

        resultado.append(
                expresion
        );

        resultado.append(
                "\n\n"
        );


        resultado.append(
                "Expresión válida ✓"
        );

        resultado.append(
                "\n\n"
        );


        resultado.append(
                "Operandos: "
        );

        resultado.append(
                String.join(
                        ", ",
                        operandos
                )
        );

        resultado.append(
                "\n"
        );


        resultado.append(
                "Operadores: "
        );

        resultado.append(
                String.join(
                        ", ",
                        operadores
                )
        );

        resultado.append(
                "\n"
        );


        resultado.append(
                "Operación principal: "
        );

        resultado.append(
                raiz.valor
        );

        resultado.append(
                "\n\n"
        );


        resultado.append(
                "Expresión totalmente agrupada:\n"
        );

        resultado.append(
                construirExpresion(
                        raiz
                )
        );


        txtResultado.setText(
                resultado.toString()
        );
    }
    // RECORRER ÁRBOL
    private void recorrerArbol(
            Nodo nodo,
            List<String> operandos,
            List<String> operadores) {

        if (nodo == null) {
            return;
        }


        if (nodo.izquierdo == null
                && nodo.derecho == null) {

            operandos.add(
                    nodo.valor
            );

        } else {

            operadores.add(
                    nodo.valor
            );
        }


        recorrerArbol(
                nodo.izquierdo,
                operandos,
                operadores
        );

        recorrerArbol(
                nodo.derecho,
                operandos,
                operadores
        );
    }
    // CONSTRUIR EXPRESIÓN
    private String construirExpresion(
            Nodo nodo) {

        if (nodo == null) {
            return "";
        }

        if (nodo.izquierdo == null
                && nodo.derecho == null) {

            return nodo.valor;
        }

        return "("
                + construirExpresion(
                nodo.izquierdo
        )
                + " "
                + nodo.valor
                + " "
                + construirExpresion(
                nodo.derecho
        )
                + ")";
    }
    // LIMPIAR
    @FXML
    private void onLimpiar() {

        txtExpresion.clear();

        txtResultado.clear();

        panelArbol
                .getChildren()
                .clear();

        txtExpresion
                .requestFocus();
    }
    // Boton para regresar al menu
    @FXML
    private void onRegresar(
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


        Scene escena =
                new Scene(root);


        ventana.setScene(
                escena
        );

        ventana.setTitle(
                "Análisis Semántico"
        );

        ventana.centerOnScreen();
    }
    // MOSTRAR ERRORES
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
                "Expresión incorrecta"
        );

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }
}