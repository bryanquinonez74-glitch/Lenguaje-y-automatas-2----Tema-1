package com.braynsystem.analisissemantico.TablaSimbolos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnalizadorS {

    private int direccionActual = 1000;

    public List<Simbolo> analizar(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            throw new RuntimeException(
                    "No hay código para analizar."
            );
        }

        direccionActual = 1000;

        List<Simbolo> simbolos =
                new ArrayList<>();

        Set<String> variables =
                new HashSet<>();

        String[] instrucciones =
                codigo.split(";");

        for (String instruccion : instrucciones) {

            instruccion =
                    instruccion.trim();

            if (instruccion.isEmpty()) {
                continue;
            }

            Simbolo simbolo =
                    analizarDeclaracion(
                            instruccion
                    );

            if (variables.contains(
                    simbolo.getVariable())) {

                throw new RuntimeException(
                        "La variable '"
                                + simbolo.getVariable()
                                + "' ya fue declarada."
                );
            }

            variables.add(
                    simbolo.getVariable()
            );

            simbolos.add(
                    simbolo
            );
        }

        if (simbolos.isEmpty()) {
            throw new RuntimeException(
                    "No se encontraron declaraciones válidas."
            );
        }

        return simbolos;
    }

    private Simbolo analizarDeclaracion(
            String instruccion) {

        String[] partes =
                instruccion.split(
                        "\\s+",
                        2
                );

        if (partes.length != 2) {
            throw new RuntimeException(
                    "Declaración incorrecta: "
                            + instruccion
            );
        }

        String tipo =
                partes[0].trim();

        validarTipo(tipo);

        String resto =
                partes[1].trim();

        String variable;
        String valor;

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

            if (valor.isEmpty()) {
                throw new RuntimeException(
                        "Falta el valor de "
                                + variable
                );
            }

        } else {

            variable =
                    resto.trim();

            valor =
                    "Sin asignar";
        }

        validarVariable(variable);

        validarValor(
                tipo,
                valor
        );

        int direccion =
                direccionActual;

        direccionActual +=
                tamanioTipo(tipo);

        return new Simbolo(
                variable,
                tipo,
                limpiarValor(valor),
                direccion
        );
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
                    "Tipo no reconocido: "
                            + tipo
            );
        }
    }

    private void validarVariable(
            String variable) {

        if (!variable.matches(
                "[a-zA-Z_][a-zA-Z0-9_]*"
        )) {

            throw new RuntimeException(
                    "Nombre de variable inválido: "
                            + variable
            );
        }
    }

    private void validarValor(
            String tipo,
            String valor) {

        if (valor.equals(
                "Sin asignar")) {
            return;
        }

        switch (tipo) {

            case "int":

                if (!valor.matches(
                        "-?\\d+"
                )) {

                    throw new RuntimeException(
                            "El valor "
                                    + valor
                                    + " no es compatible con int."
                    );
                }

                break;

            case "double":
            case "float":

                if (!valor.matches(
                        "-?\\d+(\\.\\d+)?"
                )) {

                    throw new RuntimeException(
                            "El valor "
                                    + valor
                                    + " no es numérico."
                    );
                }

                break;

            case "String":

                if (!(valor.startsWith("\"")
                        && valor.endsWith("\""))) {

                    throw new RuntimeException(
                            "Un String debe estar entre comillas."
                    );
                }

                break;

            case "char":

                if (!(valor.length() == 3
                        && valor.startsWith("'")
                        && valor.endsWith("'"))) {

                    throw new RuntimeException(
                            "Un char debe escribirse, por ejemplo: 'A'"
                    );
                }

                break;

            case "boolean":

                if (!valor.equals("true")
                        && !valor.equals("false")) {

                    throw new RuntimeException(
                            "Un boolean solo puede ser true o false."
                    );
                }

                break;
        }
    }

    private int tamanioTipo(
            String tipo) {

        return switch (tipo) {

            case "int" -> 4;

            case "float" -> 4;

            case "double" -> 8;

            case "char" -> 2;

            case "boolean" -> 1;

            case "String" -> 8;

            default -> 4;
        };
    }

    private String limpiarValor(
            String valor) {

        if (valor.startsWith("\"")
                && valor.endsWith("\"")) {

            return valor.substring(
                    1,
                    valor.length() - 1
            );
        }

        if (valor.startsWith("'")
                && valor.endsWith("'")) {

            return valor.substring(
                    1,
                    valor.length() - 1
            );
        }

        return valor;
    }
}