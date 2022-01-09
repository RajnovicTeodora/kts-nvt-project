package com.ftn.restaurant.dto;

public class TokenDTO {

    private String token;
    private String username;
    private Long expiresIn;
    private String userType;
    private boolean loggedInFirstTime;

    public TokenDTO() {}

    public TokenDTO(String token, String username, Long expiresIn, String userType, boolean loggedInFirstTime) {
        this.setToken(token);
        this.setUsername(username);
        this.setExpiresIn(expiresIn);
        this.setUserType(userType);
        this.loggedInFirstTime = loggedInFirstTime;
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

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isLoggedInFirstTime() {
        return loggedInFirstTime;
    }

    public void setLoggedInFirstTime(boolean loggedInFirstTime) {
        this.loggedInFirstTime = loggedInFirstTime;
    }
}
