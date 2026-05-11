package org.battleshipprojectp2p.networking.dto.encryptedMessageDto;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EncryptedMessageType {
    AUTHENTICATION_PAYLOAD,
    PUBLIC_KEY_PAYLOAD,
    ATTACK,
    ATTACK_RESPONSE,
    COIN_FLIP,
    CONNECT,
    ERROR,
    GAME_OVER,
    GAME_SETUP_DATA,
    READY

}
