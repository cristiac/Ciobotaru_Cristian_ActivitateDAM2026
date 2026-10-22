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

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        Curcani curcanPrimit = (Curcani) result.getData().getSerializableExtra("obiect_curcan");

                        if (curcanPrimit != null) {

                            Toast.makeText(MainActivity.this, curcanPrimit.toString(), Toast.LENGTH_LONG).show();
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
}