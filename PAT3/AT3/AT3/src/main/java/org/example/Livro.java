package org.example;

public class Livro {
    private String autor;
    private String nome;
    private String genero;
    private int numeroDeExemplares;

    public Livro(String autor, String nome, String genero, int numeroDeExemplares) {
        this.autor = autor;
        this.nome = nome;
        this.genero = genero;
        this.numeroDeExemplares = numeroDeExemplares;
    }

    public String getAutor() {
        return autor;
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public int getNumeroDeExemplares() {
        return numeroDeExemplares;
    }

    public void setNumeroDeExemplares(int numeroDeExemplares) {
        this.numeroDeExemplares = numeroDeExemplares;
    }
}
