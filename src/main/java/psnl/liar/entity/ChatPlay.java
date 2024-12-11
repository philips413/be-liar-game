package psnl.liar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder(builderMethodName = "creator")
    ChatPlay (
            String chatId
    )
    {
        this.chatId = chatId;
    }
}
