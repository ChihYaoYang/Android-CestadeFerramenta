package com.example.senac.cestadeferramenta.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.senac.cestadeferramenta.helper.AdapterList;
import com.example.senac.cestadeferramenta.helper.AdapterListaItem;
import com.example.senac.cestadeferramenta.helper.DatabaseHelper;
import com.example.senac.cestadeferramenta.model.Phone;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.senac.cestadeferramenta.R;

import java.util.ArrayList;
import java.util.List;

public class ListagemPhoneActivity extends AppCompatActivity {
    ListView listaPhone;
    List<Phone> phones;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_phone);

        final Intent intent = new Intent(this, CadastroPhoneActivity.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        //Declara ArrayList e get id da lista e seta o valor
        phones = new ArrayList<>();
        AdapterListaItem adapterListaItem = new AdapterListaItem(phones, this);
        listaPhone = findViewById(R.id.lista);
        listaPhone.setAdapter(adapterListaItem);

        listaPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("phone", phones.get(i));
                startActivity(intent);
                Toast.makeText(ListagemPhoneActivity.this, "marca: " + phones.get(i), Toast.LENGTH_SHORT);
            }
        });
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        phones = null;
        phones = databaseHelper.getAll();
    }
}
