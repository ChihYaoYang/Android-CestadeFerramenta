package com.example.senac.cestadeferramenta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Conversao extends AppCompatActivity {
    EditText editValor;
    Spinner convert;
    TextView ResultByte, ResultKB, ResultMB, ResultGB, ResultTB, error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversao);

        editValor = findViewById(R.id.editValor);
        convert = findViewById(R.id.convert);
        ResultByte = findViewById(R.id.ResultByte);
        ResultKB = findViewById(R.id.ResultKB);
        ResultMB = findViewById(R.id.ResultMB);
        ResultGB = findViewById(R.id.ResultGB);
        ResultTB = findViewById(R.id.ResultTB);
        error = findViewById(R.id.error);

        convert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                conversao(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void conversao(View V) {
        String type = convert.getSelectedItem().toString();
        NumberFormat formatter = new DecimalFormat("0.00");
        if (editValor.getText().toString().trim().length() > 0) {
            Double edtNum = Double.parseDouble(editValor.getText().toString());
            if (type.equals("MB")) {
                //bytes
                ResultByte.setText(formatter.format(edtNum * 1048576));
                //KB
                ResultKB.setText(formatter.format(edtNum * 1024));
                //MB
                ResultMB.setText(formatter.format(edtNum));
                //GB
                ResultGB.setText(formatter.format(edtNum / 1024));
                //TB
                ResultTB.setText(formatter.format(edtNum / 1048576));
            } else if (type.equals("GB")) {
                //bytes
                ResultByte.setText(formatter.format(edtNum * 1073741824));
                //KB
                ResultKB.setText(formatter.format(edtNum * 1048576));
                //MB
                ResultMB.setText(formatter.format(edtNum * 1024));
                //GB
                ResultMB.setText(formatter.format(edtNum));
                //TB
                ResultMB.setText(formatter.format(edtNum / 1024));
            }
        } else {
            ResultByte.setText("0");
            ResultKB.setText("0");
            ResultMB.setText("0");
            ResultGB.setText("0");
            ResultTB.setText("0");
            error.setText("Número informado inválido ou nulo");
        }
    }
}
