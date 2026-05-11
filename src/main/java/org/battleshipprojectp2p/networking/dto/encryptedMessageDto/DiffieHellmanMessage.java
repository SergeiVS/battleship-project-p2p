package org.battleshipprojectp2p.networking.dto.encryptedMessageDto;

public record DiffieHellmanMessage(
        String publicValue
) implements ServerMessage {
}
