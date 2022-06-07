package com.example.zcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Ajuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button2 = findViewById(R.id.back); // botão de voltar
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(Ajuda.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
