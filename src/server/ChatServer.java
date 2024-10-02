package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    private static final Set<ClientHandler> clientHandlers = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        int port = 12345; // Server listens on this port

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat server started on port " + port);

            while(true) {
                Socket socket = serverSocket.accept();  // Accept a new client connection
                System.out.println("New Client connected: " + socket.getInetAddress());

                // Create a new ClientHandler thread for each new connection
                ClientHandler clientHandler = new ClientHandler(socket, clientHandlers);
                clientHandlers.add(clientHandler);  // Add to the set of client handlers
                new Thread(clientHandler).start();  // Start the thread to handle client communication
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
