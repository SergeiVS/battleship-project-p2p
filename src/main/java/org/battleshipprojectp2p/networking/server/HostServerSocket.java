package org.battleshipprojectp2p.networking.server;

import org.battleshipprojectp2p.networking.client.ClientSocket;
import org.battleshipprojectp2p.networking.networkingDto.HostServerAddress;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public class HostServerSocket implements Closeable {
    private static final int PORT = 8080;

    private final ServerSocket server;
    private ClientSocket session;

    public HostServerSocket(Consumer<String> messageHandler, String startMessage) {
        try {
            this.server = new ServerSocket(PORT);

            CompletableFuture.runAsync(() -> {
                try {
                    openSession(messageHandler, startMessage);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openSession(Consumer<String> messageHandler, String startMessage) throws IOException, InterruptedException {
        this.session = new ClientSocket(server.accept(), messageHandler, startMessage);
        this.session.start();
    }

    public HostServerAddress getConnectionsData() {
        return new HostServerAddress(server.getInetAddress().toString(), server.getLocalPort());
    }

    public ClientSocket getHostClient() {
        return this.session;
    }

    @Override
    public void close() throws IOException {
        if (session != null) {
            session.close();
        }
        server.close();
    }
}
