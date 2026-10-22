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
import androidx.appcompat.app.AppCompatActivity;

public class FormularActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formular);

        // Legăm elementele din interfață
        EditText etRasa = findViewById(R.id.etRasa);
        CheckBox checkBox = findViewById(R.id.checkBox);
        Spinner spHrana = findViewById(R.id.spHrana);
        RadioButton rbMasculin = findViewById(R.id.rbMasculin);
        RatingBar rbSanatate = findViewById(R.id.rbSanatate);
        Switch swEsteVaccinat = findViewById(R.id.swEsteVaccinat);
        Button btnSalveaza = findViewById(R.id.btnSalveaza);

        String[] optiuniHrana = {"Grau", "Porumb", "Mix Concentrat", "Iarba"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, optiuniHrana);
        spHrana.setAdapter(adapter);

        btnSalveaza.setOnClickListener(v -> {
            String rasa = etRasa.getText().toString();
            boolean esteMatur = checkBox.isChecked();
            String tipHrana = spHrana.getSelectedItem() != null ? spHrana.getSelectedItem().toString() : "";
            boolean esteMasculin = rbMasculin.isChecked();
            float notaSanatate = rbSanatate.getRating();
            boolean esteVaccinat = swEsteVaccinat.isChecked();

            Curcani curcanNou = new Curcani(rasa, esteMatur, tipHrana, esteMasculin, notaSanatate, esteVaccinat);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("obiect_curcan", curcanNou);
            setResult(RESULT_OK, returnIntent);

            finish();
        });
    }
}