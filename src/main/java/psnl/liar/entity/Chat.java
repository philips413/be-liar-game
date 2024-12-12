package psnl.liar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import psnl.liar.utility.RandomCharactor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
public class Chat {

    @Id
    private String chatId;

    private String title;

    private int participants;

    private String leader;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder(builderMethodName = "creator")
    Chat(
            String title,
            int participants,
            String leader
    ) {
        this.chatId = RandomCharactor.creator();
        this.title = title;
        this.participants = participants;
        this.leader = leader;

    }

}
