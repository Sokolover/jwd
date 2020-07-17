package by.training.sokolov.command;

import javax.servlet.http.HttpServletRequest;

import static by.training.sokolov.core.constants.CommonAppConstants.QUERY_COMMAND_PARAM;

public class CommandUtil {

    public static String getCommandFromRequest(HttpServletRequest request) {

        return request.getAttribute(QUERY_COMMAND_PARAM) != null ?
                String.valueOf(request.getAttribute(QUERY_COMMAND_PARAM)) :
                request.getParameter(QUERY_COMMAND_PARAM);
    }
}