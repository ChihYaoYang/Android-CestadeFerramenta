package com.example.senac.cestadeferramenta;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calculo extends AppCompatActivity {

    EditText editAltura,editPeso,IMC;
    TextView Resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void calculo(View V) {
        editAltura = findViewById(R.id.editAltura);
        editPeso = findViewById(R.id.editPeso);

        String alt= editAltura.getText().toString();
        Float altura = Float.parseFloat(alt);

        String pes= editPeso.getText().toString();
        Float peso = Float.parseFloat(pes);

        float IMC = peso/(float)Math.pow(altura,2);
        Resultado = (TextView)findViewById(R.id.Resultado);

        editAltura.getText().clear();
        editPeso.getText().clear();
        if(IMC < 18.5) {
            Resultado.setText("Resultado: Abaixo do Peso " + IMC);
        } else if(IMC > 18.5 && IMC <= 24.9) {
            Resultado.setText("Resultado: Peso Normal" + IMC);
        } else if(IMC > 25 && IMC < 30){
            Resultado.setText("Resultado: Acima de Peso " + IMC);
        } else {
            Resultado.setText("Resultado: Obesidade " + IMC);
        }


    }

}
