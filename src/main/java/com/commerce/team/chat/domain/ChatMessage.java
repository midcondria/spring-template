package com.commerce.team.chat.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatMessage {

    private String content;
    private String sender;
    private MessageType type;
}
