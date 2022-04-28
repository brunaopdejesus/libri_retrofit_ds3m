package com.cristianomoraes.libri_retorfit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Livro {

    @SerializedName("cod_livro")
    @Expose
    private int cod_livro;

    @SerializedName("tblUsuarioCodUsuario")
    @Expose
    private int tblUsuarioCodUsuario;

    @SerializedName("titulo")
    @Expose
    private String titulo;

    @SerializedName("descricao")
    @Expose
    private String descricao;

    @SerializedName("imagem")
    @Expose
    private String imagem;

    public Livro() {
    }

    public Livro(int cod_livro, int tblUsuarioCodUsuario, String titulo, String descricao,
                 String imagem) {
        this.cod_livro = cod_livro;
        this.tblUsuarioCodUsuario = tblUsuarioCodUsuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public int getCod_livro() {
        return cod_livro;
    }

    public void setCod_livro(int cod_livro) {
        this.cod_livro = cod_livro;
    }

    public int getTblUsuarioCodUsuario() {
        return tblUsuarioCodUsuario;
    }

    public void setTblUsuarioCodUsuario(int tblUsuarioCodUsuario) {
        this.tblUsuarioCodUsuario = tblUsuarioCodUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
