package com.example.seminar04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private ArrayList<Curcani> listaCurcani;
    private ArrayAdapter<Curcani> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lvCurcani = findViewById(R.id.lvCurcani);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("lista_curcani")) {
            listaCurcani = (ArrayList<Curcani>) intent.getSerializableExtra("lista_curcani");
        }

        if (listaCurcani == null) {
            listaCurcani = new ArrayList<>();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCurcani);
        lvCurcani.setAdapter(adapter);

        // Cerința 3: Click pe un element -> afișare prin Toast
        lvCurcani.setOnItemClickListener((parent, view, position, id) -> {
            Curcani curcanSelectat = listaCurcani.get(position);
            Toast.makeText(ListaActivity.this, "Selectat: " + curcanSelectat.toString(), Toast.LENGTH_SHORT).show();
        });

        // Cerința 4: Long click pe un element -> ștergere din ListView și din listă
        lvCurcani.setOnItemLongClickListener((parent, view, position, id) -> {
            Curcani curcanSters = listaCurcani.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(ListaActivity.this, "Șters: " + curcanSters.getRasa(), Toast.LENGTH_SHORT).show();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("lista_curcani_actualizata", listaCurcani);
            setResult(RESULT_OK, resultIntent);

            return true;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("lista_curcani_actualizata", listaCurcani);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
