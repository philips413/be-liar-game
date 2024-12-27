package psnl.liar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import psnl.liar.constant.YesOrNo;
import psnl.liar.entity.Chat;
import psnl.liar.entity.Participants;
import psnl.liar.entity.Room;
import psnl.liar.model.ChatMessage;
import psnl.liar.payload.dto.CreateChatRoomDto;
import psnl.liar.payload.dto.ParticipantsDto;
import psnl.liar.payload.dto.WebSocketResponse;
import psnl.liar.repository.ChatRepository;
import psnl.liar.repository.ParticipantsRepository;
import psnl.liar.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public WebSocketResponse gameStart(String chatId) {

        // 제시어 사과 / 자두
        // ### 참여자 목록을 불러온다.
        List<Participants> collect = roomRepository.findByChatIdAndStatus(chatId, YesOrNo.YES)
                .stream()
                .map(
                        dto -> participantsRepository
                                .findById(Integer.valueOf(dto.getPartId()))
                                .orElseThrow(() -> new IllegalArgumentException("참여자가 존재하지 않습니다."))
                ).toList();
        int size = collect.size();
        int findNum = new Random().nextInt(size);
        List<ChatMessage> list = new ArrayList<>();
        for(int i = 0 ; i < size ; i++) {
            Participants participants = collect.get(i);
            if (i == findNum) {
                list.add(new ChatMessage(chatId, participants.getPartId(), "사과"));
            } else {
                list.add(new ChatMessage(chatId, participants.getPartId(), "자두"));
            }
        }

        // ### 목록 중 랜덤한 인물으 선택하여 다른 단어를 준다.
        return WebSocketResponse.builder()
                .users(collect)
                .question(list)
                .build();
    }

    public WebSocketResponse enterRoom(String chatId, ParticipantsDto request) {

        Room chatRoom = roomRepository.findByChatIdAndPartId(chatId, request.getPartId());
        if (chatRoom == null) {
            Room entity = Room.creator()
                    .partId(request.getPartId())
                    .chatId(request.getChatId())
                    .build();

            roomRepository.save(entity);
        } else {
            chatRoom.enter();
            roomRepository.save(chatRoom);
        }

        List<Participants> list = roomRepository.findByChatIdAndStatus(chatId, YesOrNo.YES)
                .stream()
                .map(
                        dto -> participantsRepository
                                .findById(Integer.valueOf(dto.getPartId()))
                                .orElseThrow(() -> new IllegalArgumentException("참여자가 존재하지 않습니다."))
                ).toList();

        return WebSocketResponse.builder()
                .users(list)
                .question(new ArrayList<>())
                .build();

    }

    public WebSocketResponse exitRoom(String chatId, ParticipantsDto request) {
        Room chatRoom = roomRepository.findByChatIdAndPartId(chatId, request.getPartId());
        chatRoom.exit();
        roomRepository.save(chatRoom);

        List<Participants> list = roomRepository.findByChatIdAndStatus(chatId, YesOrNo.YES)
                .stream()
                .map(
                        dto -> participantsRepository
                                .findById(Integer.valueOf(dto.getPartId()))
                                .orElseThrow(() -> new IllegalArgumentException("참여자가 존재하지 않습니다."))
                ).toList();

        return WebSocketResponse.builder()
                .users(list)
                .question(new ArrayList<>())
                .build();
    }

    public Chat getRoom(String chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        return chat;
    }
}
