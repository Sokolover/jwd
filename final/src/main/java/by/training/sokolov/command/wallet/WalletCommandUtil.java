package by.training.sokolov.command.wallet;

import by.training.sokolov.context.SecurityContext;
import by.training.sokolov.entity.user.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.training.sokolov.core.constants.CommonAppConstants.WALLET_CURRENT_MONEY_AMOUNT_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static java.lang.String.format;

public final class WalletCommandUtil {

    private static final Logger LOGGER = Logger.getLogger(WalletCommandUtil.class.getName());

    public static void setCurrentWalletMoneyAmountToRequest(HttpServletRequest request) {

        String sessionId = request.getSession().getId();
        User user = SecurityContext.getInstance().getCurrentUser(sessionId);

        request.setAttribute(WALLET_CURRENT_MONEY_AMOUNT_JSP_ATTRIBUTE, user.getWallet().getMoneyAmount());
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, WALLET_CURRENT_MONEY_AMOUNT_JSP_ATTRIBUTE));
    }
}
