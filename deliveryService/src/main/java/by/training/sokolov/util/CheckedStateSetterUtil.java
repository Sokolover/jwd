package by.training.sokolov.util;

import by.training.sokolov.entity.category.model.DishCategory;
import org.apache.log4j.Logger;

import java.util.List;

import static by.training.sokolov.core.constants.LoggerConstants.CLASS_INVOKED_METHOD_MESSAGE;
import static java.lang.String.format;

public final class CheckedStateSetterUtil {

    private static final Logger LOGGER = Logger.getLogger(CheckedStateSetterUtil.class.getName());

    private CheckedStateSetterUtil() {

    }

    public static boolean setCheckedState(DishCategory categoryFromList, List<String> checkedCategories) {

        String nameOfCurrentMethod = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        LOGGER.info(format(CLASS_INVOKED_METHOD_MESSAGE, LocalDateTimeFormattingUtil.class.getSimpleName(), nameOfCurrentMethod));

        for (String checkedCategory : checkedCategories) {
            if (categoryFromList.getCategoryName().equals(checkedCategory)) {
                return true;
            }
        }

        return false;
    }
}
