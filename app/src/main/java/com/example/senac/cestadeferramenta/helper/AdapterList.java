package com.example.senac.cestadeferramenta.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.model.Produto;

import java.util.List;

public class AdapterList extends BaseAdapter {

    private final List<Produto> produtos;
    private final Activity activity;

    public AdapterList(List<Produto> produtos, Activity activity) {
        this.produtos = produtos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.produto_item, parent, false);
        Produto produto = produtos.get(position);
        //find id
        ImageView imagem = view.findViewById(R.id.imagemProduto);
        TextView txNome = view.findViewById(R.id.nomeProduto);
        TextView quantidade = view.findViewById(R.id.qtdProduto);
        TextView stattus = view.findViewById(R.id.statusProduto);
        //Converte IMG
        byte[] decodedString = Base64.decode(produto.getFoto(), Base64.DEFAULT);
        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //Exibir
        imagem.setImageBitmap(decodeByte);
        txNome.setText(produto.getNome());
        quantidade.setText("QTD: " + produto.getQuantidade());
        stattus.setText(produto.getStatus());
        return view;
    }

    public void atualizarProdutos(List<Produto> novosProdutos) {
        this.produtos.clear();
        this.produtos.addAll(novosProdutos);
        notifyDataSetChanged();
    }
}
