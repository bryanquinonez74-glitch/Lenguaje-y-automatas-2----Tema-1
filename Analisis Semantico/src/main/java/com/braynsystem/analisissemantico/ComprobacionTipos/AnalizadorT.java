package com.braynsystem.analisissemantico.ComprobacionTipos;

public class AnalizadorT {

    public String analizar(String instruccion) {

        if (instruccion == null || instruccion.isBlank()) {
            throw new RuntimeException(
                    "La instrucción está vacía."
            );
        }

        instruccion = instruccion.trim();

        if (!instruccion.endsWith(";")) {
            throw new RuntimeException(
                    "La instrucción debe terminar con punto y coma (;)."
            );
        }

        instruccion = instruccion.substring(
                0,
                instruccion.length() - 1
        ).trim();

        if (!instruccion.contains("=")) {
            throw new RuntimeException(
                    "Debe existir una asignación con =."
            );
        }

        String[] partesAsignacion =
                instruccion.split("=", 2);

        if (partesAsignacion.length != 2) {
            throw new RuntimeException(
                    "La asignación no es válida."
            );
        }

        String ladoIzquierdo =
                partesAsignacion[0].trim();

        String expresion =
                partesAsignacion[1].trim();

        if (expresion.isEmpty()) {
            throw new RuntimeException(
                    "Falta el valor o expresión."
            );
        }

        String[] declaracion =
                ladoIzquierdo.split("\\s+");

        if (declaracion.length != 2) {
            throw new RuntimeException(
                    "La declaración debe tener tipo y variable."
            );
        }

        String tipoDeclarado =
                declaracion[0];

        String variable =
                declaracion[1];

        validarTipo(
                tipoDeclarado
        );

        validarVariable(
                variable
        );

        String tipoExpresion =
                detectarTipoExpresion(
                        expresion
                );

        boolean compatible =
                tiposCompatibles(
                        tipoDeclarado,
                        tipoExpresion
                );

        StringBuilder resultado =
                new StringBuilder();

        resultado.append(
                "COMPROBACIÓN DE TIPOS\n\n"
        );

        resultado.append(
                "Variable: "
        );

        resultado.append(
                variable
        );

        resultado.append("\n");

        resultado.append(
                "Tipo declarado: "
        );

        resultado.append(
                tipoDeclarado
        );

        resultado.append("\n");

        resultado.append(
                "Expresión: "
        );

        resultado.append(
                expresion
        );

        resultado.append("\n");

        resultado.append(
                "Tipo detectado: "
        );

        resultado.append(
                tipoExpresion
        );

        resultado.append("\n\n");

        if (compatible) {

            resultado.append(
                    "Compatibilidad: CORRECTA ✓\n\n"
            );

            resultado.append(
                    "La asignación es semánticamente válida."
            );

        } else {

            resultado.append(
                    "Compatibilidad: ERROR ✗\n\n"
            );

            resultado.append(
                    "Error semántico:\n"
            );

            resultado.append(
                    "No se puede asignar un valor de tipo "
            );

            resultado.append(
                    tipoExpresion
            );

            resultado.append(
                    " a una variable de tipo "
            );

            resultado.append(
                    tipoDeclarado
            );

            resultado.append(".");
        }

        return resultado.toString();
    }


    private String detectarTipoExpresion(
            String expresion) {

        expresion =
                expresion.trim();


        // STRING
        if (expresion.startsWith("\"")
                && expresion.endsWith("\"")) {

            return "String";
        }


        // CHAR
        if (expresion.startsWith("'")
                && expresion.endsWith("'")
                && expresion.length() == 3) {

            return "char";
        }


        // BOOLEAN
        if (expresion.equals("true")
                || expresion.equals("false")) {

            return "boolean";
        }


        // SUMA DE STRINGS
        if (expresion.contains("\"")) {

            if (soloConcatenacionString(
                    expresion
            )) {

                return "String";
            }

            return "incompatible";
        }


        // EXPRESIÓN NUMÉRICA
        if (esExpresionNumerica(
                expresion
        )) {

            if (expresion.contains(".")) {

                return "double";
            }

            return "int";
        }


        throw new RuntimeException(
                "No se pudo determinar el tipo de la expresión."
        );
    }


    private boolean soloConcatenacionString(
            String expresion) {

        String[] partes =
                expresion.split("\\+");

        if (partes.length < 2) {
            return false;
        }

        for (String parte : partes) {

            parte = parte.trim();

            if (!(parte.startsWith("\"")
                    && parte.endsWith("\""))) {

                return false;
            }
        }

        return true;
    }


    private boolean esExpresionNumerica(
            String expresion) {

        expresion =
                expresion.replace(" ", "");

        return expresion.matches(
                "[0-9.+\\-*/()]+"
        );
    }


    private boolean tiposCompatibles(
            String declarado,
            String detectado) {

        if (declarado.equals(
                detectado
        )) {

            return true;
        }


        // int puede convertirse a double
        if (declarado.equals("double")
                && detectado.equals("int")) {

            return true;
        }


        // int puede convertirse a float
        if (declarado.equals("float")
                && detectado.equals("int")) {

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
                    "Tipo de dato no reconocido: "
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
                    "Nombre de variable no válido: "
                            + variable
            );
        }
    }
}