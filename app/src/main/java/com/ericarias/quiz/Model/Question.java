package com.ericarias.quiz.Model;

public class Question {

    private int id;
    private String question;
    private String respcorrect;
    private String respaltone;
    private String respalttwo;
    private String respaltthree;
    private int user_id;
    private String theme_cod;

    public Question(String question, String respcorrect, String respaltone, String respalttwo, String respaltthree, int user_id, String theme_cod) {
        this.question = question;
        this.respcorrect = respcorrect;
        this.respaltone = respaltone;
        this.respalttwo = respalttwo;
        this.respaltthree = respaltthree;
        this.user_id = user_id;
        this.theme_cod = theme_cod;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTheme_cod() {
        return theme_cod;
    }

    public void setTheme_cod(String theme_cod) {
        this.theme_cod = theme_cod;
    }
}
