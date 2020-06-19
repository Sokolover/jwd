package by.training.sokolov.service;

import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.dao.LoyaltyDaoImpl;
import by.training.sokolov.model.Loyalty;

public class LoyaltyServiceImpl extends GenericServiceImpl<Loyalty> implements LoyaltyService {

    private static LoyaltyService loyaltyService;

    private LoyaltyServiceImpl(CRUDDao<Loyalty> dao) {
        super(dao);
    }

    public static LoyaltyService getInstance() {
        if (loyaltyService == null) {
            CRUDDao<Loyalty> loyaltyDao = new LoyaltyDaoImpl();
            loyaltyService = new LoyaltyServiceImpl(loyaltyDao);
        }
        return loyaltyService;
    }
}
