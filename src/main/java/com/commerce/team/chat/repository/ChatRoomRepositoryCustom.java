package com.commerce.team.chat.repository;

import com.commerce.team.chat.dto.ChatRoomListResponse;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoomListResponse> getMyRoomList(Long userId);
}
