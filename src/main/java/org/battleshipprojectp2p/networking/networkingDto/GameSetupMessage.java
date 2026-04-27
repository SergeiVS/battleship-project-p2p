package org.battleshipprojectp2p.networking.networkingDto;

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
    public PayloadType type() {
        return PayloadType.GAME_SETUP_DATA;
    }
}
