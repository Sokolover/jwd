package by.training.sokolov.oop.agregation_and_composition.task1.model;


import by.training.sokolov.oop.agregation_and_composition.task1.service.WordService;

public class Word implements WordService {

    private String content;

    public Word(String content) {
        this.content = content;
    }

    @Override
    public void firstUpperCase() {
        if (content == null || content.isEmpty()) {
            return;
        }
        content = content.substring(0, 1).toUpperCase() + content.substring(1);
    }

    public String getContent() {
        return content;
    }

}
