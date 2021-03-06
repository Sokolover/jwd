package by.training.sokolov.command.wallet;

import by.training.sokolov.command.Command;
import by.training.sokolov.context.SecurityContext;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.wallet.model.Wallet;
import by.training.sokolov.entity.wallet.service.WalletService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.FILL_UP_WALLET_FORM_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.String.format;

public class SubmitFillingUpWalletFormCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(SubmitFillingUpWalletFormCommand.class.getName());

    private final WalletService walletService;

    public SubmitFillingUpWalletFormCommand(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        String moneyAmount = request.getParameter(WALLET_NEW_MONEY_AMOUNT_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, WALLET_NEW_MONEY_AMOUNT_JSP_PARAM, moneyAmount));

        BigDecimal bigDecimalCost;
        try {

            bigDecimalCost = new BigDecimal(moneyAmount);
        } catch (NumberFormatException e) {

            String message = "Invalid money format or empty field";
            return createReturnAnswer(request, message);
        }

        String sessionId = request.getSession().getId();
        User user = SecurityContext.getInstance().getCurrentUser(sessionId);

        Wallet usersWallet = user.getWallet();
        BigDecimal currentMoneyAmount = usersWallet.getMoneyAmount();
        usersWallet.setMoneyAmount(currentMoneyAmount.add(bigDecimalCost));

        walletService.update(usersWallet);

        user.setWallet(usersWallet);

        String message = "Wallet has been filled up";
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, MESSAGE_JSP_ATTRIBUTE, message));

        WalletCommandUtil.setCurrentWalletMoneyAmountToRequest(request);

        return FILL_UP_WALLET_FORM_JSP;
    }

    private String createReturnAnswer(HttpServletRequest request, String message) {

        request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, ERROR_JSP_ATTRIBUTE, message));
        LOGGER.error(message);

        return FILL_UP_WALLET_FORM_JSP;
    }
}
