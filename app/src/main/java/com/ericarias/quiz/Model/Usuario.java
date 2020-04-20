package com.ericarias.quiz.Model;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("name")
    private String name;
    private String emial;
    private String pass;

    public Usuario(String name, String emial, String pass) {
        this.name = name;
        this.emial = emial;
        this.pass = pass;
    }

    public Usuario(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
