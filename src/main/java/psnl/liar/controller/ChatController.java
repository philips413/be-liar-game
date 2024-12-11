package psnl.liar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import psnl.liar.model.ChatMessage;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;

    // 채팅방 리스트 조회


    //채팅방 참가!
    @GetMapping("/chat/{id}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable String id) {
        ChatMessage test = new ChatMessage(1L, "TEST", "TEST");
        return ResponseEntity.ok().body(List.of(test));
    }


    //메시지 송신 및 수신, /pub가 생략된 모습. 클라이언트 단에선 /pub/message로 요청
    @MessageMapping("/message")
    public ResponseEntity receiveMessage(@RequestBody ChatMessage chat) {

        template.convertAndSend("/sub/chatroom/1", chat);
        return ResponseEntity.ok().build();
    }
}
