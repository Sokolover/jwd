package by.training.sokolov.service;

import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.dao.WalletDao;
import by.training.sokolov.model.Wallet;

public class WalletService extends GenericServiceImpl<Wallet> {

    private static GenericService<Wallet> walletService;

    private WalletService(CRUDDao<Wallet> dao) {
        super(dao);
    }

    public static GenericService<Wallet> getInstance() {
        if (walletService == null) {
            CRUDDao<Wallet> walletDao = new WalletDao();
            walletService = new WalletService(walletDao);
        }
        return walletService;
    }
}
