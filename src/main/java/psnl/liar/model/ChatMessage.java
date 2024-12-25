package psnl.liar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessage {

    private String chatId;
    private String name;
    private String message;

    public ChatMessage(String chatId, String name, String message) {
        this.chatId = chatId;
        this.name = name;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "chatId='" + chatId + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
