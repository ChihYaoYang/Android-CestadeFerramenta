package com.example.senac.cestadeferramenta.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Usuario implements Serializable {
    @DatabaseField(id = true)
    private Integer codigo;

    @DatabaseField(canBeNull = false)
    private String login;

    @DatabaseField(canBeNull = false)
    private String senha;

    @DatabaseField(canBeNull = false)
    private String nome;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
