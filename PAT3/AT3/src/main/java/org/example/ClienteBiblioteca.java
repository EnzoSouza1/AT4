import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClienteBiblioteca {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClienteBiblioteca() {
        try {
            socket = new Socket(HOST, PORTA);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listarLivros() {
        try {
            out.writeObject("listar");
            List<Livro> livros = (List<Livro>) in.readObject();
            livros.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void alugarLivro(String nomeLivro) {
        try {
            out.writeObject("alugar");
            out.writeObject(nomeLivro);
            String resposta = (String) in.readObject();
            System.out.println(resposta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void devolverLivro(String nomeLivro) {
        try {
            out.writeObject("devolver");
            out.writeObject(nomeLivro);
            String resposta = (String) in.readObject();
            System.out.println(resposta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void adicionarLivro(Livro livro) {
        try {
            out.writeObject("adicionar");
            out.writeObject(livro);
            String resposta = (String) in.readObject();
            System.out.println(resposta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClienteBiblioteca cliente = new ClienteBiblioteca();
        // Exemplos de uso
        cliente.listarLivros();
        cliente.adicionarLivro(new Livro("Autor Desconhecido", "Novo Livro", "Ficção", 5));
        cliente.alugarLivro("Novo Livro");
        cliente.devolverLivro("Novo Livro");
        cliente.listarLivros();
    }
}
