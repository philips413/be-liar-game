package psnl.liar.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import psnl.liar.constant.YesOrNo;
import psnl.liar.utility.RandomCharactor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Chat {

    @Id
    private String chatId;

    private String title;

    private String leader;

    private String words;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder(builderMethodName = "creator")
    Chat(
            String title,
            String leader
    ) {
        this.chatId = RandomCharactor.creator();
        this.title = title;
        this.leader = leader;

    }

    public void updateWords(String words) {
        this.words = words;
    }

}
