package com.example.seminar04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ImaginiActivity extends AppCompatActivity {

    private ArrayList<ImaginiCurcani> listaImagini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imagini);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lvImagini = findViewById(R.id.lvImagini);

        listaImagini = new ArrayList<>();
        listaImagini.add(new ImaginiCurcani(
                "Curcan Marele Alb",
                "Rasă industrială de talie mare, crescută pentru carne de calitate superioară.",
                "https://upload.wikimedia.org/wikipedia/commons/8/8a/Male_wild_turkey_%28Meleagris_gallopavo%29_strutting.jpg",
                "https://ro.wikipedia.org/wiki/Curcan"
        ));
        listaImagini.add(new ImaginiCurcani(
                "Curcan Bronzat American",
                "Rasă tradițională americană cu penaj strălucitor cu reflexe bronzate.",
                "https://upload.wikimedia.org/wikipedia/commons/9/9c/Meleagris_gallopavo_domesticus%2C_Neuss_%28DE%29_--_2022_--_0189.jpg",
                "https://en.wikipedia.org/wiki/Broad_Breasted_Bronze"
        ));
        listaImagini.add(new ImaginiCurcani(
                "Curcan Bourbon Roșu",
                "Rasă veche americană din Kentucky cu penaj maroniu-roșcat caracteristic.",
                "https://upload.wikimedia.org/wikipedia/commons/a/a8/Meleagris_gallopavo_domesticus%2C_Neuss_%28DE%29_--_2022_--_0240.jpg",
                "https://en.wikipedia.org/wiki/Bourbon_Red"
        ));
        listaImagini.add(new ImaginiCurcani(
                "Curcan Sălbatic",
                "Strămoșul curcanilor domestici, nativ din pădurile Americii de Nord.",
                "https://upload.wikimedia.org/wikipedia/commons/c/ca/Meleagris_gallopavo_qtl1.jpg",
                "https://en.wikipedia.org/wiki/Wild_turkey"
        ));
        listaImagini.add(new ImaginiCurcani(
                "Curcan Olandez Alb",
                "Rasă veche europeană cu penaj complet alb și aspect masiv.",
                "https://upload.wikimedia.org/wikipedia/commons/5/5f/Wild_turkey_crossing_the_road_by_Trustom_Pond_%2812584%29.jpg",
                "https://en.wikipedia.org/wiki/White_Holland"
        ));

        ImagineAdapter adapter = new ImagineAdapter(this, R.layout.row_imagine_curcan, listaImagini);
        lvImagini.setAdapter(adapter);

        lvImagini.setOnItemClickListener((parent, view, position, id) -> {
            ImaginiCurcani itemSelectat = listaImagini.get(position);
            Intent intent = new Intent(ImaginiActivity.this, WebViewActivity.class);
            intent.putExtra("url_web", itemSelectat.getUrlWeb());
            intent.putExtra("titlu_pagina", itemSelectat.getTitlu());
            startActivity(intent);
        });
    }
}
