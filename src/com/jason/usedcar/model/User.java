package com.jason.usedcar.model;

import lombok.Data;

@Data
public class User {

    private String account;

    private String verifyCode;

    private String nickname;

    private String email;

    private String password;

    private String confirmPassword;

    private boolean acceptTerm;

    private int accountType;

    protected User(Builder builder) {
        setAccount(builder.account);
        this.setVerifyCode(builder.verifyCode);
        setNickname(builder.nickname);
        setEmail(builder.email);
        setPassword(builder.password);
        setConfirmPassword(builder.confirmPassword);
        setAcceptTerm(builder.acceptTerm);
        setAccountType(builder.accountType);
    }

    public User() {}

    public static class Builder {

        private String account;

        private String verifyCode;

        private String nickname;

        private String email;

        private String password;

        private String confirmPassword;

        private boolean acceptTerm;

        private int accountType;

        public Builder() {
        }

        public Builder account(String account) {
            this.account = account;
            return this;
        }

        public Builder verifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder confirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public Builder acceptTerm(boolean acceptTerm) {
            this.acceptTerm = acceptTerm;
            return this;
        }

        public Builder accountType(int accountType) {
            this.accountType = accountType;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
