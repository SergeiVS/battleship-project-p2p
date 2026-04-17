package org.battleshipprojectp2p.service.mappers;

import org.battleshipprojectp2p.networking.networkingDto.BaseMessage;
import org.battleshipprojectp2p.networking.networkingDto.MessagePayload;

public class BaseMessageMapper {
    public BaseMessage buildMessage(MessagePayload payload) {
        var type = payload.type();
        return new BaseMessage(type, payload);
    }
}
