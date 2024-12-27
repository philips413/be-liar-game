package psnl.liar.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum YesOrNo {

    YES("YES", "참석/참여"),
    NO("NO", "불참/미참여");

    private final String code;

    private final String description;

}
