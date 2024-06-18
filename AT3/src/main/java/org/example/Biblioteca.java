package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Biblioteca {
    private List<Livro> livros;
    private final String arquivoJson;

    public Biblioteca(String arquivoJson) {
        this.arquivoJson = arquivoJson;
        this.livros = new ArrayList<>();
        carregarLivros();
    }

    private void carregarLivros() {
        try (FileReader reader = new FileReader(arquivoJson)) {
            Type listaLivrosType = new TypeToken<ArrayList<Livro>>() {}.getType();
            livros = new Gson().fromJson(reader, listaLivrosType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salvarLivros() {
        try (FileWriter writer = new FileWriter(arquivoJson)) {
            new Gson().toJson(livros, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Livro> listarLivros() {
        return new ArrayList<>(livros);
    }

    public synchronized boolean adicionarLivro(Livro livro) {
        boolean adicionado = livros.add(livro);
        if (adicionado) {
            salvarLivros();
        }
        return adicionado;
    }

    public synchronized boolean alugarLivro(String nomeLivro) {
        Optional<Livro> livroOptional = livros.stream()
                .filter(livro -> livro.getNome().equalsIgnoreCase(nomeLivro) && livro.getNumeroDeExemplares() > 0)
                .findFirst();

        if (livroOptional.isPresent()) {
            Livro livro = livroOptional.get();
            livro.setNumeroDeExemplares(livro.getNumeroDeExemplares() - 1);
            salvarLivros();
            return true;
        }

        return false;
    }

    public synchronized boolean devolverLivro(String nomeLivro) {
        Optional<Livro> livroOptional = livros.stream()
                .filter(livro -> livro.getNome().equalsIgnoreCase(nomeLivro))
                .findFirst();

        if (livroOptional.isPresent()) {
            Livro livro = livroOptional.get();
            livro.setNumeroDeExemplares(livro.getNumeroDeExemplares() + 1);
            salvarLivros();
            return true;
        }

        return false;
    }
}
