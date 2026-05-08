package org.battleshipprojectp2p.service;

import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.ConnectionMessage;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.GameSetupMessage;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.HostServerAddress;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.MessagePayload;
import org.battleshipprojectp2p.networking.server.HostServerSocket;
import org.battleshipprojectp2p.service.dto.HostSetupDto;

import java.io.IOException;


public class HostService extends AbstractService {

    private HostServerSocket server;
    private final String name;
    private final int rows;
    private final int cols;

    public HostService(boolean isHost, HostSetupDto setup) {
        super(isHost);

        this.name = setup.name();
        this.rows = setup.rows();
        this.cols = setup.cols();
        buildConnection(setup);
    }

    public void buildConnection(HostSetupDto setup) {
        final var setupPayload = new GameSetupMessage(setup);
        if (this.server == null) {
            try {
                var baseMsg = messageMapper.buildMessage(setupPayload);
                var startMsg = jsonMapper.baseMessageToJson(baseMsg);
                this.server = new HostServerSocket(this::handleIncomingMessage, startMsg);
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    protected synchronized void createGame(MessagePayload message) {
        if (this.session == null) {
            setSession(server.getHostClient());
        }

        if (message instanceof ConnectionMessage(String user)) {
            final var player = new Player(this.name);
            final var opponent = new Player(user);
            final var gameSetup = new GameSetup(player, opponent, this.rows, this.cols, getIsHost());
            setGame(gameSetup);
        }
    }

    public HostServerAddress getConnectionData() {
        return server.getConnectionsData();
    }

    @Override
    public void closeConnection() {
        try {
            this.server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        closeGame();
    }
}
