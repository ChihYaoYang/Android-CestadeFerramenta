package com.example.senac.cestadeferramenta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);
    }

    public void irParaCalculo(View V) {
        Intent i = new Intent(this, Calculo.class);
        startActivity(i);
    }

    public void irParaConversao(View V) {
        Intent i = new Intent(this, Conversao.class);
        startActivity(i);
    }
    public  void irParaSobre(View V) {
        Intent i = new Intent(this, Sobre.class);
        startActivity(i);
    }
}
