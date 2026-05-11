package org.battleshipprojectp2p.networking.dto.encryptedMessageDto;


public record EncryptedPayload(
        String payload,
        String iv
) {
}
