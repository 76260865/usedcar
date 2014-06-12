package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class LoginRequest extends Request {

    private String phoneOrEmail;

    private String password;
}
