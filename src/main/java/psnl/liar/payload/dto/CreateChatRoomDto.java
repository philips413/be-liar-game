package psnl.liar.payload.dto;

import lombok.Data;

@Data
public class CreateChatRoomDto {

    private String name;

    private Integer limit;

    private Integer leader;
}
