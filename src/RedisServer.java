import java.net.*;
import java.io.*;
import java.util.*;

public class RedisServer {
    private ServerSocket server;
    private Socket client;
    private int port;

    public RedisServer(int port) throws IOException {
        this.port = port;
        this.server = new ServerSocket(port);
    }

    public void connect() throws IOException {
        this.client = server.accept();
    }

    public List<String> registerClientMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(this.client.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        ArrayList<String> commands = new ArrayList<>();
        int i = 0;
        while (bf.ready()) {
            String command = bf.readLine();
            System.out.println("Message received: " + i + " " + command);
            commands.add(command);
            i ++;
        }
        return commands;
    }

    public static void main(String[] args) throws IOException {
        RedisServer server = new RedisServer(8000);
        server.connect();
        System.out.println("Client connected at port" + server.port);
        List<String> commands = server.registerClientMessage();
        for (String command : commands) {
            System.out.println(command);
        }
    }
}