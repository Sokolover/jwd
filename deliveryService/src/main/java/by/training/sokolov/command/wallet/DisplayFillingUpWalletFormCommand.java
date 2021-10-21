package by.training.sokolov.command.wallet;

import by.training.sokolov.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.sokolov.core.constants.JspName.FILL_UP_WALLET_FORM_JSP;

public class DisplayFillingUpWalletFormCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {

        WalletCommandUtil.setCurrentWalletMoneyAmountToRequest(request);

        return FILL_UP_WALLET_FORM_JSP;
    }

}
