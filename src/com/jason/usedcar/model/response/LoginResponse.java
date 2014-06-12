package com.jason.usedcar.model.response;

import lombok.Data;

@Data
public class LoginResponse extends Response {

    private String accessToken;

    private int userId;
}
