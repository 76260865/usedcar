package com.jason.usedcar.model.response;

import lombok.Data;

@Data
public class ResetPasswordByPhoneResponse extends Response {

    private String phone;
}
