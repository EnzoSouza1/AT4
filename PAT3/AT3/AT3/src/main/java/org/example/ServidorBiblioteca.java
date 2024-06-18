package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorBiblioteca {
    private final Biblioteca biblioteca;
    private final int porta = 12345; // Porta fixa especificada aqui
    private final ExecutorService pool;

    public ServidorBiblioteca(String caminhoArquivoJson) {
        this.biblioteca = new Biblioteca(caminhoArquivoJson);
        this.pool = Executors.newFixedThreadPool(4);
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket socket = serverSocket.accept();
                ManipuladorCliente manipulador = new ManipuladorCliente(socket, biblioteca);
                pool.execute(manipulador);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java ServidorBiblioteca <caminhoArquivoJson>");
            return;
        }

        String caminhoArquivoJson = args[0];

        ServidorBiblioteca servidor = new ServidorBiblioteca(caminhoArquivoJson);
        servidor.iniciar();
    }
}
