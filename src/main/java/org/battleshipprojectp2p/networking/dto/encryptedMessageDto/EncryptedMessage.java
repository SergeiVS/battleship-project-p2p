package org.battleshipprojectp2p.networking.dto.encryptedMessageDto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.MessagePayload;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AuthenticationPayload.class),
        @JsonSubTypes.Type(value = PublicKeyPayload.class),
        @JsonSubTypes.Type(value = MessagePayload.class)
})
public interface EncryptedMessage {
    EncryptedMessageType type();
}
