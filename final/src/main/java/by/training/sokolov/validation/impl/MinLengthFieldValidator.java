package com.bunis.container.validation.impl;

import com.bunis.container.validation.BrokenField;
import com.bunis.container.validation.FieldValidator;
import com.bunis.container.validation.MinLength;
import com.bunis.container.validation.ValidationException;

import java.lang.reflect.Field;

public class MinLengthFieldValidator implements FieldValidator {
    @Override
    public BrokenField validate(Object entity, Field field) {

        if (String.class.equals(field.getType())) {
            MinLength minLength = field.getAnnotation(MinLength.class);
            try {
                String fieldValue = (String) field.get(entity);
                if (fieldValue != null && fieldValue.trim().length() < minLength.value()) {

                    return new BrokenField(field.getName(), fieldValue, "minLength", minLength.value());
                }
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }
        return null;
    }
}
