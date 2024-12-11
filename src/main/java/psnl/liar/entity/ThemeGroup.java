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
public class ThemeGroup {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String themeGroupId;

    private String themeTitle;

    private String isUsed;

    @CreationTimestamp
    private LocalDateTime created;

    @Builder(builderMethodName = "creator")
    ThemeGroup (
            String themeTitle,
            String isUsed
    ) {
        this.themeTitle = themeTitle;
        this.isUsed = isUsed;
    }

}
