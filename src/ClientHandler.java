import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler extends Thread {
    private Socket client;
    private HashMap<String, String> store;

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.store = new HashMap<>();
    }

    public List<String> registerClientMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(this.client.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        ArrayList<String> commands = new ArrayList<>();

        String command = bf.readLine();
        commands.add(command);

        while (bf.ready()) {
            command = bf.readLine();
            commands.add(command);
        }
        return commands;
    }

    public void executeCommands(List<String> commands) throws IOException {
        for (String command: commands) {
            String[] commandParts = command.split(" ");
            String commandName = commandParts[0];
            PrintWriter out = new PrintWriter(this.client.getOutputStream());
            String key;
            String value;
            // System.out.println("command: " + commandName);
            switch (commandName) {
                case "SET":
                    key = commandParts[1];
                    value = commandParts[2];
                    this.store.put(key, value);
                    out.println("OK");
                    out.flush();
                    break;

                case "GET":
                    key = commandParts[1];
                    value = this.store.get(key);
                    if (value == null) {
                        out.println("nil");
                    } else {
                        out.println(value);
                    }
                    out.flush();    
                    break;

                case "PING":
                    out.println("PONG");
                    out.flush();
                    break;

                default:
                    System.out.println("Command not recognized");
            }
        }
    }

    public void run() {
        try {
            System.out.println("client connected");
            List<String> commands = this.registerClientMessage();
            for (String command : commands) {
                System.out.println("client: " + command);
            }
            this.executeCommands(commands);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            try {
                this.client.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}