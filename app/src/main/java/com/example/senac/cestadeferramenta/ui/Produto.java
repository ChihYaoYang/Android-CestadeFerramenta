package com.example.senac.cestadeferramenta.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.senac.cestadeferramenta.R;

import java.util.ArrayList;
import java.util.List;

public class Produto extends AppCompatActivity {

    EditText nomeprod, quantidade;
    Spinner status;
    ImageView imagemns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        imagemns = findViewById(R.id.imagemns);
        status = findViewById(R.id.status);
        quantidade = findViewById(R.id.quantidade);
        nomeprod = findViewById(R.id.nomeprod);
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

    //function salve
    public void salvar() {
    }

    public void mensagemErro(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(mensagem);
        //define um botão como positivo(OK or Cancel)
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(Produto.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(Produto.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
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