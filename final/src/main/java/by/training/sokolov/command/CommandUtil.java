package by.training.sokolov.command;

import by.training.sokolov.ApplicationModule;

import javax.servlet.http.HttpServletRequest;

public class CommandUtil {

    public static String getCommandFromRequest(HttpServletRequest request) {

        return request.getAttribute(ApplicationModule.COMMAND_PARAM) != null ?
                String.valueOf(request.getAttribute(ApplicationModule.COMMAND_PARAM)) :
                request.getParameter(ApplicationModule.COMMAND_PARAM);
    }

    public static String getCategoryFromRequest(HttpServletRequest request) {

        return request.getAttribute(ApplicationModule.CATEGORY_PARAM) != null ?
                String.valueOf(request.getAttribute(ApplicationModule.CATEGORY_PARAM)) :
                request.getParameter(ApplicationModule.CATEGORY_PARAM);
    }
}