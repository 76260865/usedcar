package com.jason.usedcar.model.param;

import lombok.Data;

@Data
public class LoginParam extends Param {

    private String phoneOrEmail;

    private String password;
}
