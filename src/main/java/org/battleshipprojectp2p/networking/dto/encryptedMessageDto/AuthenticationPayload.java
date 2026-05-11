package org.battleshipprojectp2p.networking.dto.encryptedMessageDto;

public record AuthenticationPayload(
        String challenge
) implements EncryptedMessage {
    @Override
    public EncryptedMessageType type() {
        return EncryptedMessageType.AUTHENTICATION_PAYLOAD;
    }
}
