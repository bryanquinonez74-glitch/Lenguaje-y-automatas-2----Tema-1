package com.braynsystem.analisissemantico.ErroresSemanticos;

import java.util.HashMap;
import java.util.Map;

public class AnalizadorE {

    private final Map<String, String> tablaSimbolos =
            new HashMap<>();


    public String analizar(String codigo) {

        if (codigo == null || codigo.isBlank()) {

            throw new RuntimeException(
                    "No hay código para analizar."
            );
        }


        tablaSimbolos.clear();


        String[] lineas =
                codigo.split("\\r?\\n");


        StringBuilder errores =
                new StringBuilder();


        int cantidadErrores = 0;


        for (int i = 0; i < lineas.length; i++) {

            String linea =
                    lineas[i].trim();


            if (linea.isEmpty()) {
                continue;
            }


            int numeroLinea =
                    i + 1;


            try {

                analizarLinea(
                        linea
                );

            } catch (RuntimeException e) {

                cantidadErrores++;

                errores.append(
                        "Línea "
                );

                errores.append(
                        numeroLinea
                );

                errores.append(
                        ":\n"
                );

                errores.append(
                        e.getMessage()
                );

                errores.append(
                        "\n\n"
                );
            }
        }


        StringBuilder resultado =
                new StringBuilder();


        if (cantidadErrores == 0) {

            resultado.append(
                    "ANÁLISIS SEMÁNTICO COMPLETADO\n\n"
            );

            resultado.append(
                    "No se encontraron errores semánticos ✓\n\n"
            );

            resultado.append(
                    "Variables registradas:\n"
            );


            if (tablaSimbolos.isEmpty()) {

                resultado.append(
                        "Ninguna."
                );

            } else {

                for (Map.Entry<String, String> entrada
                        : tablaSimbolos.entrySet()) {

                    resultado.append(
                            entrada.getKey()
                    );

                    resultado.append(
                            " : "
                    );

                    resultado.append(
                            entrada.getValue()
                    );

                    resultado.append(
                            "\n"
                    );
                }
            }

        } else {

            resultado.append(
                    "ERRORES SEMÁNTICOS ENCONTRADOS\n\n"
            );

            resultado.append(
                    errores
            );

            resultado.append(
                    "Total de errores: "
            );

            resultado.append(
                    cantidadErrores
            );
        }


        return resultado.toString();
    }


    private void analizarLinea(
            String linea) {


        if (!linea.endsWith(";")) {

            throw new RuntimeException(
                    "La instrucción debe terminar con punto y coma (;)."
            );
        }


        linea =
                linea.substring(
                        0,
                        linea.length() - 1
                ).trim();


        if (esDeclaracion(linea)) {

            analizarDeclaracion(
                    linea
            );

            return;
        }


        if (linea.contains("=")) {

            analizarAsignacion(
                    linea
            );

            return;
        }


        throw new RuntimeException(
                "La instrucción no es válida."
        );
    }


    private boolean esDeclaracion(
            String linea) {

        return linea.startsWith("int ")
                || linea.startsWith("double ")
                || linea.startsWith("float ")
                || linea.startsWith("String ")
                || linea.startsWith("boolean ")
                || linea.startsWith("char ");
    }


    private void analizarDeclaracion(
            String linea) {

        String[] partes =
                linea.split(
                        "\\s+",
                        2
                );


        if (partes.length != 2) {

            throw new RuntimeException(
                    "Declaración incorrecta."
            );
        }


        String tipo =
                partes[0].trim();

        String resto =
                partes[1].trim();


        validarTipo(tipo);


        String variable;
        String valor = null;


        if (resto.contains("=")) {

            String[] asignacion =
                    resto.split(
                            "=",
                            2
                    );


            variable =
                    asignacion[0].trim();

            valor =
                    asignacion[1].trim();

        } else {

            variable =
                    resto;
        }


        validarVariable(
                variable
        );


        if (tablaSimbolos.containsKey(
                variable)) {

            throw new RuntimeException(
                    "La variable '"
                            + variable
                            + "' ya fue declarada."
            );
        }


        if (valor != null) {

            if (valor.isEmpty()) {

                throw new RuntimeException(
                        "Falta un valor para la variable '"
                                + variable
                                + "'."
                );
            }


            String tipoValor =
                    detectarTipoValor(
                            valor
                    );


            if (!tiposCompatibles(
                    tipo,
                    tipoValor)) {

                throw new RuntimeException(
                        "No se puede asignar "
                                + tipoValor
                                + " a una variable de tipo "
                                + tipo
                                + "."
                );
            }
        }


        tablaSimbolos.put(
                variable,
                tipo
        );
    }


    private void analizarAsignacion(
            String linea) {

        String[] partes =
                linea.split(
                        "=",
                        2
                );


        if (partes.length != 2) {

            throw new RuntimeException(
                    "Asignación incorrecta."
            );
        }


        String variable =
                partes[0].trim();

        String valor =
                partes[1].trim();


        validarVariable(
                variable
        );


        if (!tablaSimbolos.containsKey(
                variable)) {

            throw new RuntimeException(
                    "La variable '"
                            + variable
                            + "' no ha sido declarada."
            );
        }


        if (valor.isEmpty()) {

            throw new RuntimeException(
                    "Falta el valor de la asignación."
            );
        }


        String tipoVariable =
                tablaSimbolos.get(
                        variable
                );


        String tipoValor =
                detectarTipoValor(
                        valor
                );


        if (!tiposCompatibles(
                tipoVariable,
                tipoValor)) {

            throw new RuntimeException(
                    "No se puede asignar "
                            + tipoValor
                            + " a una variable de tipo "
                            + tipoVariable
                            + "."
            );
        }
    }


    private String detectarTipoValor(
            String valor) {

        valor =
                valor.trim();


        if (valor.startsWith("\"")
                && valor.endsWith("\"")) {

            return "String";
        }


        if (valor.length() == 3
                && valor.startsWith("'")
                && valor.endsWith("'")) {

            return "char";
        }


        if (valor.equals("true")
                || valor.equals("false")) {

            return "boolean";
        }


        if (valor.matches(
                "-?\\d+"
        )) {

            return "int";
        }


        if (valor.matches(
                "-?\\d+\\.\\d+"
        )) {

            return "double";
        }


        // VARIABLE EXISTENTE
        if (valor.matches(
                "[a-zA-Z_][a-zA-Z0-9_]*"
        )) {

            if (!tablaSimbolos.containsKey(
                    valor)) {

                throw new RuntimeException(
                        "La variable '"
                                + valor
                                + "' no ha sido declarada."
                );
            }


            return tablaSimbolos.get(
                    valor
            );
        }


        throw new RuntimeException(
                "No se pudo determinar el tipo del valor: "
                        + valor
        );
    }


    private boolean tiposCompatibles(
            String destino,
            String origen) {

        if (destino.equals(
                origen)) {

            return true;
        }


        if (destino.equals("double")
                && origen.equals("int")) {

            return true;
        }


        if (destino.equals("float")
                && origen.equals("int")) {

            return true;
        }


        return false;
    }


    private void validarTipo(
            String tipo) {

        if (!tipo.equals("int")
                && !tipo.equals("double")
                && !tipo.equals("float")
                && !tipo.equals("String")
                && !tipo.equals("boolean")
                && !tipo.equals("char")) {

            throw new RuntimeException(
                    "Tipo de dato desconocido: "
                            + tipo
            );
        }
    }


    private void validarVariable(
            String variable) {

        if (variable == null
                || variable.isBlank()) {

            throw new RuntimeException(
                    "El nombre de la variable está vacío."
            );
        }


        if (!variable.matches(
                "[a-zA-Z_][a-zA-Z0-9_]*"
        )) {

            throw new RuntimeException(
                    "Nombre de variable inválido: "
                            + variable
            );
        }
    }
}