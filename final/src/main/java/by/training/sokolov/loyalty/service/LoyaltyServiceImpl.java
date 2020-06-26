package by.training.sokolov.loyalty.service;

import by.training.sokolov.loyalty.dao.LoyaltyDao;
import by.training.sokolov.loyalty.model.Loyalty;
import by.training.sokolov.service.GenericServiceImpl;

public class LoyaltyServiceImpl extends GenericServiceImpl<Loyalty> implements LoyaltyService {

    private static LoyaltyService loyaltyService;
    private LoyaltyDao loyaltyDao;

    public LoyaltyServiceImpl(LoyaltyDao dao) {
        super(dao);
        this.loyaltyDao = dao;
    }

}
