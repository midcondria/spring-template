package com.commerce.team.chat.ui;

import com.commerce.team.chat.application.ChatService;
import com.commerce.team.chat.domain.ChatMessage;
import com.commerce.team.chat.domain.MessageType;
import com.commerce.team.chat.dto.ChatRoomListResponse;
import com.commerce.team.chat.dto.CreateChatRoomRequest;
import com.commerce.team.global.config.security.UserPrincipal;
import com.commerce.team.product.Product;
import com.commerce.team.product.ProductRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ProductRepository productRepository;

    // @Service
    // EventListenr
    // publish()
//    private final SimpMessageSendingOperations messageTemplate;

    @GetMapping("/rooms")
    public List<ChatRoomListResponse> getChatRoomList() {

        return chatService.getMyRoomList(1L);
    }

    @PostMapping("/rooms")
    public void createRoom(@RequestBody CreateChatRoomRequest request) {
        productRepository.save(Product.builder().name("나는짱물건").build());
        chatService.createRoom(request);
    }

    @GetMapping("/chat-list")
    public String[] get(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUserId();
        // chatroomRepo.findby
        // 1~10
        return new String[]{"1","2","3"};
    }

    public ChatMessage createMessage() {
        return ChatMessage.builder()
            .sender("예전 메시지 보낸사람1")
            .content("예전 메시지1")
            .type(MessageType.CHAT)
            .build();
    }

    @MessageMapping("/chat.sendMessage/{roomId}") // 프론트가 publish 해주는 백엔드 엔드포인트
    @SendTo("/topic/{roomId}")   // 백엔드에서 웹소켓으로 메시지를 프론트로 보내주는 구독 url
    public ChatMessage sendMessageToOne(@Payload ChatMessage chatMessage, @DestinationVariable String roomId) {
        log.info("[메시지 전송 = {}] chatMessage = {}", roomId,chatMessage);
        log.info("id = {}", roomId);
        // 채팅방 10개 ->
        // chatRepo.save()
        // 트랜잭셔널 애프터 이벤트
        // service.publish()
        return chatMessage;
    }

//    @MessageMapping("/chat.sendMessage/{roomId}")
//    @SendTo("/topic/{roomId}")
//    public DTO sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String roomId) {
//        log.info("[메시지 전송] chatMessage = {}", chatMessage);
//        return new DTO(MessageType.RECONNECT, new ChatMessage[]{chatMessage, chatMessage, chatMessage});
//    }

    @Getter
    static class DTO {
        private MessageType type;
        private ChatMessage[] messages;

        public DTO(MessageType type, ChatMessage[] messages) {
            this.messages = messages;
            this.type = type;
        }
    }

    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/topic/{roomId}")
    public DTO addUser(@Payload ChatMessage chatMessage,@DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in websocket session
        log.info("[유저 입장] chatMessage = {}", chatMessage);
        log.info("[유저 입장] id = {}", roomId);
        ChatMessage message = createMessage();
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return new DTO(MessageType.JOIN, new ChatMessage[]{message, message, message});
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
