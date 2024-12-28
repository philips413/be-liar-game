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

    private int participants;

    private String leader;

    private String words;

    @Enumerated(EnumType.STRING)
    private YesOrNo status;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

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
        this.status = YesOrNo.YES;

    }

    public void updateWords(String words) {
        this.words = words;
    }

}
