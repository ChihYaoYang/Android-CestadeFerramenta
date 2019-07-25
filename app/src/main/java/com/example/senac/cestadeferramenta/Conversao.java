package com.example.senac.cestadeferramenta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Conversao extends AppCompatActivity {
    EditText editValor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversao);

        editValor = findViewById(R.id.editValor);
    }

    public void conversao(View V) {
        String valor = editValor.getText().toString();
    }
}
