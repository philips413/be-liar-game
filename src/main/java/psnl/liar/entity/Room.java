package psnl.liar.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer chatRoomSysId;

    private String chatId;

    private String partId;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder(builderMethodName = "creator")
    public Room(String chatId, String partId) {
        this.chatId = chatId;
        this.partId = partId;
    }

}
