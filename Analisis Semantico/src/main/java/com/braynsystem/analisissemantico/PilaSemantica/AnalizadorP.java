package com.braynsystem.analisissemantico.PilaSemantica;

import java.util.Stack;

public class AnalizadorP {

    public String procesar(String expresion) {

        if (expresion == null || expresion.isBlank()) {
            throw new RuntimeException(
                    "La expresión está vacía."
            );
        }

        expresion = expresion.replace(" ", "");

        Stack<String> operandos = new Stack<>();
        Stack<Character> operadores = new Stack<>();

        StringBuilder proceso = new StringBuilder();

        int i = 0;

        while (i < expresion.length()) {

            char c = expresion.charAt(i);

            // LETRAS O NÚMEROS
            if (Character.isLetterOrDigit(c)) {

                StringBuilder valor =
                        new StringBuilder();

                while (i < expresion.length()
                        && Character.isLetterOrDigit(
                        expresion.charAt(i))) {

                    valor.append(
                            expresion.charAt(i)
                    );

                    i++;
                }

                operandos.push(
                        valor.toString()
                );

                proceso.append(
                        "PUSH "
                );

                proceso.append(
                        valor
                );

                proceso.append(
                        "\n"
                );

                proceso.append(
                        "Pila de operandos: "
                );

                proceso.append(
                        operandos
                );

                proceso.append(
                        "\n\n"
                );

                continue;
            }


            // PARÉNTESIS (
            if (c == '(') {

                operadores.push(c);

                proceso.append(
                        "PUSH operador: (\n"
                );

                proceso.append(
                        "Pila de operadores: "
                );

                proceso.append(
                        operadores
                );

                proceso.append(
                        "\n\n"
                );
            }


            // PARÉNTESIS )
            else if (c == ')') {

                while (!operadores.isEmpty()
                        && operadores.peek() != '(') {

                    procesarOperacion(
                            operandos,
                            operadores,
                            proceso
                    );
                }

                if (operadores.isEmpty()) {

                    throw new RuntimeException(
                            "Paréntesis incorrectos."
                    );
                }

                operadores.pop();

                proceso.append(
                        "POP paréntesis (\n\n"
                );
            }


            // OPERADORES
            else if (esOperador(c)) {

                while (!operadores.isEmpty()
                        && operadores.peek() != '('
                        && prioridad(
                        operadores.peek()
                ) >= prioridad(c)) {

                    procesarOperacion(
                            operandos,
                            operadores,
                            proceso
                    );
                }

                operadores.push(c);

                proceso.append(
                        "PUSH operador: "
                );

                proceso.append(c);

                proceso.append("\n");

                proceso.append(
                        "Pila de operadores: "
                );

                proceso.append(
                        operadores
                );

                proceso.append(
                        "\n\n"
                );
            }


            else {

                throw new RuntimeException(
                        "Carácter no permitido: "
                                + c
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

            procesarOperacion(
                    operandos,
                    operadores,
                    proceso
            );
        }


        if (operandos.size() != 1) {

            throw new RuntimeException(
                    "La expresión no es válida."
            );
        }


        proceso.append(
                "RESULTADO FINAL\n"
        );

        proceso.append(
                "Pila final: "
        );

        proceso.append(
                operandos
        );

        proceso.append(
                "\n"
        );

        proceso.append(
                "Expresión final: "
        );

        proceso.append(
                operandos.peek()
        );


        return proceso.toString();
    }


    private void procesarOperacion(
            Stack<String> operandos,
            Stack<Character> operadores,
            StringBuilder proceso) {

        if (operandos.size() < 2) {

            throw new RuntimeException(
                    "Expresión incorrecta."
            );
        }

        char operador =
                operadores.pop();

        String derecho =
                operandos.pop();

        String izquierdo =
                operandos.pop();


        proceso.append(
                "POP operandos: "
        );

        proceso.append(
                izquierdo
        );

        proceso.append(
                " y "
        );

        proceso.append(
                derecho
        );

        proceso.append("\n");


        proceso.append(
                "POP operador: "
        );

        proceso.append(
                operador
        );

        proceso.append("\n");


        String resultado =
                "("
                        + izquierdo
                        + " "
                        + operador
                        + " "
                        + derecho
                        + ")";


        operandos.push(
                resultado
        );


        proceso.append(
                "PUSH resultado: "
        );

        proceso.append(
                resultado
        );

        proceso.append("\n");


        proceso.append(
                "Pila de operandos: "
        );

        proceso.append(
                operandos
        );

        proceso.append(
                "\n\n"
        );
    }


    private boolean esOperador(
            char c) {

        return c == '+'
                || c == '-'
                || c == '*'
                || c == '/';
    }


    private int prioridad(
            char operador) {

        if (operador == '+'
                || operador == '-') {

            return 1;
        }

        if (operador == '*'
                || operador == '/') {

            return 2;
        }

        return 0;
    }
}