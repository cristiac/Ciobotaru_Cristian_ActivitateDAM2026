package com.example.seminar04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class FormularAiActivity extends AppCompatActivity {

    private final String[] raseAi = {"Marele Alb", "Bronzat American", "Olandez Alb", "Bourbon Roșu"};
    private final String[] optiuniHrana = {"Grau Bio", "Porumb Premium", "Mix Concentrat AI", "Iarba Proaspata"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formular_ai);

        EditText etRasa = findViewById(R.id.etRasaAi);
        CheckBox cbMatur = findViewById(R.id.cbMaturAi);
        Spinner spHrana = findViewById(R.id.spHranaAi);
        RadioButton rbMasculin = findViewById(R.id.rbMasculinAi);
        RatingBar rbSanatate = findViewById(R.id.rbSanatateAi);
        Switch swVaccinat = findViewById(R.id.swVaccinatAi);
        Button btnSalveaza = findViewById(R.id.btnSalveazaAi);
        Button btnGenerareAi = findViewById(R.id.btnGenerareAi);
        TextView tvAiStatus = findViewById(R.id.tvAiStatus);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, optiuniHrana);
        spHrana.setAdapter(adapter);


        btnGenerareAi.setOnClickListener(v -> {
            Random random = new Random();
            etRasa.setText(raseAi[random.nextInt(raseAi.length)]);
            cbMatur.setChecked(random.nextBoolean());
            spHrana.setSelection(random.nextInt(optiuniHrana.length));
            rbMasculin.setChecked(random.nextBoolean());
            rbSanatate.setRating(random.nextInt(3) + 3); // 3, 4 sau 5 stele
            swVaccinat.setChecked(true);

            tvAiStatus.setText("Status AI: Preset generat cu succes! 🎯");
            Toast.makeText(this, "AI-ul a completat formularul!", Toast.LENGTH_SHORT).show();
        });


        rbSanatate.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (rating >= 4.0) {
                tvAiStatus.setText("Status AI: Exemplar de Top 🌟 (Scor Excelent)");
            } else if (rating >= 2.5) {
                tvAiStatus.setText("Status AI: Exemplar Mediu 📊 (Necesită atenție)");
            } else {
                tvAiStatus.setText("Status AI: Stare Critică ⚠️ (Control recomandat)");
            }
        });

        btnSalveaza.setOnClickListener(v -> {
            String rasa = etRasa.getText().toString();
            boolean esteMatur = cbMatur.isChecked();
            String tipHrana = spHrana.getSelectedItem() != null ? spHrana.getSelectedItem().toString() : "";
            boolean esteMasculin = rbMasculin.isChecked();
            float notaSanatate = rbSanatate.getRating();
            boolean esteVaccinat = swVaccinat.isChecked();

            Curcani curcanNou = new Curcani(rasa, esteMatur, tipHrana, esteMasculin, notaSanatate, esteVaccinat);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("obiect_curcan", curcanNou);
            setResult(RESULT_OK, returnIntent);
            finish();
        });
    }
}