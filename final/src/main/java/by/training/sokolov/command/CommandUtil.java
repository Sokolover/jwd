package by.training.sokolov.command;

import by.training.sokolov.core.QueryParamConstants;

import javax.servlet.http.HttpServletRequest;

public class CommandUtil {

    public static String getCommandFromRequest(HttpServletRequest request) {

        return request.getAttribute(QueryParamConstants.COMMAND_PARAM) != null ?
                String.valueOf(request.getAttribute(QueryParamConstants.COMMAND_PARAM)) :
                request.getParameter(QueryParamConstants.COMMAND_PARAM);
    }

    public static String getCategoryFromRequest(HttpServletRequest request) {

        return request.getAttribute(QueryParamConstants.CATEGORY_PARAM) != null ?
                String.valueOf(request.getAttribute(QueryParamConstants.CATEGORY_PARAM)) :
                request.getParameter(QueryParamConstants.CATEGORY_PARAM);
    }
}