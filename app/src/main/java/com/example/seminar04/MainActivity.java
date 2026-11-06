package com.example.seminar04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;
    private ActivityResultLauncher<Intent> launcherLista;
    private ArrayList<Curcani> listaCurcani = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Curcani curcanPrimit = result.getData().getParcelableExtra("obiect_curcan");
                        if (curcanPrimit != null) {
                            listaCurcani.add(curcanPrimit);
                            Toast.makeText(MainActivity.this, "Curcan adăugat!\n" + curcanPrimit.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        launcherLista = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ArrayList<Curcani> listaActualizata = result.getData().getParcelableArrayListExtra("lista_curcani_actualizata");
                        if (listaActualizata != null) {
                            listaCurcani = listaActualizata;
                        }
                    }
                }
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void culegeDate(View view) {
        Intent intent = new Intent(MainActivity.this, FormularActivity.class);
        launcher.launch(intent);
    }

    public void culegeDateAi(View view) {
        Intent intent = new Intent(MainActivity.this, FormularAiActivity.class);
        launcher.launch(intent);
    }

    public void afiseazaLista(View view) {
        Intent intent = new Intent(MainActivity.this, ListaActivity.class);
        intent.putParcelableArrayListExtra("lista_curcani", listaCurcani);
        launcherLista.launch(intent);
    }

    public void deschideActivitateNoua(View view) {
        Intent intent = new Intent(MainActivity.this, ActivitateNoua.class);
        startActivity(intent);
    }
}
