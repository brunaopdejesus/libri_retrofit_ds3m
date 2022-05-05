package com.cristianomoraes.libri_retorfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri_retorfit.model.Livro;
import com.cristianomoraes.libri_retorfit.remote.APIUtil;
import com.cristianomoraes.libri_retorfit.remote.RouterInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlterarLivro extends AppCompatActivity {

    RouterInterface routerInterface;
    List<Livro> list = new ArrayList<Livro>();

    EditText txtTitulo;
    EditText txtDescricao;
    EditText txtFoto;
    Button btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_livro);

        txtTitulo = findViewById(R.id.txtEditarTitulo);
        txtDescricao = findViewById(R.id.txtEditarLivroDescricao);
        txtFoto = findViewById(R.id.txtEditarFoto);
        btnEditar = findViewById(R.id.btnEditarLivro);

        int cod_livro = getIntent().getExtras().getInt("cod_livro");

        routerInterface = APIUtil.getUsuarioInterface();
        Call<List<Livro>> callGetLivroId = routerInterface.getLivroId(cod_livro);
        callGetLivroId.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful()) {

                    list = response.body();
                    txtTitulo.setText(list.get(0).getTitulo());
                    txtDescricao.setText(list.get(0).getDescricao());
                    txtFoto.setText(list.get(0).getImagem());

                    btnEditar.setOnClickListener(view -> {

                        Livro livro = new Livro();
                        livro.setTitulo(txtTitulo.getText().toString());
                        livro.setDescricao(txtDescricao.getText().toString());
                        livro.setImagem(txtFoto.getText().toString());
                        livro.setCod_livro(cod_livro);
                        livro.setTblUsuarioCodUsuario(1);

                        Call<Livro> callUpdateLivro = routerInterface.updateLivro(livro);
                        callUpdateLivro.enqueue(new Callback<Livro>() {
                            @Override
                            public void onResponse(Call<Livro> call, Response<Livro> response) {

                                Toast.makeText(AlterarLivro.this, "Livro alterado com sucesso!", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(AlterarLivro.this, FeedLivro.class));

                            }

                            @Override
                            public void onFailure(Call<Livro> call, Throwable t) {

                            }
                        });

                    });

                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {

            }
        });

    }
}