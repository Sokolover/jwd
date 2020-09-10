package by.training.sokolov.entity.wallet.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.entity.wallet.dao.WalletDao;
import by.training.sokolov.entity.wallet.model.Wallet;

public class WalletServiceImpl extends GenericServiceImpl<Wallet> implements WalletService {

    public WalletServiceImpl(WalletDao dao) {
        super(dao);
    }
}
