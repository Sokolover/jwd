package by.training.sokolov.loyalty.model;

import by.training.sokolov.dao.IdentifiedRow;

public class Loyalty implements IdentifiedRow {

    private Long id;
    private Integer pointsAmount;

    public Loyalty() {

    }

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
