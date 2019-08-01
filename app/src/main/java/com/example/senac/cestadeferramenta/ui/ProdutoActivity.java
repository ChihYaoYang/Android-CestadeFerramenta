package com.example.senac.cestadeferramenta.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.helper.DatabaseHelper;
import com.example.senac.cestadeferramenta.model.Produto;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {

    EditText nomeprod, quantidade;
    Spinner status;
    ImageView imagemns;
    Button btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        imagemns = findViewById(R.id.imagemns);
        status = findViewById(R.id.status);
        quantidade = findViewById(R.id.quantidade);
        nomeprod = findViewById(R.id.nomeprod);
        btnExcluir = findViewById(R.id.btnExcluir);
        //Oculta informações na tela
        btnExcluir.setVisibility(View.GONE);
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
        //verifica os campos
        String mensagem = validation();
        //caso seja true salvar os campos
        if (mensagem.length() == 0) {
            //call function salvar
            salvar();
        } else {
            //call function para exibir as mensagem
            mensagemErro(mensagem);
        }
    }

    public void excluirCadastro(View v) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        //databaseHelper.removerProduto();
    }

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

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.salvarProduto(pro);
        finish();
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
}