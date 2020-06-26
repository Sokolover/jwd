package by.training.sokolov.wallet.service;

import by.training.sokolov.model.Wallet;
import by.training.sokolov.service.GenericServiceImpl;
import by.training.sokolov.wallet.dao.WalletDao;

public class WalletServiceImpl extends GenericServiceImpl<Wallet> implements WalletService {

    private static WalletService walletService;
    private WalletDao walletDao;

    public WalletServiceImpl(WalletDao dao) {
        super(dao);
        this.walletDao = dao;
    }

}
