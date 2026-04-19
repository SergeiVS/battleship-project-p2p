package org.battleshipprojectp2p.networking.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

public class HostClientSocket extends Thread implements Closeable {
    private final Socket socket;
    private final Consumer<String> handleMessage;

    private final String startMsg;
    private PrintWriter out;
    private BufferedReader in;


    public HostClientSocket(Socket socket, Consumer<String> handleMessage, String startMsg) throws SocketException, InterruptedException {
        this.socket = socket;
        this.startMsg = startMsg;
        this.socket.setKeepAlive(true);
        this.handleMessage = handleMessage;
        this.join();
    }

    public void sendMessage(String s) throws IOException {
        if (out != null) {
            IO.println("Host sends: " + s);
            out.println(s);
        }
    }

    @Override
    public void run() {
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.sendMessage(startMsg);
            while (!this.isInterrupted()) {
                var message = in.readLine();
                if (message != null) {
                    handleMessage.accept(message);
                }
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("Host client socket Error: " + e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
        this.interrupt();
    }
}
