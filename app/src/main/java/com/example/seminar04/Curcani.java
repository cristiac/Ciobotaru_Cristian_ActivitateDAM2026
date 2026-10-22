package com.example.seminar04;

import java.io.Serializable;

public class Curcani implements Serializable {
    private String rasa;
    private boolean esteMatur;
    private String tipHrana;
    private boolean esteMasculin;
    private float notaSanatate;
    private boolean esteVaccinat;

    public Curcani(String rasa, boolean esteMatur, String tipHrana, boolean esteMasculin, float notaSanatate, boolean esteVaccinat) {
        this.rasa = rasa;
        this.esteMatur = esteMatur;
        this.tipHrana = tipHrana;
        this.esteMasculin = esteMasculin;
        this.notaSanatate = notaSanatate;
        this.esteVaccinat = esteVaccinat;
    }

    @Override
    public String toString() {
        return "Rasă: " + rasa +
                " | Matur: " + (esteMatur ? "Da" : "Nu") +
                " | Hrană: " + tipHrana +
                " | Mascul: " + (esteMasculin ? "Da" : "Nu") +
                " | Sănătate: " + notaSanatate + "/5" +
                " | Vaccinat: " + (esteVaccinat ? "Da" : "Nu");
    }
}