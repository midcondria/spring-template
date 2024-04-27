package com.commerce.team.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomListResponse {

    private Long roomId;

    private UserInfo opponentUser;

    private ProductInfo productInfo;

    public ChatRoomListResponse(Long roomId, UserInfo opponentUser, ProductInfo productInfo) {
        this.roomId = roomId;
        this.opponentUser = opponentUser;
        this.productInfo = productInfo;
    }
}
