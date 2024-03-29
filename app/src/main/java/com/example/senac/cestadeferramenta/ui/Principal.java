package com.example.senac.cestadeferramenta.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.senac.cestadeferramenta.JogoDaVelhaActivity;
import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.model.Produto;
import com.example.senac.cestadeferramenta.model.Usuario;

public class Principal extends AppCompatActivity {
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);

        //pega item do outro pagina
        Intent i = getIntent();
        usuario = (Usuario) i.getSerializableExtra("usuario");
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
        i.putExtra("usuario", usuario);
        startActivity(i);
    }

    public void telephone(View v) {
        Intent i = new Intent(this, ListagemPhoneActivity.class);
        startActivity(i);
    }

    public void irParaJogoDaVelha(View v) {
        Intent i = new Intent(this, JogoDaVelhaActivity.class);
        startActivity(i);
    }
}