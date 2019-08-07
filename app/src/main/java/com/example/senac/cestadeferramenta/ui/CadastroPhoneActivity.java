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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.helper.DatabaseHelper;
import com.example.senac.cestadeferramenta.model.Phone;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CadastroPhoneActivity extends AppCompatActivity {

    //Declara variavel
    ImageView imagephone;
    EditText modelophone, precophone;
    Spinner marca, color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_phone);


        //find id
        imagephone = findViewById(R.id.imagePhone);
        modelophone = findViewById(R.id.modelophone);
        precophone = findViewById(R.id.precophone);
        marca = findViewById(R.id.marca);
        color = findViewById(R.id.color);
    }

    //<!--Validação de campo
    public String validation() {
        if (modelophone.getText().toString().trim().length() == 0) {
            return "Preencher o campo modelo";
        }
        if (precophone.getText().toString().trim().length() == 0) {
            return "Preencher o campo Preço";
        } else {
            float preco = Float.parseFloat(precophone.getText().toString());
            if (preco == 0) {
                return "Preencher o campo Preço";
            }
        }
        return "";
    }
    //Validação de campo-->

    //<!-- Erro de mensagem      Atribui String menssage  e exibir dialog
    public void mensagemErro(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(mensagem);
        //define um botão como positivo(OK or Cancel)
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
//                Toast.makeText(CadastroPhoneActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
//                Toast.makeText(CadastroPhoneActivity.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o Alert Dialog
        builder.create().show();
    }
    // Erro de mensagem-->

    //<!-- Salvar Cadastro
    public void salvecadastro(View v) {
        //Declara variavel mensagem para pega valor validation
        String mensagem = validation();
        if (mensagem.length() == 0) {
            salvar();
        } else {
            //Passa variavel mensagem para mensagemErro
            mensagemErro(mensagem);
        }
    }
    //Salvar Cadastro-->

    //<!-- salvar
    public void salvar() {
        //Declara variavel e passa o valor para metodo create no DatabaseHelper
        String modelo = modelophone.getText().toString();
        float preco = Float.parseFloat(precophone.getText().toString());
        //Setar valor
        Phone ph = new Phone();
        ph.setModelo(getImagem());
        ph.setModelo(modelo);
        ph.setPreco(preco);
        ph.setMarca(marca.getSelectedItem().toString().equals("Asus") ? "A" : "X");
        ph.setColor(color.getSelectedItem().toString().equals("Preto") ? "P" : "B");
        //Depois setar valor passa valor para DatabaseHelper
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.salvarPhone(ph);
        finish();
    }


    //<!-- getImagem
    public String getImagem() {
        Bitmap bitmap = ((BitmapDrawable) imagephone.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    //getImagem-->
    //salvar-->

    //<!--Permissão de tirafoto
    public void tirapicture(View V) {
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
            imagephone.setImageBitmap(imageBitmap);
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
    //Permissão de tirafoto-->
}