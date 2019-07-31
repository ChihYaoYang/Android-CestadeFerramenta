package com.example.senac.cestadeferramenta.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.senac.cestadeferramenta.R;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);
    }
    //Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aplicativo, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sobre:
                //abrir tela de sobre
                Intent i = new Intent(this, Sobre.class);
                startActivity(i);
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void irParaCalculo(View V) {
        Intent i = new Intent(this, Calculo.class);
        startActivity(i);
    }

    public void irParaConversao(View V) {
        Intent i = new Intent(this, Conversao.class);
        startActivity(i);
    }
    public void irParalistagem(View V) {
        Intent i = new Intent(this, Listagem.class);
        startActivity(i);
    }
}
