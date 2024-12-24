package psnl.liar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import psnl.liar.entity.Chat;
import psnl.liar.model.ChatMessage;
import psnl.liar.payload.dto.CreateChatRoomDto;
import psnl.liar.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;

    private final ChatService chatService;

    // 채팅방 리스트 조회
    @GetMapping("/rooms")
    public ResponseEntity<List<Chat>> getRooms() {
        List<Chat> rooms = chatService.getRooms();
        return ResponseEntity.ok().body(rooms);
    }

    // 채팅방 만들기
    @PostMapping("/room")
    public ResponseEntity<Chat> createRoom(@RequestBody CreateChatRoomDto request) {
        Chat room = chatService.createRoom(request);
        return ResponseEntity.ok().body(room);
    }


    //채팅방 참가!
    @GetMapping("/chat/{id}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable String id) {
        ChatMessage test = new ChatMessage(id, "TEST", "TEST");
        return ResponseEntity.ok().body(List.of(test));
    }


    //메시지 송신 및 수신, /pub가 생략된 모습. 클라이언트 단에선 /pub/message로 요청
    @MessageMapping("/message")
    public ResponseEntity receiveMessage(@RequestBody ChatMessage chat) {

        template.convertAndSend("/sub/chatroom/"+chat.getChatId(), chat);
        return ResponseEntity.ok().build();
    }
}
