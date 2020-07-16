package by.training.sokolov.validation.impl;

import by.training.sokolov.validation.BrokenField;
import by.training.sokolov.validation.FieldValidator;
import by.training.sokolov.validation.Password;
import by.training.sokolov.validation.ValidationException;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class PasswordFieldValidator implements FieldValidator {

    @Override
    public BrokenField validate(Object entity, Field field) {

        if (String.class.isAssignableFrom(field.getType())) {
            Password annotation = field.getAnnotation(Password.class);
            String regex = annotation.regex();
            Pattern pattern = Pattern.compile(regex);
            try {
                String fieldValue = (String) field.get(entity);
                if (fieldValue != null
                        && !fieldValue.isEmpty()
                        && !pattern.matcher(fieldValue).find()) {
                    return new BrokenField(field.getName(), fieldValue, "password");
                }
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }

        return null;
    }
}
