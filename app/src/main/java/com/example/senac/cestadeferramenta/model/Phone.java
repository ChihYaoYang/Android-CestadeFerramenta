package com.example.senac.cestadeferramenta.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Phone implements Serializable {
    @DatabaseField(generatedId = true)
    private Integer codigo;
    @DatabaseField(canBeNull = false)
    private String modelo;
    @DatabaseField(canBeNull = false)
    private String marca;
    @DatabaseField(canBeNull = false)
    private float preco;
    @DatabaseField(canBeNull = false)
    private String color;
    @DatabaseField(canBeNull = false)
    private String picture;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
