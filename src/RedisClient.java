import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class RedisClient {
    private Socket client;
    private int port;

    public RedisClient(int port) throws IOException {
        this.port = port;
        this.client = new Socket("localhost", port);
    }

    private void sendRequest(ArrayList<String> commands) throws IOException {
        PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
        for (String command : commands) {
            out.println(command);
        }
    }

    public static void main(String[] args) throws IOException {
        RedisClient client = new RedisClient(8000);
        System.out.println("Client connected at port" + client.port);

        ArrayList<String> commands = new ArrayList<>();
        commands.add("SET key value");
        commands.add("GET key");
        client.sendRequest(commands);
    }
}