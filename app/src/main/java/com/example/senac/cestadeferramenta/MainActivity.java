package com.example.senac.cestadeferramenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editPassword,editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ciclo", "Passando pelo método Oncreate . . .");
        setContentView(R.layout.activity_main);

        editEmail =findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
    }
    public void irParaPrincipal(View V) {
        //atribui o valor do campo da tela para a variavel do tipo string
        String email = editEmail.getText().toString();
        String senha = editPassword.getText().toString();

        if(email.equals("chih.yang@aluno.sc.senac.br") && senha.equals("1234")) {
            //mensagem para exibicao de informações
            Toast.makeText( this,  "logado", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Principal.class);
            startActivity(i);
        } else {
            Toast.makeText( this,  "Falha ao logar", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStart() {
        super.onStart();
        Log.i("ciclo", "Passando pelo método onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ciclo" , "onresume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ciclo", "onpause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ciclo" , "onstop");
    }

    public void onRestart() {
        super.onRestart();
        Log.i("ciclo", "Passando pelo método onRestart. . .");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ciclo" , "destroty");
    }
}
