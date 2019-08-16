package com.example.senac.cestadeferramenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senac.cestadeferramenta.helper.JogodaVelha;


public class JogoDaVelhaActivity extends AppCompatActivity {
    ImageView imageGrid01, imageGrid02, imageGrid03, imageGrid04, imageGrid05, imageGrid06, imageGrid07, imageGrid08, imageGrid09;
    //    Boolean quadrado = true;
//    String[] jogo = new String[9];
    int o = 0;
    int x = 0;


    //Codigo prof
    String[] tabuleiro = new String[9];
    Boolean marcacaoX = true;

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

    //    public void click(View v) {
//        switch (v.getId()) {
//            case R.id.imageGrid01:
//                jogo[0] = quadrado ? "x" : "o";
//                break;
//            case R.id.imageGrid02:
//                jogo[1] = quadrado ? "x" : "o";
//                break;
//            case R.id.imageGrid03:
//                jogo[2] = quadrado ? "x" : "o";
//                break;
//            case R.id.imageGrid04:
//                jogo[3] = quadrado ? "x" : "o";
//                break;
//            case R.id.imageGrid05:
//                jogo[4] = quadrado ? "x" : "o";
//                break;
//            case R.id.imageGrid06:
//                jogo[5] = quadrado ? "x" : "o";
//                break;
//            case R.id.imageGrid07:
//                jogo[6] = quadrado ? "x" : "o";
//                break;
//            case R.id.imageGrid08:
//                jogo[7] = quadrado ? "x" : "o";
//                break;
//            case R.id.imageGrid09:
//                jogo[8] = quadrado ? "x" : "o";
//                break;
//            default:
//                break;
//        }
//        ImageView imagem = findViewById(v.getId());
//        if (quadrado == true) {
//            imagem.setImageResource(R.drawable.cicles);
//
//        } else {
//            imagem.setImageResource(R.drawable.imgx);
//        }
//        imagem.setClickable(false);
//        //Validar todos maneira de quem ganhou
//        if (jogo[0] != null && jogo[1] != null && jogo[0].equals(jogo[1]) && jogo[1].equals(jogo[2])) {
//            Toast.makeText(JogoDaVelhaActivity.this, "Parabens você ganhou o jogo", Toast.LENGTH_SHORT).show();
//            finish();
//        } else if (jogo[3] != null && jogo[4] != null && jogo[3].equals(jogo[4]) && jogo[4].equals(jogo[5])) {
//            Toast.makeText(JogoDaVelhaActivity.this, "Parabens você ganhou o jogo", Toast.LENGTH_SHORT).show();
//            finish();
//        } else if (jogo[6] != null && jogo[7] != null && jogo[6].equals(jogo[7]) && jogo[7].equals(jogo[8])) {
//            Toast.makeText(JogoDaVelhaActivity.this, "Parabens você ganhou o jogo", Toast.LENGTH_SHORT).show();
//            finish();
//        } else if (jogo[0] != null && jogo[3] != null && jogo[0].equals(jogo[3]) && jogo[3].equals(jogo[6])) {
//            Toast.makeText(JogoDaVelhaActivity.this, "Parabens você ganhou o jogo", Toast.LENGTH_SHORT).show();
//            finish();
//        } else if (jogo[1] != null && jogo[4] != null && jogo[1].equals(jogo[4]) && jogo[4].equals(jogo[7])) {
//            Toast.makeText(JogoDaVelhaActivity.this, "Parabens você ganhou o jogo", Toast.LENGTH_SHORT).show();
//            finish();
//        } else if (jogo[2] != null && jogo[5] != null && jogo[2].equals(jogo[5]) && jogo[5].equals(jogo[8])) {
//            Toast.makeText(JogoDaVelhaActivity.this, "Parabens você ganhou o jogo", Toast.LENGTH_SHORT).show();
//            finish();
//        } else if (jogo[0] != null && jogo[4] != null && jogo[0].equals(jogo[4]) && jogo[4].equals(jogo[8])) {
//            Toast.makeText(JogoDaVelhaActivity.this, "Parabens você ganhou o jogo", Toast.LENGTH_SHORT).show();
//            finish();
//        } else if (jogo[2] != null && jogo[4] != null && jogo[2].equals(jogo[4]) && jogo[4].equals(jogo[6])) {
//            Toast.makeText(JogoDaVelhaActivity.this, "Parabens você ganhou o jogo", Toast.LENGTH_SHORT).show();
//            finish();
//        } else {
////            Toast.makeText(JogoDaVelhaActivity.this, "O jogo foi empatados", Toast.LENGTH_SHORT).show();
//        }
//        quadrado = !quadrado;
//    }
    public void click(View v) {
        //Codigo prof
        ImageView image = (ImageView) v;
        image.setImageResource(marcacaoX ? R.drawable.cicles : R.drawable.imgx);
        tabuleiro[getPosition(image.getId())] = marcacaoX ? "O" : "X";
        marcacaoX = !marcacaoX;
        String vencedor = JogodaVelha.obtemVencedor(tabuleiro);
        if (vencedor != null) {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setTitle(null);
            dialog.setContentView(R.layout.resultado_jogo);

            TextView descricao = dialog.findViewById(R.id.descricao);
            TextView contatoro = dialog.findViewById(R.id.contatoro);
            TextView contatorx = dialog.findViewById(R.id.contatorx);

            descricao.setText("Parabéns o " + vencedor);

            if (vencedor == "O") {
                contatoro.setText("O: " + o + 1);
            }
            if (vencedor == "X") {
                contatorx.setText("X: " + x + 1);
            }
            dialog.show();

        }
        image.setClickable(false);
    }

    private int getPosition(int id) {
        switch (id) {
            case R.id.imageGrid01:
                return 0;
            case R.id.imageGrid02:
                return 1;
            case R.id.imageGrid03:
                return 2;
            case R.id.imageGrid04:
                return 3;
            case R.id.imageGrid05:
                return 4;
            case R.id.imageGrid06:
                return 5;
            case R.id.imageGrid07:
                return 6;
            case R.id.imageGrid08:
                return 7;
            case R.id.imageGrid09:
                return 8;
        }
        return 0;
    }
}