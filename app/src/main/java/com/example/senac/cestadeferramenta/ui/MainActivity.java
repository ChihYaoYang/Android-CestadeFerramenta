package com.example.senac.cestadeferramenta.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.constantes.Request;
import com.example.senac.cestadeferramenta.helper.DatabaseHelper;
import com.example.senac.cestadeferramenta.model.Usuario;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    EditText editPassword, editEmail;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ciclo", "Passando pelo método Oncreate . . .");
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

    }

    public void irParaPrincipal(View V) {
//        //Chama Databasehelper
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        String email = editEmail.getText().toString();
//        String senha = editPassword.getText().toString();
//
//        //Atribui o valor email e senha
//        Usuario usuario = databaseHelper.validarUsuario(email, senha);
//        //validation
//        if (usuario != null) {
//            Toast.makeText(this, "Bem Vindos " + email, Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, Principal.class));
//            finish();
//        } else {
//            Toast.makeText(this, "Usuário e senha inválidos", Toast.LENGTH_SHORT).show();
//        }

        String email = editEmail.getText().toString();
        String senha = editPassword.getText().toString();
        Usuario usuario = new Usuario();
        usuario.setLogin(email);
        usuario.setSenha(senha);
        new Login().execute(usuario);
    }

    private class Login extends AsyncTask<Usuario, Void, Usuario> {
        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(MainActivity.this);
            progress.show();
            progress.setCancelable(false);
            progress.setContentView(R.layout.progres);
        }

        @Override //deve retornar um usuario para o metodo onPostExecute
        // recebe um objeto de usuario por parametro
        protected Usuario doInBackground(Usuario... usuarios) {
            try {
                //delay pagina
                Thread.sleep(2000);
                URL url = new URL(Request.URL_REQUEST + "/ferramentas/autenticacao");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                Gson gson = new Gson();
                DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                String parametro = gson.toJson(usuarios[0]);
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
                    return gson.fromJson(jsonResposta, Usuario.class);

                } else {
                    return null;
                }

            } catch (Exception e) {
                Log.e("request", "Erro");
            }
            return null;
        }

        @Override //usuario vem do parametro do metodo doInBackground
        protected void onPostExecute(Usuario usuario) {
            progress.dismiss();

            if (usuario != null) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                databaseHelper.salvarUsuario(usuario);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Bem Vindo");
                alertDialog.setMessage(usuario.getNome());
                alertDialog.create().show();

                startActivity(new Intent(MainActivity.this, Principal.class));
                finish();
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Atenção");
                alertDialog.setMessage("Falha ao logar");
                alertDialog.create().show();
            }

        }
    }


//    public void onStart() {
//        super.onStart();
//        Log.i("ciclo", "Passando pelo método onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i("ciclo", "onresume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i("ciclo", "onpause");
//    }
//
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.i("ciclo", "onstop");
//    }
//
//    public void onRestart() {
//        super.onRestart();
//        Log.i("ciclo", "Passando pelo método onRestart. . .");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.i("ciclo", "destroty");
//    }
}
