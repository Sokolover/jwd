package by.training.sokolov.validation.impl;

import by.training.sokolov.validation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class AnnotationBasedBeanValidator implements BeanValidator {

    private final Map<Class<? extends Annotation>, FieldValidator> validationFunctions;
    private final Set<Class<? extends Annotation>> supportedFieldAnnotations;

    public AnnotationBasedBeanValidator(Map<Class<? extends Annotation>, FieldValidator> validationFunctions) {
        this.validationFunctions = validationFunctions;
        supportedFieldAnnotations = this.validationFunctions.keySet();
    }

    @Override
    public ValidationResult validate(Object bean) {

        ValidationResult validationResult = new ValidationResult();

        if (isNull(bean)) {
            throw new ValidationException("Passed bean is null");
        }

        Class<?> clazz = bean.getClass();
        if (!clazz.isAnnotationPresent(ValidBean.class)) {
            String msg = MessageFormat.format("{0} does not have the {1} annotation.",
                    clazz, ValidBean.class.getName());
            throw new ValidationException(msg);
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            List<BrokenField> brokenFields = supportedFieldAnnotations.stream()
                    .filter(field::isAnnotationPresent)
                    .map(validationFunctions::get)
                    .map(fieldValidator -> fieldValidator.validate(bean, field))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            validationResult.addBrokenFields(brokenFields);
        }

        return validationResult;
    }
}
