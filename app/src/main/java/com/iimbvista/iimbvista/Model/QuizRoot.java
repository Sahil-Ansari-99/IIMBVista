package com.iimbvista.iimbvista.Model;

import java.util.List;

public class QuizRoot {
    private List<Quiz> Quiz;

    public QuizRoot(List<com.iimbvista.iimbvista.Model.Quiz> quiz) {
        Quiz = quiz;
    }

    public List<Quiz> getQuiz() {
        return Quiz;
    }
}
