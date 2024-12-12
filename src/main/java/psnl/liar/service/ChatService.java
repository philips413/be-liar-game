package psnl.liar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import psnl.liar.entity.Chat;
import psnl.liar.payload.dto.CreateChatRoomDto;
import psnl.liar.repository.ChatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public List<Chat> getRooms() {
        List<Chat> rooms = chatRepository.findAll();
        return rooms;

    }

    public Chat createRoom(CreateChatRoomDto request) {
        Chat entity = Chat.creator()
                .title(request.getRoomName())
                .leader(request.getLeader().toString())
                .participants(request.getLimit())
                .build();
        return chatRepository.save(entity);
    }
}
