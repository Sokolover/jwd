package by.training.sokolov.command.wallet;

import by.training.sokolov.command.Command;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.wallet.service.WalletService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.JspName.FILL_UP_WALLET_FORM_JSP;
import static java.lang.String.format;

public class DisplayFillingUpWalletFormCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DisplayFillingUpWalletFormCommand.class.getName());

    private final WalletService walletService;

    public DisplayFillingUpWalletFormCommand(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        WalletCommandUtil.setCurrentWalletMoneyAmountToRequest(request);

        return FILL_UP_WALLET_FORM_JSP;
    }



}
