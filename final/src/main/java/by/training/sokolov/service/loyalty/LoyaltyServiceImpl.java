package by.training.sokolov.service.loyalty;

import by.training.sokolov.dao.loyalty.LoyaltyDao;
import by.training.sokolov.dao.loyalty.LoyaltyDaoImpl;
import by.training.sokolov.model.Loyalty;
import by.training.sokolov.service.GenericServiceImpl;

public class LoyaltyServiceImpl extends GenericServiceImpl<Loyalty> implements LoyaltyService {

    private static LoyaltyService loyaltyService;
    private LoyaltyDao loyaltyDao;

    private LoyaltyServiceImpl(LoyaltyDao dao) {
        super(dao);
        this.loyaltyDao = dao;
    }

    public static LoyaltyService getInstance() {
        if (loyaltyService == null) {
            LoyaltyDao loyaltyDao = new LoyaltyDaoImpl();
            loyaltyService = new LoyaltyServiceImpl(loyaltyDao);
        }
        return loyaltyService;
    }
}
