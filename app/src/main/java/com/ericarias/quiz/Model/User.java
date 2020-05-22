package com.ericarias.quiz.Model;

public class User {

    private int id;
    private String name;
    private String email;
    private String pass;
    private String token;

    /**
     * Constructor para la sesion
     * @param id
     * @param token
     */
    public User(int id, String token) {
        this.id = id;
        this.token = token;
    }

    /**
     * Constructor para recuperar contraseña
     * @param email
     */
    public User(String email) {
        this.email = email;
    }

    /**
     * Constructor para el login
     * @param name
     * @param pass
     */
    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    /**
     * Constructor para el registro
     * @param name
     * @param email
     * @param pass
     */
    public User(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    /**
     * Constructor general
     * @param id
     * @param name
     * @param email
     * @param pass
     * @param token
     */
    public User(int id, String name, String email, String pass, String token) {
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


