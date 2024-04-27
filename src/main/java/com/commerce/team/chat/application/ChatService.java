package com.commerce.team.chat.application;

import com.commerce.team.chat.dto.ChatRoomListResponse;
import com.commerce.team.chat.dto.CreateChatRoomRequest;
import com.commerce.team.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public void createRoom(CreateChatRoomRequest request) {
        chatRoomRepository.save(request.toChatRoom());
    }

    public List<ChatRoomListResponse> getMyRoomList(Long userId) {
        return chatRoomRepository.getMyRoomList(userId);
    }
}
