package org.battleshipprojectp2p.service;

import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.networking.networkingDto.ConnectionMessage;
import org.battleshipprojectp2p.networking.networkingDto.GameSetupMessage;
import org.battleshipprojectp2p.networking.networkingDto.HostServerAddress;
import org.battleshipprojectp2p.networking.networkingDto.MessagePayload;
import org.battleshipprojectp2p.networking.server.HostClientSocket;
import org.battleshipprojectp2p.networking.server.HostServerSocket;
import org.battleshipprojectp2p.service.dto.HostSetupDto;
import org.battleshipprojectp2p.service.mappers.BaseMessageMapper;
import org.battleshipprojectp2p.service.mappers.JSONMapper;

import java.io.IOException;


public class HostService extends AbstractService {

    private HostServerSocket server;
    private HostClientSocket session;
    private final HostSetupDto setup;
    private final BaseMessageMapper messageMapper = new BaseMessageMapper();
    private final JSONMapper jsonMapper = new JSONMapper();

    public HostService(boolean isHost, HostSetupDto setup) throws IOException {
        super(isHost);
        this.setup = setup;
        buildConnection(setup);
    }

    public void buildConnection(HostSetupDto setup) {
        final var setupPayload = new GameSetupMessage(setup);
        if (this.server == null) {
            try {
                var baseMsg = messageMapper.buildMessage(setupPayload);
                var startMsg = jsonMapper.toJson(baseMsg);
                this.server = new HostServerSocket(this::handleIncomingMessage, startMsg);
                this.session = server.getHostClient();
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleIncomingMessage(String message) {
        final var msg = jsonMapper.toBaseMessage(message);
        switch (msg.type()) {
            case CONNECT -> createGame(msg.payload());
        }

    }

    private synchronized void createGame(MessagePayload message) {
        if (message instanceof ConnectionMessage(String user)) {
            final var player = new Player(setup.name());
            final var opponent = new Player(user);
            final var gameSetup = new GameSetup(player, opponent, setup.rows(), setup.cols(), setup.isHost());
            setGame(gameSetup);
        }
    }

    public HostServerAddress getConnectionData() {
        return server.getConnectionsData();
    }
}
