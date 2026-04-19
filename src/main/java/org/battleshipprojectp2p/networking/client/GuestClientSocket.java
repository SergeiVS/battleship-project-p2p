package org.battleshipprojectp2p.networking.client;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class GuestClientSocket extends Thread implements Closeable {
    private final Socket socket;
    private final Consumer<String> messageHandler;

    private BufferedReader in;
    private PrintWriter out;

    public GuestClientSocket(String ip, int port, Consumer<String> messageHandler) throws IOException, InterruptedException {
        this.messageHandler = messageHandler;
        this.socket = new Socket(ip, port);
        this.socket.setKeepAlive(true);
        this.join();
    }

    public void run() {
        try {
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            while (!this.isInterrupted()) {
                try {
                    final var msg = in.readLine();
                    messageHandler.accept(msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMessage(String s) {
        if (this.out != null) {
            IO.println("Client sends: " + s);
            this.out.println(s);
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.println("Connection Closes");
        out.close();
        socket.close();
        this.interrupt();
    }
}
