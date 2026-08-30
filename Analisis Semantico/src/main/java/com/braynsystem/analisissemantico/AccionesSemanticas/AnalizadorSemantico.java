package com.braynsystem.analisissemantico.AccionesSemanticas;

public class AnalizadorSemantico {

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

        // Quitamos el ;
        instruccion = instruccion.substring(
                0,
                instruccion.length() - 1
        ).trim();


        // DECLARACIONES
        if (esDeclaracion(instruccion)) {

            return analizarDeclaracion(
                    instruccion
            );
        }


        // ASIGNACIONES
        if (instruccion.contains("=")) {

            return analizarAsignacion(
                    instruccion
            );
        }


        throw new RuntimeException(
                "No se reconoce la instrucción."
        );
    }


    private boolean esDeclaracion(
            String instruccion) {

        return instruccion.startsWith("int ")
                || instruccion.startsWith("double ")
                || instruccion.startsWith("float ")
                || instruccion.startsWith("String ")
                || instruccion.startsWith("boolean ")
                || instruccion.startsWith("char ");
    }


    private String analizarDeclaracion(
            String instruccion) {

        String tipo;

        if (instruccion.startsWith("int ")) {
            tipo = "int";
        } else if (instruccion.startsWith("double ")) {
            tipo = "double";
        } else if (instruccion.startsWith("float ")) {
            tipo = "float";
        } else if (instruccion.startsWith("String ")) {
            tipo = "String";
        } else if (instruccion.startsWith("boolean ")) {
            tipo = "boolean";
        } else if (instruccion.startsWith("char ")) {
            tipo = "char";
        } else {
            throw new RuntimeException(
                    "Tipo de dato no válido."
            );
        }


        String resto =
                instruccion.substring(
                        tipo.length()
                ).trim();


        if (!resto.contains("=")) {

            String variable = resto;

            validarVariable(variable);

            return ""
                    + "Tipo de instrucción: DECLARACIÓN\n\n"
                    + "Tipo de dato: " + tipo + "\n"
                    + "Variable: " + variable + "\n"
                    + "Valor inicial: Sin asignar\n\n"
                    + "Acción semántica realizada:\n"
                    + "Registrar la variable en la tabla de símbolos.";
        }


        String[] partes =
                resto.split("=", 2);

        if (partes.length != 2) {
            throw new RuntimeException(
                    "Declaración incorrecta."
            );
        }


        String variable =
                partes[0].trim();

        String valor =
                partes[1].trim();


        validarVariable(
                variable
        );

        if (valor.isEmpty()) {
            throw new RuntimeException(
                    "Falta el valor de la variable."
            );
        }


        return ""
                + "Tipo de instrucción: DECLARACIÓN\n\n"
                + "Tipo de dato: " + tipo + "\n"
                + "Variable: " + variable + "\n"
                + "Valor: " + valor + "\n\n"
                + "Acciones semánticas realizadas:\n"
                + "1. Reconocer el tipo de dato.\n"
                + "2. Reconocer el identificador.\n"
                + "3. Registrar la variable.\n"
                + "4. Asociar el valor con la variable.\n"
                + "5. Agregar la información a la tabla de símbolos.";
    }


    private String analizarAsignacion(
            String instruccion) {

        String[] partes =
                instruccion.split("=", 2);


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


        if (valor.isEmpty()) {

            throw new RuntimeException(
                    "Falta el valor de la asignación."
            );
        }


        return ""
                + "Tipo de instrucción: ASIGNACIÓN\n\n"
                + "Variable: " + variable + "\n"
                + "Nuevo valor: " + valor + "\n\n"
                + "Acciones semánticas realizadas:\n"
                + "1. Buscar la variable en la tabla de símbolos.\n"
                + "2. Analizar el nuevo valor.\n"
                + "3. Verificar la compatibilidad del valor.\n"
                + "4. Actualizar el valor de la variable.";
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
                    "Nombre de variable no válido: "
                            + variable
            );
        }
    }
}