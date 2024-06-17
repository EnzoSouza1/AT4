import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorBiblioteca {
    private static final int PORTA = 12345;
    private Biblioteca biblioteca;
    private ExecutorService pool;

    public ServidorBiblioteca(String caminhoArquivoJson) {
        biblioteca = new Biblioteca(caminhoArquivoJson);
        pool = Executors.newCachedThreadPool();
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor da biblioteca iniciado na porta " + PORTA);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ManipuladorCliente manipuladorCliente = new ManipuladorCliente(clientSocket, biblioteca);
                pool.execute(manipuladorCliente);
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
        ServidorBiblioteca servidor = new ServidorBiblioteca(args[0]);
        servidor.iniciar();
    }
}
