package com.jason.usedcar.response;

public class LoginResponse extends Response {

    private String accessToken;

    private String username;

    private String password;

    private Integer userId;

    private Integer accountType;

    /**
     *
     * @return
     * The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     * The accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The accountType
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     *
     * @param accountType
     * The accountType
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
