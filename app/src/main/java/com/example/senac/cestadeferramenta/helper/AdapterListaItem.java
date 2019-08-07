package com.example.senac.cestadeferramenta.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.senac.cestadeferramenta.R;
import com.example.senac.cestadeferramenta.model.Phone;

import java.util.List;

public class AdapterListaItem extends BaseAdapter {
    private final List<Phone> phones;
    private final Activity activity;

    public AdapterListaItem(List<Phone> phones, Activity activity) {
        this.phones = phones;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return phones.size();
    }

    @Override
    public Object getItem(int i) {
        return phones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return phones.get(i).getCodigo();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.phone_item, parent, false);
        Phone phone = phones.get(i);
        //find id
        ImageView image = view.findViewById(R.id.imagePicture);
        //Converte IMG
        byte[] decodedString = Base64.decode(phone.getPicture(), Base64.DEFAULT);
        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return view;
    }
}
