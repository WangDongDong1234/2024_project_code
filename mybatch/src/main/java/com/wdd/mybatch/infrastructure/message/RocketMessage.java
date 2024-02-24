package com.wdd.mybatch.infrastructure.message;

import com.wdd.mybatch.core.enums.MessageTypeEnum;
import com.wdd.mybatch.core.message.MessagePublisher;

public class RocketMessage implements MessagePublisher {
    @Override
    public <T> void sendMessage(MessageTypeEnum messageTypeEnum, T object) {

    }
}
