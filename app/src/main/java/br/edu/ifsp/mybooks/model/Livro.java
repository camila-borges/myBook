package br.edu.ifsp.mybooks.model;

/**
 * Created by Camila on 28/11/2017.
 */

public class Livro {
    private int id;
    private int id_user;
    private String nome;
    private String autor;
    private String descricao;
    private String qualidade;
    private String imagem;

    public Livro() {
    }

    public Livro(int id, int id_user, String nome, String autor, String descricao, String qualidade, String imagem){
        this.id = id;
        this.id_user = id_user;
        this.nome = nome;
        this.autor = autor;
        this.descricao = descricao;
        this.qualidade = qualidade;
        this.imagem = imagem;
    }

    public Livro(int id_user, String nome, String autor, String descricao, String qualidade, String imagem){
        this.id_user = id_user;
        this.nome = nome;
        this.autor = autor;
        this.descricao = descricao;
        this.qualidade = qualidade;
        this.imagem = imagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() { return id_user; }

    public void setId_user(int id_user) { this.id_user = id_user; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getAutor() { return autor; }

    public void setAutor(String autor) { this.autor = autor; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getQualidade() { return qualidade; }

    public void setQualidade(String qualidade) { this.qualidade = qualidade; }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
