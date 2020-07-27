package by.training.sokolov.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.training.sokolov.core.constants.CommonAppConstants.QUERY_COMMAND_PARAM;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

public final class CommandUtil {

    private static final Logger LOGGER = Logger.getLogger(CommandUtil.class.getName());

    private CommandUtil() {

    }

    public static String getCommandFromRequest(HttpServletRequest request) {

        String commandType;
        if (nonNull(request.getAttribute(QUERY_COMMAND_PARAM))) {
            commandType = String.valueOf(request.getAttribute(QUERY_COMMAND_PARAM));
        } else {
            commandType = request.getParameter(QUERY_COMMAND_PARAM);
        }

        LOGGER.info(format("Getting command from request param [%s]", commandType));

        return commandType;
    }
}