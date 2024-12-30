package psnl.liar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import psnl.liar.entity.Chat;
import psnl.liar.entity.Participants;
import psnl.liar.model.ChatMessage;
import psnl.liar.payload.dto.CreateChatRoomDto;
import psnl.liar.payload.dto.ParticipantChatRoomDto;
import psnl.liar.payload.dto.ParticipantsDto;
import psnl.liar.payload.dto.WebSocketResponse;
import psnl.liar.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;

    private final ChatService chatService;

    private final static String TOPIC = "/sub/chatroom/";

    // 채팅방 만들기
    @PostMapping("/room")
    public ResponseEntity<Chat> createRoom(@RequestBody CreateChatRoomDto request) {
        Chat room = chatService.createRoom(request);
        return ResponseEntity.ok().body(room);
    }

    // 채팅방 정보 가져오기
    @GetMapping("/room/{chatId}")
    public ResponseEntity<Chat> getRoom(@PathVariable String chatId) {
        Chat room = chatService.getRoom(chatId);
        return ResponseEntity.ok().body(room);
    }

    // 채팅방 참가하기
    @PostMapping("/room/enter")
    public void enterRoom(@RequestBody ParticipantsDto participants) {
        WebSocketResponse webSocketResponse = chatService.enterRoom(participants);
        template.convertAndSend(TOPIC+participants.getChatId(), webSocketResponse);
    } 

    // 채팅방 퇴장하기
    @PostMapping("/room/exit")
    public void exitRoom(@RequestBody ParticipantsDto participants) {
        WebSocketResponse webSocketResponse = chatService.exitRoom(participants);
        template.convertAndSend(TOPIC+participants.getChatId(), webSocketResponse);
    }

    // 게임 시작!!!
    @GetMapping("/gameStart/{chatId}")
    public void gameStart(@PathVariable String chatId) {
        WebSocketResponse webSocketResponse = chatService.gameStart(chatId);
        template.convertAndSend(TOPIC+chatId, webSocketResponse);
    }

    //메시지 송신 및 수신, /pub가 생략된 모습. 클라이언트 단에선 /pub/message로 요청
    @MessageMapping("/message")
    public ResponseEntity receiveMessage(@RequestBody ChatMessage chat) {
        template.convertAndSend(TOPIC+chat.getChatId(), chat);
        return ResponseEntity.ok().build();
    }
}
