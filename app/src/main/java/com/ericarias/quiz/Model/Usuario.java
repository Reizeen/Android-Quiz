package com.ericarias.quiz.Model;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("name")
    private String name;
    private String email;
    private String pass;

    public Usuario(String name, String email, String pass) {
        this.name = name;
        this.email = email;
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
        return email;
    }

    public void setEmial(String emial) {
        this.email = emial;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
