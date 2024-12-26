package psnl.liar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import psnl.liar.entity.Chat;
import psnl.liar.entity.Participants;
import psnl.liar.entity.Room;
import psnl.liar.model.ChatMessage;
import psnl.liar.payload.dto.CreateChatRoomDto;
import psnl.liar.payload.dto.request.ParticipantChatRoomRequest;
import psnl.liar.payload.dto.request.ParticipantsRequest;
import psnl.liar.repository.ChatRepository;
import psnl.liar.repository.ParticipantsRepository;
import psnl.liar.repository.RoomRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final RoomRepository roomRepository;

    private final ParticipantsRepository participantsRepository;

    public List<Chat> getRooms() {
        List<Chat> rooms = chatRepository.findAll();
        return rooms;

    }

    public Chat createRoom(CreateChatRoomDto request) {
        Chat entity = Chat.creator()
                .title(request.getName())
                .leader(request.getLeader().toString())
                .participants(request.getLimit())
                .build();
        return chatRepository.save(entity);
    }

    public Participants participateChat(ParticipantChatRoomRequest request) {
        Participants participants = participantsRepository.findById(Integer.valueOf(request.getPartId()))
                .orElseThrow(() -> new IllegalArgumentException("참여자가 존재하지 않습니다."));

        return participants;

    }

    public List<ChatMessage> gameStart(String id) {

        // 제시어 사과 / 자두
        // ### 참여자 목록을 불러온다.
        List<String> collect = roomRepository.findAllByChatId(id)
                .stream()
                .map(Room::getPartId)
                .collect(Collectors.toList());

        int size = collect.size();
        int findNum = new Random().nextInt(size);
        List<ChatMessage> list = new ArrayList<>();
        for(int i = 0 ; i < size ; i++) {
            if (i == findNum) {
                list.add(new ChatMessage(id, collect.get(i), "사과"));
            } else {
                list.add(new ChatMessage(id, collect.get(i), "자두"));
            }
        }
        // ### 목록 중 랜덤한 인물으 선택하여 다른 단어를 준다.
        return list;
    }

    public void enterRoom(String chatId, ParticipantsRequest request) {
        System.out.println("====== 참가쓰!!! =====");
        Room entity = Room.creator()
                .partId(request.getPartId())
                .chatId(request.getChatId())
                .build();

        roomRepository.save(entity);
    }
}
