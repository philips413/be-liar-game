package psnl.liar.payload.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import psnl.liar.entity.Participants;
import psnl.liar.model.ChatMessage;

import java.util.List;

@Getter
@Setter
@Builder
public class WebSocketResponse {

    private List<Participants> users;

    private List<ChatMessage> question;


}
