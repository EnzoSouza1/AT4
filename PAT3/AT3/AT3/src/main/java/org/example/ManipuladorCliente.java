package org.example;

import java.io.*;
import java.net.Socket;

import com.google.gson.Gson;

public class ManipuladorCliente implements Runnable {
    private final Socket socket;
    private final Biblioteca biblioteca;

    public ManipuladorCliente(Socket socket, Biblioteca biblioteca) {
        this.socket = socket;
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" ", 2);
                String comando = partes[0];

                switch (comando) {
                    case "LISTAR":
                        writer.println(biblioteca.listarLivros());
                        break;
                    case "ADICIONAR":
                        Livro livro = new Gson().fromJson(partes[1], Livro.class);
                        boolean adicionado = biblioteca.adicionarLivro(livro);
                        writer.println(adicionado ? "Livro adicionado" : "Falha ao adicionar livro");
                        break;
                    case "ALUGAR":
                        boolean alugado = biblioteca.alugarLivro(partes[1]);
                        writer.println(alugado ? "Livro alugado" : "Falha ao alugar livro");
                        break;
                    case "DEVOLVER":
                        boolean devolvido = biblioteca.devolverLivro(partes[1]);
                        writer.println(devolvido ? "Livro devolvido" : "Falha ao devolver livro");
                        break;
                    default:
                        writer.println("Comando desconhecido");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
