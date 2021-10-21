package by.training.sokolov.validation.impl;

import by.training.sokolov.validation.BrokenField;
import by.training.sokolov.validation.FieldValidator;
import by.training.sokolov.validation.MinLength;
import by.training.sokolov.validation.ValidationException;

import java.lang.reflect.Field;

public class MinLengthFieldValidator implements FieldValidator {

    @Override
    public BrokenField validate(Object entity, Field field) {

        if (String.class.equals(field.getType())) {
            MinLength minLength = field.getAnnotation(MinLength.class);
            try {
                String fieldValue = (String) field.get(entity);
                if (fieldValue != null && fieldValue.trim().length() < minLength.value()) {

                    String annotationArg = String.format("Min length in field %s is %d", field.getName(), minLength.value());
                    return new BrokenField(field.getName(), fieldValue, "minLength", annotationArg);
                }
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }
        return null;
    }
}
