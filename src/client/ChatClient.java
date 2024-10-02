package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void start(String serverAddress, int port) {
        try{
            socket = new Socket(serverAddress, port);  // Connect to the server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Initialize input stream
            out = new PrintWriter(socket.getOutputStream(), true);  // Initialize output stream

            // Start a thread to handle incoming messages
            new Thread(new IncomingMessageHandler(in)).start();

            // Read input from the user and send messages to the server
            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to the chat server. Type your messages below:");

            while(scanner.hasNextLine()) {
                String message = scanner.nextLine();
                if(message.equalsIgnoreCase("/exit")) {
                    break;  // Exit on user command
                }
                out.println(message); // Send the message to the server
            }
            closeConnection(); // Clean up when done

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (socket != null) {
                socket.close();  // Close the socket connection
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server address (localhost for local server): ");
        String serverAddress = scanner.nextLine();
        int port = 12345;

        ChatClient client = new ChatClient();
        client.start(serverAddress, port);  // Start the client and connect to the server
    }

}
