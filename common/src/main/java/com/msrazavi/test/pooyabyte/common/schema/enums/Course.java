package com.msrazavi.test.pooyabyte.common.schema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Map;
import java.util.Objects;

/**
 * @author Mehdi Sadat Razavi
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Course {

    DAILY("DA", "روزانه"),
    WEEKLY("WE", "هفتگی"),
    ONCE_EVERY_TWO_WEEK("2W", "دو هفته یک بار"),
    MONTHLY("MO", "ماهانه"),
    END_OF_MONTH("EM", "پایان هر ماه"),
    ;

    private final String code;
    private final String description;

    Course(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Course findByCode(String code) {
        if (Objects.isNull(code)) {
            return null;
        }

        for (Course duration : Course.values()) {
            if (code.equals(duration.code)) {
                return duration;
            }
        }

        return null;
    }

    @JsonCreator
    public static Course fromObject(Map<String, Object> map) {
        Object code = map.get("code");
        if(Objects.isNull(code)) return null;
        return findByCode(map.get("code").toString());
    }
}
