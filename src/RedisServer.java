import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.*;

public class RedisServer {
    private ServerSocket serverSocket;
    public RedisServer(int port) throws IOException {
        // Initialize Redis server
        this.serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        RedisServer server = new RedisServer(8000);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        
        while (true) {
            Socket clientSocket = server.serverSocket.accept();
            executor.execute(new ClientHandler(clientSocket));
        }
    }
}