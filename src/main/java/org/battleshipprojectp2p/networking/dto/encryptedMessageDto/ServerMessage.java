package org.battleshipprojectp2p.networking.dto.encryptedMessageDto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SignedMessage.class),
        @JsonSubTypes.Type(value = DiffieHellmanMessage.class),
})
public interface ServerMessage {
}
