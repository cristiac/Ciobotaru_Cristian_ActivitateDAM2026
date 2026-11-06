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

    private int pozitieModificata = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formular);

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

        // Cerința 3: Verificare dacă s-a transmis un obiect pentru modificare
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("obiect_editat")) {
            Curcani curcanDeEditat = intent.getParcelableExtra("obiect_editat");
            pozitieModificata = intent.getIntExtra("pozitie", -1);

            if (curcanDeEditat != null) {
                etRasa.setText(curcanDeEditat.getRasa());
                checkBox.setChecked(curcanDeEditat.isEsteMatur());

                for (int i = 0; i < optiuniHrana.length; i++) {
                    if (optiuniHrana[i].equalsIgnoreCase(curcanDeEditat.getTipHrana())) {
                        spHrana.setSelection(i);
                        break;
                    }
                }

                rbMasculin.setChecked(curcanDeEditat.isEsteMasculin());
                rbSanatate.setRating(curcanDeEditat.getNotaSanatate());
                swEsteVaccinat.setChecked(curcanDeEditat.isEsteVaccinat());

                btnSalveaza.setText("Salvează Modificările");
            }
        }

        btnSalveaza.setOnClickListener(v -> {
            String rasa = etRasa.getText().toString();
            boolean esteMatur = checkBox.isChecked();
            String tipHrana = spHrana.getSelectedItem() != null ? spHrana.getSelectedItem().toString() : "";
            boolean esteMasculin = rbMasculin.isChecked();
            float notaSanatate = rbSanatate.getRating();
            boolean esteVaccinat = swEsteVaccinat.isChecked();

            Curcani curcanRezultat = new Curcani(rasa, esteMatur, tipHrana, esteMasculin, notaSanatate, esteVaccinat);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("obiect_curcan", curcanRezultat);
            if (pozitieModificata != -1) {
                returnIntent.putExtra("pozitie", pozitieModificata);
            }
            setResult(RESULT_OK, returnIntent);

            finish();
        });
    }
}
