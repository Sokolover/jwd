package by.training.sokolov.validation.impl;

import by.training.sokolov.validation.BrokenField;
import by.training.sokolov.validation.FieldValidator;
import by.training.sokolov.validation.ValidationException;
import by.training.sokolov.validation.cost.Digits;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class DigitsFieldValidator implements FieldValidator {

    @Override
    public BrokenField validate(Object entity, Field field) {

        if (BigDecimal.class.equals(field.getType())) {

            Digits digits = field.getAnnotation(Digits.class);
            try {
                BigDecimal fieldValue = (BigDecimal) field.get(entity);

                if (fieldValue != null) {
                    String fieldValueString = fieldValue.toString();

                    if (fieldValueString.split("\\.").length == 2) {

                        if (validateIntegerFractionDecimal(digits, fieldValueString)) {

                            return new BrokenField(field.getName(), fieldValue, "digits", digits.integer(), digits.fraction());
                        }
                    } else {

                        if (validateIntegerDecimal(digits, fieldValueString)) {

                            return new BrokenField(field.getName(), fieldValue, "digits", digits.integer());
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }
        return null;
    }

    private boolean validateIntegerDecimal(Digits digits, String fieldValueString) {

        String integer = fieldValueString.split("\\.")[0];

        return digits.integer() < integer.length();
    }

    private boolean validateIntegerFractionDecimal(Digits digits, String fieldValueString) {

        String integer = fieldValueString.split("\\.")[0];
        String fraction = fieldValueString.split("\\.")[1];

        return digits.integer() < integer.length()
                || digits.fraction() < fraction.length();
    }
}
