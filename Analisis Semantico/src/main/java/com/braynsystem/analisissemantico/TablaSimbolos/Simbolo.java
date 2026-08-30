package com.braynsystem.analisissemantico.TablaSimbolos;

public class Simbolo {

    private String variable;
    private String tipo;
    private String valor;
    private int direccion;

    public Simbolo(
            String variable,
            String tipo,
            String valor,
            int direccion) {

        this.variable = variable;
        this.tipo = tipo;
        this.valor = valor;
        this.direccion = direccion;
    }

    public String getVariable() {
        return variable;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
}