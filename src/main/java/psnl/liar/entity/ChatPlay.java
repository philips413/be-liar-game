package psnl.liar.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class ChatPlay {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int playId;

    private String chatId;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder(builderMethodName = "creator")
    ChatPlay (String chatId)
    {
        this.chatId = chatId;
    }
}
