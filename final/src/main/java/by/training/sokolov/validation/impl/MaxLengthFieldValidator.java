package by.training.sokolov.validation.impl;

import by.training.sokolov.validation.BrokenField;
import by.training.sokolov.validation.FieldValidator;
import by.training.sokolov.validation.MaxLength;
import by.training.sokolov.validation.ValidationException;

import java.lang.reflect.Field;

public class MaxLengthFieldValidator implements FieldValidator {

    @Override
    public BrokenField validate(Object entity, Field field) {

        if (String.class.equals(field.getType())) {
            MaxLength maxLength = field.getAnnotation(MaxLength.class);
            try {
                String fieldValue = (String) field.get(entity);
                if (fieldValue != null && fieldValue.trim().length() > maxLength.value()) {

                    return new BrokenField(field.getName(), fieldValue, "maxLength", maxLength.value());
                }
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }
        return null;
    }
}
