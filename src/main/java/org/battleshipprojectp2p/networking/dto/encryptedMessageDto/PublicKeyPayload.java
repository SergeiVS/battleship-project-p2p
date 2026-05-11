package org.battleshipprojectp2p.networking.dto.encryptedMessageDto;

public record PublicKeyPayload(
        String publicKey,
        String nonce
) implements EncryptedMessage {
    @Override
    public EncryptedMessageType type() {
        return EncryptedMessageType.PUBLIC_KEY_PAYLOAD;
    }
}
