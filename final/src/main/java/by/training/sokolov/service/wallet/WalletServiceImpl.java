package by.training.sokolov.service.wallet;

import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.dao.wallet.WalletDao;
import by.training.sokolov.dao.wallet.WalletDaoImpl;
import by.training.sokolov.model.Wallet;
import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.GenericServiceImpl;

public class WalletServiceImpl extends GenericServiceImpl<Wallet> implements WalletService {

    private static WalletService walletService;
    private WalletDao walletDao;

    private WalletServiceImpl(WalletDao dao) {
        super(dao);
        this.walletDao = dao;
    }

    public static GenericService<Wallet> getInstance() {
        if (walletService == null) {
            WalletDao walletDao = new WalletDaoImpl();
            walletService = new WalletServiceImpl(walletDao);
        }
        return walletService;
    }
}
