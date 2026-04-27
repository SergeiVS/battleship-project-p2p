package org.battleshipprojectp2p.networking.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

public class ClientSocket extends Thread implements Closeable {
    private final Socket socket;
    private final Consumer<String> handleMessage;
    private final String startMsg;
    private PrintWriter out;


    public ClientSocket(Socket socket, Consumer<String> handleMessage, String startMsg) throws SocketException, InterruptedException {
        this.socket = socket;
        this.startMsg = startMsg;
        this.socket.setKeepAlive(true);
        this.handleMessage = handleMessage;
        this.join();
    }

    public ClientSocket(String ip, int port, Consumer<String> handleMessage, String startMsg) throws IOException, InterruptedException {
        this.socket = new Socket(ip, port);
        this.startMsg = startMsg;
        this.socket.setKeepAlive(true);
        this.handleMessage = handleMessage;
        this.join();
    }

    @Override
    public void run() {
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.sendMessage(startMsg);
            while (!this.isInterrupted()) {
                var message = in.readLine();
                if (message != null) {
                    handleMessage.accept(message);
                }
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String s) throws IOException {
        if (out != null) {
            out.println(s);
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
        this.interrupt();
    }
}
