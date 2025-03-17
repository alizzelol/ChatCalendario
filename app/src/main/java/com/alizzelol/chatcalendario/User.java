package com.alizzelol.chatcalendario;

public class User {
    private String username;
    private String email;
    private String userId;
    private String rol;

    public User() {}

    public User(String username, String email, String userId, String rol) {
        this.username = username;
        this.email = email;
        this.userId = userId;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRol() { // Añadir métodos get y set para rol
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

