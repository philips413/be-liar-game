package psnl.liar.payload.dto;

import lombok.Data;

@Data
public class CreateChatRoomDto {

    private String roomName;

    private Integer limit;

    private Integer leader;
}
