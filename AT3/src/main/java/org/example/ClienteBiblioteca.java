package org.example;

import java.io.*;
import java.net.Socket;

public class ClienteBiblioteca {
    private final String endereco;
    private final int porta;

    public ClienteBiblioteca(String endereco, int porta) {
        this.endereco = endereco;
        this.porta = porta;
    }

    public void iniciar() {
        try (Socket socket = new Socket(endereco, porta);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            String linha;
            while ((linha = console.readLine()) != null) {
                writer.println(linha);
                System.out.println(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: java ClienteBiblioteca <endereco> <porta>");
            return;
        }

        String endereco = args[0];
        int porta = Integer.parseInt(args[1]);

        ClienteBiblioteca cliente = new ClienteBiblioteca(endereco, porta);
        cliente.iniciar();
    }
}
