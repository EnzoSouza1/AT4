import java.io.*;
import java.net.Socket;
import java.util.List;

public class ManipuladorCliente implements Runnable {
    private Socket clientSocket;
    private Biblioteca biblioteca;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ManipuladorCliente(Socket clientSocket, Biblioteca biblioteca) {
        this.clientSocket = clientSocket;
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            String comando;
            while ((comando = (String) in.readObject()) != null) {
                switch (comando.toLowerCase()) {
                    case "listar":
                        listarLivros();
                        break;
                    case "alugar":
                        alugarLivro();
                        break;
                    case "devolver":
                        devolverLivro();
                        break;
                    case "adicionar":
                        adicionarLivro();
                        break;
                    default:
                        out.writeObject("Comando desconhecido");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void listarLivros() throws IOException {
        List<Livro> livros = biblioteca.listarLivros();
        out.writeObject(livros);
    }

    private void alugarLivro() throws IOException, ClassNotFoundException {
        String nomeLivro = (String) in.readObject();
        boolean sucesso = biblioteca.alugarLivro(nomeLivro);
        out.writeObject(sucesso ? "Livro alugado com sucesso" : "Livro não disponível");
    }

    private void devolverLivro() throws IOException, ClassNotFoundException {
        String nomeLivro = (String) in.readObject();
        boolean sucesso = biblioteca.devolverLivro(nomeLivro);
        out.writeObject(sucesso ? "Livro devolvido com sucesso" : "Livro não encontrado");
    }

    private void adicionarLivro() throws IOException, ClassNotFoundException {
        Livro livro = (Livro) in.readObject();
        boolean sucesso = biblioteca.adicionarLivro(livro);
        out.writeObject(sucesso ? "Livro adicionado com sucesso" : "Falha ao adicionar livro");
    }
}
