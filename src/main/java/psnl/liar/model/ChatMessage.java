package psnl.liar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import psnl.liar.entity.Participants;

@Getter
@NoArgsConstructor
public class ChatMessage {

    private String chatId;
    private Integer partId;
    private String message;

    public ChatMessage(String chatId, Integer partId, String message) {
        this.chatId = chatId;
        this.partId = partId;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "chatId='" + chatId + '\'' +
                ", partId='" + partId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
