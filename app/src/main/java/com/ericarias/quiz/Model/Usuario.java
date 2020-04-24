package com.ericarias.quiz.Model;

public class Usuario {

    private int id;
    private String name;
    private String email;
    private String pass;
    private String token;

    // Constructor para el login
    public Usuario(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    // Costructor para el registro
    public Usuario(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    public Usuario(int id, String name, String email, String pass, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


