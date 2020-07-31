package by.training.sokolov.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.training.sokolov.core.constants.CommonAppConstants.QUERY_PARAM_COMMAND;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

public final class CommandUtil {

    private static final Logger LOGGER = Logger.getLogger(CommandUtil.class.getName());

    private CommandUtil() {

    }

    public static String getCommandFromRequest(HttpServletRequest request) {

        String commandType;
        if (nonNull(request.getAttribute(QUERY_PARAM_COMMAND))) {
            commandType = String.valueOf(request.getAttribute(QUERY_PARAM_COMMAND));
        } else {
            commandType = request.getParameter(QUERY_PARAM_COMMAND);
        }

        LOGGER.info(format("Getting command from request param [%s]", commandType));

        return commandType;
    }
}