package com.ericarias.quiz.Model;

public class Points {

    private int id;
    private int points;
    private String name;

    public Points(int id, int points, String name) {
        this.id = id;
        this.points = points;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
