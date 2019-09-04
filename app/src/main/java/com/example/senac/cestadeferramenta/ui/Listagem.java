package com.example.senac.cestadeferramenta.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.constantes.Request;
import com.example.senac.cestadeferramenta.helper.AdapterList;
import com.example.senac.cestadeferramenta.helper.DatabaseHelper;
import com.example.senac.cestadeferramenta.model.Produto;
import com.example.senac.cestadeferramenta.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Listagem extends AppCompatActivity {
    ListView listaProdutos;
    List<Produto> produtos;
    DatabaseHelper databaseHelper;
    ProgressDialog progress;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        Intent i = getIntent();
        usuario = (Usuario) i.getSerializableExtra("usuario");


        final Intent intent = new Intent(this, ProdutoActivity.class);
        intent.putExtra("usuario",usuario);

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
//        produtos = null;
//        produtos = databaseHelper.buscarTodos();
//        AdapterList adapterList = (AdapterList) listaProdutos.getAdapter();
//        adapterList.atualizarProdutos(produtos);
        new BuscarProduto().execute();
    }

    //Get product no banco
    private class BuscarProduto extends AsyncTask<Void, Void, List<Produto>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(Listagem.this);
            progress.show();
            progress.setCancelable(false);
            progress.setContentView(R.layout.progres);
        }

        @Override
        protected List<Produto> doInBackground(Void... produtos) {
            try {
                URL url = new URL(Request.URL_REQUEST + "/ferramentas/adquirirProduto");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("codigo", usuario.getCodigo().toString());

                Gson gson = new Gson();


                int codigoResposta = urlConnection.getResponseCode();
                Log.e("request", "código " + codigoResposta);

                if (codigoResposta == 200) {
                    String jsonResposta = "";
                    InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStream);
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        jsonResposta += line;
                    }
                    Log.e("request", jsonResposta);

                    Type listType = new TypeToken<ArrayList<Produto>>() {
                    }.getType();
                    return gson.fromJson(jsonResposta, listType);

                } else {
                    return null;
                }

            } catch (Exception e) {
                Log.e("request", "Erro");
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Produto> produtos) {
            progress.dismiss();
            if (produtos != null) {
                AdapterList adapterList = (AdapterList) listaProdutos.getAdapter();
                adapterList.atualizarProdutos(produtos);
            }
        }
    }
}