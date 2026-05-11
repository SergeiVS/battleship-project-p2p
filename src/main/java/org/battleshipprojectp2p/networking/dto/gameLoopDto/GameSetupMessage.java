package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import org.battleshipprojectp2p.networking.dto.encryptedMessageDto.EncryptedMessageType;
import org.battleshipprojectp2p.service.dto.HostSetupDto;

import java.io.Serializable;

public record GameSetupMessage(
        int rows,
        int columns,
        String host
) implements MessagePayload, Serializable {

    public GameSetupMessage(HostSetupDto setup) {
        this(setup.rows(), setup.cols(), setup.name());
    }

    @Override
    public EncryptedMessageType type() {
        return EncryptedMessageType.GAME_SETUP_DATA;
    }
}
