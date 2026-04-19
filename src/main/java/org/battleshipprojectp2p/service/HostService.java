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

import java.io.IOException;


public class HostService extends AbstractService {

    private HostServerSocket server;
    private HostClientSocket session;
    private final HostSetupDto setup;

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
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleIncomingMessage(String msg) {
        final var message = jsonMapper.toBaseMessage(msg);
        try {
            switch (message.type()) {
                case CONNECT -> createGame(message.payload());
                case COIN_FLIP -> handleOpponentsCoinFlip(message.payload());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void createGame(MessagePayload message) throws IOException {
        if (this.session == null) {
            this.session = server.getHostClient();
        }

        if (message instanceof ConnectionMessage(String user)) {
            final var player = new Player(setup.name());
            final var opponent = new Player(user);
            final var gameSetup = new GameSetup(player, opponent, setup.rows(), setup.cols(), setup.isHost());
            setGame(gameSetup);
            session.sendMessage(getCoinFlipMessageJson());
        }
    }

    public HostServerAddress getConnectionData() {
        return server.getConnectionsData();
    }
}
