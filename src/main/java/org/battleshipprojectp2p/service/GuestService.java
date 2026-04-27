package org.battleshipprojectp2p.service;

import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.networking.client.ClientSocket;
import org.battleshipprojectp2p.networking.networkingDto.ConnectionMessage;
import org.battleshipprojectp2p.networking.networkingDto.GameSetupMessage;
import org.battleshipprojectp2p.networking.networkingDto.MessagePayload;
import org.battleshipprojectp2p.service.dto.GuestSetupDto;

import java.io.IOException;

public class GuestService extends AbstractService {
    private final String name;

    public GuestService(boolean isHost, GuestSetupDto setup) throws IOException, InterruptedException {
        super(isHost);
        final var baseMsg = messageMapper.buildMessage(new ConnectionMessage(setup.name()));
        final var startMsg = jsonMapper.toJson(baseMsg);
        this.name = setup.name();
        final var session = new ClientSocket(setup.ip(), setup.port(), this::handleIncomingMessage, startMsg);
        session.start();
        setSession(session);
    }

    @Override
    public void createGame(MessagePayload payload) {

        if (payload instanceof GameSetupMessage(int rows, int columns, String host)) {
            final var player = new Player(name);
            final var opponent = new Player(host);
            final var gameSetup = new GameSetup(player, opponent, rows, columns, getIsHost());
            setGame(gameSetup);
        }
    }

    @Override
    public void closeConnection() {
        if (session != null) {
            try {
                this.session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (getGame() != null) {
            closeGame();
        }
    }
}
