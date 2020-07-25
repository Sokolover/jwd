package by.training.sokolov.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.training.sokolov.core.constants.CommonAppConstants.QUERY_COMMAND_PARAM;
import static java.lang.String.format;

public final class CommandUtil {

    private static final Logger LOGGER = Logger.getLogger(CommandUtil.class.getName());

    private CommandUtil(){

    }

    public static String getCommandFromRequest(HttpServletRequest request) {

        LOGGER.info(format("Getting command from request param [%s]", QUERY_COMMAND_PARAM));
        return request.getAttribute(QUERY_COMMAND_PARAM) != null ?
                String.valueOf(request.getAttribute(QUERY_COMMAND_PARAM)) :
                request.getParameter(QUERY_COMMAND_PARAM);
    }
}