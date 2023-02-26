package com.example.mp9uf3.Multicast;

public class Palabras {


    String[] palabras = {"mesa", "silla", "mochila", "portatil"};
    String palabra;
    public Palabras() {
    }

    public String[] getPalabras() {
        return palabras;
    }

    public String cogerPalabras() {
        this.palabra = palabras[(int)(Math.random()* palabras.length)];
        return palabra;
    }

}
