package by.training.sokolov.entity.dish.model;

import by.training.sokolov.core.dao.IdentifiedRow;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.validation.MinLength;
import by.training.sokolov.validation.NotEmpty;
import by.training.sokolov.validation.ValidBean;
import by.training.sokolov.validation.cost.Digits;
import by.training.sokolov.validation.cost.MinCost;

import java.math.BigDecimal;

@ValidBean("dish")
public class Dish implements IdentifiedRow {

    private Long id;
    @MinLength(4)
    private String name;
    @MinCost(value = "0.0", inclusive = true)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal cost;
    @NotEmpty
    private String description;
    @NotEmpty
    private String picture;
    private DishCategory dishCategory;

    public Dish() {
        this.dishCategory = new DishCategory();
    }

    @Override
    public String toString() {

        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", picture='" + picture.substring(0, 20) + '\'' +
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



