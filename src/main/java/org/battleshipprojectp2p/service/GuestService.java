package org.battleshipprojectp2p.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.battleshipprojectp2p.game.gameDto.GameSetup;
import org.battleshipprojectp2p.game.player.Player;
import org.battleshipprojectp2p.networking.client.GuestClientSocket;
import org.battleshipprojectp2p.networking.networkingDto.ConnectionMessage;
import org.battleshipprojectp2p.networking.networkingDto.GameSetupMessage;
import org.battleshipprojectp2p.networking.networkingDto.MessagePayload;
import org.battleshipprojectp2p.service.dto.GuestSetupDto;

import java.io.IOException;

public class GuestService extends AbstractService {

    private final GuestClientSocket session;

    private final GuestSetupDto setup;

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
        try {
            switch (message.type()) {
                case GAME_SETUP_DATA -> createGame(message.payload());
                case COIN_FLIP -> handleOpponentsCoinFlip(message.payload());
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void createGame(MessagePayload payload) throws JsonProcessingException {

        if (payload instanceof GameSetupMessage(int rows, int columns, String host)) {
            final var player = new Player(setup.name());
            final var opponent = new Player(host);
            final var gameSetup = new GameSetup(player, opponent, rows, columns, setup.isHost());
            setGame(gameSetup);
            session.sendMessage(getCoinFlipMessageJson());
        }
    }
}
