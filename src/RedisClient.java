import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class RedisClient {
    private Socket client;
    private int port;

    public RedisClient(int port) throws IOException {
        this.port = port;
        this.client = new Socket("localhost", port);
    }

    private void sendRequest(List<String> commands) throws IOException {
        PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
        for (String command : commands) {
            out.println(command);
        }
    }

    public List<String> registerServerMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(this.client.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        
        List<String> responses = new ArrayList<>();
        String response = bf.readLine();
        responses.add(response);

        while (bf.ready()) {
            response = bf.readLine();
            responses.add(response);
        }
        return responses;
    }

    public static void main(String[] args) throws IOException {
        RedisClient client = new RedisClient(8000);
        System.out.println("Client connected at port: " + client.port);

        ArrayList<String> commands = new ArrayList<>();
        commands.add("PING");
        commands.add("PING");
        commands.add("SET key value");
        commands.add("GET key");
        commands.add("GET key2");
        client.sendRequest(commands);
        
        List<String> responses = client.registerServerMessage();
        for (String response : responses) {
            System.out.println("server: " + response);
        }
    }
}