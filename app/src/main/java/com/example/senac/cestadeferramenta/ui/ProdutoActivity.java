package com.example.senac.cestadeferramenta.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.constantes.Request;
import com.example.senac.cestadeferramenta.helper.DatabaseHelper;
import com.example.senac.cestadeferramenta.model.Produto;
import com.example.senac.cestadeferramenta.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {
    EditText nomeprod, quantidade;
    Spinner status;
    ImageView imagemns;
    Button btnExcluir;
    Produto pro;
    ProgressDialog progress;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        imagemns = findViewById(R.id.imagemns);
        status = findViewById(R.id.status);
        quantidade = findViewById(R.id.quantidade);
        nomeprod = findViewById(R.id.nomeprod);
        btnExcluir = findViewById(R.id.btnExcluir);

        //Get id update
        Intent i = getIntent();
        pro = (Produto) i.getSerializableExtra("produto");
        usuario = (Usuario) i.getSerializableExtra("usuario");

        if (pro != null) {
            //Oculta informações na tela
            btnExcluir.setVisibility(View.VISIBLE);
            nomeprod.setText(pro.getNome());
            quantidade.setText(Integer.toString(pro.getQuantidade()));
            status.setSelection(pro.getStatus().equals("C") ? 1 : 0);

            //Converte IMG
            byte[] decodedString = Base64.decode(pro.getFoto(), Base64.DEFAULT);
            Bitmap decodeByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //Exibir
            imagemns.setImageBitmap(decodeByte);

            Toast.makeText(this, "Editanto " + pro.getNome(), Toast.LENGTH_SHORT).show();
        } else {
            btnExcluir.setVisibility(View.GONE);
        }
        //Visible
        //btnExcluir.setVisibility(View.VISIBLE);

    }

    //Validation
    public String validation() {
        if (nomeprod.getText().toString().trim().length() == 0) {
            return "Preencher o campo nome";
        }
        if (quantidade.getText().toString().trim().length() == 0) {
            return "Preencher o campo quantidade";
        } else {
            int quant = Integer.parseInt(quantidade.getText().toString());
            if (quant == 0) {
                return "Preencher o campo quantidade";
            }
        }
        return "";
    }

    //Verifica
    public void salvarcadastro(View v) {
//        verifica os campos
        String mensagem = validation();
        //update
        if (mensagem.length() == 0) {
            if (pro != null) {
                updatecadastro(v);
            } else {
                //call function salvar
                salvar();
            }
        } else {
            //call function para exibir as mensagem
            mensagemErro(mensagem);
        }
    }

    //update
    public void updatecadastro(View v) {
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        String nome = nomeprod.getText().toString();
//        int quant = Integer.parseInt(quantidade.getText().toString());
//        //Get values
//
//        pro.setFoto(getImagem());
//        pro.setNome(nome);
//        pro.setQuantidade(quant);
//        //Valida status
//        pro.setStatus(status.getSelectedItem().toString().equals("Comprado") ? "C" : "N");
//
//        databaseHelper.update(pro);
//        finish();
    }

//    public void excluirCadastro(View v) {
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        databaseHelper.removerProduto(pro);
//        finish();
//    }

    //function salve
    public void salvar() {
        String nome = nomeprod.getText().toString();
        int quant = Integer.parseInt(quantidade.getText().toString());
        //Get values
        Produto pro = new Produto();
        pro.setFoto(getImagem());
        pro.setNome(nome);
        pro.setQuantidade(quant);
        //Valida status
        pro.setStatus(status.getSelectedItem().toString().equals("Comprado") ? "C" : "N");

        new CadastrarProduto().execute(pro);
    }

    //getImagem
    public String getImagem() {
        Bitmap bitmap = ((BitmapDrawable) imagemns.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public void mensagemErro(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(mensagem);
        //define um botão como positivo(OK or Cancel)
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(ProdutoActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(ProdutoActivity.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });

        //cria o AlertDialog
        builder.create().show();
    }

    public void tirafoto(View V) {
        if (checkAndRequestPermissions()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 100);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagemns.setImageBitmap(imageBitmap);
        }
    }

    public boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }


    //Salvar no banco
    private class CadastrarProduto extends AsyncTask<Produto, Void, List<Produto>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(ProdutoActivity.this);
            progress.show();
            progress.setCancelable(false);
            progress.setContentView(R.layout.progres);
        }

        @Override
        protected List<Produto> doInBackground(Produto... produtos) {
            try {
                URL url = new URL(Request.URL_REQUEST + "/ferramentas/salvarProduto");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("codigo", usuario.getCodigo().toString());
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                Gson gson = new Gson();
                DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                String parametro = gson.toJson(produtos[0]);
                Log.e("request", parametro);
                outputStream.writeBytes(parametro);
                outputStream.flush();
                outputStream.close();

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
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProdutoActivity.this);
            if (produtos != null) {
                alertDialog.setTitle("Atenção");
                alertDialog.setMessage("Salvar produto com successo");
                alertDialog.create().show();
                finish();
            } else {
                alertDialog.setTitle("Atenção");
                alertDialog.setMessage("Falha ao salvar");
                alertDialog.create().show();
            }
        }
    }
}