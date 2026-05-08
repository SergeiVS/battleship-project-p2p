package org.battleshipprojectp2p.networking.dto.gameLoopDto;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PayloadType {
    ATTACK,
    ATTACK_RESPONSE,
    COIN_FLIP,
    CONNECT,
    ERROR,
    GAME_OVER,
    GAME_SETUP_DATA,
    READY;
}
