package org.battleshipprojectp2p.service;

import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.networking.client.GuestClientSocket;
import org.battleshipprojectp2p.networking.networkingDto.ConnectionMessage;
import org.battleshipprojectp2p.networking.networkingDto.GameSetupMessage;
import org.battleshipprojectp2p.networking.networkingDto.MessagePayload;
import org.battleshipprojectp2p.service.dto.GuestSetupDto;
import org.battleshipprojectp2p.service.mappers.BaseMessageMapper;
import org.battleshipprojectp2p.service.mappers.JSONMapper;

import java.io.IOException;

public class GuestService extends AbstractService {

    private final GuestClientSocket session;

    private final GuestSetupDto setup;

    private final BaseMessageMapper messageMapper = new BaseMessageMapper();
    private final JSONMapper jsonMapper = new JSONMapper();

    public GuestService(boolean isHost, GuestSetupDto setup) throws IOException, InterruptedException {
        super(isHost);
        this.session = new GuestClientSocket(setup.ip(), setup.port(), this::handleIncomingMessage);
        this.setup = setup;
        this.session.start();
        final var baseMsg = messageMapper.buildMessage(new ConnectionMessage(setup.name()));
        final var startMsg = jsonMapper.toJson(baseMsg);
        this.session.sendMessage(startMsg);
    }

    public void handleIncomingMessage(String msg) {

        final var message = jsonMapper.toBaseMessage(msg);

        switch (message.type()) {
            case GAME_SETUP_DATA -> createGame(message.payload());
        }

    }

    public void createGame(MessagePayload payload) {
        if (payload instanceof GameSetupMessage(int rows, int columns, String host)) {
            final var player = new Player(setup.name());
            final var opponent = new Player(host);
            final var gameSetup = new GameSetup(player, opponent, rows, columns, setup.isHost());
            setGame(gameSetup);
        }
    }
}
