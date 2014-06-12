package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class SignOnRequest extends Request {

    private String phone;

    private String phoneVerifyCode;

    private boolean acceptTerm;

    private String password;

    private String repassword;

    private int accountType;
}
