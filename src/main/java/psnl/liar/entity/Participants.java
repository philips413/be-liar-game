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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id", nullable = false)
    private int partId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "cookie", length = 512)
    private String cookie;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder(builderMethodName = "creator")
    public Participants(String name, String cookie) {
        this.name = name;
        this.cookie = cookie;
    }
}
