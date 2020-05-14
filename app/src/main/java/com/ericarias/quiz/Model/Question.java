package com.ericarias.quiz.Model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private int id;
    private String question;
    private List<String> answers;
    private String theme;
    private String user;

    /**
     * Constructor para añadir pregunta
     * @param question
     * @param answers
     * @param theme
     * @param user
     */
    public Question(String question, List<String> answers, String theme, String user) {
        this.question = question;
        this.theme = theme;
        this.user = user;
        this.answers = answers;

    }

    /**
     * Constructor general
     * @param id
     * @param question
     * @param answers
     * @param theme
     * @param user
     */
    public Question(int id, String question, List<String> answers, String theme, String user) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.theme = theme;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
