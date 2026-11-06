package com.example.seminar04;

import android.os.Parcel;
import android.os.Parcelable;

public class Curcani implements Parcelable {
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

    protected Curcani(Parcel in) {
        rasa = in.readString();
        esteMatur = in.readByte() != 0;
        tipHrana = in.readString();
        esteMasculin = in.readByte() != 0;
        notaSanatate = in.readFloat();
        esteVaccinat = in.readByte() != 0;
    }

    public static final Creator<Curcani> CREATOR = new Creator<Curcani>() {
        @Override
        public Curcani createFromParcel(Parcel in) {
            return new Curcani(in);
        }

        @Override
        public Curcani[] newArray(int size) {
            return new Curcani[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rasa);
        dest.writeByte((byte) (esteMatur ? 1 : 0));
        dest.writeString(tipHrana);
        dest.writeByte((byte) (esteMasculin ? 1 : 0));
        dest.writeFloat(notaSanatate);
        dest.writeByte((byte) (esteVaccinat ? 1 : 0));
    }

    public String getRasa() {
        return rasa;
    }

    public void setRasa(String rasa) {
        this.rasa = rasa;
    }

    public boolean isEsteMatur() {
        return esteMatur;
    }

    public void setEsteMatur(boolean esteMatur) {
        this.esteMatur = esteMatur;
    }

    public String getTipHrana() {
        return tipHrana;
    }

    public void setTipHrana(String tipHrana) {
        this.tipHrana = tipHrana;
    }

    public boolean isEsteMasculin() {
        return esteMasculin;
    }

    public void setEsteMasculin(boolean esteMasculin) {
        this.esteMasculin = esteMasculin;
    }

    public float getNotaSanatate() {
        return notaSanatate;
    }

    public void setNotaSanatate(float notaSanatate) {
        this.notaSanatate = notaSanatate;
    }

    public boolean isEsteVaccinat() {
        return esteVaccinat;
    }

    public void setEsteVaccinat(boolean esteVaccinat) {
        this.esteVaccinat = esteVaccinat;
    }

    @Override
    public String toString() {
        return "Rasă: " + rasa +
                " | Matur: " + (esteMatur ? "Da" : "Nu") +
                " | Hrană: " + tipHrana +
                " | Gen: " + (esteMasculin ? "Mascul" : "Femelă") +
                " | Sănătate: " + notaSanatate + "/5" +
                " | Vaccinat: " + (esteVaccinat ? "Da" : "Nu");
    }
}
