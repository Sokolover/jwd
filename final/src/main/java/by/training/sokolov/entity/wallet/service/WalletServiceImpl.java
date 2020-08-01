package by.training.sokolov.entity.wallet.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.entity.wallet.dao.WalletDao;
import by.training.sokolov.entity.wallet.model.Wallet;
import org.apache.log4j.Logger;

public class WalletServiceImpl extends GenericServiceImpl<Wallet> implements WalletService {

    private static final Logger LOGGER = Logger.getLogger(WalletServiceImpl.class.getName());

    private WalletDao walletDao;

    public WalletServiceImpl(WalletDao dao) {
        super(dao);
        this.walletDao = dao;
    }

}
