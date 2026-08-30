package com.braynsystem.analisissemantico.AnalizadorExpresiones;

public class Nodo {

    String valor;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(String valor) {
        this.valor = valor;
    }

    public Nodo(String valor, Nodo izquierdo, Nodo derecho) {
        this.valor = valor;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }
}