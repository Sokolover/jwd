package by.training.sokolov.wallet.service;

import by.training.sokolov.service.GenericServiceImpl;
import by.training.sokolov.wallet.dao.WalletDao;
import by.training.sokolov.wallet.model.Wallet;

public class WalletServiceImpl extends GenericServiceImpl<Wallet> implements WalletService {

    private WalletDao walletDao;

    public WalletServiceImpl(WalletDao dao) {
        super(dao);
        this.walletDao = dao;
    }

}
