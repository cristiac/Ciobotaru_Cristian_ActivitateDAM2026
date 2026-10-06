package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
        } catch (Exception e) {
           Log.e("eroare", "eroare");
        }
    }

    @Override
    protected void onResume() {
        try{
            super.onResume();
        } catch (Exception e) {
            Log.w("warning", "avertizare");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("d","d");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("info", "informare");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("verizium", "ver");
    }

}