package by.training.sokolov.dishfeedback.model;

import by.training.sokolov.dao.IdentifiedRow;
import by.training.sokolov.dish.model.Dish;
import by.training.sokolov.user.model.User;

public class DishFeedback implements IdentifiedRow {

    private Long id;
    private Integer dishRating;
    private String dishComment;
    private User user;
    private Dish dish;

    public DishFeedback(){
        this.user = new User();
        this.dish = new Dish();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
