package com.commerce.team.chat.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatMessage {

    private String content;
    private String sender;
    private MessageType type;

    @Builder
    public ChatMessage(String content, String sender, MessageType type) {
        this.content = content;
        this.sender = sender;
        this.type = type;
    }
}
