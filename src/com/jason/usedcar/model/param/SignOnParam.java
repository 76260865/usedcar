package com.jason.usedcar.model.param;

import lombok.Data;

@Data
public class SignOnParam extends Param {

    private String phone;

    private String phoneVerifyCode;

    private boolean acceptTerm;

    private String password;

    private String repassword;

    private int accountType;
}
