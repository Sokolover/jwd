package by.training.sokolov.validation.impl;

import by.training.sokolov.validation.BrokenField;
import by.training.sokolov.validation.FieldValidator;
import by.training.sokolov.validation.ValidationException;
import by.training.sokolov.validation.cost.MinCost;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class MinCostFieldValidator implements FieldValidator {

    @Override
    public BrokenField validate(Object entity, Field field) {

        if (BigDecimal.class.equals(field.getType())) {

            MinCost minCost = field.getAnnotation(MinCost.class);
            try {
                BigDecimal fieldValue = (BigDecimal) field.get(entity);

                if (fieldValue != null) {
                    BigDecimal annotationValue = new BigDecimal(minCost.value());
                    if (
                            (minCost.inclusive() && fieldValue.compareTo(annotationValue) < 0)
                                    || (!minCost.inclusive() && fieldValue.compareTo(annotationValue) <= 0)
                    ) {

                        return new BrokenField(field.getName(), fieldValue, "minCost", minCost.value());
                    }
                }
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }
        return null;
    }
}
