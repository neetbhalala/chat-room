package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final Set<ClientHandler> clientHandlers;

    public ClientHandler(Socket socket, Set<ClientHandler> clientHandlers) throws IOException {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }


    @Override
    public void run() {
        String message;
        try {
            broadcastMessage("New Client has joined the chat.");

            // Listen for incoming messages from the client
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message); // Print received message
                broadcastMessage(message); // Broadcast the message to other clients
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
    }

    // Broadcast a message to all connected clients, except the sender
    private void broadcastMessage(String message) {
        synchronized (clientHandlers) {
            for (ClientHandler client : clientHandlers) {
                if (client != this) {   // Don't send the message to the sender
                    client.out.println(message);
                }
            }
        }
    }

    // Clean up resources when a client disconnects
    private void closeConnection() {
        try {
            clientHandlers.remove(this); // Remove this client from the set
            broadcastMessage("A client has left the chat."); // Notify others
            socket.close(); // Close the socket connection
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
