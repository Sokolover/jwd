package by.training.sokolov.command;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.QUERY_CATEGORY_PARAM;

public class CategoryNameUtil {

    public static final String ALL_CATEGORIES = "all";

    public static List<String> getCategoryNamesFromRequest(HttpServletRequest request) {

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (QUERY_CATEGORY_PARAM.equals(paramName)) {
                return createCategoryListFromParameters(request, paramName);
            }
        }

        return new ArrayList<>();
    }

    private static List<String> createCategoryListFromParameters(HttpServletRequest request, String paramName) {

        String[] paramValues;
        List<String> categoryNames = new ArrayList<>();
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
