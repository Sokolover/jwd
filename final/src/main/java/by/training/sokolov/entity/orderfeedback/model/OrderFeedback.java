package by.training.sokolov.orderfeedback.model;

import by.training.sokolov.dao.IdentifiedRow;

public class OrderFeedback implements IdentifiedRow {

    private Long id;
    private Integer orderRating;
    private String orderComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderRating() {
        return orderRating;
    }

    public void setOrderRating(Integer orderRating) {
        this.orderRating = orderRating;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }
}
