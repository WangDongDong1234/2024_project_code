package com.wdd.mybatch.core.message;

import com.wdd.mybatch.core.enums.MessageTypeEnum;

public interface MessagePublisher {

    <T> void sendMessage(MessageTypeEnum messageTypeEnum ,T object);
}
