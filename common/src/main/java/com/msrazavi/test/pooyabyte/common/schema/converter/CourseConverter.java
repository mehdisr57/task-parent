package com.msrazavi.test.pooyabyte.common.schema.converter;

import com.msrazavi.test.pooyabyte.common.schema.enums.Course;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * @author Mehdi Sadat Razavi
 */
@Converter
public class CourseConverter implements AttributeConverter<Course, String> {

    @Override
    public String convertToDatabaseColumn(Course course) {
        if (Objects.isNull(course)) return null;
        return course.getCode();
    }

    @Override
    public Course convertToEntityAttribute(String code) {
        return Course.findByCode(code);
    }
}
