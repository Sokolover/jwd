package by.training.sokolov.validation.cost;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinCost {

    String value();

    boolean inclusive();
}