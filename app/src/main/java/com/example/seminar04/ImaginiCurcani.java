package com.example.seminar04;

import android.os.Parcel;
import android.os.Parcelable;

public class ImaginiCurcani implements Parcelable {
    private String titlu;
    private String descriere;
    private String urlImagine;
    private String urlWeb;

    public ImaginiCurcani(String titlu, String descriere, String urlImagine, String urlWeb) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.urlImagine = urlImagine;
        this.urlWeb = urlWeb;
    }

    protected ImaginiCurcani(Parcel in) {
        titlu = in.readString();
        descriere = in.readString();
        urlImagine = in.readString();
        urlWeb = in.readString();
    }

    public static final Creator<ImaginiCurcani> CREATOR = new Creator<ImaginiCurcani>() {
        @Override
        public ImaginiCurcani createFromParcel(Parcel in) {
            return new ImaginiCurcani(in);
        }

        @Override
        public ImaginiCurcani[] newArray(int size) {
            return new ImaginiCurcani[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titlu);
        dest.writeString(descriere);
        dest.writeString(urlImagine);
        dest.writeString(urlWeb);
    }

    public String getTitlu() {
        return titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getUrlImagine() {
        return urlImagine;
    }

    public String getUrlWeb() {
        return urlWeb;
    }
}
