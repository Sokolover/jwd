package by.training.sokolov.dish.model;

import by.training.sokolov.category.model.DishCategory;
import by.training.sokolov.dao.IdentifiedRow;

import java.math.BigDecimal;

public class Dish implements IdentifiedRow {

    private Long id;
    private String name;
    private BigDecimal cost;
    private String description;
    private String picture;
    private DishCategory dishCategory;

    public Dish() {
        dishCategory = new DishCategory();
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", dishCategory=" + dishCategory +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DishCategory getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(DishCategory dishCategory) {
        this.dishCategory = dishCategory;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}



