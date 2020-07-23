package by.training.sokolov.entity.dishfeedback.model;

import by.training.sokolov.core.dao.IdentifiedRow;

public class DishFeedback implements IdentifiedRow {

    private Long id;
    private Integer dishRating;
    private String dishComment;
    private Long userId;
    private Long dishId;

    public DishFeedback() {

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDishRating() {
        return dishRating;
    }

    public void setDishRating(Integer dishRating) {
        this.dishRating = dishRating;
    }

    public String getDishComment() {
        return dishComment;
    }

    public void setDishComment(String dishComment) {
        this.dishComment = dishComment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }
}
