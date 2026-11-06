package com.example.seminar04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private ArrayList<Curcani> listaCurcani;
    private CurcanAdapter adapter;
    private ActivityResultLauncher<Intent> editLauncher;

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
            listaCurcani = intent.getParcelableArrayListExtra("lista_curcani");
        }

        if (listaCurcani == null) {
            listaCurcani = new ArrayList<>();
        }

        // Cerința 2: Adapter personalizat (CurcanAdapter cu row_curcan.xml)
        adapter = new CurcanAdapter(this, R.layout.row_curcan, listaCurcani);
        lvCurcani.setAdapter(adapter);

        // Launcher pentru primirea modificărilor din FormularActivity
        editLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Curcani curcanModificat = result.getData().getParcelableExtra("obiect_curcan");
                        int pozitie = result.getData().getIntExtra("pozitie", -1);

                        if (curcanModificat != null && pozitie != -1 && pozitie < listaCurcani.size()) {
                            // Modificăm obiectul selectat pe poziția primită, fără a adăuga unul nou
                            listaCurcani.set(pozitie, curcanModificat);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(ListaActivity.this, "Curcan modificat cu succes!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // Cerința 3: La ItemClick se deschide activitatea de introducere date cu informațiile obiectului selectat
        lvCurcani.setOnItemClickListener((parent, view, position, id) -> {
            Curcani curcanDeEditat = listaCurcani.get(position);
            Intent editIntent = new Intent(ListaActivity.this, FormularActivity.class);
            editIntent.putExtra("obiect_editat", curcanDeEditat);
            editIntent.putExtra("pozitie", position);
            editLauncher.launch(editIntent);
        });

        // Eveniment de LongItemClick pentru ștergere
        lvCurcani.setOnItemLongClickListener((parent, view, position, id) -> {
            Curcani curcanSters = listaCurcani.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(ListaActivity.this, "Șters: " + curcanSters.getRasa(), Toast.LENGTH_SHORT).show();

            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("lista_curcani_actualizata", listaCurcani);
            setResult(RESULT_OK, resultIntent);

            return true;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent resultIntent = new Intent();
                resultIntent.putParcelableArrayListExtra("lista_curcani_actualizata", listaCurcani);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
