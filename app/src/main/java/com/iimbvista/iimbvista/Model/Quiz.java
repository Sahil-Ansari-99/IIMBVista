package com.iimbvista.iimbvista.Model;

import java.util.List;

public class Quiz {
    private String question;
    private List<String> answers;
    private String solution;

    public Quiz(String question, List<String> answers, String solution) {
        this.question = question;
        this.answers = answers;
        this.solution = solution;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getSolution() {
        return solution;
    }
}
