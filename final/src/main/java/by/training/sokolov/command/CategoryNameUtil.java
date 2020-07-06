package by.training.sokolov.command;

import by.training.sokolov.core.QueryParamConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class CategoryNameUtil {

    public static final String ALL_CATEGORIES = "all";

    public static List<String> getCategoryNames(HttpServletRequest request) {

        Enumeration<String> paramNames = request.getParameterNames();
        String[] paramValues;
        List<String> categoryNames = new ArrayList<>();

        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (QueryParamConstants.CATEGORY_PARAM.equals(paramName)) {
                paramValues = request.getParameterValues(paramName);

                for (String paramValue : paramValues) {

                    if (ALL_CATEGORIES.equals(paramValue)) {
                        return Collections.singletonList(ALL_CATEGORIES);
                    }
                    categoryNames.add(paramValue);
                }
                return categoryNames;
            }
        }

        return new ArrayList<>();
    }
}
