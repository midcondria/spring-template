package com.commerce.team.chat.dto;

import com.commerce.team.chat.domain.ChatRoom;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateChatRoomRequest {

    @NotNull
    private Long buyerId;

    @NotNull
    private Long sellerId;

    @NotNull
    private Long productId;

    @Builder
    public CreateChatRoomRequest(Long sellerId, Long buyerId, Long productId) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.productId = productId;
    }

    public ChatRoom toChatRoom() {
        return ChatRoom.builder()
            .buyerId(buyerId)
            .sellerId(sellerId)
            .productId(productId)
            .build();
    }
}
