package com.braynsystem.analisissemantico.EsquemaTraduccion;

import java.util.Stack;

public class AnalizadorT {

    public ResultadoTraduccion traducir(String expresion) {

        if (expresion == null || expresion.isBlank()) {
            throw new RuntimeException(
                    "La expresión está vacía."
            );
        }

        expresion = expresion.replace(" ", "");

        Stack<Character> operadores = new Stack<>();

        StringBuilder postfija = new StringBuilder();
        StringBuilder proceso = new StringBuilder();

        int i = 0;

        proceso.append("EXPRESIÓN INFIJA: ")
                .append(expresion)
                .append("\n\n");

        proceso.append("PROCESO DE TRADUCCIÓN\n\n");

        while (i < expresion.length()) {

            char c = expresion.charAt(i);

            // =========================
            // OPERANDOS
            // =========================
            if (Character.isLetterOrDigit(c)) {

                StringBuilder operando =
                        new StringBuilder();

                while (i < expresion.length()
                        && Character.isLetterOrDigit(
                        expresion.charAt(i))) {

                    operando.append(
                            expresion.charAt(i)
                    );

                    i++;
                }

                postfija.append(
                        operando
                );

                proceso.append(
                        "Lee operando: "
                );

                proceso.append(
                        operando
                );

                proceso.append("\n");

                proceso.append(
                        "Salida: "
                );

                proceso.append(
                        postfija
                );

                proceso.append("\n");

                proceso.append(
                        "Pila: "
                );

                proceso.append(
                        operadores
                );

                proceso.append(
                        "\n\n"
                );

                continue;
            }


            // =========================
            // PARÉNTESIS (
            // =========================
            if (c == '(') {

                operadores.push(c);

                proceso.append(
                        "Lee: (\n"
                );

                proceso.append(
                        "Acción: PUSH (\n"
                );

                proceso.append(
                        "Pila: "
                );

                proceso.append(
                        operadores
                );

                proceso.append(
                        "\n\n"
                );
            }


            // =========================
            // PARÉNTESIS )
            // =========================
            else if (c == ')') {

                proceso.append(
                        "Lee: )\n"
                );

                while (!operadores.isEmpty()
                        && operadores.peek() != '(') {

                    char operador =
                            operadores.pop();

                    postfija.append(
                            operador
                    );

                    proceso.append(
                            "POP "
                    );

                    proceso.append(
                            operador
                    );

                    proceso.append(
                            " → salida\n"
                    );
                }

                if (operadores.isEmpty()) {

                    throw new RuntimeException(
                            "Los paréntesis están incorrectos."
                    );
                }

                operadores.pop();

                proceso.append(
                        "Se elimina el paréntesis (\n"
                );

                proceso.append(
                        "Salida: "
                );

                proceso.append(
                        postfija
                );

                proceso.append("\n");

                proceso.append(
                        "Pila: "
                );

                proceso.append(
                        operadores
                );

                proceso.append(
                        "\n\n"
                );
            }


            // =========================
            // OPERADORES
            // =========================
            else if (esOperador(c)) {

                proceso.append(
                        "Lee operador: "
                );

                proceso.append(c);

                proceso.append("\n");

                while (!operadores.isEmpty()
                        && operadores.peek() != '('
                        && prioridad(
                        operadores.peek()
                ) >= prioridad(c)) {

                    char operador =
                            operadores.pop();

                    postfija.append(
                            operador
                    );

                    proceso.append(
                            "POP "
                    );

                    proceso.append(
                            operador
                    );

                    proceso.append(
                            " → salida\n"
                    );
                }

                operadores.push(c);

                proceso.append(
                        "PUSH "
                );

                proceso.append(c);

                proceso.append("\n");

                proceso.append(
                        "Salida: "
                );

                proceso.append(
                        postfija
                );

                proceso.append("\n");

                proceso.append(
                        "Pila: "
                );

                proceso.append(
                        operadores
                );

                proceso.append(
                        "\n\n"
                );
            }


            // =========================
            // CARÁCTER INVÁLIDO
            // =========================
            else {

                throw new RuntimeException(
                        "Carácter no permitido: "
                                + c
                );
            }

            i++;
        }


        // =========================
        // VACIAR PILA
        // =========================
        proceso.append(
                "FIN DE LA EXPRESIÓN\n\n"
        );

        while (!operadores.isEmpty()) {

            if (operadores.peek() == '(') {

                throw new RuntimeException(
                        "Falta cerrar un paréntesis."
                );
            }

            char operador =
                    operadores.pop();

            postfija.append(
                    operador
            );

            proceso.append(
                    "POP "
            );

            proceso.append(
                    operador
            );

            proceso.append(
                    " → salida\n"
            );
        }

        proceso.append(
                "\nPOSTFIJA FINAL: "
        );

        proceso.append(
                postfija
        );


        return new ResultadoTraduccion(
                expresion,
                postfija.toString(),
                proceso.toString()
        );
    }


    private boolean esOperador(char c) {

        return c == '+'
                || c == '-'
                || c == '*'
                || c == '/';
    }


    private int prioridad(char operador) {

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


    // =========================
    // RESULTADO
    // =========================
    public static class ResultadoTraduccion {

        private final String infija;
        private final String postfija;
        private final String proceso;

        public ResultadoTraduccion(
                String infija,
                String postfija,
                String proceso) {

            this.infija = infija;
            this.postfija = postfija;
            this.proceso = proceso;
        }

        public String getInfija() {
            return infija;
        }

        public String getPostfija() {
            return postfija;
        }

        public String getProceso() {
            return proceso;
        }
    }
}