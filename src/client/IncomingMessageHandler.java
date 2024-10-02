package client;

import java.io.BufferedReader;
import java.io.IOException;

public class IncomingMessageHandler implements Runnable {

    private final BufferedReader in;  // Input stream to read messages

    public IncomingMessageHandler(BufferedReader in) {
        this.in = in;  // Initialize the input stream
    }

    @Override
    public void run() {
        String message;
        try {
            // Continuously listen for incoming messages
            while((message = in.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
