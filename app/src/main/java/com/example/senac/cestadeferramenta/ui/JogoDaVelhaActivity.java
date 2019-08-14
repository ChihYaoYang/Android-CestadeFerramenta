package com.example.senac.cestadeferramenta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class JogoDaVelhaActivity extends AppCompatActivity {
    ImageView imageGrid01, imageGrid02, imageGrid03, imageGrid04, imageGrid05, imageGrid06, imageGrid07, imageGrid08, imageGrid09;
    Boolean O = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo_da_velha);

        imageGrid01 = findViewById(R.id.imageGrid01);
        imageGrid02 = findViewById(R.id.imageGrid02);
        imageGrid03 = findViewById(R.id.imageGrid03);
        imageGrid04 = findViewById(R.id.imageGrid04);
        imageGrid05 = findViewById(R.id.imageGrid05);
        imageGrid06 = findViewById(R.id.imageGrid06);
        imageGrid07 = findViewById(R.id.imageGrid07);
        imageGrid08 = findViewById(R.id.imageGrid08);
        imageGrid09 = findViewById(R.id.imageGrid09);
    }

    public void click(View v) {
        ImageView imagem = findViewById(v.getId());
//        int[][] winstatus = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {0, 4, 8}, {1, 4, 7}, {2, 4, 6}, {2, 5, 8}};
        if (O == true) {
            imagem.setImageResource(R.drawable.checked);
            O = !O;
        } else {
            imagem.setImageResource(R.drawable.alert);
            O = !O;
        }
        imagem.setClickable(false);
    }
}
