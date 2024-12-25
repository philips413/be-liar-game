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
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private int themeId;

    private String themeGroupId;

    private String word;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder(builderMethodName = "creator")
    Theme(
            String themeGroupId,
            String word
    ) {
        this.themeGroupId = themeGroupId;
        this.word = word;
    }

}
