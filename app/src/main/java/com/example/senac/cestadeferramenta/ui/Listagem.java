package com.example.senac.cestadeferramenta.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.helper.AdapterList;
import com.example.senac.cestadeferramenta.helper.DatabaseHelper;
import com.example.senac.cestadeferramenta.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Listagem extends AppCompatActivity {
    ListView listaProdutos;
    List<Produto> produtos;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        final Intent intent = new Intent(this, ProdutoActivity.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.removeExtra("produto");
                startActivity(intent);
            }
        });


        produtos = new ArrayList<>();
        AdapterList adapterList = new AdapterList(produtos, this);
        listaProdutos = findViewById(R.id.lista);
        listaProdutos.setAdapter(adapterList);


        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("produto", produtos.get(i));
                startActivity(intent);
                Toast.makeText(Listagem.this, "Nome: " + produtos.get(i), Toast.LENGTH_SHORT);
            }
        });


        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        produtos = null;
        produtos = databaseHelper.buscarTodos();
        AdapterList adapterList = (AdapterList) listaProdutos.getAdapter();
        adapterList.atualizarProdutos(produtos);
    }
}