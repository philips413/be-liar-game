package psnl.liar.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import psnl.liar.entity.Participants;

@Getter
@Builder
public class ChatMessage {

    private String chatId;
    private Integer partId;
    private String message;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "chatId='" + chatId + '\'' +
                ", partId=" + partId +
                ", message='" + message + '\'' +
                '}';
    }

}
