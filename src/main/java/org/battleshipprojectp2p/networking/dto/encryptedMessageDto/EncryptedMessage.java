package org.battleshipprojectp2p.networking.dto.encryptedMessageDto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.battleshipprojectp2p.networking.dto.gameLoopDto.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AttackMessage.class),
        @JsonSubTypes.Type(value = AttackResponseMessage.class),
        @JsonSubTypes.Type(value = CoinFlipMessage.class),
        @JsonSubTypes.Type(value = ConnectionMessage.class),
        @JsonSubTypes.Type(value = ErrorMessage.class),
        @JsonSubTypes.Type(value = GameOverMessage.class),
        @JsonSubTypes.Type(value = GameSetupMessage.class),
        @JsonSubTypes.Type(value = ReadyMessage.class),
})
public interface EncryptedMessage {
    EncryptedMessageType type();
}
