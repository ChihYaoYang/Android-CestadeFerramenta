package com.example.senac.cestadeferramenta.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.senac.cestadeferramenta.model.Phone;
import com.example.senac.cestadeferramenta.model.Produto;
import com.example.senac.cestadeferramenta.model.Usuario;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ferramentas.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Usuario, Integer> usuarioDao = null;
    private Dao<Produto, Integer> produtoDao = null;
    private Dao<Phone, Integer> phoneDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Produto.class);
            TableUtils.createTable(connectionSource, Phone.class);

            //Usuario
            Usuario usuario = new Usuario();
            usuario.setEmail("chih.yang@aluno.sc.senac.br");
            usuario.setSenha("123456");
            getUsuarioDao().create(usuario);

        } catch (Exception e) {
            Log.e("banco", "Erro ao criar banco");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Usuario.class, true);
            TableUtils.dropTable(connectionSource, Produto.class, true);
            TableUtils.dropTable(connectionSource, Phone.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (Exception e) {
            Log.e("banco", "Erro ao criar banco");
        }
    }

    //-------Usuario-------
    public Dao<Usuario, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            try {
                usuarioDao = getDao(Usuario.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return usuarioDao;
    }

    public Usuario validarUsuario(String email, String senha) {
        try {
            Usuario usuario = getUsuarioDao().queryBuilder().where().eq("email", email).and().eq("senha", senha).queryForFirst();
            return usuario;
        } catch (Exception e) {
            Log.e("banco", "Falha ao recuperar usuario");
        }
        return null;
    }

    //-------ProdutoActivity-------
    public Dao<Produto, Integer> getProdutoDao() throws SQLException {
        if (produtoDao == null) {
            try {
                produtoDao = getDao(Produto.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return produtoDao;
    }

    public void salvarProduto(Produto produto) {
        try {
            getProdutoDao().create(produto);
        } catch (Exception e) {
            Log.e("banco", "Falha ao salvar produto");
        }
    }

    public void removerProduto(Produto produto) {
        try {
            getProdutoDao().delete(produto);
        } catch (Exception e) {
            Log.e("banco", "Falha ao remover produto");
        }
    }

    public void update(Produto produto) {
        try {
            getProdutoDao().update(produto);
        } catch (Exception e) {
            Log.e("banco", "Falha no update produto");
        }
    }

    public List<Produto> buscarTodos() {
        List<Produto> produtos = null;
        try {
            return getProdutoDao().queryBuilder().query();
        } catch (Exception e) {
            Log.e("banco", "Falha ao busca produtos");
        }
        return new ArrayList<>();
    }

    //PhoneActivity  CRUD
    public Dao<Phone, Integer> getPhoneDao() throws SQLException {
        if (produtoDao == null) {
            try {
                phoneDao = getDao(Phone.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return phoneDao;
    }

    //Read
    public List<Phone> getAll() {
        List<Phone> phones = null;
        try {
            return getPhoneDao().queryBuilder().query();
        } catch (Exception e) {
            Log.e("banco", "Falha ao busca produtos");
        }
        return new ArrayList<>();
    }

    //Create
    public void salvarPhone(Phone ph) {
        try {
            getPhoneDao().create(ph);
        } catch (Exception e) {
            Log.e("Banco", "Falha ao cadastrar phone");
        }
    }
}