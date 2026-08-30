package com.braynsystem.analisissemantico.AnalizadorExpresiones;

import java.util.Stack;

public class AnalizadorExpresion {

    public Nodo construirArbol(String expresion) {

        if (expresion == null) {
            throw new RuntimeException("La expresión está vacía.");
        }

        expresion = expresion.replace(" ", "");

        if (expresion.isEmpty()) {
            throw new RuntimeException("La expresión está vacía.");
        }

        Stack<Nodo> operandos = new Stack<>();
        Stack<Character> operadores = new Stack<>();

        int i = 0;

        while (i < expresion.length()) {

            char c = expresion.charAt(i);

            // LETRAS O NÚMEROS
            if (Character.isLetterOrDigit(c)) {

                StringBuilder valor = new StringBuilder();

                while (i < expresion.length()
                        && Character.isLetterOrDigit(expresion.charAt(i))) {

                    valor.append(expresion.charAt(i));
                    i++;
                }

                operandos.push(new Nodo(valor.toString()));
                continue;
            }

            // PARÉNTESIS DE APERTURA
            if (c == '(') {

                operadores.push(c);

            }

            // PARÉNTESIS DE CIERRE
            else if (c == ')') {

                while (!operadores.isEmpty()
                        && operadores.peek() != '(') {

                    procesarOperador(
                            operandos,
                            operadores
                    );
                }

                if (operadores.isEmpty()) {
                    throw new RuntimeException(
                            "Los paréntesis están incorrectos."
                    );
                }

                operadores.pop();
            }

            // OPERADORES
            else if (esOperador(c)) {

                while (!operadores.isEmpty()
                        && operadores.peek() != '('
                        && prioridad(operadores.peek()) >= prioridad(c)) {

                    procesarOperador(
                            operandos,
                            operadores
                    );
                }

                operadores.push(c);
            }

            // CARÁCTER NO PERMITIDO
            else {

                throw new RuntimeException(
                        "Carácter no permitido: " + c
                );
            }

            i++;
        }

        // PROCESAR OPERADORES RESTANTES
        while (!operadores.isEmpty()) {

            if (operadores.peek() == '(') {
                throw new RuntimeException(
                        "Falta cerrar un paréntesis."
                );
            }

            procesarOperador(
                    operandos,
                    operadores
            );
        }

        if (operandos.size() != 1) {
            throw new RuntimeException(
                    "La expresión no es válida."
            );
        }

        return operandos.pop();
    }

    private void procesarOperador(
            Stack<Nodo> operandos,
            Stack<Character> operadores) {

        if (operandos.size() < 2) {
            throw new RuntimeException(
                    "La expresión no es válida."
            );
        }

        char operador = operadores.pop();

        Nodo derecho = operandos.pop();
        Nodo izquierdo = operandos.pop();

        Nodo nuevo = new Nodo(
                String.valueOf(operador),
                izquierdo,
                derecho
        );

        operandos.push(nuevo);
    }

    private boolean esOperador(char c) {

        return c == '+'
                || c == '-'
                || c == '*'
                || c == '/';
    }

    private int prioridad(char operador) {

        if (operador == '+' || operador == '-') {
            return 1;
        }

        if (operador == '*' || operador == '/') {
            return 2;
        }

        return 0;
    }
}