package com.bunis.container.validation.impl;

import com.bunis.container.validation.ValidationException;
import com.bunis.container.validation.FieldValidator;
import com.bunis.container.validation.MaxLength;
import com.bunis.container.validation.BrokenField;

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
