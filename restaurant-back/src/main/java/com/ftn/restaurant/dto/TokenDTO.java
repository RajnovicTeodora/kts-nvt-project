package com.ftn.restaurant.dto;

public class TokenDTO {

    private String token;
    private String username;
    private String password;
    private String userType;

    public TokenDTO() {}

    public TokenDTO(String token, String username, String password, String userType) {
        this.setToken(token);
        this.setUsername(username);
        this.setPassword(password);
        this.setUserType(userType);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
