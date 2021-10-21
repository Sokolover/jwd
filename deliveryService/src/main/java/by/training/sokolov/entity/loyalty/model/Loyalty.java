package by.training.sokolov.entity.loyalty.model;

import by.training.sokolov.core.dao.IdentifiedRow;

public class Loyalty implements IdentifiedRow {

    private Long id;
    private Integer pointsAmount;

    @Override
    public String toString() {
        return "LoyaltyPoints{" +
                "id=" + id +
                ", pointsAmount=" + pointsAmount +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPointsAmount() {
        return pointsAmount;
    }

    public void setPointsAmount(Integer pointsAmount) {
        this.pointsAmount = pointsAmount;
    }
}
