package com.jason.usedcar.model.result;

import lombok.Data;

@Data
public class LoginResult extends Result {

    private String accessToken;

    private int userId;
}
