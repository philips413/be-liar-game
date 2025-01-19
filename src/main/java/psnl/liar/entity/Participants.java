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
public class Participants {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "part_id", nullable = false, columnDefinition = "integer auto_increment COMMENT '참가자ID'")
    private int partId;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(20) COMMENT '참가자명'")
    private String name;

    @Column(name = "cookie", nullable = true, columnDefinition = "varchar(512) COMMENT '쿠키값'")
    private String cookie;

    @Column(name = "created_at", nullable = false, columnDefinition = "datetime DEFAULT current_timestamp() COMMENT '생성일자'")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder(builderMethodName = "creator")
    public Participants(String name, String cookie) {
        this.name = name;
        this.cookie = cookie;
    }
}
