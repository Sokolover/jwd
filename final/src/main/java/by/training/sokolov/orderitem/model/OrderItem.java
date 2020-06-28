package by.training.sokolov.orderitem.model;

import by.training.sokolov.dao.IdentifiedRow;
import by.training.sokolov.dish.model.Dish;
import by.training.sokolov.order.model.UserOrder;

import java.math.BigDecimal;

public class OrderItem implements IdentifiedRow {

    private Long id;
    private Integer dishAmount;
    private BigDecimal itemCost;
    private Dish dish;
    private UserOrder userOrder;

    public OrderItem() {
        this.dish = new Dish();
        this.userOrder = new UserOrder();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDishAmount() {
        return dishAmount;
    }

    public void setDishAmount(Integer dishAmount) {
        this.dishAmount = dishAmount;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }
}
