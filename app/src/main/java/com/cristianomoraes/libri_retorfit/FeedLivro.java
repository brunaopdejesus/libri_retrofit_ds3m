package com.cristianomoraes.libri_retorfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cristianomoraes.libri_retorfit.model.Item;
import com.cristianomoraes.libri_retorfit.model.Livro;
import com.cristianomoraes.libri_retorfit.remote.APIUtil;
import com.cristianomoraes.libri_retorfit.remote.RouterInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedLivro extends AppCompatActivity {

    /** DECLARAÇÃO DOS ATRIBUTOS **/
    RouterInterface routerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_livro);

        // CONECTA O APLICATIVO COM A API
        routerInterface = APIUtil.getUsuarioInterface();

        // EXECUTA A CHAMADA PARA A ROTA DE LISTAGEM DE LIVROS
        Call<List<Livro>> call = routerInterface.getLivros();
        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {

                if (response.isSuccessful()){
                    List<Item> itens = new ArrayList<>();

                    // RECEBE OS DADOS DA API
                    List<Livro> list = new ArrayList<Livro>();
                    list = response.body();

                    for (int i = 0; i < list.size(); i++){
                        itens.add(new Item(0, list.get(i)));
                    }

                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    recyclerView.setAdapter(new LivroAdapter(itens));

                }

            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {

            }
        });
    }

    private class LivroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Item> itens;

        public LivroAdapter(List<Item> itens) {
            this.itens = itens;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LivroAdapter.LivroViewHolder(LayoutInflater.from(
                            parent.getContext()).inflate(
                                    R.layout.item_container_livro,
                                    parent,
                                    false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            // DADOS DE LIVRO
            if (getItemViewType(position) == 0) {

                Livro livro = (Livro) itens.get(position).getObject();
                ((LivroAdapter.LivroViewHolder) holder).setLivroData(livro);

            }
        }

        @Override
        public int getItemCount() {
            return itens.size();
        }

        public int getItemViewType(int position) {
            return itens.get(position).getType();
        }

        /* CLASSE DE VIEWHOLDER DA RECYCLERVIEW */
        class LivroViewHolder extends RecyclerView.ViewHolder{

            /** ATRIBUTOS DA CLASS LIVROVIEWHOLDER **/
            private TextView txtTitulo, txtDescricao;
            private int cod_livro;

            public LivroViewHolder(@NonNull View itemView) {
                // super chama o método construtor de quem foi herdado
                super(itemView);

                txtTitulo = itemView.findViewById(R.id.txt_livro_titulo_container);
                txtDescricao = itemView.findViewById(R.id.txt_livro_descricao_container);

                /* AÇÃO DE CLIQUE PARA EDITAR E EXCLUIR LIVRO */
                itemView.setOnClickListener(view -> {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(FeedLivro.this)
                            .setMessage("Escolha a ação que deseja executar 👀🙀")
                            .setPositiveButton("Editar ✍️", (dialog1, witch)->{

                                Intent intent = new Intent(FeedLivro.this, AlterarLivro.class);
                                intent.putExtra("cod_livro", cod_livro);
                                startActivity(intent);

                            })
                            .setNegativeButton("Excluir ☠️", (dialog2, witch)->{

                                routerInterface = APIUtil.getUsuarioInterface();

                                Call<Livro> call = routerInterface.deleteLivro(cod_livro);

                                // retorno da chamada de call
                                call.enqueue(new Callback<Livro>() {
                                    @Override
                                    public void onResponse(Call<Livro> call, Response<Livro> response) {
                                        Toast.makeText(FeedLivro.this, "Livro excluído com sucesso 🥳", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(FeedLivro.this, FeedLivro.class));
                                    }

                                    @Override
                                    public void onFailure(Call<Livro> call, Throwable t) {

                                    }
                                });

                            });
                    alertDialog.show();

                });

            }

            /* MÉTODO QUE CARREGA OS VALORES NOS ELEMENTOS DE TEXTVIEW (txtTitulo; txtDescricao) */
            public void setLivroData(Livro livro){

                txtTitulo.setText(livro.getTitulo());
                txtDescricao.setText(livro.getDescricao());
                cod_livro = livro.getCod_livro();

            }

        }

    }

}