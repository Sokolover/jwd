package by.training.sokolov.model;

import by.training.sokolov.dao.IdentifiedRow;

import java.math.BigDecimal;

public class Dish implements IdentifiedRow {

    private Long id;
    private String name;
    private BigDecimal cost;
    private String description;
    //pr BLOB;
    private Long categoryId;

    public Dish() {

    }

    public Dish(Long id, String name, BigDecimal cost, String description, Long categoryId) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.categoryId = categoryId;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
