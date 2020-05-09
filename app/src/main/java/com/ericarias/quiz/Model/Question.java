package com.ericarias.quiz.Model;

import java.io.Serializable;

public class Question implements Serializable {

    private int id;
    private String question;
    private String respcorrect;
    private String respaltone;
    private String respalttwo;
    private String respaltthree;
    private String theme;
    private String user;

    /**
     * Constructor para añadir pregunta
     * @param question
     * @param respcorrect
     * @param respaltone
     * @param respalttwo
     * @param respaltthree
     */
    public Question(String question, String respcorrect, String respaltone, String respalttwo, String respaltthree, String theme, String user) {
        this.question = question;
        this.respcorrect = respcorrect;
        this.respaltone = respaltone;
        this.respalttwo = respalttwo;
        this.respaltthree = respaltthree;
        this.theme = theme;
        this.user = user;

    }

    /**
     * Constrcutor general
     * @param id
     * @param question
     * @param respcorrect
     * @param respaltone
     * @param respalttwo
     * @param respaltthree
     * @param user
     * @param theme
     */
    public Question(int id, String question, String respcorrect, String respaltone, String respalttwo, String respaltthree, String theme, String user) {
        this.id = id;
        this.question = question;
        this.respcorrect = respcorrect;
        this.respaltone = respaltone;
        this.respalttwo = respalttwo;
        this.respaltthree = respaltthree;
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

    public String getRespcorrect() {
        return respcorrect;
    }

    public void setRespcorrect(String respcorrect) {
        this.respcorrect = respcorrect;
    }

    public String getRespaltone() {
        return respaltone;
    }

    public void setRespaltone(String respaltone) {
        this.respaltone = respaltone;
    }

    public String getRespalttwo() {
        return respalttwo;
    }

    public void setRespalttwo(String respalttwo) {
        this.respalttwo = respalttwo;
    }

    public String getRespaltthree() {
        return respaltthree;
    }

    public void setRespaltthree(String respaltthree) {
        this.respaltthree = respaltthree;
    }

    public String getTheme_name() {
        return theme;
    }

    public void setTheme_name(String theme_name) {
        this.theme = theme_name;
    }

    public String getUser_name() {
        return user;
    }

    public void setUser_name(String user_name) {
        this.user = user_name;
    }
}
