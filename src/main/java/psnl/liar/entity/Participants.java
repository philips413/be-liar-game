package psnl.liar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private int partId;

    private String name;

    private String cookie;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
