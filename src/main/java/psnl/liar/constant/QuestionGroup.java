package psnl.liar.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum QuestionGroup {

    FOOD("FOOD", "음식"),
    FRUIT("FRUIT", "과일"),
    ANIMAL("ANIMAL", "동물"),
    SPORT("SPORT", "스포츠"),
    MOVIE("MOVIE", "영화"),
    MUSIC("MUSIC", "음악"),
    OBJECT("OBJECT", "물건"),
    TRAFFIC("TRAFFIC", "교통"),
    CULTURE("CULTURE", "문화"),
    LOCATION("LOCATION", "장소");

    private final String code;

    private final String description;

}
