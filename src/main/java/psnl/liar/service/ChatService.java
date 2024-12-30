package psnl.liar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import psnl.liar.constant.YesOrNo;
import psnl.liar.entity.Chat;
import psnl.liar.entity.Participants;
import psnl.liar.entity.Room;
import psnl.liar.entity.Theme;
import psnl.liar.model.ChatMessage;
import psnl.liar.payload.dto.CreateChatRoomDto;
import psnl.liar.payload.dto.ParticipantsDto;
import psnl.liar.payload.dto.WebSocketResponse;
import psnl.liar.repository.ChatRepository;
import psnl.liar.repository.ParticipantsRepository;
import psnl.liar.repository.RoomRepository;
import psnl.liar.repository.ThemeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final RoomRepository roomRepository;

    private final ParticipantsRepository participantsRepository;

    private final ThemeRepository themeRepository;

    public Chat createRoom(CreateChatRoomDto request) {
        Chat entity = Chat.creator()
                .title(request.getName())
                .leader(request.getLeader().toString())
                .participants(request.getLimit())
                .build();
        return chatRepository.save(entity);
    }

    // 참가자 목록을 구한다.
    private List<Participants> getList(String chatId) {
        return roomRepository.findByChatIdAndStatus(chatId, YesOrNo.YES)
                .stream()
                .map(
                        dto -> participantsRepository
                                .findById(Integer.valueOf(dto.getPartId()))
                                .orElseThrow(() -> new IllegalArgumentException("참여자가 존재하지 않습니다."))
                ).toList();
    }

    public WebSocketResponse gameStart(String chatId) {

        List<Participants> collect = getList(chatId);
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        List<Long> keywords = new ArrayList<>();
        if (chat.getWords() != null) {
            long _count = Arrays.stream(chat.getWords()
                    .split(",")).count();
            if(_count == 1) {
                keywords.add(Long.parseLong(chat.getWords()));
            } else {
                keywords = Arrays.stream(chat.getWords()
                                .split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
            }
        }

        // 키워드 랜덤
        long count = themeRepository.count();
        Long randomKeyword = 0L;
        for(int turn = 0 ; turn < 1000 ; turn++) {

            // 키워드 뽑기
            randomKeyword = new Random().nextLong(count) + 1;
            boolean isComplete = false;
            for(Long keyword : keywords) {
                if (keyword == randomKeyword) {
                    isComplete = true;
                }
            }
            if (!isComplete) {
                break;
            }
        }
        keywords.add(randomKeyword);
        chat.updateWords(keywords.stream().map(String::valueOf).collect(Collectors.joining(",")));
        chatRepository.save(chat);

        Theme theme = themeRepository.findById(randomKeyword)
                .orElseThrow(() -> new IllegalArgumentException("테마가 존재하지 않습니다."));
        // 랜덤 돌리기
        int size = collect.size();
        int findNum = new Random().nextInt(size);
        List<ChatMessage> list = new ArrayList<>();
        for(int i = 0 ; i < size ; i++) {
            Participants participants = collect.get(i);
            if (i == findNum) {
                list.add(new ChatMessage(chatId, participants.getPartId(), theme.getQuestion()));
            } else {
                list.add(new ChatMessage(chatId, participants.getPartId(), theme.getWord()));
            }
        }

        // ### 목록 중 랜덤한 인물으 선택하여 다른 단어를 준다.
        return WebSocketResponse.builder()
                .users(collect)
                .question(list)
                .build();
    }

    public WebSocketResponse enterRoom(ParticipantsDto request) {

        Room chatRoom = roomRepository.findByChatIdAndPartId(request.getChatId(), request.getPartId());
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

        List<Participants> list = roomRepository.findByChatIdAndStatus(request.getChatId(), YesOrNo.YES)
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

    public WebSocketResponse exitRoom(ParticipantsDto request) {
        Room chatRoom = roomRepository.findByChatIdAndPartId(request.getChatId(), request.getPartId());
        chatRoom.exit();
        roomRepository.save(chatRoom);

        List<Participants> list = roomRepository.findByChatIdAndStatus(request.getChatId(), YesOrNo.YES)
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
