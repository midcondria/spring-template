package com.commerce.team.chat.ui;

import com.commerce.team.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        System.out.println(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in websocket session
        System.out.println(chatMessage);
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }


//    private final SimpMessagingTemplate messagingTemplate;

//    @GetMapping("/connect")
//    public String connect() {
//        // 클라이언트에게 CONNECT 이벤트 발생시키기
//        messagingTemplate.convertAndSend("/test", "서버와 연결되었습니다.");
//        return "connected";
//    }
//
//    @GetMapping("/chat")
//    public LocalDateTime chat(String message) {
//        LocalDateTime currentTime = LocalDateTime.now();
//        return currentTime;
//    }
//
//    @PostMapping("/chat")
//    public String receiveChat(@RequestBody String message) {
//        System.out.println(message);
//        return message;
//    }
//
//    @MessageMapping("/message")
//    public String handleMessage(@Payload String message) {
//        System.out.println(message);
//        System.out.println("handleMessage");
//        messagingTemplate.convertAndSend("/test", message);
//        return message;
//    }
}
