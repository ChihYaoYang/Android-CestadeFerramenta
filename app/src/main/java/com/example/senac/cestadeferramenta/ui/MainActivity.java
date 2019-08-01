package com.example.senac.cestadeferramenta.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.helper.DatabaseHelper;
import com.example.senac.cestadeferramenta.model.Usuario;

public class MainActivity extends AppCompatActivity {
    EditText editPassword, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ciclo", "Passando pelo método Oncreate . . .");
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
    }

    public void irParaPrincipal(View V) {
        //Chama Databasehelper
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        String email = editEmail.getText().toString();
        String senha = editPassword.getText().toString();

        //Atribui o valor email e senha
        Usuario usuario = databaseHelper.validarUsuario(email, senha);
        //validation
        if (usuario != null) {
            Toast.makeText(this,"Bem Vindos " + email , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Principal.class));
            finish();
        } else {
            Toast.makeText(this, "Usuário e senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStart() {
        super.onStart();
        Log.i("ciclo", "Passando pelo método onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ciclo", "onresume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ciclo", "onpause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ciclo", "onstop");
    }

    public void onRestart() {
        super.onRestart();
        Log.i("ciclo", "Passando pelo método onRestart. . .");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ciclo", "destroty");
    }
}
